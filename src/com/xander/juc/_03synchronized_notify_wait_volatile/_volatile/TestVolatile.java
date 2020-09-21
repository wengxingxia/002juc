package com.xander.juc._03synchronized_notify_wait_volatile._volatile;

import java.util.concurrent.TimeUnit;

/**
 * Description:
 *
 * @author Xander
 * datetime: 2020/9/18 17:53
 */
public class TestVolatile {

    public static void main(String[] args) throws InterruptedException {
        FlagThread thread = new FlagThread();
        thread.start();
        while (!thread.isFlag()){
            if(thread.isFlag()){
                System.out.println("读取到 flag = true");
                break;
            }
        }
    }
}

class FlagThread extends Thread {

    // 使用 volatile 修饰变量，保证变量修改后，线程间可见
    private volatile boolean flag;

    public boolean isFlag() {
        return flag;
    }

    @Override
    public void run() {
        try {
            TimeUnit.MILLISECONDS.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.flag = true;
    }
}
