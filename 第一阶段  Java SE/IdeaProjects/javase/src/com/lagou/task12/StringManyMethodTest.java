package com.lagou.task12;

public class StringManyMethodTest {

    public static void main(String[] args) {

        /*// 1.构造String类型的对象并打印
        String str1 = new String("     Let Me Give You Some Color To See See!");
        System.out.println("str1 =  " + str1); //      Let Me Give You Some Color To See See!

        // 2.实现各种成员方法的调用和测试
        boolean b1 = str1.contains("some");
        System.out.println("b1 = " + b1); // false 区分大小写
        b1 = str1.contains("Some");
        System.out.println("b1 = " + b1); // true

        System.out.println("------------------------------------------");
        // 将所有字符串转换为大写   小写  以及去除两边的空白字符
        String str2 = str1.toUpperCase();
        System.out.println("str2 = " + str2); //     LET ME GIVE YOU SOME COLOR TO SEE SEE!
        System.out.println("str1 = " + str1); //     Let Me Give You Some Color To See See!   常量

        String str3 = str1.toLowerCase();
        System.out.println("str3 = " + str3); //     let me give you some color to see see!
        System.out.println("str1 = " + str1); //     Let Me Give You Some Color To See See!

        String str4 = str1.trim();
        System.out.println("str4 = " + str4); //Let Me Give You Some Color To See See!  奇点

        System.out.println("------------------------------------");
        // 判断字符串是否以...开头    以...结尾
        b1 = str1.startsWith("Let");
        System.out.println("b1 = " + b1); // false
        b1 = str1.startsWith(" ");
        System.out.println("b1 = " + b1); // true
        // 从下标5开始是否以"Let"开头
        b1 = str1.startsWith("Let", 5);
        System.out.println("b1 = " + b1); // true

        b1 = str1.endsWith("See");
        System.out.println("b1 = " + b1); // false
        b1 = str1.endsWith("See!");
        System.out.println("b1 = " + b1); // true*/
        // 构造String类型对象并打印
        String str = new String("     I Love You!");
        System.out.println("str = " + str); //     I Love You!
        // 判断当前字符串是否包含参数指定的内容
        boolean b1 = str.contains("love");
        System.out.println("b1 = " + b1); // false
        b1 = str.contains("Love");
        System.out.println("b1 = " + b1); // true
        // 返回字符串小写形式
        String str1 = str.toLowerCase();
        System.out.println("str1 = " + str1); //     i love you!
        // 返回字符串大写形式
        String str2 = str.toUpperCase();
        System.out.println("str2 = " + str2); //     I LOVE YOU!
        // 去掉两边空白字符
        String str3 = str.trim();
        System.out.println("str3 = " + str3); //I Love You!
        // 判断字符串是否以参数字符串开头
        b1 = str.startsWith("I");
        System.out.println("b1 = " + b1); // false
        b1 = str.startsWith(" ");
        System.out.println("b1 = " + b1); // true
        // 从指定位置开始是否以参数字符串开头
        b1 = str.startsWith("I", 5);
        System.out.println("b1 = " + b1); // true
        // 判断字符串是否以参数字符串结尾
        b1 = str.endsWith("You");
        System.out.println("b1 = " + b1); // false
        b1 = str.endsWith("You!");
        System.out.println("b1 = " + b1); // true

    }
}
