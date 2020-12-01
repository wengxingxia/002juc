package com.xander.juc._11threadPool.executorService;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Description: 演示 isShutdown 使用
 *
 * @author Xander
 * datetime: 2020/7/20 15:06
 */
public class IsShutDownDemo {

    public static void main(String[] args) throws InterruptedException {
        ThreadPoolExecutor executorService = new ThreadPoolExecutor(3, 5, 30, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<>(5));
        for (int i = 0; i < 5; i++) {
            executorService.execute(new Task(i));
        }
        // isShutdown 如果 ExecutorService 已经 shut down，返回true
        System.out.println("isShutdown："+executorService.isShutdown());
        executorService.shutdown();
        System.out.println("isShutdown："+executorService.isShutdown());
    }
}
