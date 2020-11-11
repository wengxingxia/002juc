package com.xander.juc._04threadLocal;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * Description:
 *
 * @author Xander
 * datetime: 2020-11-10 15:20
 */
public class NoThreadLocalTest {

    /**
     * 用 map 模拟数据源
     */
    private Map<Long, User> userMap;

    /**
     * 执行测试用例前，初始化数据源
     */
    @Before
    public void initUserList() {
        userMap = new ConcurrentHashMap<>();
        User u1 = new User(1, "陈一");
        User u2 = new User(2, "钱二");
        User u3 = new User(3, "张三");
        User u4 = new User(4, "李四");
        User u5 = new User(5, "王五");
        User u6 = new User(6, "赵六");
        userMap.put(u1.getId(), u1);
        userMap.put(u2.getId(), u2);
        userMap.put(u3.getId(), u3);
        userMap.put(u4.getId(), u4);
        userMap.put(u5.getId(), u5);
        userMap.put(u6.getId(), u6);
    }

    /**
     * 模拟从数据库中，根据 id 查询 user
     *
     * @param id
     * @return
     */
    private User getById(Long id) {
        return this.userMap.get(id);
    }

    /**
     * 模拟项目的 Filter 或者 Interceptor 层，身份认证和鉴权环节
     *
     * @param id
     */
    private void doAuth(Long id) throws InterruptedException {
        System.out.println(Thread.currentThread().getName() + "-- " + id + " --" + "开始进行身份认证");
        // 从数据库中，根据 id 查询 user
        User user = this.getById(id);
        if (user == null)
            throw new NullPointerException("user is null");
        // 省略其他校验逻辑
        // 模拟身份认证处理的耗时，这里设置 50ms
        TimeUnit.MILLISECONDS.sleep(50);
        System.out.println(Thread.currentThread().getName() + "-- " + id + " --" + "身份认证通过");
    }

    /**
     * 模拟项目的 service 层，进行业务处理
     *
     * @param id
     */
    private void doService(Long id) throws InterruptedException {
        System.out.println(Thread.currentThread().getName() + "-- " + id + " --" + "开始进行业务处理");
        // 再次从数据库中，根据 id 查询 user
        User user = this.getById(id);
        if (user == null)
            throw new NullPointerException("user is null");
        // 省略其他业务处理逻辑
        // 模拟业务处理的耗时，这里设置 100ms
        TimeUnit.MILLISECONDS.sleep(100);
        System.out.println(Thread.currentThread().getName() + "-- " + id + " --" + "身份认证业务处理");
    }

    /**
     * 测试不使用 ThreadLocal，当需要使用 user 时，每次都从数据库查询用户信息
     */
    @Test
    public void test() throws InterruptedException {
        List<Thread> threads = new ArrayList<>();
        // 新建 6 条请求线程，模拟生产环境中并发请求场景
        for (long i = 1; i <= this.userMap.size(); i++) {
            long id = i;
            Thread requestThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        // 模拟项目中，每个请求都要进行 身份认证和业务处理 两步逻辑
                        doAuth(id);
                        doService(id);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
            threads.add(requestThread);
        }
        // 启动所有的请求线程，并发执行
        for (Thread thread : threads) {
            thread.start();
        }
        TimeUnit.SECONDS.sleep(5);
    }

}
