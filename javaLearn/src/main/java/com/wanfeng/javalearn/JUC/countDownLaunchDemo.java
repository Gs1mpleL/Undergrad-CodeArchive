package com.wanfeng.javalearn.JUC;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class countDownLaunchDemo {
    static ExecutorService pool = Executors.newFixedThreadPool(2);
    public static void main(String[] args) throws Exception {
        CountDownLatch countDownLatch = new CountDownLatch(3);
        pool.execute(()->{
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("1结束");
            countDownLatch.countDown();
        });

        pool.execute(()->{
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("2结束");
            countDownLatch.countDown();
        });
        System.out.println("等待");
        countDownLatch.await();
        System.out.println("结束");
    }
}
