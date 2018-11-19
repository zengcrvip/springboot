package com.example.demo.exception;

import enums.ResponseCode;

/**
 * @Description:
 * @Author: changrong.zeng
 * @Date: Created in 下午7:10 2018/11/19 .
 */
public class ServiceException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    private String code;
    private String msg;

    public ServiceException(String code, String msg) {
        super(code + ":" + msg);
        this.code = code;
        this.msg = msg;
    }

    public ServiceException(String code, String msg, Throwable cause) {
        super(code + ":" + msg, cause);
        this.code = code;
        this.msg = msg;
    }

    public ServiceException(ResponseCode responseCode) {
        super(responseCode.getCode() + ":" + responseCode.getMsg());
        this.code = responseCode.getCode();
        this.msg = responseCode.getMsg();
    }

    public ServiceException(ResponseCode responseCode, Throwable cause) {
        super(responseCode.getCode() + ":" + responseCode.getMsg(), cause);
        this.code = responseCode.getCode();
        this.msg = responseCode.getMsg();
    }

    public String getMsg() {
        return this.msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
