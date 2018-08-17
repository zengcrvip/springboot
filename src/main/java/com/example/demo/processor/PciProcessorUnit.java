package com.example.demo.processor;

import com.example.demo.processor.model.ProcessContext;
import org.springframework.stereotype.Service;

/**
 * @Description:
 * @Author: changrong.zeng
 * @Date: Created in 19:28 2018/8/16 .
 */
@Service
public abstract class PciProcessorUnit extends ProcessorUnit<ProcessContext>{

    /**
     * 处理异常,发生异常时写入返回码
     */
    @Override
    protected void dealException(ProcessContext ctx, AppBizException e) {

    }

    /**
     * 下层实现
     */
    abstract protected boolean doProcess(ProcessContext ctx) throws AppBizException;

    /**
     * 检查输入参数
     */
    @Override
    protected void doCheck(ProcessContext ctx) throws AppBizException {

    }
}
