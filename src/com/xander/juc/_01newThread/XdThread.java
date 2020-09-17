package com.xander.juc._01newThread;

import java.util.concurrent.TimeUnit;

/**
 * Description: 自定义线程类， extends Thread
 *
 * @author Xander
 * datetime: 2020/9/16 19:22
 */
public class XdThread extends Thread {

    public XdThread() {
    }

    /**
     * 指定线程名称
     * @param name 线程名称
     */
    public XdThread(String name) {
        super(name);
    }

    /**
     * run()方法是子线程执行的任务逻辑
     */
    @Override
    public void run() {
        System.out.println("子线程名称：" + Thread.currentThread().getName() + " 开始执行任务...");
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("子线程名称：" + Thread.currentThread().getName() + " 任务结束...");
    }
}
