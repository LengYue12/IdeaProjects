package com.lagou.task14;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class CollectionTest {

    public static void main(String[] args) {

        // 1.准备一个Collection集合并打印
        // 接口类型的引用指向实现类的对象形成多态
        Collection c1 = new ArrayList();
        // 自动调用toString方法，调用ArrayList类中的toString方法
        System.out.println("集合中的元素有：" + c1);

        System.out.println("------------------------------------------------");
        // 2.向集合中添加单个元素并打印
        boolean b1 = c1.add(new String("one"));
        System.out.println("b1 = " + b1);
        System.out.println("集合中的元素有：" + c1); // [one]
        b1 = c1.add(Integer.valueOf(2));
        System.out.println("b1 = " + b1);
        System.out.println("集合中的元素有：" + c1); // [one, 2]
        b1 = c1.add(new Person("zhangfei", 30));
        System.out.println("b1 = " + b1);
        // 打印集合中的所有元素时，本质上就是打印集合中的每个对象，也就是让每个对象调用对应类的toString方法
        System.out.println("集合中的元素有：" + c1); // [one, 2, Person{name='zhangfei', age=30}]

        System.out.println("------------------------------------------------");
        // 3.向集合中添加多个元素并打印
        Collection c2 = new ArrayList();
        c2.add("three"); // 常量池
        c2.add(4);       // 自动装箱机制
        System.out.println(c2); // [three, 4]
        // 将c2中的所有元素全部添加到集合c1中，也就是将集合c2中的元素一个一个依次添加到集合c1中
        c1.addAll(c2);
        // 表示将集合c2整体看做一个元素添加到集合c1中
        //c1.add(c2);
        System.out.println(c1); // [one, 2, Person{name= 'zhangfei', age=30}, three, 4]
        System.out.println("------------------------------------------------");
        // 4.判断集合中是否包含参数指定的单个元素
        b1 = c1.contains(new String("one"));
        System.out.println(b1); // true
    }
}
