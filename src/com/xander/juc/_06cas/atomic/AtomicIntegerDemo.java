package com.xander.juc._06cas.atomic;

import java.util.concurrent.atomic.AtomicInteger;

/**
* Description: 演示AtomicInteger的基本用法，对比非原子类的线程安全问题，使用了原子类之后，不需要加锁，也可以保证线程安全。
* @author Xander
* datetime: 2020-11-13 12:53
*/
public class AtomicIntegerDemo implements Runnable {

    /**
     * 普通变量，对变量的操作非线程安全
     */
    private static volatile int var = 0;

    /**
     * 原子类变量，线程安全操作
     */
    private static final AtomicInteger atomicInteger = new AtomicInteger();

    /**
     * 对普通变量进行+1
     */
    public void incrementBasic() {
        var++;
    }

    /**
     * 对原子变量进行+1
     */
    public void incrementAtomic() {
        atomicInteger.getAndIncrement();
    }

    @Override
    public void run() {
        for (int i = 0; i < 10000; i++) {
            incrementAtomic();
            incrementBasic();
        }
    }


}
