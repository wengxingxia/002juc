package com.xander.juc._03synchronized_notify_wait_volatile.notify_wait;

import org.junit.Test;

import java.util.concurrent.TimeUnit;

/**
 * Description: 测试生产者消费者
 *
 * @author Xander
 * datetime: 2020/9/18 17:23
 */
public class TestProducerConsumer {

    @Test
    public void testProducerConsumer() throws InterruptedException {
        Factory factory = new Factory();
        new Thread(new Producer(factory), "生产者A").start();
        new Thread(new Producer(factory), "生产者B").start();
        new Thread(new Producer(factory), "生产者C").start();
        new Thread(new Consumer(factory), "消费者1").start();
        new Thread(new Consumer(factory), "消费者2").start();
        new Thread(new Consumer(factory), "消费者3").start();
        TimeUnit.SECONDS.sleep(20);
    }
}
