package com.lagou.demo;

/**
 * @author 张舒
 * @date 2022/5/8 23:46
 * @description
 */

/**
 * debug 调试错误，查看参数
 * 1.debug 查看参数传递，跟踪参数对应的变化
 *
 *
 * 打断点的原则：调试的两种不同的目的，打断点的方式也不同
 *  1）查看，目的只是想跟踪，可以在方法的第一行打断点，和你关心的参数使用处打断点
 *  2）调试错误，可以在出错行的前一行或出错行打断点
 */
public class Demo2 {

    public static void main(String[] args) {
        Student student1 = new Student();
        student1.setName("小青");
        student1.setAge(25);
        System.out.println(student1.getName() + student1.getAge());


        Student student2 = new Student();
        student2.setName("香香");
        student2.setAge(29);
        System.out.println(student2.getName() + student2.getAge());

        // 如果student2 == null
        student2 = new Student();
        test03(student2);
    }

    public static void test03(Student student){
        System.out.println(student);
        System.out.println(student.getName() + student.getAge());
    }
}
