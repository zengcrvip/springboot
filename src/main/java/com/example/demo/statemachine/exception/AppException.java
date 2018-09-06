package com.example.demo.statemachine.exception;

/**
 * @Description:
 * @Author: changrong.zeng
 * @Date: Created in 19:51 2018/9/6 .
 */
public class AppException extends AbstractException {
    public AppException()
    {
    }

    public AppException(String message)
    {
        super(message);
    }

    public AppException(Throwable cause)
    {
        super(cause);
    }

    public AppException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public AppException(String message, ErrorLevel errorLevel)
    {
        super(message);
        this.errorLevel = errorLevel;
    }

    public AppException(String message, ErrorLevel errorLevel, Object arguments[])
    {
        super(message);
        this.errorLevel = errorLevel;
        this.arguments = arguments;
    }

    public AppException(String message, Throwable cause, ErrorLevel errorLevel)
    {
        super(message, cause);
        this.errorLevel = errorLevel;
    }

    private static final long serialVersionUID = 8612329288169174939L;

}
