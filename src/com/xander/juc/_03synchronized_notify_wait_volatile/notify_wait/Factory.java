package com.xander.juc._03synchronized_notify_wait_volatile.notify_wait;

/**
 * Description: 工厂，控制生产者和消费者
 *
 * @author Xander
 * datetime: 2020/9/18 17:08
 */
public class Factory {

    // 可生产的最大数量
    private final int maxCount = 5;

    // 当前已有数量
    private int curCount;

    /**
     * 生产
     */
    public synchronized void produce() throws InterruptedException {
        // 抢到锁
        while (curCount >= maxCount) {
            System.out.println("生产已满，等待消费--" + Thread.currentThread().getName());
            // wait() 释放当前持有的锁
            this.wait();
        }
        this.curCount++;
        System.out.println(Thread.currentThread().getName() + "生产，当前数量--" + curCount);
        this.notifyAll();
    }

    /**
     * 消费
     */
    public synchronized void consume() throws InterruptedException {
        while (curCount <= 0) {
            System.out.println("无货消费，等待生产====" + Thread.currentThread().getName());
            this.wait();
        }
        this.curCount--;
        System.out.println(Thread.currentThread().getName() + "消费，当前剩余数量--" + curCount);
        this.notifyAll();
    }
}
