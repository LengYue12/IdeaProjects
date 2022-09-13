package com.lagou.demo;

/**
 * @author 张舒
 * @date 2022/5/8 23:46
 * @description
 */

/**
 * debug 运行，操作debug 排查错误
 */
public class Demo1 {

    public static void main(String[] args) {
        System.out.println(0);
        test01();
    }

    public static void test01(){
        System.out.println(1);
        System.out.println(2);
        int x = 33;
        test02(x);
        System.out.println(4);
    }

    public static void test02(int x){
        System.out.println(5);
        test03();
    }

    public static void test03(){
        System.out.println(6);
    }
}
