package com.xander.juc._11threadPool.executorService;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Description: 演示 shutdown 使用
 *
 * @author Xander
 * datetime: 2020/7/20 15:06
 */
public class ShutDownDemo {

    public static void main(String[] args) throws InterruptedException {
        ThreadPoolExecutor executorService = new ThreadPoolExecutor(3, 5, 30, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<>(5));
        for (int i = 0; i < 5; i++) {
            executorService.execute(new Task(i));
        }
        // shutdown() 执行后，会把存量的任务都执行完毕，但是不会加入新任务
        executorService.shutdown();
        // 这个任务会被 拒绝加入
        executorService.execute(new Task(5));
    }
}
