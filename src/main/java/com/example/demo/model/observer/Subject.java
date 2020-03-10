package com.example.demo.model.observer;

/**
 * @Author changrong.zeng
 * @Description
 * @Date 17:27 2020-03-06
 **/
public interface Subject {

    void add(Observer observer);

    void del(Observer observer);

    void notifyObservers();


    void operation();
}
