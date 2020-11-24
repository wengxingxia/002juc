package com.xander.juc._09concurrentTools;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Exchanger;

/**
 * Description:
 *
 * @author Xander
 * datetime: 2020-11-24 11:15
 */
public class ExchangeDemo {
    private static final Exchanger<Set<String>> exchange = new Exchanger<Set<String>>();

    public static void main(String[] args) {

        //第一个线程
        new Thread(new Runnable() {
            @Override
            public void run() {
                Set<String> setA = new HashSet<String>();//存放数据的容器
                try {
                    // 添加数据
                    setA.add("A1");
                    setA.add("A2");
                    // 先调用 exchange() 的线程会阻塞，直到后面的线程调用 exchange()执行时，才进行数据交换，继续运行
                    setA = exchange.exchange(setA);//交换set
                    // 处理交换后的数据
                    System.out.println(Thread.currentThread().getName() + "----" + setA);
                } catch (InterruptedException e) {
                }
            }
        }, "ThreadA").start();

        //第二个线程
        new Thread(new Runnable() {
            @Override
            public void run() {
                Set<String> setB = new HashSet<String>();//存放数据的容器
                try {
                    // 添加数据
                    setB.add("B1");
                    setB.add("B2");
                    setB.add("B3");
                    // 先调用 exchange() 的线程会阻塞，直到后面的线程调用 exchange()执行时，才进行数据交换，继续运行
                    setB = exchange.exchange(setB);//交换set
                    // 处理交换后的数据
                    System.out.println(Thread.currentThread().getName() + "----" + setB);

                } catch (InterruptedException e) {
                }
            }
        }, "ThreadB").start();

    }
}
