package com.example.demo.epcc.service;

import com.example.demo.epcc.model.EpccPaymentRequestDto;

/**
 * @Description:协议支付申请epcc.201.001.01
 * @Author: changrong.zeng
 * @Date: Created in 11:21 2018/9/4 .
 */
public interface EpccSingleService {
    public abstract void doDebitSingle(EpccPaymentRequestDto epccpaymentrequestdto);
}
