package com.example.demo.epcc.model;

import com.sun.javafx.collections.MappingChange;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;
import java.util.Map;

/**
 * @Description:网联交易传输对象
 * @Author: changrong.zeng
 * @Date: Created in 13:39 2018/9/4 .
 */
public class EpccTxn implements Serializable {
    private static final long serialVersionUID = -6452901730584883453L;

    /**
     * 交易唯一标识
     */
    private String debitTxnId;

    /**
     * 交易序列号
     */
    private String trxId;

    /**
     * 请求报文编号
     */
    private String msgTp;
    /**
     * 返回报文编号
     */
    private String rspMsgTp;
    /**
     * 请求报文体Body对象
     */
    private Object messageBody;

    /**
     * 加签后的报文总对象
     */
    private String signedReqMessage;

    /**
     * 返回报文体
     */
    private Object rspMessageBody;

    /**
     * 交易类型
     */
    private String trxCtgyCd;

    /**
     * 系统级错误码
     */
    private String sysErrorCode;

    /**
     * 系统级错误信息
     */
    private String sysErrorMessage;

    /**
     * 响应地点
     */
    private String responseQueue;

    /**
     * 业务返回码
     */
    private String bizStsCd;

    /**
     * 业务返回说明
     */
    private String bizStsDesc;

    /**
     * 交易状态
     */
    private String trxStatus;

    /**
     * 一个idc对应一个专线url
     */
    private String idc;

    /**
     * 返回值解析Map
     */
    private Map<String, String> resultMap;

    @Override
    public String toString()
    {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }

    public String getDebitTxnId() {
        return debitTxnId;
    }

    public void setDebitTxnId(String debitTxnId) {
        this.debitTxnId = debitTxnId;
    }

    public String getTrxId() {
        return trxId;
    }

    public void setTrxId(String trxId) {
        this.trxId = trxId;
    }

    public String getMsgTp() {
        return msgTp;
    }

    public void setMsgTp(String msgTp) {
        this.msgTp = msgTp;
    }

    public String getRspMsgTp() {
        return rspMsgTp;
    }

    public void setRspMsgTp(String rspMsgTp) {
        this.rspMsgTp = rspMsgTp;
    }

    public Object getMessageBody() {
        return messageBody;
    }

    public void setMessageBody(Object messageBody) {
        this.messageBody = messageBody;
    }

    public String getSignedReqMessage() {
        return signedReqMessage;
    }

    public void setSignedReqMessage(String signedReqMessage) {
        this.signedReqMessage = signedReqMessage;
    }

    public Object getRspMessageBody() {
        return rspMessageBody;
    }

    public void setRspMessageBody(Object rspMessageBody) {
        this.rspMessageBody = rspMessageBody;
    }

    public String getTrxCtgyCd() {
        return trxCtgyCd;
    }

    public void setTrxCtgyCd(String trxCtgyCd) {
        this.trxCtgyCd = trxCtgyCd;
    }

    public String getSysErrorCode() {
        return sysErrorCode;
    }

    public void setSysErrorCode(String sysErrorCode) {
        this.sysErrorCode = sysErrorCode;
    }

    public String getSysErrorMessage() {
        return sysErrorMessage;
    }

    public void setSysErrorMessage(String sysErrorMessage) {
        this.sysErrorMessage = sysErrorMessage;
    }

    public String getResponseQueue() {
        return responseQueue;
    }

    public void setResponseQueue(String responseQueue) {
        this.responseQueue = responseQueue;
    }

    public String getBizStsCd() {
        return bizStsCd;
    }

    public void setBizStsCd(String bizStsCd) {
        this.bizStsCd = bizStsCd;
    }

    public String getBizStsDesc() {
        return bizStsDesc;
    }

    public void setBizStsDesc(String bizStsDesc) {
        this.bizStsDesc = bizStsDesc;
    }

    public String getTrxStatus() {
        return trxStatus;
    }

    public void setTrxStatus(String trxStatus) {
        this.trxStatus = trxStatus;
    }

    public String getIdc() {
        return idc;
    }

    public void setIdc(String idc) {
        this.idc = idc;
    }

    public Map<String, String> getResultMap() {
        return resultMap;
    }

    public void setResultMap(Map<String, String> resultMap) {
        this.resultMap = resultMap;
    }
}
