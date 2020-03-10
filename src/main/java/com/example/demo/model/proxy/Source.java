package com.example.demo.model.proxy;

/**
 * @Author changrong.zeng
 * @Description
 * @Date 14:18 2020-03-06
 **/
public class Source implements Sourceable{

    @Override
    public void method() {

        System.out.println("this is source");
    }
}
