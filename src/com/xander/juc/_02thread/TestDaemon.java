package com.xander.juc._02thread;

import org.junit.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

/**
 * Description: 测试守护线程
 *
 * @author Xander
 * datetime: 2020/9/18 9:24
 */
public class TestDaemon {

    @Test
    public void testDaemon() throws InterruptedException {
        // 演示 setDaemon()、isDaemon() 方法使用
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //判断当前线程是不是守护线程
                    System.out.println(Thread.currentThread().getName() + "是守护线程: " + Thread.currentThread().isDaemon());
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                    // 如果当前线程不处于中断状态，则每秒打印当前时间
                    while (!Thread.currentThread().isInterrupted()) {
                        try {
                            // sleep() 方法会检测线程的中断状态，如果已中断，则抛出InterruptedException
                            TimeUnit.SECONDS.sleep(1);
                            System.out.println(Thread.currentThread().getName() + " 打印当前时间：" + formatter.format(LocalDateTime.now()));
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            // InterruptedException 会清空中断状态，需要再次执行interrupt()，才能将线程状态置为已中断，退出循环
                            Thread.currentThread().interrupt();
                        }
                    }
                    System.out.println(Thread.currentThread().getName() + " 任务结束..." + formatter.format(LocalDateTime.now()));
                } finally {
                    System.out.println(Thread.currentThread().getName() + "执行 finlly 代码块");
                }
            }
        }, "t1");
        //设置为守护线程，在start()方法之前调用才有效
        t1.setDaemon(true);
        t1.start();
        TimeUnit.MILLISECONDS.sleep(3000);
    }
}
