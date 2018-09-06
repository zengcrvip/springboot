package com.example.demo.statemachine.exception;

/**
 * @Description:
 * @Author: changrong.zeng
 * @Date: Created in 19:49 2018/9/6 .
 */
public class ErrorLevel {

    private ErrorLevel(String level)
    {
        this.level = level;
    }

    public String toString()
    {
        return level;
    }

    private final String level;
    public static final ErrorLevel WARN = new ErrorLevel("WARN");
    public static final ErrorLevel ERROR = new ErrorLevel("ERROR");
    public static final ErrorLevel FATAL = new ErrorLevel("FATAL");
}
