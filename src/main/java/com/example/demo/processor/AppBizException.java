package com.example.demo.processor;

/**
 * @Description:自定义异常
 * @Author: changrong.zeng
 * @Date: Created in 17:25 2018/8/16 .
 */
public class AppBizException extends Exception {


    private static final long serialVersionUID = 3651414624026128126L;
    private String code;
    private String textMessage;

    public AppBizException() {}

    public AppBizException(String code, String msg) {
        super((new StringBuilder()).append(code).append(": ").append(msg).toString());
        this.code = code;
    }

    public AppBizException(String code, String msg, Throwable cause)
    {
        super((new StringBuilder()).append(code).append(": ").append(msg).toString(), cause);
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTextMessage() {
        return textMessage;
    }

    public void setTextMessage(String textMessage) {
        this.textMessage = textMessage;
    }
}
