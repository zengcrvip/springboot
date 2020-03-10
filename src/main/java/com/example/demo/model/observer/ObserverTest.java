package com.example.demo.model.observer;

/**
 * @Author changrong.zeng
 * @Description
 * @Date 10:37 2020-03-09
 **/
public class ObserverTest {

    public static void main(String[] args) {
        MySubject mySubject = new MySubject();
        mySubject.add(new Observer1());
        mySubject.add(new Observer2());

        mySubject.operation();
    }
}
