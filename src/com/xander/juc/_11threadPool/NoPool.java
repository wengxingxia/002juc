package com.xander.juc._11threadPool;

/**
 * Description: 演示不使用线程池，每个任务新建一个Thread
 *
 * @author Xander
 * datetime: 2020-11-30 9:40
 */
public class NoPool {

    public static void main(String[] args) {

        // 模拟程序需要执行1000个任务
        for (int i = 0; i < 1000; i++) {
            // 每个任务启动一条线程去处理
            new Thread(new Task(), "Thread" + i).start();
        }
    }

    static class Task implements Runnable {

        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName() + "执行任务...");
        }
    }
}

