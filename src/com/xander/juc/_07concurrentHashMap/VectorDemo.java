package com.xander.juc._07concurrentHashMap;

import java.util.Vector;

/**
 * Description:
 *
 * @author Xander
 * datetime: 2020-11-16 20:10
 */
public class VectorDemo {

    public static void main(String[] args) {
        Vector<String> vector = new Vector<>();
        vector.add("test");
        System.out.println(vector.get(0));
    }
}
