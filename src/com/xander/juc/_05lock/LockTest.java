package com.xander.juc._05lock;

import org.junit.Test;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Description:
 *
 * @author Xander
 * datetime: 2020-11-12 15:19
 */
public class LockTest {

    // 显式锁 Lock
    private static Lock lock = new ReentrantLock();

    @Test
    public void testLock() {
        lock.lock();
        try {
            //获取本锁保护的资源
            System.out.println(Thread.currentThread().getName() + "开始执行任务");
        } finally {
            // 很重要，必须记得在 finally unlock()
            // Lock不会像synchronized一样，异常的时候自动释放锁，所以最佳实践是，finally中释放锁，以便保证发生异常的时候锁一定被释放
            lock.unlock();
        }
    }

    /**
     * tryLock(long time, TimeUnit unit) 避免死锁演示
     *
     * @throws InterruptedException
     */
    @Test
    public void testTryLock() throws InterruptedException {
        // 模拟两个线程同时抢占 lock1 和 lock2 ，使用 tryLock(long time, TimeUnit unit) 尝试获取锁，并避免死锁发生
        Lock lock1 = new ReentrantLock();
        Lock lock2 = new ReentrantLock();
        new Thread(() -> {
            // 尝试获取 lock1 ， 再获取 lock2，只要任何一把锁超时获取不到，则释放已占有的锁，sleep 一会，再重试
            lock1Tolock2(lock1, lock2);
        }).start();
        new Thread(() -> {
            // 尝试获取 lock2 ， 再获取 lock1，只要任何一把锁超时获取不到，则释放已占有的锁，sleep 一会，再重试
            lock2Tolock1(lock1, lock2);
        }).start();
        Thread.sleep(10000);
    }

    /**
     * 尝试获取 lock1 ， 再获取 lock2，只要任何一把锁超时获取不到，则释放已占有的锁，sleep 一会，再重试
     *
     * @param lock1
     * @param lock2
     */
    private void lock1Tolock2(Lock lock1, Lock lock2) {
        boolean success = false;
        while (!success) {
            try {
                // 先尝试获取 lock1，100ms 内获取不到，则重试
                if (lock1.tryLock(100, TimeUnit.MILLISECONDS)) {
                    try {
                        System.out.println(Thread.currentThread().getName() + "获取到了锁1");
                        Thread.sleep(new Random().nextInt(1000));
                        // 尝试获取 lock2，如果 100ms 内获取不到，则返回false
                        if (lock2.tryLock(100, TimeUnit.MILLISECONDS)) {
                            try {
                                success = true;
                                Thread.sleep(new Random().nextInt(1000));
                                System.out.println(Thread.currentThread().getName() + "获取到了锁2");
                                System.out.println(Thread.currentThread().getName() + "成功获取到了两把锁");
                            } finally {
                                //释放锁 lock2
                                lock2.unlock();
                                System.out.println(Thread.currentThread().getName() + "释放了锁2");
                            }
                        } else {
                            System.out.println(Thread.currentThread().getName() + "获取锁2失败，已重试");
                        }
                    } finally {
                        lock1.unlock();
                        System.out.println(Thread.currentThread().getName() + "释放了锁1");
                    }
                } else {
                    System.out.println(Thread.currentThread().getName() + "获取锁1失败，已重试");
                }
                // sleep 一会再重试
                Thread.sleep(new Random().nextInt(1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 尝试获取 lock2 ， 再获取 lock1，只要任何一把锁超时获取不到，则释放已占有的锁，sleep 一会，再重试
     *
     * @param lock1
     * @param lock2
     */
    private void lock2Tolock1(Lock lock1, Lock lock2) {
        boolean success = false;
        while (!success) {
            try {
                // 尝试获取 lock2，如果 100ms 内获取不到，则返回false
                if (lock2.tryLock(100, TimeUnit.MILLISECONDS)) {
                    try {
                        System.out.println(Thread.currentThread().getName() + "获取到了锁2");
                        Thread.sleep(new Random().nextInt(1000));
                        // 尝试获取 lock1，如果 100ms 内获取不到，则返回false
                        if (lock1.tryLock(100, TimeUnit.MILLISECONDS)) {
                            try {
                                success = true;
                                Thread.sleep(new Random().nextInt(1000));
                                System.out.println(Thread.currentThread().getName() + "获取到了锁1");
                                System.out.println(Thread.currentThread().getName() + "成功获取到了两把锁");
                            } finally {
                                //释放锁 lock2
                                lock1.unlock();
                                System.out.println(Thread.currentThread().getName() + "释放了锁1");
                            }
                        } else {
                            System.out.println(Thread.currentThread().getName() + "获取锁1失败，已重试");
                        }
                    } finally {
                        lock2.unlock();
                        System.out.println(Thread.currentThread().getName() + "释放了锁2");
                    }
                } else {
                    System.out.println(Thread.currentThread().getName() + "获取锁2失败，已重试");
                }
                // sleep 一会再重试
                Thread.sleep(new Random().nextInt(1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * lock.lockInterruptibly() 尝试获取锁，直到线程被中断
     * @throws InterruptedException
     */
    @Test
    public void testLockInterruptibly() throws InterruptedException {
        Lock lock = new ReentrantLock();
        Thread thread1 = new Thread(() -> {
            doRun(lock);
        });
        Thread thread2 = new Thread(() -> {
            doRun(lock);
        });
        thread1.start();
        thread2.start();
        // 标志 thread1 中断
        thread2.interrupt();
        Thread.sleep(10000);
    }

    private void doRun(Lock lock) {
        System.out.println(Thread.currentThread().getName() + "尝试获取锁");
        try {
            lock.lockInterruptibly();
            try {
                System.out.println(Thread.currentThread().getName() + "获取到了锁");
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                System.out.println(Thread.currentThread().getName() + "睡眠期间被中断了");
            } finally {
                lock.unlock();
                System.out.println(Thread.currentThread().getName() + "释放了锁");
            }
        } catch (InterruptedException e) {
            System.out.println(Thread.currentThread().getName() + "获得锁期间被中断了");
        }
    }

}
