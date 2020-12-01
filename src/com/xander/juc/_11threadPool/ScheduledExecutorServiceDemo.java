package com.xander.juc._11threadPool;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Description: 演示ScheduledExecutorService的用法
 *
 * @author Xander
 * datetime: 2020-11-30 23:51
 */
public class ScheduledExecutorServiceDemo {

    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static void main(String[] args) {
        // 创建 ScheduledExecutorService ，能执行延迟和周期性任务
        ScheduledExecutorService schedule = Executors.newScheduledThreadPool(5);
        System.out.println(Thread.currentThread().getName() + "开始执行：" + formatter.format(LocalDateTime.now()));
        // testSchedule(schedule);
        // testScheduleAtFixedRate1(schedule);
        // testScheduleAtFixedRate2(schedule);
        for (int i = 0; i < 1000; i++) {

            testScheduleWithFixedDelay(schedule);
        }

    }

    /**
     * 测试 schedule 方法，延迟执行，并且只执行一次
     *
     * @param schedule
     */
    private static void testSchedule(ScheduledExecutorService schedule) {
        schedule.schedule(() -> {
                    // 延迟1000ms，只执行一次
                    System.out.println(Thread.currentThread().getName() + "延迟1000ms，只执行一次：" + formatter.format(LocalDateTime.now()));
                },
                1000, TimeUnit.MILLISECONDS);
    }

    /**
     * 测试 scheduleAtFixedRate，执行任务时间 2000ms 小于设置的 period 周期时长 3000ms
     *
     * @param schedule
     */
    private static void testScheduleAtFixedRate1(ScheduledExecutorService schedule) {
        schedule.scheduleAtFixedRate(() -> {
                    try {
                        // 周期性任务，延迟1000ms执行，并且每隔3000ms 开始一次任务
                        System.out.println("scheduleAtFixedRate: " + Thread.currentThread().getName() + " 开始时间" + formatter.format(LocalDateTime.now()));
                        TimeUnit.MILLISECONDS.sleep(2000);
                        System.out.println("scheduleAtFixedRate: " + Thread.currentThread().getName() + " 执行任务花费 2000ms 结束时间" + formatter.format(LocalDateTime.now()));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                },
                1000, 3000, TimeUnit.MILLISECONDS);
    }

    /**
     * 测试 scheduleAtFixedRate，执行任务时间 4000ms 大于设置的 period 周期时长 3000ms
     * 虽然执行任务时间大于设置的 period 周期时长，但是只有等上一个任务执行结束后，才会开启新的任务
     *
     * @param schedule
     */
    private static void testScheduleAtFixedRate2(ScheduledExecutorService schedule) {
        schedule.scheduleAtFixedRate(() -> {
                    try {
                        // 周期性任务，延迟1000ms执行，并且每隔3000ms 开始一次任务
                        System.out.println("scheduleAtFixedRate: " + Thread.currentThread().getName() + " 开始时间" + formatter.format(LocalDateTime.now()));
                        TimeUnit.MILLISECONDS.sleep(4000);
                        System.out.println("scheduleAtFixedRate: " + Thread.currentThread().getName() + " 执行任务花费 4000ms 结束时间" + formatter.format(LocalDateTime.now()));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                },
                1000, 3000, TimeUnit.MILLISECONDS);
    }

    /**
     * 测试 scheduleWithFixedDelay
     * 周期性任务，延迟1000ms执行，前一个任务结束 3000ms 后下一个任务开始执行
     * @param schedule
     */
    private static void testScheduleWithFixedDelay(ScheduledExecutorService schedule) {
        schedule.scheduleWithFixedDelay(() -> {
                    try {
                        // 周期性任务，延迟1000ms执行，前一个任务结束 3000ms 后下一个任务开始执行
                        System.out.println("scheduleWithFixedDelay周期性任务，" + Thread.currentThread().getName() + " 开始时间" + formatter.format(LocalDateTime.now()));
                        TimeUnit.MILLISECONDS.sleep(5000);
                        System.out.println("scheduleAtFixedRate周期性任务，" + Thread.currentThread().getName() + " 执行任务花费 5000ms 结束时间" + formatter.format(LocalDateTime.now()));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                },
                1000, 3000, TimeUnit.MILLISECONDS);
    }

}
