package com.example.demo.model.factory;

/**
 * @Author changrong.zeng
 * @Description
 * @Date 16:38 2020-03-05
 **/
public class MailProviderFactory implements Provider {

    @Override
    public Sender produce() {
        return new MailSender();
    }
}
