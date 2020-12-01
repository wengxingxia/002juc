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
public class AwaitTerminationDemo {

    public static void main(String[] args) throws InterruptedException {
        ThreadPoolExecutor executorService = new ThreadPoolExecutor(3, 5, 30, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<>(5));
        for (int i = 0; i < 5; i++) {
            executorService.execute(new Task(i));
        }

        // isShutdown 如果 ExecutorService 已经 shut down，返回true
        System.out.println("isShutdown：" + executorService.isShutdown());
        executorService.shutdown();
        System.out.println("isShutdown：" + executorService.isShutdown());
        // 阻塞直到 shutdown()方法执行后，所有任务都已执行完成，或直到发生超时，或直到当前线程中断(interrupted)（以先发生者为准）。
        boolean b = executorService.awaitTermination(300, TimeUnit.MILLISECONDS);
        System.out.println("awaitTermination 300ms : " + b);
        boolean termination = executorService.awaitTermination(3, TimeUnit.SECONDS);
        System.out.println("awaitTermination 3s : " + termination);
        // 如果 shut down 后所有任务都已完成，则返回true，注意，isTerminated() 永远不是 true ，除非先调用 shutdown() 或 shutdownNow()
        System.out.println("isTerminated：" + executorService.isTerminated());
    }
}
