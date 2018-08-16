package com.example.demo.process;

import com.example.demo.process.model.PayTxn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description:付款处理器管理器
 * @Author: changrong.zeng
 * @Date: Created in 12:38 2018/8/16 .
 */
@Service
public class PayTxnManager {

    @Autowired
    private List<TxnProcessor> txnProcessors;

    public TxnProcessor getProcessor(PayTxn payTxn){
        TxnProcessor processor = null;
        for(TxnProcessor processor1 : txnProcessors){
            if(processor1.isSupports(payTxn)){
                processor = processor1;
                break;
            }
        }
        return processor;
    }
}
