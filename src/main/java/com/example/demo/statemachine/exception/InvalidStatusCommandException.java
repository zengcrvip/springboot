package com.example.demo.statemachine.exception;
/**
 * @Description:
 * @Author: changrong.zeng
 * @Date: Created in 19:48 2018/9/6 .
 */
public class InvalidStatusCommandException extends AppException {

    public InvalidStatusCommandException()
    {
        super("2001", ErrorLevel.ERROR);
    }

    public InvalidStatusCommandException(Object arguments[])
    {
        super("2001", ErrorLevel.ERROR, arguments);
    }

    public InvalidStatusCommandException(Throwable cause)
    {
        super("2001", cause, ErrorLevel.ERROR);
    }

    private static final long serialVersionUID = 5831959292779913504L;

}
