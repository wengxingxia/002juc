package com.xander.juc._11threadPool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Description: 演示 newSingleThreadExecutor 的使用
 *
 * @author Xander
 * datetime: 2020-11-30 23:51
 */
public class NewSingleThreadExecutorDemo {

    public static void main(String[] args) {
        // 创建只有一条线程的线程池
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        // 模拟程序需要执行1000个任务
        for (int i = 0; i < 1000; i++) {
            // 每个任务启动一条线程去处理
            int index = i;
            // 给线程池提交任务
            executorService.submit(()->{
                System.out.println(Thread.currentThread().getName() + "执行任务..."+ index);
            });
        }
        executorService.shutdown();//关闭线程池
    }
}
