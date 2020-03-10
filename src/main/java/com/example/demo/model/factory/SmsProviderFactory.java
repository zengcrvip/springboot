package com.example.demo.model.factory;

/**
 * @Author changrong.zeng
 * @Description
 * @Date 16:39 2020-03-05
 **/
public class SmsProviderFactory implements Provider {

    @Override
    public Sender produce() {
        return new SmsSender();
    }
}
