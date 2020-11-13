package com.xander.juc._06cas.atomic;

import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * 演示带版本戳的原子操作类
 */
public class AtomicStampedReferenceDemo {

    static AtomicStampedReference<String> asr =
            new AtomicStampedReference<>("Xander", 0);


    public static void main(String[] args) throws InterruptedException {
        final int oldStamp = asr.getStamp();//拿初始的版本号
        final String oldReferenc = asr.getReference();//拿初始的引用

        System.out.println(oldReferenc + "===========" + oldStamp);

        Thread rightStampThread = new Thread(new Runnable() {

            @Override
            public void run() {
                String reference = asr.getReference();
                System.out.println(Thread.currentThread().getName()
                        + "  修改前变量值：" + reference + "  修改前版本戳：" + asr.getStamp() + "  修改成功："
                        + asr.compareAndSet(oldReferenc, oldReferenc + "Java", oldStamp, oldStamp + 1));
            }

        });

        Thread errorStampThread = new Thread(new Runnable() {

            @Override
            public void run() {
                String reference = asr.getReference();
                System.out.println(Thread.currentThread().getName()
                        + "  修改前变量值：" + reference + "  修改前版本戳：" + asr.getStamp() + "  修改成功："
                        + asr.compareAndSet(reference, reference + "C", oldStamp, oldStamp + 1));

            }

        });

        rightStampThread.start();
        rightStampThread.join();
        errorStampThread.start();
        errorStampThread.join();
        System.out.println(asr.getReference() + "===========" + asr.getStamp());

    }
}
