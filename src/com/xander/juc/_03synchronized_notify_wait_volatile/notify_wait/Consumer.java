package com.xander.juc._03synchronized_notify_wait_volatile.notify_wait;

import java.util.concurrent.TimeUnit;

/**
 * Description: 消费者
 *
 * @author Xander
 * datetime: 2020/9/18 16:58
 */
public class Consumer implements Runnable {

    private Factory factory;

    public Consumer(Factory factory) {
        this.factory = factory;
    }

    @Override
    public void run() {
        // 每个消费者生产3次
        for (int i = 0; i < 3; i++) {
            try {
                // 消费时间设置 200 ms
                TimeUnit.MILLISECONDS.sleep(200);
                factory.consume();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}