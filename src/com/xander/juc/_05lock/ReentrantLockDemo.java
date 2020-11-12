package com.xander.juc._05lock;

import java.util.concurrent.locks.ReentrantLock;

/**
* Description: 可重入锁 ReentrantLock
* @author Xander
* datetime: 2020-11-12 18:26
*/
public class ReentrantLockDemo {

    private static ReentrantLock lock = new ReentrantLock();

    private static void accessResource() {
        lock.lock();
        try {
            System.out.println(lock.getHoldCount());
            if (lock.getHoldCount() < 5) {
                // 如果重复占有锁的数量小于5，重复获取锁
                accessResource();
            }
            System.out.println("已经对资源进行了处理");
        } finally {
            lock.unlock();
            System.out.println("释放锁后：" + lock.getHoldCount());
        }
    }

    public static void main(String[] args) {
        accessResource();
    }
}
