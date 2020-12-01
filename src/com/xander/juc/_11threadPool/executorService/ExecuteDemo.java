package com.xander.juc._11threadPool.executorService;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Description: 演示  void execute(Runnable command) 方法使用
 *
 * @author Xander
 * datetime: 2020-12-01 14:57
 */
public class ExecuteDemo {

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
        // 模拟程序需要执行10个任务
        for (int i = 0; i < 10; i++) {
            // 每个任务启动一条线程去处理
            int index = i;
            // 给线程池提交任务
            threadPoolExecutor.execute(()->{
                System.out.println(Thread.currentThread().getName() + "执行任务..."+ index);
            });
        }
        threadPoolExecutor.shutdown();//关闭线程池
    }
}
