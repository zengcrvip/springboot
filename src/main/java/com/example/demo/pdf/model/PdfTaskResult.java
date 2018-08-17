package com.example.demo.pdf.model;

import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Description: pdf返回结果
 * @Author: changrong.zeng
 * @Date: Created in 11:19 2018/8/17 .
 */
public class PdfTaskResult {
    /**
     * 处理成功的笔数
     */
    private AtomicInteger successCount = new AtomicInteger(0);
    /**
      *总笔数
     */
    private AtomicInteger totalCount =new AtomicInteger(0) ;

    private Date orderDate;
    private String  appCode;
    //错误信息
    private String errorMsg;

    public void addTotalCount(int count){
        this.totalCount.addAndGet(count);
    }

    public void addSuccessCount(){
        this.successCount.incrementAndGet();
    }

    public AtomicInteger getSuccessCount() {
        return successCount;
    }

    public AtomicInteger getTotalCount() {
        return totalCount;
    }


    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public String getAppCode() {
        return appCode;
    }

    public void setAppCode(String appCode) {
        this.appCode = appCode;
    }

    public void setSuccessCount(AtomicInteger successCount) {
        this.successCount = successCount;
    }

    public void setTotalCount(AtomicInteger totalCount) {
        this.totalCount = totalCount;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
