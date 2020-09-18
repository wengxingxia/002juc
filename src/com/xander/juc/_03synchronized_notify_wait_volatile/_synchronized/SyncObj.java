package com.xander.juc._03synchronized_notify_wait_volatile._synchronized;

import java.util.concurrent.TimeUnit;

/**
 * Description: 对象锁
 *
 * @author Xander
 * datetime: 2020/9/18 15:30
 */
public class SyncObj {

    //数值
    private static int value;

    //对象锁
    private Object lock = null;

    public SyncObj() {
    }

    public SyncObj(Object lock) {
        this.lock = lock;
    }

    /**
     * 对象锁，进行打印
     * synchronized 作用在实例方法(未使用static修饰的方法)
     * 用的锁是对象锁，是当前实例对象，也就是 this
     */
    public synchronized void print() {
        // 对于所有请求同一个对象锁资源的线程来说，下面的代码是线程安全的
        // 其他试图访问该对象锁线程将被阻塞
        for (int i = 0; i < 3; i++) {
            try {
                TimeUnit.MILLISECONDS.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + " -obj lock print- " + ++value);
        }
    }

    /**
     * 对象锁，当前实例类变量value +1
     */
    public void add() {
        // synchronized 作用在代码块上
        // 如果 synchronized 作用的是类的对象实例，是对象锁
        // 如果 synchronized 作用的是类的Class对象，是类锁
        synchronized (this.lock) {
            // 对于所有请求同一个对象锁资源的线程来说，下面的代码是线程安全的
            // 其他试图访问该对象锁线程将被阻塞
            for (int i = 0; i < 3; i++) {
                try {
                    TimeUnit.MILLISECONDS.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + " -obj lock add- " + ++value);
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
