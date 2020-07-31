package com.example.demo.model.singleton.way2;

/**
 * @Author changrong.zeng
 * @Description
 * @Date 17:43 2020-03-05
 **/
public class Singleton {


    private static class SingletonFactory{
        private static Singleton instance = new Singleton();
    }

    private Singleton(){

    }

    public static Singleton getInstance(){
        return SingletonFactory.instance;
    }
}



