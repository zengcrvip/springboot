package com.example.demo.epcc.model;

/**
 * @Description:账号类型
 * @Author: changrong.zeng
 * @Date: Created in 17:14 2018/9/4 .
 */
public enum AccType {

    BUSINESS_ACCT("0100","05", "对公账户"),
    INDIVIDUAL_DEBIT_ACCT("0201","00","个人借记卡账户"),
    INDIVIDUAL_QUASI_CREDIT_ACCT("0202","02", "个人准贷记卡账户"),
    INDIVIDUAL_CREDIT_ACCT("0203","01", "个人贷记卡账户"),
    INDIVIDUAL_DEPOSIT_ACCT("0204","07", "个人存折账户");

    /**
     * 本地-值
     */
    public String kqValue;
    /**
     * 网联-值
     */
    public String bankValue;

    /**
     * 名称
     */
    public String label;

    AccType(String kqValue, String bankValue, String label) {
        this.kqValue = kqValue;
        this.bankValue = bankValue;
        this.label = label;
    }

    /**
     * 根据本地值映射到银行值
     * @param kqValue
     * @return
     */
    public static String getBankValueByKqValue(String kqValue) {
        for (AccType accType : values()) {
            if (accType.kqValue.equals(kqValue)) {
                return accType.bankValue;
            }
        }
        return null;
    }
}
