package com.example.demo.pdf.service.impl;

import com.example.demo.pdf.model.PdfTaskResult;
import com.example.demo.pdf.service.PdfCreateService;
import com.example.demo.pdf.service.PdfManageService;
import com.example.demo.pdf.task.AbstractPdfTask;
import com.example.demo.pdf.task.PdfTask;
import com.example.demo.util.DateUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Date;
import java.util.Map;

import java.util.concurrent.*;

/**
 * @Description: pdf生成服务
 * @Author: changrong.zeng
 * @Date: Created in 11:16 2018/8/17 .
 */
@Service
public class PdfManageServiceImpl implements PdfManageService {

    private static final Logger logger = LogManager.getLogger(PdfManageServiceImpl.class);

    private Map<String, AbstractPdfTask> TASKTABLE = new ConcurrentHashMap<String, AbstractPdfTask>();
    /**
     * 启用线程去生成pdf
     */
    private ExecutorService threadpool = null;

    /**
     * 等待结果的处理队列
     */
    private CompletionService<PdfTaskResult> workPool = null;

    @Autowired
    private PdfCreateService pdfCreateService;

    private String baseDir = "D:/baseDir";
    private String zipDir = "D:/zipDir";
    private String tempDir = "D:/baseDir";

    public PdfManageServiceImpl() {
        int taskNum = 10;
        // 启用一个线程池
        threadpool = Executors.newFixedThreadPool(taskNum);
        workPool = new ExecutorCompletionService<PdfTaskResult>(threadpool);
        // 启动一个后台线程，一直获取workPool的处理结果，并处理
        Thread workPoolFutureGet = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    PdfTaskResult result = null;
                    while (true) {
                        // 阻塞等待
                        result = workPool.take().get();
                        //结果处理  TODO
//                        processVoucherTaskResult(result);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    logger.error("workPool take PdfTaskResult error..", e);
                } catch (ExecutionException e) {
                    e.printStackTrace();
                    logger.error("workPool PdfTaskResult execution error..", e);
                }

            }
        });
        workPoolFutureGet.setName("PdfWorkPoolResultMonitor");
        workPoolFutureGet.start();
    }

    @Override
    public void pdfHandle_async(String appCode,Date date) throws Exception {
        String orderDate = DateUtil.formatDateTime("yyyy-MM-dd", date);
        String taskKey = appCode + "_" + orderDate;
        // 先查找是否有存在的任务，如果有，则判断已经执行的时间是否超过两小时，超过的话，终止任务
        AbstractPdfTask registTask = getTask(taskKey);
        if(registTask != null){
            logger.warn("[PdfManageService] task :" + taskKey + " existed..");
            //7200000单位秒，两小时
            boolean isTimeout = registTask.isExecuteTimeOut(7200000);
            if (isTimeout) {
                // 结束任务
                registTask.taskStop();

            }
            return;
        }

        initUserDir(appCode, orderDate);
        String basePath = generatePath(appCode, orderDate, baseDir);
        String tempPath = generatePath(appCode, orderDate, tempDir);
        AbstractPdfTask task = new PdfTask(appCode,date,basePath,tempPath,pdfCreateService);
        workPool.submit(task);
        registTask(taskKey, task);
    }

    private AbstractPdfTask getTask(String taskKey) {
        return TASKTABLE.get(taskKey);
    }

    /**
     * 获取相应的路径 dir传入的是baseDir,获取basePath; 传入tempDir,获取tempPath;
     * 传入zipDir,获取zipPath;
     */
    private String generatePath(String appCode, String orderDate, String dir) {
        StringBuffer path = new StringBuffer(dir);
        path.append(File.separatorChar).append(appCode)
                .append(File.separatorChar).append(orderDate)
                .append(File.separatorChar);
        logger.info("--: 获取相应路径  | " + path);
        return path.toString();
    }

    /**
     * 初始化用户目录
     *
     * @param appCode
     * @param orderDate
     */
    private void initUserDir(String appCode, String orderDate) {
        String basePath = generatePath(appCode, orderDate, baseDir);
        String zipPath  = generatePath(appCode, orderDate, zipDir);
        String tempPath = generatePath(appCode, orderDate, tempDir);
        String path_array[] = { basePath, zipPath,tempPath };
        logger.info("--: 初始化用户目录  appCode = " + appCode);
        for (String path : path_array) {
            initDir(path);
        }
    }

    private void initDir(String path) {
        try {
            File dir = new File(path);
            if (!dir.exists()){
                dir.mkdirs();
            }
            logger.info("init Dir is finished .|" + path);
        } catch (Exception e) {
            logger.error("create dir occurs error ", e);
            return;
        }
    }

    /**
     * 注册一个任务到任务表，防止重复提交一样的任务，造成CPU栈溢出 taskKey的格式为 appCode+orderDate
     * value是具体的任务
     * @param taskKey
     */
    public void registTask(String taskKey, AbstractPdfTask task) {
        TASKTABLE.put(taskKey, task);
        logger.warn("[PdfManageService] ****** TASKLIST size " + TASKTABLE.size() + " REMAINS :" + TASKTABLE.keySet());
    }
}
