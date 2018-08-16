package com.example.demo.process;

import com.example.demo.process.model.PayTxn;
import com.example.demo.process.model.PayTxnResponse;

/**
 * @Description:处理器上下文
 * @Author: changrong.zeng
 * @Date: Created in 12:25 2018/8/16 .
 */
public interface TxnProcessor {

    /**
     * 是否支持该处理器
     * @param txn
     * @return
     */
    boolean isSupports(PayTxn txn);

    PayTxnResponse handle(PayTxn txn);

}
