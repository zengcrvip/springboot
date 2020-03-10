package com.example.demo.model.observer;

/**
 * @Author changrong.zeng
 * @Description
 * @Date 17:26 2020-03-06
 **/
public class Observer2 implements Observer {

    @Override
    public void update() {
        System.out.println("observer2 is received");
    }
}
