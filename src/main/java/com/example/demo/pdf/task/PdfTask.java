package com.example.demo.pdf.task;

import com.example.demo.pdf.model.PdfTaskResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Description:pdf生成任务
 * @Author: changrong.zeng
 * @Date: Created in 11:23 2018/8/17 .
 */
public class PdfTask extends AbstractPdfTask{
    private static final Logger logger = LogManager.getLogger(PdfTask.class);
    private volatile boolean isStop = false;
    private PdfTaskResult taskResult = new PdfTaskResult();


    public PdfTask() {
    }

    public PdfTask(String appCode,Date date,String basePath, String tmpPath) {
       this.appCode = appCode;
       this.basePath = basePath;
       this.tmpPath = tmpPath;
       this.orderDate = date;
       this.formatOrderDate();
       logger.info("[PdfTask ] PdfTask [appCode:" + appCode
               + "],[orderDate:" + formatedOrderDate + "],[basePath:" + basePath
               + "],[tempPath," + tmpPath + "] created successfully.");
    }



    @Override
    public void rollback() {
        logger.warn("[PdfTask] execute rollback [appCode:" + appCode + "],[orderDate:" + formatedOrderDate + "] at "+System.currentTimeMillis());
        this.isStop = true;
        this.taskResult.setTotalCount(new AtomicInteger(9999999));
        this.taskResult.setSuccessCount(new AtomicInteger(0));
    }


    @Override
    public void taskStop() {
        logger.error("[PdfTask] execute TASKSTOP [appCode:" + appCode + "],[orderDate:" + formatedOrderDate + "] executes recores [Total:"
                + taskResult.getTotalCount().get()+"],[Success:"+taskResult.getSuccessCount().get()
                +"] at "+System.currentTimeMillis());
        this.taskResult.setErrorMsg("TASK execute TIMEOUT.");
        this.isStop = true;
        rollback();
    }

    @Override
    public PdfTaskResult call() throws Exception {
        logger.info("[PdfTask] starting execute task [appCode:" + appCode + "],[orderDate:" + formatedOrderDate + "] at " + System.currentTimeMillis());

        taskResult.setAppCode(appCode);
        taskResult.setOrderDate(orderDate);

        //模拟数据分批处理的情况
        int page = 1;
        List<String> data = loadData(page);

        try {
            while (data != null && data.size() > 0 && !isStop){
                boolean flag = false;// TODO createPdf(basePath, tmpPath, data);
                if (!flag) {
                    // 如果有处理失败的情况，则全部失败
                    taskResult.setSuccessCount(new AtomicInteger(0));
                    logger.error("[PdfTask]  execute task failed [appCode:" + appCode + "],[orderDate:" + formatedOrderDate + "] at "+System.currentTimeMillis());
                    // 退出循环，不再执行下面的步骤
                    return taskResult;
                }
                page ++;
                loadData(page);
            }
        } catch (Exception e) {
            logger.error("[PdfTask] execute task exception [appCode:" + appCode + "],[orderDate:" + formatedOrderDate + "] at "+System.currentTimeMillis(),e);
            taskResult.setTotalCount(new AtomicInteger(9999999));
            taskResult.setSuccessCount(new AtomicInteger(0));
            taskResult.setErrorMsg(e.getMessage());
        }
        logger.info("[PdfTask]  execute task complete [appCode:" + appCode + "],[orderDate:" + formatedOrderDate + "] at "+System.currentTimeMillis());
        return taskResult;

    }

    /**
     * 模拟dao层获取数据
     * @return
     */
    private List<String> loadData(int page){
        List<String> list = new ArrayList<String>();
        if(page < 6){
            list.add("zhangsan");
            list.add("lisi");
            list.add("wangsu");
            list.add("zhangsan2222");
            list.add("zalhe");
        }
        return list;
    }
}
