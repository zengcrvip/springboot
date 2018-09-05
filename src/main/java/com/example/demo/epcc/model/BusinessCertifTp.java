package com.example.demo.epcc.model;

/**
 * @Description:公司证件类型
 * @Author: changrong.zeng
 * @Date: Created in 17:23 2018/9/4 .
 */
public enum BusinessCertifTp {

    BUSINESS_LICENSE("0", "11", "营业执照"),
    NO_BUSINESS_LICENSE("1", "99", "非营业执照"),
    SOCIAL_CREDIT_CODE("2", "11", "统一社会信用代码(三证合一)"),
    PERSON_CERTIFICATE("3", "12", "事业单位法人证书"),
    PRIVATE_NON_CORPORATE("4", "12", "民办非企业单位登记证书"),
    ORGANIZATION("5", "12", "社会团体法人登记证书"),
    Other("6", "99", "其他");

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
    BusinessCertifTp(String kqValue, String bankValue, String label) {
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
        for (BusinessCertifTp type : values()) {
            if (type.kqValue.equals(kqValue)) {
                return type.bankValue;
            }
        }
        return Other.bankValue;
    }
}
