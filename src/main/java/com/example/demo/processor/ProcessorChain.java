package com.example.demo.processor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description:
 * @Author: changrong.zeng
 * @Date: Created in 19:53 2018/8/16 .
 */
@Service
public class ProcessorChain<T> implements IProcessor<T> {

    /**
     * 业务处理器列表
     */
    @Autowired
    private List<IProcessor<T>> processors;

    /**
     * 外层继续,用在子流程跳过,外流程继续
     */
    private boolean outerContinue = false;

    @Override
    public boolean process(T ctx) throws AppBizException {
        boolean isContinue = true;
        if (processors != null) {
            for (IProcessor<T> p : processors) {
                isContinue = p.process(ctx);
                if (!isContinue) {
                    break;
                }
            }
        }

        if (this.outerContinue) {
            return true;
        }

        return isContinue;
    }

    public void setProcessors(List<IProcessor<T>> processors) {
        this.processors = processors;
    }

    public void setOuterContinue(boolean outerContinue) {
        this.outerContinue = outerContinue;
    }
}
