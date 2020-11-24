package com.xander.juc._09concurrentTools;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Description: Condition 演示生产者消费者
 * <p>
 * 生产者生产产品，可生产的最大数量 maxCount，消费者消费产品
 *
 * @author Xander
 * datetime: 2020-11-24 15:00
 */
public class ConditionDemo {
    // 可生产的最大数量
    private final int maxCount = 5;
    // 当前已有数量
    private int curCount;
    // lock
    private Lock lock = new ReentrantLock();
    // lock新建一个 notFull 的 Condition，
    // 如果notFull.await()  表示已达到最大生产数量，生产者阻塞等待
    private Condition notFull = lock.newCondition();

    // lock新建一个 notEmpty 的 Condition，
    // 如果notEmpty.await()  表示当前数量为空，消费者阻塞等待
    private Condition notEmpty = lock.newCondition();

    public static void main(String[] args) {
        ConditionDemo demo = new ConditionDemo();
        for (int i = 0; i < 3; i++) {
            Producer producer = new Producer(demo);
            new Thread(producer, "生产者P" + (i+1)).start();
        }
        for (int i = 0; i < 2; i++) {
            Consumer consumer = new Consumer(demo);
            new Thread(consumer, "消费者C" + (i+1)).start();
        }
    }

    /**
     * 生产
     */
    public void produce() {
        //获取锁
        lock.lock();
        try {
            // 抢到锁
            while (curCount >= maxCount) {
                System.out.println(Thread.currentThread().getName() + "生产已满，等待消费--");
                // notFull.await()  表示已达到最大生产数量，生产者阻塞等待
                this.notFull.await();
            }
            this.curCount++;
            System.out.println(Thread.currentThread().getName() + "生产，当前数量--" + curCount);
            this.notEmpty.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            //释放锁
            lock.unlock();
        }
    }

    /**
     * 消费
     */
    public void consume() {
        //获取锁
        lock.lock();
        try {
            // 抢到锁
            while (curCount <= 0) {
                System.out.println(Thread.currentThread().getName() + "无货消费，等待生产====");
                // notEmpty.await()  表示当前数量为空，消费者阻塞等待
                this.notEmpty.await();
            }
            this.curCount--;
            System.out.println(Thread.currentThread().getName() + "消费，当前剩余数量--" + curCount);
            this.notFull.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            //释放锁
            lock.unlock();
        }
    }

}

//生产者
class Producer implements Runnable {
    private ConditionDemo demo;

    public Producer(ConditionDemo demo) {
        this.demo = demo;
    }

    @Override
    public void run() {
        while (true) {

            try {
                // 生产者生产
                this.demo.produce();
                TimeUnit.MILLISECONDS.sleep((long) (Math.random()*1500));//模拟生产耗时
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

//消费者
class Consumer implements Runnable {
    private ConditionDemo demo;

    public Consumer(ConditionDemo demo) {
        this.demo = demo;
    }

    @Override
    public void run() {
        while (true) {

            try {
                // 消费者消费
                this.demo.consume();
                TimeUnit.MILLISECONDS.sleep((long) (Math.random()*1000));//模拟消费耗时
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
