package com.example.demo.model.singleton;

/**
 * @Author changrong.zeng
 * @Description
 * @Date 17:01 2020-03-05
 **/
public class Singleton {

    private static Singleton instance = null;

    private Singleton(){

    }

    public static Singleton getInstance(){
        if(instance == null){
            synchronized (Singleton.class){
                if(instance == null){
                    instance = new Singleton();
                }
            }
        }
        return instance;
    }
}