package com.example.demo.process.impl;

import com.example.demo.process.TxnProcessor;
import com.example.demo.process.model.PayTxn;
import com.example.demo.process.model.PayTxnResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

/**
 * @Description:核心处理器
 * @Author: changrong.zeng
 * @Date: Created in 12:35 2018/8/16 .
 */
@Service
public abstract class AbstractTxnProcessor implements TxnProcessor {
    private static final Logger log = LogManager.getLogger(AbstractTxnProcessor.class);

    @Override
    public PayTxnResponse handle(PayTxn txn) {
        log.info("AbstractTxnProcessor handle");
        return null;
    }
}
