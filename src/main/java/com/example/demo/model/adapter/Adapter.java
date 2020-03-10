package com.example.demo.model.adapter;

/**
 * @Author changrong.zeng
 * @Description
 * @Date 11:14 2020-03-06
 **/
public class Adapter extends Source implements Targetable {

    @Override
    public void method2() {
        System.out.println("this is method2");
    }
}
