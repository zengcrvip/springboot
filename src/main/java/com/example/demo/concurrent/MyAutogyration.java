package com.example.demo.concurrent;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @Author changrong.zeng
 * @Description 自写一个自旋锁
 * @Date 17:56 2020-03-25
 **/
public class MyAutogyration {

    public AtomicReference<Thread> atomicReference = new AtomicReference<>();


    // 加锁
    public void lock(){
        Thread thread = Thread.currentThread();
        System.out.println(thread.getName() + "\t is come in ");
        while(!atomicReference.compareAndSet(null,thread)){
            //当前锁没人拿，获取到锁后才退出
        }
    }

    // 解锁
    public void unLock(){
        Thread thread = Thread.currentThread();
        atomicReference.compareAndSet(thread,null);
        System.out.println(thread.getName() + "\t invoke unLock()");
    }

    public static void main(String[] args) {
        MyAutogyration demo = new MyAutogyration();

        new Thread(() -> {
            demo.lock();
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            demo.unLock();

        },"t1 thread").start();


        new Thread(() -> {
            demo.lock();
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            demo.unLock();

        },"t2 thread").start();
    }


}
