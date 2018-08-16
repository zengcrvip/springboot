package com.example.demo.process.impl;

import com.example.demo.process.model.PayTxn;
import com.example.demo.process.model.TxnType;
import org.springframework.stereotype.Service;

/**
 * @Description:付款到银行处理器
 * @Author: changrong.zeng
 * @Date: Created in 12:35 2018/8/16 .
 */
@Service
public class Pay2bankTxnProcessor extends AbstractTxnProcessor {

    @Override
    public boolean isSupports(PayTxn txn) {
        return TxnType.PAY_2_BANK.getCode().equals(txn.getTxnType());
    }
}
