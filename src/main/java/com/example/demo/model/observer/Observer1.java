package com.example.demo.model.observer;

/**
 * @Author changrong.zeng
 * @Description
 * @Date 17:25 2020-03-06
 **/
public class Observer1 implements Observer {

    @Override
    public void update() {
        System.out.println("observer1 is received");
    }
}
