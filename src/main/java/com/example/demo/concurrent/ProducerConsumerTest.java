package com.example.demo.concurrent;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author changrong.zeng
 * @Description 生产者消费者模型
 * @Date 23:31 2020-03-31
 **/
public class ProducerConsumerTest {

    public static void main(String[] args) {
        ShareSource shareSource = new ShareSource(new ArrayBlockingQueue<String>(10));

        new Thread(() -> {
            try {
                shareSource.prod();
            } catch (Exception e) {
                e.printStackTrace();
            }
        },"AA" ).start();

        new Thread(() -> {
            try {
                shareSource.consume();
            } catch (Exception e) {
                e.printStackTrace();
            }
        },"BB" ).start();

        try {
            TimeUnit.SECONDS.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("10秒后 main叫停线程");
        shareSource.stop();


    }
}

class ShareSource{
    private volatile boolean flag = true;
    private AtomicInteger atomicInteger = new AtomicInteger(0);
    private BlockingQueue<String> queue;

    public ShareSource(BlockingQueue<String> queue) {
        this.queue = queue;
    }

    public void prod() throws Exception{
        String str = "";
        while (flag){
            str = atomicInteger.incrementAndGet()+"";
            boolean res = queue.offer(str,2, TimeUnit.SECONDS);
            if(res){
                System.out.println("生产者生产成功： data = " + str);
            }else{
                System.out.println("生产者生产失败： data = " + str);
            }
            TimeUnit.SECONDS.sleep(1);
        }
        System.out.println("生产者停止生产 ");
    }

    public void consume() throws Exception{
        String res = "";
        while (flag){
            res = queue.poll(2, TimeUnit.SECONDS);
            if(null == res || res.equals("")){
                flag = false;
                System.out.println("没有消费品消费，消费者退出");
                return;
            }
            System.out.println("消费者消费成功： data = " + res);
        }
    }

    public void stop(){
        this.flag = false;
    }
}
