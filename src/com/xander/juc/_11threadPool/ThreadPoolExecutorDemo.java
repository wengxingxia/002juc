package com.xander.juc._11threadPool;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Description: 演示 ThreadPoolExecutor 创建线程池
 *
 * @author Xander
 * datetime: 2020-11-30 23:51
 */
public class ThreadPoolExecutorDemo {

    public static void main(String[] args) {

        int corePoolSize = 3;//核心线程数=3，
        int maximumPoolSize = 5;//最大线程数=5
        int keepAliveTime = 30;//超出核心线程数的线程空闲时间超过 30ms 就会被回收
        int capacity = 10;//阻塞队列的容量=10
        ArrayBlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<>(capacity);
        // 创建线程池，
        // 没有指定线程工厂threadFactory，则默认使用Executors.defaultThreadFactory()
        // 没有指定拒绝策略 RejectedExecutionHandler，则默认使用 AbortPolicy，表示直接抛出异常
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, TimeUnit.MILLISECONDS, workQueue);

        // 下面演示 taskCount 为同数值时的场景

        // 1. 任务数 <= corePoolSize + capacity，线程池中只有 corePoolSize 个线程
        // int taskCount = corePoolSize + capacity;

        // 2. corePoolSize + capacity < 任务数 <= maximumPoolSize + capacity，这时 corePoolSize < 线程池中线程数 <= maximumPoolSize
        // int taskCount = maximumPoolSize + capacity;

        // 3. 任务数 > maximumPoolSize + capacity , 线程池无法接受你所提交的任务的时候，采取的拒绝策略
        int taskCount = maximumPoolSize + capacity + 1;
        // 模拟程序需要执行 taskCount 个任务
        for (int i = 0; i < taskCount; i++) {
            // 每个任务启动一条线程去处理
            int index = i;
            // 给线程池提交任务
            threadPoolExecutor.submit(() -> {
                try {
                    System.out.println(Thread.currentThread().getName() + "执行任务..." + index);
                    TimeUnit.MILLISECONDS.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
        threadPoolExecutor.shutdown();//关闭线程池
    }
}
