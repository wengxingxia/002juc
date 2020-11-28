package com.xander.juc._10blockingQueue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Description: 生产者消费者模式
 *
 * @author Xander
 * datetime: 2020-11-27 15:16
 */
public class ArrayBlockingQueueDemo {

    //用阻塞队列作为容器，指定容量为5
    private ArrayBlockingQueue<Goods> blockingQueue = new ArrayBlockingQueue<>(5);

    // 全局商品索引
    static int goodsIndex;

    // 打印日志的lock，是公平锁，等待时间长的线程优先拿到锁
    private Lock fairLock = new ReentrantLock(true);

    public static void main(String[] args) {
        ArrayBlockingQueueDemo demo = new ArrayBlockingQueueDemo();
        for (int i = 0; i < 3; i++) {
            Producer producer = new Producer(demo);
            new Thread(producer, "生产者P" + (i + 1)).start();
        }
        for (int i = 0; i < 2; i++) {
            Consumer consumer = new Consumer(demo);
            new Thread(consumer, "消费者C" + (i + 1)).start();
        }
    }

    /**
     * 生产的商品
     */
    class Goods {
        // 商品下标
        String index;
    }

    /**
     * 生产
     */
    public void produce() throws InterruptedException {
        Goods goods = new Goods();
        // 如果有空间，队尾插入数据；如果队列已满，则无法插入，阻塞，直到有空闲时间
        this.blockingQueue.put(goods);
        this.log(goods, true);//打印生产日志
    }

    /**
     * 消费
     */
    public void consume() throws InterruptedException {
        // 获取并删除队头节点，<br>如果队列无数据，则阻塞，直到有数据
        Goods goods = this.blockingQueue.take();
        this.log(goods, false);//打印消费日志
    }

    /**
     * 打印生产或者消费日志
     *
     * @param goods
     * @param isProduct true表示生产，false表示消费
     */
    private void log(Goods goods, boolean isProduct) {
        this.fairLock.lock();
        try {
            String flag = isProduct ? "生产--" : "消费--";
            if (isProduct) {
                //如果是生产日志，这里设置商品下标
                goods.index = "商品" + goodsIndex++;
            }
            System.out.println(Thread.currentThread().getName() + flag + goods.index);
        } finally {
            this.fairLock.unlock();
        }
    }

}

//生产者
class Producer implements Runnable {
    private ArrayBlockingQueueDemo demo;

    public Producer(ArrayBlockingQueueDemo demo) {
        this.demo = demo;
    }

    @Override
    public void run() {
        while (true) {

            try {
                // 生产者生产
                this.demo.produce();
                TimeUnit.MILLISECONDS.sleep(3000);//模拟生产耗时
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

//消费者
class Consumer implements Runnable {
    private ArrayBlockingQueueDemo demo;

    public Consumer(ArrayBlockingQueueDemo demo) {
        this.demo = demo;
    }

    @Override
    public void run() {
        while (true) {

            try {
                // 消费者消费
                this.demo.consume();
                TimeUnit.MILLISECONDS.sleep(2000);//模拟消费耗时
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
