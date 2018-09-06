package com.example.demo.statemachine.exception;

/**
 * @Description:
 * @Author: changrong.zeng
 * @Date: Created in 19:50 2018/9/6 .
 */
public class AbstractException extends Exception {

    public AbstractException()
    {
    }

    public AbstractException(String message)
    {
        super(message);
    }

    public AbstractException(Throwable cause)
    {
        super(cause);
    }

    public AbstractException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public AbstractException(String message, ErrorLevel errorLevel)
    {
        super(message);
        this.errorLevel = errorLevel;
    }

    public AbstractException(String message, ErrorLevel errorLevel, Object arguments[])
    {
        super(message);
        this.errorLevel = errorLevel;
        this.arguments = arguments;
    }

    public AbstractException(String message, Throwable cause, ErrorLevel errorLevel)
    {
        super(message, cause);
        this.errorLevel = errorLevel;
    }

    public ErrorLevel getErrorLevel()
    {
        return errorLevel;
    }

    public Object[] getArguments()
    {
        return arguments;
    }

    public void setArguments(Object arguments[])
    {
        this.arguments = arguments;
    }

    private static final long serialVersionUID = 1L;
    protected ErrorLevel errorLevel;
    protected Object arguments[];
}
