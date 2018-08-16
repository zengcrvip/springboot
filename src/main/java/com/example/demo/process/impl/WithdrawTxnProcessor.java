package com.example.demo.process.impl;

import com.example.demo.process.model.PayTxn;
import com.example.demo.process.model.TxnType;
import org.springframework.stereotype.Service;

/**
 * @Description:提现处理器
 * @Author: changrong.zeng
 * @Date: Created in 12:36 2018/8/16 .
 */
@Service
public class WithdrawTxnProcessor extends AbstractTxnProcessor {

    @Override
    public boolean isSupports(PayTxn txn) {
        return TxnType.WITHDRAW.getCode().equals(txn.getTxnType());
    }
}
