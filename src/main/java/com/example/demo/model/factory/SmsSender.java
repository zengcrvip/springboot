package com.example.demo.model.factory;

/**
 * @Author changrong.zeng
 * @Description
 * @Date 16:36 2020-03-05
 **/
public class SmsSender implements Sender {

    @Override
    public void sender() {

        System.out.println("send sms");
    }
}
