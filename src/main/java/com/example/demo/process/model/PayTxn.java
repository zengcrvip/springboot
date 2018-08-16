package com.example.demo.process.model;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description:付款实体对象
 * @Author: changrong.zeng
 * @Date: Created in 12:42 2018/8/16 .
 */
public class PayTxn implements Serializable {


    private static final long serialVersionUID = -4671357736015211145L;

    // 商户请求ID
    private String merchantBizId;

    // 发起方流水号
    private String submitSerialNo;

    // 付款方会员号
    private Long membercode;

    // 付款方姓名
    private String payerName;

    // 发起方
    private Long submitMemberAcctcode;

    // 请求时间
    private Date requestDate;

    // 请求金额
    private Long amount;

    // 收款方户名
    private String payeeName;

    // 收款方账号
    private String bankAcctNo;

    // 收款方机构代码
    private Integer payeeOrgCode;

    // 开户行
    private String branchBankName;

    private String province;
    // 省份代码
    private Long provinceCode;

    private String city;

    // 城市代码
    private Long cityCode;

    // 费用作用方 0 收款方付费 1付款方付费
    private Integer feeaction;

    // 付款方币种
    private String payerCurrencyCode;

    // 收款方币种
    private String payeeCurrencyCode;

    // 费用
    private Long fee;

    // 收款方联系电话
    private String payeeTel;

    // 收款方e-mail
    private String payeeEmail;

    // 附言
    private String payeeNotes;

    // 备注
    private String remark;

    // 银行备注
    private String toBankMemo;

    private String toBankPurpose;

    // 交易类型
    private Integer txnType;

    // 终端类型
    private Integer terminalTypeCode;

    // 业务类型
    private Integer withdrawType;

    // 计费模式:2-实时收费 5-预收 6-后收 7-后返
    private Integer feeType;

    public String getMerchantBizId() {
        return merchantBizId;
    }

    public void setMerchantBizId(String merchantBizId) {
        this.merchantBizId = merchantBizId;
    }

    public String getSubmitSerialNo() {
        return submitSerialNo;
    }

    public void setSubmitSerialNo(String submitSerialNo) {
        this.submitSerialNo = submitSerialNo;
    }

    public Long getMembercode() {
        return membercode;
    }

    public void setMembercode(Long membercode) {
        this.membercode = membercode;
    }

    public String getPayerName() {
        return payerName;
    }

    public void setPayerName(String payerName) {
        this.payerName = payerName;
    }

    public Long getSubmitMemberAcctcode() {
        return submitMemberAcctcode;
    }

    public void setSubmitMemberAcctcode(Long submitMemberAcctcode) {
        this.submitMemberAcctcode = submitMemberAcctcode;
    }

    public Date getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(Date requestDate) {
        this.requestDate = requestDate;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public String getPayeeName() {
        return payeeName;
    }

    public void setPayeeName(String payeeName) {
        this.payeeName = payeeName;
    }

    public String getBankAcctNo() {
        return bankAcctNo;
    }

    public void setBankAcctNo(String bankAcctNo) {
        this.bankAcctNo = bankAcctNo;
    }

    public Integer getPayeeOrgCode() {
        return payeeOrgCode;
    }

    public void setPayeeOrgCode(Integer payeeOrgCode) {
        this.payeeOrgCode = payeeOrgCode;
    }

    public String getBranchBankName() {
        return branchBankName;
    }

    public void setBranchBankName(String branchBankName) {
        this.branchBankName = branchBankName;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public Long getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(Long provinceCode) {
        this.provinceCode = provinceCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Long getCityCode() {
        return cityCode;
    }

    public void setCityCode(Long cityCode) {
        this.cityCode = cityCode;
    }

    public Integer getFeeaction() {
        return feeaction;
    }

    public void setFeeaction(Integer feeaction) {
        this.feeaction = feeaction;
    }

    public String getPayerCurrencyCode() {
        return payerCurrencyCode;
    }

    public void setPayerCurrencyCode(String payerCurrencyCode) {
        this.payerCurrencyCode = payerCurrencyCode;
    }

    public String getPayeeCurrencyCode() {
        return payeeCurrencyCode;
    }

    public void setPayeeCurrencyCode(String payeeCurrencyCode) {
        this.payeeCurrencyCode = payeeCurrencyCode;
    }

    public Long getFee() {
        return fee;
    }

    public void setFee(Long fee) {
        this.fee = fee;
    }

    public String getPayeeTel() {
        return payeeTel;
    }

    public void setPayeeTel(String payeeTel) {
        this.payeeTel = payeeTel;
    }

    public String getPayeeEmail() {
        return payeeEmail;
    }

    public void setPayeeEmail(String payeeEmail) {
        this.payeeEmail = payeeEmail;
    }

    public String getPayeeNotes() {
        return payeeNotes;
    }

    public void setPayeeNotes(String payeeNotes) {
        this.payeeNotes = payeeNotes;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getToBankMemo() {
        return toBankMemo;
    }

    public void setToBankMemo(String toBankMemo) {
        this.toBankMemo = toBankMemo;
    }

    public String getToBankPurpose() {
        return toBankPurpose;
    }

    public void setToBankPurpose(String toBankPurpose) {
        this.toBankPurpose = toBankPurpose;
    }

    public Integer getTxnType() {
        return txnType;
    }

    public void setTxnType(Integer txnType) {
        this.txnType = txnType;
    }

    public Integer getTerminalTypeCode() {
        return terminalTypeCode;
    }

    public void setTerminalTypeCode(Integer terminalTypeCode) {
        this.terminalTypeCode = terminalTypeCode;
    }

    public Integer getWithdrawType() {
        return withdrawType;
    }

    public void setWithdrawType(Integer withdrawType) {
        this.withdrawType = withdrawType;
    }

    public Integer getFeeType() {
        return feeType;
    }

    public void setFeeType(Integer feeType) {
        this.feeType = feeType;
    }

    @Override
    public String toString() {
        return "PayTxn{" +
                "merchantBizId='" + merchantBizId + '\'' +
                ", submitSerialNo='" + submitSerialNo + '\'' +
                ", membercode=" + membercode +
                ", payerName='" + payerName + '\'' +
                ", submitMemberAcctcode=" + submitMemberAcctcode +
                ", requestDate=" + requestDate +
                ", amount=" + amount +
                ", payeeName='" + payeeName + '\'' +
                ", bankAcctNo='" + bankAcctNo + '\'' +
                ", payeeOrgCode=" + payeeOrgCode +
                ", branchBankName='" + branchBankName + '\'' +
                ", province='" + province + '\'' +
                ", provinceCode=" + provinceCode +
                ", city='" + city + '\'' +
                ", cityCode=" + cityCode +
                ", feeaction=" + feeaction +
                ", payerCurrencyCode='" + payerCurrencyCode + '\'' +
                ", payeeCurrencyCode='" + payeeCurrencyCode + '\'' +
                ", fee=" + fee +
                ", payeeTel='" + payeeTel + '\'' +
                ", payeeEmail='" + payeeEmail + '\'' +
                ", payeeNotes='" + payeeNotes + '\'' +
                ", remark='" + remark + '\'' +
                ", toBankMemo='" + toBankMemo + '\'' +
                ", toBankPurpose='" + toBankPurpose + '\'' +
                ", txnType=" + txnType +
                ", terminalTypeCode=" + terminalTypeCode +
                ", withdrawType=" + withdrawType +
                ", feeType=" + feeType +
                '}';
    }
}
