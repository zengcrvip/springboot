package com.example.demo.processor.model;

/**
 * @Description:
 * @Author: changrong.zeng
 * @Date: Created in 19:31 2018/8/16 .
 */
public enum TaisConstants {
    WORK_START("START","开始节点"),
    WORK_END("END","结束节点"),
    PROCESS_FAILED("FAILED","处理失败"),
    PROCESS_TIMEOUT("TIMEOUT","处理超时"),
    PROCESS_WAIT("WAIT","处理等待"),
    PROCESS_CONTINUE("CONTINUE","继续处理"),
    PROCESS_END("END","结束处理"),
    PROCESS_SUCCESS("SUCCESS","成功");


    private final String code;
    private final String msg;

    private TaisConstants(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
