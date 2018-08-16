package com.example.demo.controller;

import com.example.demo.process.PayTxnManager;
import com.example.demo.process.TxnProcessor;
import com.example.demo.process.model.PayTxn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description: 付款到到银行入口，调用process包下的逻辑
 * @Author: changrong.zeng
 * @Date: Created in 13:01 2018/8/16 .
 */
@RestController
public class PayController {

    @Autowired
    private PayTxnManager payTxnManager;

    @RequestMapping("/pay")
    public String pay(){
        PayTxn txn = new PayTxn();
        txn.setTxnType(925);
        txn.setMerchantBizId("abc12345");
        TxnProcessor processor =  payTxnManager.getProcessor(txn);
        processor.handle(txn);
        return "success";


    }
}
