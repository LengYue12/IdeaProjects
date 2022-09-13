package com.lagou.task12;

public class StringIndexTest {

    public static void main(String[] args) {

        /*// 1.构造String类型的对象并打印
        String str1 = new String("Good Good Study, Day Day Up!");
        System.out.println("str1 = " + str1); // Good Good Study, Day Day Up!

        // 2.实现字符串中指定字符和字符串的查找功能
        int pos = str1.indexOf('g');
        System.out.println("pos = " + pos); // -1   代表查找失败
        pos = str1.indexOf('G');
        System.out.println("pos = " + pos); // 0    该字符第一次出现的索引的位置
        // 表示从下标0开始查找字符'G'第一次出现的索引位置，包含0
        pos = str1.indexOf('G', 0);
        System.out.println("pos = " + pos); // 0
        pos = str1.indexOf('G', 1);
        System.out.println("pos = " + pos); // 5

        System.out.println("------------------------------------------------");
        // 查找字符串
        pos = str1.indexOf("day");
        System.out.println("pos = " + pos); // -1
        pos = str1.indexOf("Day");
        System.out.println("pos = " + pos); // 17   字符串中第一个字符的下标
        pos = str1.indexOf("Day", 17);
        System.out.println("pos = " + pos); // 17   字符串中第一个字符的下标
        pos = str1.indexOf("Day", 18);
        System.out.println("pos = " + pos); // 21   字符串中第一个字符的下标

        System.out.println("------------------------------------------------");
        // 编写通用代码实现将字符串str1中所有"Day"出现的索引位置找到并打印出来
        pos = str1.indexOf("Day");
        while (-1 != pos) {
            System.out.println("pos = " + pos); // 17
            pos = str1.indexOf("Day", pos+1);
        }

        System.out.println("------------------------------------------------");
        // 优化一下
        pos = 0;
        while ((pos = str1.indexOf("Day",pos)) != -1) {
            System.out.println("pos = " + pos);
            pos += "Day".length();
        }

        System.out.println("------------------------------------------------");
        // 3.实现字符和字符串的内容反向查找
        pos = str1.lastIndexOf("G");
        System.out.println("pos = " + pos); // 5
        // 从下标5的位置开始反向查找
        pos = str1.lastIndexOf('G', 5);
        System.out.println("pos = " + pos); // 5

        pos = str1.lastIndexOf('G', 6);
        System.out.println("pos = " + pos); // 5

        pos = str1.lastIndexOf('G', 4);
        System.out.println("pos = " + pos); // 0

        System.out.println("------------------------------------------------");
        pos = str1.lastIndexOf("Day");
        System.out.println("pos = " + pos); // 21
        pos = str1.lastIndexOf("Day",21);
        System.out.println("pos = " + pos); // 21
        pos = str1.lastIndexOf("Day",20);
        System.out.println("pos = " + pos); // 17
        pos = str1.lastIndexOf("Day",15);
        System.out.println("pos = " + pos); // -1*/
        // 构造String类型对象并打印
        String str = new String("Good Good Study, Day Day Up!");
        System.out.println("str = " + str); // I Love You!
        // 返回当前字符串中参数指定的字符第一次出现的下标
        System.out.println(str.indexOf("G")); // 0
        System.out.println(str.indexOf("g")); // -1
        // 用于返回指定位置的字符
        System.out.println(str.indexOf("d", 4)); // 8
        // 用于返回当前字符串中参数指定的字符串第一次出现的下标
        System.out.println(str.indexOf("Day")); // 17
        System.out.println(str.indexOf("day")); // -1
        // 用于返回指定位置的字符串
        System.out.println(str.indexOf("Day", 15)); // 17
        System.out.println(str.indexOf("Day", 20)); // 21
        // 反向查找
        System.out.println(str.lastIndexOf('G')); // 5
        System.out.println(str.lastIndexOf('G', 5)); // 5
        System.out.println(str.lastIndexOf('G', 4)); // 0
        System.out.println(str.lastIndexOf('G', 8)); // 5
        System.out.println(str.lastIndexOf("Day")); // 21
        System.out.println(str.lastIndexOf("Day", 20)); // 17
        System.out.println(str.lastIndexOf("Day", 21)); // 21
        System.out.println(str.lastIndexOf("Day", 22)); // 21
    }
}
