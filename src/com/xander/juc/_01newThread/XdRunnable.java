package com.xander.juc._01newThread;

import java.util.concurrent.TimeUnit;

/**
 * Description: 通过实现 Runnable 接口创建线程
 *
 * @author Xander
 * datetime: 2020/9/16 19:34
 */
public class XdRunnable implements Runnable {

    /**
     * 线程任务执行逻辑
     */
    @Override
    public void run() {
        System.out.println("Runnable方式，子线程名称：" + Thread.currentThread().getName() + " 开始执行任务...");
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Runnable方式，子线程名称：" + Thread.currentThread().getName() + " 任务结束...");
    }
}
