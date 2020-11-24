package com.xander.juc._09concurrentTools;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * Description:
 *
 * @author Xander
 */
public class CountDownLatchDemo3 {

    static CountDownLatch latch = new CountDownLatch(3);

    public static void main(String[] args) throws InterruptedException {
        // 启动业务线程，需要等待 CountDownLatch 计数为0 才能执行它的业务
        new Thread(new BusiThread(),"Thread1").start();

        // 模拟一个线程中可以多次 countDown()
        // 这个线程有 2 步操作，假设每步操作完成后都需要扣减 1 次
        new Thread(() -> {
            try {
                TimeUnit.MILLISECONDS.sleep(5);
                System.out.println(Thread.currentThread().getName() + " ready init work step 1st......");
                //第一步工作完成，扣减一次
                latch.countDown();

                TimeUnit.MILLISECONDS.sleep(100);
                System.out.println("begin step 2nd.......");
                System.out.println(Thread.currentThread().getName() + " ready init work step 2nd......");
                //第二步工作完成，再扣减一次
                latch.countDown();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"Thread1").start();

        // 另外一条子线程，执行一次 countDown()
        new Thread(() -> {
            try {
                TimeUnit.MILLISECONDS.sleep(10);
                System.out.println(Thread.currentThread().getName() + " countDown......");
                latch.countDown();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"Thread2").start();


        // Main线程指定超时时间阻塞等待
        System.out.println("Main await--");
        latch.await(10,TimeUnit.MILLISECONDS);
        // Main线程等待超时，放弃继续等待，继续执行业务
        System.out.println("time out，Main do its work........");
    }

    //业务线程
    private static class BusiThread implements Runnable {

        @Override
        public void run() {
            try {
                System.out.println("BusiThread await--");
                latch.await();
                System.out.println("BusiThread do business-----");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}

