package com.example.demo.epcc.model;

/**
 * @Description:
 * @Author: changrong.zeng
 * @Date: Created in 13:44 2018/9/4 .
 */
public class AppBizException extends Exception {
    private static final long serialVersionUID = -5487680337639118270L;

    /**
     * 应用异常码
     */
    private String code;

    public AppBizException() {
    }

    public AppBizException(String code, String msg) {
        super(code + ": " + msg);
        this.code = code;
    }

    public AppBizException(String code, String msg, Throwable cause) {
        super(code + ": " + msg, cause);
        this.code = code;
    }

    public AppBizException(Throwable cause) {
        super(cause);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String string) {
        code = string;
    }
}
