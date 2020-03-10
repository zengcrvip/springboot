package com.example.demo.model.adapter;

/**
 * @Author changrong.zeng
 * @Description
 * @Date 11:25 2020-03-06
 **/
public class Wrapper implements Targetable{

    private Source source;

    public Wrapper(Source source) {
        this.source = source;
    }

    @Override
    public void method1() {
       source.method1();
    }

    @Override
    public void method2() {
        System.out.println("this is method2");
    }
}
