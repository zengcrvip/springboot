package com.example.demo.processor;

/**
 * @Description:处理器
 * @Author: changrong.zeng
 * @Date: Created in 17:56 2018/8/16 .
 */
public interface IProcessor<T> {

    boolean process(T ctx) throws AppBizException;


}
