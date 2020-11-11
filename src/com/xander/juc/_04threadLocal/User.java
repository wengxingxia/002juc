package com.xander.juc._04threadLocal;

/**
 * Description:
 *
 * @author Xander
 * datetime: 2020-11-10 15:18
 */
public class User {

    /**
     * 用户 id
     */
    private long id;

    /**
     * 用户姓名
     */
    private String name;

    public User(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
