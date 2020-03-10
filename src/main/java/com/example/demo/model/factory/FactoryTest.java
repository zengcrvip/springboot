package com.example.demo.model.factory;

/**
 * @Author changrong.zeng
 * @Description
 * @Date 16:40 2020-03-05
 **/
public class FactoryTest {

    public static void main(String[] args){

        Provider provider = new MailProviderFactory();
        Sender produce = provider.produce();
        produce.sender();


    }
}
