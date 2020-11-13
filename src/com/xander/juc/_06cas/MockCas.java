package com.xander.juc._06cas;

/**
 * Description: 模仿 CAS
 *
 * @author Xander
 * datetime: 2020-11-13 7:02
 */
public class MockCas {

    /**
     * 内存值
     */
    private volatile int value;

    public MockCas(int value) {
        this.value = value;
    }

    /**
     * 模仿 CAS
     *
     * @param expectedValue 预期值
     * @param newValue      要更新的新值
     * @return
     */
    public synchronized int compareAndSwap(int expectedValue, int newValue) {
        int oldValue = value;
        if (oldValue == expectedValue) {
            value = newValue;
            System.out.println(Thread.currentThread().getName() + " cas 操作成功");
        } else {
            System.out.println(Thread.currentThread().getName() + " cas 操作失败");
        }
        return oldValue;
    }

    public static void main(String[] args) throws InterruptedException {
        // 内存值是 100
        int value = 100;
        int expectedValue = 100;
        MockCas cas = new MockCas(value);
        Thread thread0 = new Thread(() -> {
            int oldValue = cas.compareAndSwap(expectedValue, 101);
            System.out.println(Thread.currentThread().getName() + " cas 内存值：" + oldValue);
        });
        Thread thread1 = new Thread(() -> {
            int oldValue = cas.compareAndSwap(expectedValue, 101);
            System.out.println(Thread.currentThread().getName() + " cas 内存值：" + oldValue);
        });
        thread0.start();
        thread1.start();
        thread0.join();
        thread1.join();
    }
}