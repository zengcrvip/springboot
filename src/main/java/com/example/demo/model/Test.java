package com.example.demo.model;


import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;

/**
 * @Author changrong.zeng
 * @Description
 * @Date 23:30 2020-03-09
 **/
public  class Test {
    private static CyclicBarrier cyclicBarrier = new CyclicBarrier(4);
    private static volatile int num = 0;


    public static void main(String[] args) {

       for(int j=0;j< 10;j++){

           for(int i=0;i<4;i++){
               new Thread(() -> {
                   int value = num*10;
                   do{
                       value++;
                       System.out.println(Thread.currentThread().getName() + ":" + value );
                   }while(value %10 !=0);

                   try {
                       cyclicBarrier.await();
                   } catch (InterruptedException e) {
                       e.printStackTrace();
                   } catch (BrokenBarrierException e) {
                       e.printStackTrace();
                   }
               },"线程"+i).start();
           }
           try {
               Thread.sleep(1000);
           } catch (InterruptedException e) {
               e.printStackTrace();
           }
           System.out.println("====================================第" + (j+1) + "波次结束！");
           num++;
       }


    }


}
