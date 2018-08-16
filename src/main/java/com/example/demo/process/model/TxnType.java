package com.example.demo.process.model;

/**
 * @Description:付款类型枚举
 * @Author: changrong.zeng
 * @Date: Created in 12:50 2018/8/16 .
 */
public enum TxnType {
    PAY_2_BANK(925,"付款到银行类型"),
    WITHDRAW(920,"提现类型");


    private final Integer code;
    private final String desc;

    TxnType(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public Integer getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
