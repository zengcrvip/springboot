package com.example.demo.epcc.model;

/**
 * @Description:商户类型
 * @Author: changrong.zeng
 * @Date: Created in 17:21 2018/9/4 .
 */
public enum  MerchantTp {

    PERSONAL("1", "04", "个人"),
    Other("", "01", "企业");

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

    /**
     * 构造方法
     * @param label
     *            名称
     */
    MerchantTp(String kqValue, String bankValue, String label) {
        this.kqValue = kqValue;
        this.bankValue = bankValue;
        this.label = label;
    }

    /**
     * 根据快钱值获取银行值
     * @param kqValue
     * @return
     */
    public static String getBankValueByKqValue(String kqValue) {
        for (MerchantTp type : values()) {
            if (type.kqValue.trim().equals(kqValue)) {
                return type.bankValue;
            }
        }
        return Other.bankValue;
    }
}
