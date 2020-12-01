package com.xander.juc._11threadPool.executorService;

import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Description: 演示 shutdownNow 使用
 *
 * @author Xander
 * datetime: 2020/7/20 15:06
 */
public class ShutDownNowDemo {

    public static void main(String[] args) throws InterruptedException {
        ThreadPoolExecutor executorService = new ThreadPoolExecutor(3, 5, 30, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<>(5));
        for (int i = 0; i < 8; i++) {
            executorService.execute(new Task(i));
        }
        // sleep 100ms 后，前3个任务还在工作中，会直接被中断
        Thread.sleep(100);
        // 线程池立即停止，正在执行任务的线程会被interrupt，并返回还未开始执行的任务列表
        List<Runnable> runnables = executorService.shutdownNow();
        System.out.println("还未开始执行的任务数："+runnables.size());
    }
}

