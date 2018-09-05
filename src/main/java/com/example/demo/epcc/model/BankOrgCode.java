package com.example.demo.epcc.model;

/**
 * @Description:bankid与银行机构标识映射
 * @Author: changrong.zeng
 * @Date: Created in 17:11 2018/9/4 .
 */
public enum BankOrgCode {

    ICBC_BANK("ICBC","C1010211000012", "1001**************190","工商银行"),
    CCB_BANK("CCB","C1010511003703", "1001**************190","建设银行"),
    BOC_BANK("BOC","C1010411000013", "1001**************190","中国银行"),
    BCOM_BANK("BCOM","C1030131001288", "1001**************190","交通银行"),
    CMB_BANK("CMB","C1030811000498","1001**************190",  "招行银行"),
    CITIC_BANK("CITIC", "C1051444000016","1001**************190",    "中信银行"),
    PAB_BANK("PAB","C1030744001296","1001**************190",  "平安银行"),
    HXB_BANK("HXB","C1030411000431","1001**************190",  "华夏银行"),
    Test("TEST","C1010611003601","991584000049",  "模拟银行");

    /**
     * bankid-值
     */
    public String bankid;
    /**
     * 银行机构标志-值
     */
    public String bankOrgCode;
    /**
     * 支付机构在银行的备付金账号-值
     */
    public String depositAcct;

    /**
     * 银行名称
     */
    public String label;

    /**
     * 备付金名称
     */
    public static String depositAcctNm="******备付金";

    BankOrgCode(String kqValue, String bankValue,String depositAcct, String label) {
        this.bankid = kqValue;
        this.bankOrgCode = bankValue;
        this.label = label;
        this.depositAcct = depositAcct;
    }

    /**
     * 根据bankid值映射到本地在各银行开户的备付金账户信息值
     * @param bankid
     * @return
     */
    public static String getDepositAcctInfoBybankid(String bankid) {
        for (BankOrgCode bankOrgCode : values()) {
            if (bankOrgCode.bankid.equals(bankid)) {
                return bankOrgCode.depositAcct+"_"+depositAcctNm+"_"+bankOrgCode.bankOrgCode;
            }
        }
        return null;
    }

    /**
     * 根据bankid值映射到银行机构标识值
     * @param bankid
     * @return
     */
    public static String getDepositAcctBybankid(String bankid) {
        for (BankOrgCode bankOrgCode : values()) {
            if (bankOrgCode.bankid.equals(bankid)) {
                return bankOrgCode.depositAcct;
            }
        }
        return null;
    }

    /**
     * 根据bankid值映射到银行机构标识值
     * @param bankid
     * @return
     */
    public static String getBankOrgCodeBybankid(String bankid) {
        for (BankOrgCode bankOrgCode : values()) {
            if (bankOrgCode.bankid.equals(bankid)) {
                return bankOrgCode.bankOrgCode;
            }
        }
        return null;
    }
}
