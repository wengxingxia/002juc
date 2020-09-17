package com.xander.juc._01newThread;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

/**
 * Description: 通过实现 Callable 接口创建线程，允许有返回值
 *
 * @author Xander
 * datetime: 2020/9/16 19:52
 */
public class XdCallable implements Callable<Integer> {

    /**
     * 线程任务执行逻辑
     *
     * @return
     * @throws Exception
     */
    @Override
    public Integer call() throws Exception {
        System.out.println("Callable方式，子线程名称：" + Thread.currentThread().getName() + " 开始执行任务...");
        // 返回一个 0-100(不包含) 随机整数
        Random random = new Random();
        int num = random.nextInt(100);
        TimeUnit.SECONDS.sleep(2);
        System.out.println("Callable方式，子线程名称：" + Thread.currentThread().getName() + " 任务结束...");
        return num;
    }
}
