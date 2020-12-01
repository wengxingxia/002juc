package com.xander.juc._11threadPool;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Description:
 *
 * @author Xander
 * datetime: 2020-11-30 23:50
 */
public class ScheduleWorker implements Runnable{
    public final static int Normal = 0;//普通任务类型
    public final static int HasException = -1;//会抛出异常的任务类型
    public final static int ProcessException = 1;//抛出异常但会捕捉的任务类型

    public static SimpleDateFormat formater = new SimpleDateFormat(
            "yyyy-MM-dd HH:mm:ss");

    private int taskType;
    public ScheduleWorker(int taskType) {
        this.taskType = taskType;
    }

    @Override
    public void run() {
        if(taskType==HasException) {
            System.out.println(Thread.currentThread().getId()+"---"+formater.format(new Date())+" Exception made...");
            throw new RuntimeException("HasException Happen");
        }else if(taskType==ProcessException) {
            try {
                System.out.println(Thread.currentThread().getId()+"---"+formater.format(new Date())
                        +" Exception made,but catch");
                throw new RuntimeException("HasException Happen");
            }catch(Exception e) {
                System.out.println(" Exception be catched");
            }
        }else {
            System.out.println(Thread.currentThread().getId()+"---"+" Normal ...."+formater.format(new Date()));
        }
    }
}



