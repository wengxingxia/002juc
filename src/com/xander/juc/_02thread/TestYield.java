package com.xander.juc._02thread;

import org.junit.Test;

import java.util.concurrent.TimeUnit;

/**
 * Description:
 *
 * @author Xander
 * datetime: 2020/9/18 9:24
 */
public class TestYield {

    @Test
    public void testYield() throws InterruptedException {
        // 演示 yield() 方法使用
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 5; i++) {
                    if (i == 2) {
                        System.out.println(Thread.currentThread().getName() + "--> " + i + " -- yield");
                        // yield()：线程让出CPU，变成就绪状态
                        Thread.currentThread().yield();
                    }else
                        System.out.println(Thread.currentThread().getName() + "--> " + i);
                }
            }
        }, "t1");
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 5; i++) {
                    System.out.println(Thread.currentThread().getName() + "=== " + i);
                }
            }
        }, "t2");
        t1.start();
        t2.start();
        TimeUnit.MILLISECONDS.sleep(1500);
    }
}
