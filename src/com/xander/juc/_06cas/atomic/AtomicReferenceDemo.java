package com.xander.juc._06cas.atomic;

import java.util.concurrent.atomic.AtomicReference;

/**
 * 演示引用类型的原子操作类
 */
public class AtomicReferenceDemo {
	
	static AtomicReference<UserInfo> userRef = new AtomicReference<UserInfo>();
	
    public static void main(String[] args) {
        UserInfo user = new UserInfo("Mark", 15);//要修改的实体的实例
        userRef.set(user);

        UserInfo updateUser = new UserInfo("Bill", 17);//要变化的新实例
        userRef.compareAndSet(user, updateUser);
        System.out.println(userRef.get().getName());
        System.out.println(userRef.get().getAge());
        System.out.println(user.getName());
        System.out.println(user.getAge());        
    }
    
    //定义一个实体类
    static class UserInfo {
        private String name;
        private int age;
        public UserInfo(String name, int age) {
            this.name = name;
            this.age = age;
        }
        public String getName() {
            return name;
        }
        public int getAge() {
            return age;
        }
    }

}
