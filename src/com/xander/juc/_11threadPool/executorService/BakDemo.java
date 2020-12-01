package com.xander.juc._11threadPool.executorService;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Description: 演示关闭线程池
 *
 * @author Xander
 * datetime: 2020/7/20 15:06
 */
public class BakDemo {

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        for (int i = 0; i < 5; i++) {
            executorService.execute(new Task(i));
        }
        Thread.sleep(600);
        // 线程池立即停止，正在执行任务的线程会被interrupt，并返回还未开始执行的任务列表
        List<Runnable> runnables = executorService.shutdownNow();
        System.out.println("执行的任务数："+runnables.size());
        // System.out.println(runnables);
//        boolean b = executorService.awaitTermination(3, TimeUnit.SECONDS);
//        System.out.println("awaitTermination 3s : "+b);

       // System.out.println("isShutdown："+executorService.isShutdown());
       // executorService.shutdown();
       // System.out.println("isShutdown："+executorService.isShutdown());
//        Thread.sleep(10000);
//        System.out.println("after 10s isTerminated："+executorService.isTerminated());
    }
}

class ShutDownTask implements Runnable {
    private int index;

    public ShutDownTask(int index) {
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
