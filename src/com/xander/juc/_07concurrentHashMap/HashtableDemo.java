package com.xander.juc._07concurrentHashMap;

import java.util.Hashtable;

/**
 * Description:
 *
 * @author Xander
 * datetime: 2020-11-16 20:11
 */
public class HashtableDemo {

    public static void main(String[] args) {
        Hashtable<String, String> hashtable = new Hashtable<>();
        hashtable.put("k1", "v1");
        System.out.println(hashtable.get("k1"));
    }
}