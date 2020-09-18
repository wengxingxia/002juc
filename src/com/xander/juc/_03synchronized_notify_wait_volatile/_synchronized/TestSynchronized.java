package com.xander.juc._03synchronized_notify_wait_volatile._synchronized;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Description:
 *
 * @author Xander
 * datetime: 2020/9/18 15:29
 */
public class TestSynchronized {

    /**
     * 测试没有锁
     *
     * @throws InterruptedException
     */
    @Test
    public void testNoLock() throws InterruptedException {
        SyncObj so1 = new SyncObj();
        List<Thread> list = new ArrayList<>();
        // 使用多线程去执行 SyncObj.sayHello()
        for (int i = 0; i < 5; i++) {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    // 不加锁，不同线程，并发去对 so1 中的类变量value +1
                    // addNoLock() 没有加锁，线程不安全
                    so1.addNoLock();
                }
            });
            list.add(thread);
        }
        for (Thread thread : list) {
            thread.start();
        }
        TimeUnit.SECONDS.sleep(3);
    }

    /**
     * 测试不同实例持有同一把对象锁，SyncObj中的类变量 value 还是线程安全地递增
     *
     * @throws InterruptedException
     */
    @Test
    public void testSyncObjSameLock() throws InterruptedException {
        //对象锁
        Object lock = new Object();
        SyncObj so1 = new SyncObj(lock);
        SyncObj so2 = new SyncObj(lock);
        List<Thread> list = new ArrayList<>();
        // 使用多线程去执行 SyncObj.sayHello()
        for (int i = 0; i < 5; i++) {
            //当前线程执行的实例
            SyncObj instance = i < 3 ? so1 : so2;
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    // 加对象锁，不同线程，并发去对 so1 中的类变量value +1
                    // add() 加了同一把对象锁，类变量value还是线程安全递增
                    instance.add();
                }
            });
            list.add(thread);
        }
        for (Thread thread : list) {
            thread.start();
        }
        TimeUnit.SECONDS.sleep(3);
    }

    /**
     * 测试不同实例持有不同对象锁，SyncObj中的类变量 value 递增不是线程安全的
     *
     * @throws InterruptedException
     */
    @Test
    public void testSyncObjDiffLock() throws InterruptedException {
        //对象锁
        Object lock1 = new Object();
        Object lock2 = new Object();
        SyncObj so1 = new SyncObj(lock1);
        SyncObj so2 = new SyncObj(lock2);
        List<Thread> list = new ArrayList<>();
        // 使用多线程去执行 SyncObj.sayHello()
        for (int i = 0; i < 5; i++) {
            //当前线程执行的实例
            SyncObj instance = i < 3 ? so1 : so2;
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    // 加对象锁，不同线程，并发去对 so1 中的类变量value +1
                    // add() 加了同一把对象锁，类变量value还是线程安全递增
                    instance.add();
                }
            });
            list.add(thread);
        }
        for (Thread thread : list) {
            thread.start();
        }
        TimeUnit.SECONDS.sleep(3);
    }

    /**
     * 测试类锁，add() 加类锁，线程安全
     * 不同线程，不同的SyncClazz实例 并发去对SyncClazz中的类变量value +1 都是线程安全的，value数值有序地加一递增
     *
     * @throws InterruptedException
     */
    @Test
    public void testSyncClazz() throws InterruptedException {
        SyncClazz sc1 = new SyncClazz();
        SyncClazz sc2 = new SyncClazz();
        List<Thread> list = new ArrayList<>();
        // 使用多线程去执行 SyncClazz.sayHello()
        for (int i = 0; i < 5; i++) {
            //当前线程执行的实例
            SyncClazz instance = i < 3 ? sc1 : sc2;
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    // 不同线程，不同的SyncClazz实例 并发去对SyncClazz中的类变量value +1
                    // add() 加类锁，线程安全
                    instance.add();
                }
            });
            list.add(thread);
        }
        for (Thread thread : list) {
            thread.start();
        }
        TimeUnit.SECONDS.sleep(3);
    }
}
