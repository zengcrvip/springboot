package com.example.demo.processor;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

/**
 * @Description:单元处理器基类
 * @Author: changrong.zeng
 * @Date: Created in 18:00 2018/8/16 .
 */
@Service
public abstract class ProcessorUnit<T> implements IProcessor<T>{
    private static final Logger log = LogManager.getLogger(ProcessorUnit.class);

    @Override
    public boolean process(T ctx) throws AppBizException {
        log.info("entry " + this.getClass().getName());
        try {
            doCheck(ctx);
            return doProcess(ctx);
        } catch (AppBizException e) {
            dealException(ctx, e);
            throw e;
        }
    }

    /**
     * 前置检查器
     * @param ctx
     * @throws AppBizException
     */
    protected void doCheck(T ctx) throws AppBizException {

    }

    /**
     * 子类覆盖此方法，执行具体的异常处理
     * @param ctx
     * @param e
     */
    protected void dealException(T ctx, AppBizException e) {
        log.error("process app exception", e);
    }

    /**
     * 子类覆盖此方法，执行具体的业务逻辑
     * @param ctx
     * @return
     * @throws AppBizException
     */
    abstract protected boolean doProcess(T ctx) throws AppBizException;


}
