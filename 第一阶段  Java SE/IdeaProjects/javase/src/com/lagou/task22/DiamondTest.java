package com.lagou.task22;

import java.util.Comparator;

public class DiamondTest {

    public static void main(String[] args) {

        // 实现匿名内部类和钻石操作符的搭配使用
        Comparator<Integer> comparator = new Comparator<>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return 0;
            }
        };
    }
}
