package com.example.demo.pdf.task;

import com.example.demo.pdf.model.PdfTaskResult;
import com.example.demo.pdf.service.PdfCreateService;
import com.example.demo.util.StringUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;
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
    private PdfCreateService pdfCreateService;


    public PdfTask() {
    }

    public PdfTask(String appCode,Date date,String basePath, String tmpPath,PdfCreateService pdfCreateService) {
        this.pdfCreateService = pdfCreateService;
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
                + taskResult.getTotalCount().get() + "],[Success:" + taskResult.getSuccessCount().get()
                + "] at " + System.currentTimeMillis());
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
        List<Map<String,Object>> data = loadData(page);

        try {
            while (data != null && data.size() > 0 && !isStop){
                boolean flag =  createPdf(basePath, tmpPath, data);
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
        logger.info("[PdfTask]  execute task complete [appCode:" + appCode + "],[orderDate:" + formatedOrderDate + "] at " + System.currentTimeMillis());
        return taskResult;

    }

    private boolean createPdf(String basePath, String tempPath, List<Map<String,Object>> list) {
        logger.info("[VoucherTask] createPdf [appCode:" + appCode + "],[orderDate:" + formatedOrderDate + "] starting at " + System.currentTimeMillis());

        // 创建Voucher的时候的标识，只要有失败，则全部失败
        boolean procFlag = true;
        for (int i = 0; i < list.size(); i++) {
            Map map = list.get(i);
            Long sequenceid = null;
            try {
                // 生成pdf文件
                sequenceid = Long.parseLong(map.get("SEQUENCEID").toString());
                pdfCreateService.createVoucher(map, tempPath, basePath);
                // 成功一个，加一个
                taskResult.addSuccessCount();
            } catch (Exception e) {
                logger.error("[VoucherTask FO] [Genarate id= " + sequenceid
                        + " PDF voucher file occurs error] ", e);
                // 如果有一个生成失败，立即退出，后续全部重新生成
                procFlag = false;
                break;
            }
        }
        logger.info("[VoucherTask FO] foCreateVouchers [appCode:" + appCode
                + "],[orderDate:" + formatedOrderDate + "] end at "
                + System.currentTimeMillis());
        return procFlag;

    }

    /**
     * 模拟dao层获取数据
     * @return
     */
    private List<Map<String,Object>> loadData(int page){
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("isNewVoucher",false);
        map.put("SEQUENCEID",123455);
        map.put("ORDERID","ORDERID123456");
        map.put("PAYER_NAME","张三");
        map.put("BANKACCTID","6225768710691070");
        map.put("PAYEE_NAME","曾跑跑");
        map.put("PAYER_IDC","PAYER_IDC1234");
        map.put("strOrderAmount","伍拾圆整");
        map.put("ORDERAMOUNT","50.00");
        map.put("strOrderAmount","伍拾圆整");
        map.put("strPayerFee","叁圆伍角");
        map.put("payFee","3.50");
        map.put("strTotalAmount","伍拾圆整");
        map.put("totalAmount","50.00");
        map.put("MEMBERACCTNAME","代付");
        map.put("ORDERTYPENAME","付款到银行账户");
        map.put("ORDERTIME",new Date());
        map.put("MEMO","我是备注");

        List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
        if(page < 6){
            list.add(map);
        }
        return list;
    }
}
