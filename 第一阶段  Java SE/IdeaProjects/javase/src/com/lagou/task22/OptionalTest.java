package com.lagou.task22;

import java.util.Optional;
import java.util.function.Function;

public class OptionalTest {

    public static void main(String[] args) {

//        String str1 = "hello";
        String str1 = null;
        if (null != str1) {
            System.out.println("字符串的长度是：" + str1.length()); // 5    空指针异常
        } else {
            System.out.println("字符串为空，因此长度为0!");
        }

        System.out.println("----------------------------------------------------------");
        // Java8中使用Optional类实现空值的处理
        // 1.将数据str1装到Optional对象代表的容器中
        Optional<String> optional = Optional.ofNullable(str1);
        // 2.建立映射关系  使用字符串的长度与字符串建立映射关系
        Optional<Integer> integer = optional.map(new Function<String, Integer>() {
            @Override
            public Integer apply(String s) {
                return s.length();
            }
        });

//        Optional<Integer> integer1 = optional.map(s -> s.length());
        Optional<Integer> integer1 = optional.map(String::length);
        // 3.若字符串为空则打印0，否则打印字符串的数值
        System.out.println("integer = " + integer1); // null
        System.out.println(integer.orElse(0)); // 0

    }
}
