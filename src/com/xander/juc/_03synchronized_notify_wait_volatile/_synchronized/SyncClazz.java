package com.xander.juc._03synchronized_notify_wait_volatile._synchronized;

import java.util.concurrent.TimeUnit;

/**
 * Description: 类锁
 *
 * @author Xander
 * datetime: 2020/9/18 15:33
 */
public class SyncClazz {

    // 当前数值
    private static int value;

    /**
     * synchronized 作用在static方法是类锁，锁的是类的Class对象
     */
    public static synchronized void print() {
        // 获得类锁后，下面的代码是线程安全的
        for (int i = 0; i < 3; i++) {
            try {
                TimeUnit.MILLISECONDS.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + " -class lock print- " + ++value);
        }
    }

    /**
     * 类锁，SyncClazz中的类变量value +1
     */
    public void add() {
        // synchronized 作用在代码块上
        // 如果 synchronized 作用的是类的对象实例，是对象锁
        // 如果 synchronized 作用的是类的Class对象，是类锁
        synchronized (SyncClazz.class) {
            // 获得类锁后，下面的代码是线程安全的
            for (int i = 0; i < 3; i++) {
                try {
                    TimeUnit.MILLISECONDS.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + " -class lock add- " + ++value);
            }
        }
    }

    /**
     * 不加锁，SyncClazz中的类变量value +1
     */
    public void addNoLock() {
        for (int i = 0; i < 3; i++) {
            try {
                TimeUnit.MILLISECONDS.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + " -no lock- " + ++value);
        }
    }
}
