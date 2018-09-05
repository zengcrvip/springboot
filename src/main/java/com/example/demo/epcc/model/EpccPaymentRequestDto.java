package com.example.demo.epcc.model;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * @Description:
 * @Author: changrong.zeng
 * @Date: Created in 11:24 2018/9/4 .
 */
public class EpccPaymentRequestDto {

    private String requestBatchId;
    private String bankId;
    private String accType;
    private String sgnNo;
    private String pyeeAcctId;
    private String pyeeNm;
    private String pyeeAcctTp;
    private String pyeeCtryNo;
    private String trxAmt;
    private String trxCtgy;
    private String bizTp;
    private String ordrId;
    private String merchantQuantity;
    private String merchantRealQuantity;
    private String merchantAbbreviation;
    private String merchantCode;
    private String merchantType;
    private String merchantIdType;
    private String merchantIdCode;
    private String merchantMcc;
    private String productQuantity;
    private String productRealQuantity;
    private String productAbbreviation;
    private String productAmount;
    private String productCount;
    private String pltfrmNm;

    public EpccPaymentRequestDto() {
    }

    @Override
    public String toString()
    {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }

    public String getRequestBatchId() {
        return requestBatchId;
    }

    public void setRequestBatchId(String requestBatchId) {
        this.requestBatchId = requestBatchId;
    }

    public String getBankId() {
        return bankId;
    }

    public void setBankId(String bankId) {
        this.bankId = bankId;
    }

    public String getAccType() {
        return accType;
    }

    public void setAccType(String accType) {
        this.accType = accType;
    }

    public String getSgnNo() {
        return sgnNo;
    }

    public void setSgnNo(String sgnNo) {
        this.sgnNo = sgnNo;
    }

    public String getPyeeAcctId() {
        return pyeeAcctId;
    }

    public void setPyeeAcctId(String pyeeAcctId) {
        this.pyeeAcctId = pyeeAcctId;
    }

    public String getPyeeNm() {
        return pyeeNm;
    }

    public void setPyeeNm(String pyeeNm) {
        this.pyeeNm = pyeeNm;
    }

    public String getPyeeAcctTp() {
        return pyeeAcctTp;
    }

    public void setPyeeAcctTp(String pyeeAcctTp) {
        this.pyeeAcctTp = pyeeAcctTp;
    }

    public String getPyeeCtryNo() {
        return pyeeCtryNo;
    }

    public void setPyeeCtryNo(String pyeeCtryNo) {
        this.pyeeCtryNo = pyeeCtryNo;
    }

    public String getTrxAmt() {
        return trxAmt;
    }

    public void setTrxAmt(String trxAmt) {
        this.trxAmt = trxAmt;
    }

    public String getTrxCtgy() {
        return trxCtgy;
    }

    public void setTrxCtgy(String trxCtgy) {
        this.trxCtgy = trxCtgy;
    }

    public String getBizTp() {
        return bizTp;
    }

    public void setBizTp(String bizTp) {
        this.bizTp = bizTp;
    }

    public String getOrdrId() {
        return ordrId;
    }

    public void setOrdrId(String ordrId) {
        this.ordrId = ordrId;
    }

    public String getMerchantQuantity() {
        return merchantQuantity;
    }

    public void setMerchantQuantity(String merchantQuantity) {
        this.merchantQuantity = merchantQuantity;
    }

    public String getMerchantRealQuantity() {
        return merchantRealQuantity;
    }

    public void setMerchantRealQuantity(String merchantRealQuantity) {
        this.merchantRealQuantity = merchantRealQuantity;
    }

    public String getMerchantAbbreviation() {
        return merchantAbbreviation;
    }

    public void setMerchantAbbreviation(String merchantAbbreviation) {
        this.merchantAbbreviation = merchantAbbreviation;
    }

    public String getMerchantCode() {
        return merchantCode;
    }

    public void setMerchantCode(String merchantCode) {
        this.merchantCode = merchantCode;
    }

    public String getMerchantType() {
        return merchantType;
    }

    public void setMerchantType(String merchantType) {
        this.merchantType = merchantType;
    }

    public String getMerchantIdType() {
        return merchantIdType;
    }

    public void setMerchantIdType(String merchantIdType) {
        this.merchantIdType = merchantIdType;
    }

    public String getMerchantIdCode() {
        return merchantIdCode;
    }

    public void setMerchantIdCode(String merchantIdCode) {
        this.merchantIdCode = merchantIdCode;
    }

    public String getMerchantMcc() {
        return merchantMcc;
    }

    public void setMerchantMcc(String merchantMcc) {
        this.merchantMcc = merchantMcc;
    }

    public String getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(String productQuantity) {
        this.productQuantity = productQuantity;
    }

    public String getProductRealQuantity() {
        return productRealQuantity;
    }

    public void setProductRealQuantity(String productRealQuantity) {
        this.productRealQuantity = productRealQuantity;
    }

    public String getProductAbbreviation() {
        return productAbbreviation;
    }

    public void setProductAbbreviation(String productAbbreviation) {
        this.productAbbreviation = productAbbreviation;
    }

    public String getProductAmount() {
        return productAmount;
    }

    public void setProductAmount(String productAmount) {
        this.productAmount = productAmount;
    }

    public String getProductCount() {
        return productCount;
    }

    public void setProductCount(String productCount) {
        this.productCount = productCount;
    }

    public String getPltfrmNm() {
        return pltfrmNm;
    }

    public void setPltfrmNm(String pltfrmNm) {
        this.pltfrmNm = pltfrmNm;
    }
}
