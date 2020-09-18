package com.xander.juc._03synchronized_notify_wait_volatile.notify_wait;

import java.util.concurrent.TimeUnit;

/**
 * Description: 生产者
 *
 * @author Xander
 * datetime: 2020/9/18 16:58
 */
public class Producer implements Runnable{

    private Factory factory;

    public Producer(Factory factory) {
        this.factory = factory;
    }

    @Override
    public void run() {
        // 每个生产者生产3次
        for (int i = 0; i < 3; i++) {
            try {
                // 生产时间设置 200 ms
                TimeUnit.MILLISECONDS.sleep(200);
                factory.produce();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
