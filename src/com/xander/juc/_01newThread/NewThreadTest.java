package com.xander.juc._01newThread;

import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

/**
 * Description:
 *
 * @author Xander
 * datetime: 2020/9/16 19:22
 */
public class NewThreadTest {

    /**
     * 创建线程方式一：定义一个类 extends Thread，并重写 run() 方法，run()方法的方法体就子线程的任务逻辑
     */
    @Test
    public void testNewThread() throws InterruptedException {
        //新建线程，不指定线程名称，系统会给线程定义一个名称
        XdThread thread = new XdThread();
        //启动子线程
        thread.start();

        //新建线程，指定线程名称
        XdThread threadWithName = new XdThread("Another-Thread");
        //启动子线程
        threadWithName.start();
        TimeUnit.SECONDS.sleep(3);
    }

    /**
     * 创建线程方式二：实现Runnable接口
     */
    @Test
    public void testRunnable() throws InterruptedException {
        Thread thread = new Thread(new XdRunnable());
        //启动子线程
        thread.start();
        // 通过 Lambda 创建Runnable实例
        new Thread(() -> {
            System.out.println("我是另一个子线程，开始执行任务...");
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("我是另一个子线程，执行结束...");
        }).start();
        TimeUnit.SECONDS.sleep(3);
    }

    /**
     * 创建线程方式三：实现 Callable 接口
     */
    @Test
    public void testCallable() throws ExecutionException, InterruptedException {
        // 通过子线程返回一个 0-100(不包含) 随机整数
        FutureTask future = new FutureTask(new XdCallable());
        // FutureTask 可以作为 Runnable ，通过 Thread(Runnable target) 新建线程
        Thread thread = new Thread(future);
        //启动子线程
        thread.start();
        System.out.println("子线程返回：" + future.get());
        System.out.println("主线程结束");
    }
}
