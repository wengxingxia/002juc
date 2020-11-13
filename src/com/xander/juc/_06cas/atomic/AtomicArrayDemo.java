package com.xander.juc._06cas.atomic;

import java.util.concurrent.atomic.AtomicIntegerArray;

/**
 * 演示原子数组的使用方法
 */
public class AtomicArrayDemo implements Runnable {

    static int length = 5;
    /**
     * 普通变量，对变量的操作非线程安全
     */
    private static volatile int[] array = new int[length];

    /**
     * 原子类变量，线程安全操作
     */
    private static final AtomicIntegerArray atomicIntegerArray = new AtomicIntegerArray(length);

    /**
     * 对普通变量进行+1
     */
    public void incrementBasic(int index) {
        array[index]++;
    }

    /**
     * 对原子变量进行+1
     */
    public void incrementAtomic(int index) {
        atomicIntegerArray.getAndIncrement(index);
    }

    @Override
    public void run() {
        for (int index = 0; index < length; index++) {
            for (int count = 0; count < 10000; count++) {
                incrementAtomic(index);
                incrementBasic(index);
            }
        }
    }


    public static void main(String[] args) throws InterruptedException {
        AtomicArrayDemo r = new AtomicArrayDemo();
        Thread t1 = new Thread(r);
        Thread t2 = new Thread(r);
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println("原子类的结果：" + atomicIntegerArray);
        System.out.println("普通变量的结果：");
        for (int item : array) {
            System.out.print(item + " ");
        }
    }
}
