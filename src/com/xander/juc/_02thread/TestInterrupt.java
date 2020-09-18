package com.xander.juc._02thread;

import org.junit.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

/**
 * Description:
 *
 * @author Xander
 * datetime: 2020/9/17 17:26
 */
public class TestInterrupt {

    @Test
    public void testInterrupt() throws InterruptedException {
        // 演示 interrupt() 、 isInterrupted() 和 InterruptedException
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                System.out.println("子线程开始执行任务..." + formatter.format(LocalDateTime.now()));
                // 如果当前线程不处于中断状态，则每秒打印当前时间
                while (!Thread.currentThread().isInterrupted()) {
                    try {
                        // sleep() 方法会检测线程的中断状态，如果已中断，则抛出InterruptedException
                        TimeUnit.SECONDS.sleep(1);
                        System.out.println("打印当前时间：" + formatter.format(LocalDateTime.now()));
                    } catch (InterruptedException e) {
                        System.out.println("catch InterruptedException：" + e.getMessage());
                        // InterruptedException 会清空中断状态，所以此时的中断状态为 false
                        System.out.println("当前线程中断状态：" + Thread.currentThread().isInterrupted());
//                        e.printStackTrace();
                        // InterruptedException 会清空中断状态，需要再次执行interrupt()，才能将线程状态置为已中断，退出循环
                        Thread.currentThread().interrupt();
                    }
                }
                System.out.println("子线程任务结束..." + formatter.format(LocalDateTime.now()));
            }
        });
        thread.start();
        TimeUnit.SECONDS.sleep(3);
        System.out.println("主线程执行子线程的interrupt()");
        // 只是将线程的中断标志位置为true，并不是强行关闭这个线程，线程是否中断，由线程本身决定。
        thread.interrupt();
    }

    @Test
    public void testInterrupted() throws InterruptedException {
        // 演示 interrupted()
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                // 静态方法 Thread.interrupted() 获取当前线程的中断状态，不管当前线程中断状态如何，接着清空当前线程的中断状态
                System.out.println("静态方法 Thread.interrupted() 获取当前线程的中断状态：" + Thread.interrupted() + "，然后清空中断状态");
                System.out.println("成员方法 interrupted() 获取当前线程的中断状态："+Thread.currentThread().interrupted());
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                System.out.println("子线程开始执行任务..." + formatter.format(LocalDateTime.now()));
                // 如果当前线程不处于中断状态，则每秒打印当前时间
                if (!Thread.currentThread().isInterrupted()) {
                    System.out.println("打印当前时间：" + formatter.format(LocalDateTime.now()));
                }
                System.out.println("子线程任务结束..." + formatter.format(LocalDateTime.now()));
            }
        });
        thread.start();
        thread.interrupt();
        TimeUnit.SECONDS.sleep(3);
    }
}
