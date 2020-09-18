package com.xander.juc._02thread;

import org.junit.Test;

import java.util.concurrent.TimeUnit;

/**
 * Description:
 *
 * @author Xander
 * datetime: 2020/9/18 9:24
 */
public class TestJoin {

    @Test
    public void testJoin() throws InterruptedException {
        // 演示 join() 方法使用
        //线程自然死亡(结束)：自然执行完或者抛出未处理异常

        // main线程开始运行
        System.out.println(Thread.currentThread().getName() + "开始运行...");
        // t1 不需要等待其他线程运行结束
        JoinThread t1 = new JoinThread("t1", null);
        // t2 线程等待 t1 线程死亡，再继续往下运行
        JoinThread t2 = new JoinThread("t2", t1);
        // t3 线程等待 t2 线程死亡，再继续往下运行
        JoinThread t3 = new JoinThread("t3", t2);
        // t4 线程等待 t3 线程死亡，再继续往下运行
        JoinThread t4 = new JoinThread("t4", t3);

        t1.start();
        t2.start();
        t3.start();
        t4.start();
        // main线程等待 t4 线程死亡，再继续往下运行
        t4.join();
        System.out.println(Thread.currentThread().getName() + "继续运行");
        System.out.println(Thread.currentThread().getName() + "运行结束...");
    }

    @Test
    public void testJoinWithTimeOut() throws InterruptedException {
        // 演示 join(long millis) 方法使用: 设置等待的超时时间，阻塞等待此线程死亡(运行结束)，若超时时间为0，则一直等待直到此线程死亡。

        // main线程开始运行
        System.out.println(Thread.currentThread().getName() + "开始运行...");
        Thread thread = new Thread(()->{
            System.out.println(Thread.currentThread().getName() + "开始运行...");
            try {
                // sleep 5 秒，模拟业务执行花了5秒
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + "结束...");

        });
        thread.start();
        // join()，阻塞等待 thread 运行结束，设置超时时间为 3000 ms
        thread.join(3000);
        System.out.println(Thread.currentThread().getName() + "继续运行");
        System.out.println(Thread.currentThread().getName() + "运行结束...");
        TimeUnit.SECONDS.sleep(3);
    }
}

//自定义线程类
class JoinThread extends Thread {

    // previousThread：前一个线程
    // 当前线程等待 previousThread 运行结束再继续运行
    private Thread previousThread;

    public JoinThread(String name, Thread previousThread) {
        super(name);
        this.previousThread = previousThread;
    }

    @Override
    public void run() {
        if (this.previousThread != null) {
            System.out.println(Thread.currentThread().getName() + "调用" + this.previousThread.getName() + ".join()，等待" + this.previousThread.getName() + "死亡");
            try {
                // 当前线程等待 previousThread 运行结束再继续运行
                this.previousThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + "继续运行");
        }
        try {
            // sleep 2 秒，模拟业务执行花了2秒
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + "运行结束...");
    }
}
