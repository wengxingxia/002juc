package com.xander.juc._03synchronized_notify_wait_volatile._volatile;

/**
 * Description:
 *
 * @author Xander
 * datetime: 2020/9/18 17:53
 */
public class TestVolatile {


    public static void main(String[] args) throws InterruptedException {
        NumThread thread = new NumThread();
        thread.start();
        for (int i = 0; i < 500000; i++) {
            thread.reduce();
        }
        System.out.println(Thread.currentThread().getName() + " 获取num: " + thread.getNum());
    }
}

class NumThread extends Thread {

    private int num;

    public void inc() {
        this.num++;
    }

    public void reduce() {
        this.num--;
    }

    public int getNum() {
        return num;
    }

    @Override
    public void run() {
        for (int i = 0; i < 500000; i++) {
            num++;
        }
        System.out.println(Thread.currentThread().getName() + " 当前num: " + num);
    }
}
