package com.xander.juc._11threadPool.executorService;

/**
 * Description: 线程池中的任务
 *
 * @author Xander
 * datetime: 2020-12-01 18:53
 */
public class Task implements Runnable {
    private int index;

    public Task(int index) {
        this.index = index;
    }

    @Override
    public void run() {
        try {
            System.out.println(Thread.currentThread().getName() + "开始执行任务..."+index);
            Thread.sleep(500);
        } catch (InterruptedException e) {
            System.out.println(Thread.currentThread().getName() + "被中断了");
        }
    }
}