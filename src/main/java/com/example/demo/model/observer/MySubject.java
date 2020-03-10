package com.example.demo.model.observer;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author changrong.zeng
 * @Description
 * @Date 10:31 2020-03-09
 **/
public class MySubject implements Subject {

    private List<Observer> observers = new ArrayList<Observer>();

    @Override
    public void add(Observer observer) {
        observers.add(observer);

    }

    @Override
    public void del(Observer observer) {
        observers.remove(observer);

    }

    @Override
    public void notifyObservers() {
        for(Observer observer : observers){
            observer.update();
        }

    }

    @Override
    public void operation() {
        System.out.println("this is my subject");
        notifyObservers();
    }
}
