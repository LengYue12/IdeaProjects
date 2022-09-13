package com.lagou.task12;

public class StringCompareTest {

    public static void main(String[] args) {

        /*// 1.构造String类型的对象并打印
        String str1 = new String("hello");
        System.out.println("str1 = " + str1); // hello

        // 2.使用构造好的对象与其他字符串之间比较大小并打印
        System.out.println(str1.compareTo("world"));  // 'h' - 'w' => 104 - 119 => -15
        System.out.println(str1.compareTo("haha"));   // 'e' - 'a' => 101 - 97 => 4
        System.out.println(str1.compareTo("hehe"));   // 'l' - 'h' => 108 - 104 => 4
        System.out.println(str1.compareTo("heihei")); // 'l' - 'i' => 108 - 105 => 3
        System.out.println(str1.compareTo("helloworld")); // 长度：5 - 10 => -5
        System.out.println(str1.compareToIgnoreCase("HELLO")); // 0*/
        // 1.构造String类型对象并打印
        String str = new String("tangxin");
        System.out.println("str = " + str); // tangxin
        // 2.使用构造好的对象与其他字符串之间比较大小并打印
        System.out.println(str.compareTo("lingling"));
        System.out.println(str.compareTo("tang"));
        System.out.println(str.compareTo("tan"));
        System.out.println(str.compareToIgnoreCase("TANGXIN"));
        String str1 = new String("hello");
        System.out.println(str1.compareTo("haha"));
        System.out.println(str1.compareTo("hehe"));
        System.out.println(str1.compareTo("heihei"));
        System.out.println(str1.compareTo("helloworld"));
        System.out.println(str1.compareToIgnoreCase("HELLO"));
    }
}
