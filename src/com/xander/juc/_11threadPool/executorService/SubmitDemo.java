package com.xander.juc._11threadPool.executorService;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Description: 演示 submit 方法使用
 *
 * @author Xander
 * datetime: 2020-12-01 14:57
 */
public class SubmitDemo {

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

        // 用于存放 submit 返回的 Future
        List<Future> futureList = new ArrayList<>();
        // 1. 演示 Future<T> submit(Runnable task) 的使用，所有 Future.get() 都返回 null
        // submitRunnableNoResult(threadPoolExecutor, futureList);

        // 2. 演示 Future<T> submit(Runnable task, T result) 的使用，所有 Future.get() 都返回submit时指定的 result
        // submitRunnableWithResult(threadPoolExecutor, futureList);

        // 3. 演示 Future<T> submit(Callable<T> task)  的使用，Future返回的是 Callable 的 call() 方法执行的结果
        submitCallable(threadPoolExecutor, futureList);
        //遍历 futureList 打印任务执行结果
        futureList.forEach(future -> {
            try {
                System.out.println("Future获取任务执行结果：" + future.get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        });

        threadPoolExecutor.shutdown();//关闭线程池

    }

    /**
     * 演示 Future<T> submit(Runnable task) 的使用，所有 Future.get() 都返回 null
     *
     * @param threadPoolExecutor
     * @param futureList
     */
    private static void submitRunnableNoResult(ThreadPoolExecutor threadPoolExecutor, List<Future> futureList) {
        for (int i = 0; i < 5; i++) {
            // 每个任务启动一条线程去处理
            int index = i;
            // Future<?> submit(Runnable task) 给线程池提交任务
            Future<?> future = threadPoolExecutor.submit(() -> {
                System.out.println(Thread.currentThread().getName() + "执行任务..." + index);
            });
            futureList.add(future);
        }
    }

    /**
     * 演示 Future<T> submit(Runnable task, T result) 的使用，Future.get() 都返回submit时指定的 result
     *
     * @param threadPoolExecutor
     * @param futureList
     */
    private static void submitRunnableWithResult(ThreadPoolExecutor threadPoolExecutor, List<Future> futureList) {
        for (int i = 0; i < 5; i++) {
            // 每个任务启动一条线程去处理
            int index = i;
            // Future<T> submit(Runnable task, T result) 给线程池提交任务
            Future<?> future = threadPoolExecutor.submit(() -> {
                System.out.println(Thread.currentThread().getName() + "执行任务..." + index);
            }, "hello"+index);
            futureList.add(future);
        }
    }


    /**
     * 演示 Future<T> submit(Callable<T> task)  的使用，Future返回的是 Callable 的 call() 方法执行的结果
     *
     * @param threadPoolExecutor
     * @param futureList
     */
    private static void submitCallable(ThreadPoolExecutor threadPoolExecutor, List<Future> futureList) {
        for (int i = 0; i < 5; i++) {
            // 每个任务启动一条线程去处理
            int index = i;
            // Future<T> submit(Callable<T> task)  给线程池提交任务
            Future<String> future = threadPoolExecutor.submit(new Callable<String>() {
                @Override
                public String call() throws Exception {
                    System.out.println(Thread.currentThread().getName() + "执行任务..." + index);
                    String result = "result " + index;
                    return result;
                }
            });
            futureList.add(future);
        }
    }

}
