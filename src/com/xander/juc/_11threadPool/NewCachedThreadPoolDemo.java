package com.xander.juc._11threadPool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Description: 演示 newCachedThreadPool 的使用
 *
 * @author Xander
 * datetime: 2020-11-30 23:51
 */
public class NewCachedThreadPoolDemo {

    public static void main(String[] args) {
        // 创建无界线程池，线程池中线程最大数量 maximumPoolSize = Integer.MAX_VALUE 近似无界，具有自动回收多余线程的功能
        ExecutorService executorService = Executors.newCachedThreadPool();
        // 模拟程序需要执行1000个任务
        for (int i = 0; i < 1000; i++) {
            // 每个任务启动一条线程去处理
            int index = i;
            // 给线程池提交任务
            executorService.submit(()->{
                try {
                    System.out.println(Thread.currentThread().getName() + "执行任务..."+ index);
                    // 模拟执行任务耗时 50ms，
                    // 用于演示池中所有线程都繁忙时，需要创建新的线程去执行新任务
                    TimeUnit.MILLISECONDS.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
        executorService.shutdown();//关闭线程池
    }
}
