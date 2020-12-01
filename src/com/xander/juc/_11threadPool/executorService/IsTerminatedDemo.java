package com.xander.juc._11threadPool.executorService;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Description: 演示 isTerminated 使用
 *
 * @author Xander
 * datetime: 2020/7/20 15:06
 */
public class IsTerminatedDemo {

    public static void main(String[] args) throws InterruptedException {
        ThreadPoolExecutor executorService = new ThreadPoolExecutor(3, 5, 30, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<>(5));
        for (int i = 0; i < 5; i++) {
            executorService.execute(new Task(i));
        }
        // isShutdown 如果 ExecutorService 已经 shut down，返回true
        System.out.println("isShutdown：" + executorService.isShutdown());
        executorService.shutdown();
        System.out.println("isShutdown：" + executorService.isShutdown());
        Thread.sleep(100);
        // 如果 shut down 后所有任务都已完成，则返回true，<br>注意，isTerminated() 永远不是 true ，除非先调用 shutdown() 或 shutdownNow()
        System.out.println("after 100ms isTerminated：" + executorService.isTerminated());
        Thread.sleep(1000);
        System.out.println("after 1000ms isTerminated：" + executorService.isTerminated());
    }
}
