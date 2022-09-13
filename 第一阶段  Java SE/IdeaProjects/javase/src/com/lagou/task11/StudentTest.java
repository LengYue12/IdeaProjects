package com.lagou.task11;

public class StudentTest {

    public static void main(String[] args) {

        // 1.使用有参方式构造Student类型的两个对象并判断是否相等
        Student student = new Student(1001, "张飞");
        //Student student1 = new Student(1002, "关羽");
        Student student1 = new Student(1001, "张飞");
        //Student student1 = student; // 表示student和student1都指向了同一个对象，地址相同了
        // 下面调用从Object类中继承下来的equals方法，该方法默认比较两个对象的地址，可以查看源码验证
        // 当Student类中重写equals方法后，则调用重写以后的版本，比较内容
        //boolean b1 = student.equals(student1);
        //Student student2 = null;
        //boolean b1 = student.equals(student2);
        //Student student2 = student;
        boolean b1 = student.equals(student1);
        System.out.println("b1 = " + b1); // false  true
        System.out.println(student == student1); // 比较地址  false

        System.out.println("-----------------------------------------");
        // 下面调用从Object类中继承下来的hashCode方法，获取调用对象的哈希码值(内存地址的编号)
        // 当Student类中重写hashCode方法后，则调用重写以后的版本
        int ia = student.hashCode();
        int ib = student1.hashCode();
        System.out.println("ia = " + ia);
        System.out.println("ib = " + ib);

        System.out.println("-----------------------------------------");
        // 下面调用从Object类中继承下来的toString方法，获取调用对象的字符串形式：包名.类名@哈希码值的十六进制
        // 当Student类中重写toString方法后，则调用重写以后的版本：Student[id = 1001, name = zhangfei]
        String string = student.toString();
        System.out.println("string = " + string); // com.lagou.task11.Student@55d
        System.out.println(student); // 当打印一个引用变量时会自动调用toString方法
        String string1 = "hello" + student;
        System.out.println("string1 = " + string1);
    }
}
