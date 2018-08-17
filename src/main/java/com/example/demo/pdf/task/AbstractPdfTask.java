package com.example.demo.pdf.task;

import com.example.demo.pdf.model.PdfTaskResult;
import com.example.demo.util.DateUtil;

import java.util.Date;
import java.util.concurrent.Callable;

/**
 * @Description:
 * @Author: changrong.zeng
 * @Date: Created in 11:57 2018/8/17 .
 */
public abstract class AbstractPdfTask implements Callable<PdfTaskResult>{
    protected String appCode;
    protected Date orderDate;
    protected String formatedOrderDate;
    /**
     * pdf保存的位置
      */
    protected String basePath;
    /**
     * 临时目录
      */
    protected String tmpPath;

    /**
     * 任务开始时间
     */
    protected long taskBeginTime = System.currentTimeMillis();

    /**
     * 判断任务是否已经执行超时
     * timeout 为超时时间
     * 返回true为超时，false->不超时
     * @return
     */
    public boolean isExecuteTimeOut(long timeout){
        long currentTime = System.currentTimeMillis();
        if(currentTime - taskBeginTime > timeout){
            return true;
        }
        return false;
    }

    /**
     * 结束任务
     */
    public abstract void taskStop();

    /**
     * 用于回滚当前的操作
     */
    public abstract void rollback();

    public void formatOrderDate(){
        this.formatedOrderDate = DateUtil.formatDateTime("yyyy-MM-dd", orderDate);
    }


}
