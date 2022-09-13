package com.lagou.task22;

import java.util.Arrays;
import java.util.Comparator;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class MethodReferenceTest {

    public static void main(String[] args) {

        Person person = new Person("zhangfei", 30);
        // 1.使用匿名内部类的方式通过函数式接口Runnable中的方法实现对Person类中show方法的调用
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                person.show();
            }
        };
        runnable.run(); // 没事出来秀一下哦！

        System.out.println("-----------------------------------------------");
        // 2.使用Lambda表达式的方式实现Person类中show方法的调用
        Runnable runnable1 = () -> person.show();
        runnable1.run(); // 没事出来秀一下哦！

        System.out.println("------------------------------------------------");
        // 3.使用方法引用的方式实现Person类中show方法的调用
        Runnable runnable2 = person::show;
        runnable2.run();

        System.out.println("------------------------------------------------");
        // 4.使用匿名内部类的方式通过函数式接口Consumer中的方法来实现Person类中setName方法的调用
        Consumer<String> consumer = new Consumer<String>() {
            @Override
            public void accept(String s) {
                person.setName(s);
            }
        };
        consumer.accept("guanyu");
        System.out.println("person = " + person); // guanyu 30

        System.out.println("------------------------------------------------");
        // 5.使用Lambda表达式的方式实现Person类中setName方法的调用
        Consumer<String> consumer1 = s -> person.setName(s);
        consumer1.accept("liubei");
        System.out.println("person = " + person); // liubei 30

        System.out.println("------------------------------------------------");
        // 6.使用方法引用的方式实现Person类中setName方法的调用
        Consumer<String> consumer2 = person::setName;
        consumer2.accept("zhangfei");
        System.out.println("person = " + person); // zhangfei 30

        System.out.println("------------------------------------------------");
        // 7.使用匿名内部类的方式通过函数式接口Supplier中的方法来实现Person类中getName方法的调用
        Supplier<String> supplier = new Supplier<String>() {
            @Override
            public String get() {
                return person.getName();
            }
        };
        System.out.println(supplier.get()); // zhangfei

        Supplier<String> supplier1 = () -> person.getName();
        System.out.println(supplier1.get()); // zhangfei

        Supplier<String> supplier2 = person::getName;
        System.out.println(supplier2.get()); // zhangfei

        System.out.println("------------------------------------------------");
        // 8.使用匿名内部类的方式通过函数式接口Function中的方法实现Integer类中parseInt方法的调用
        Function<String, Integer> function = new Function<String, Integer>() {
            @Override
            public Integer apply(String s) {
                return Integer.parseInt(s);
            }
        };
        System.out.println(function.apply("123456")); // 123456

        Function<String, Integer> function1 = s -> Integer.parseInt(s);
        System.out.println(function1.apply("123456")); // 123456

        Function<String, Integer> function2 = Integer::parseInt;
        System.out.println(function2.apply("123456")); // 123456

        System.out.println("------------------------------------------------");
        // 9.使用匿名内部类的方式通过函数式接口Comparator中的方法实现Integer类中compare方法的调用
        Comparator<Integer> comparator = new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return Integer.compare(o1, o2);
            }
        };
        System.out.println(comparator.compare(10, 20)); // -1

        Comparator<Integer> comparator1 = (o1, o2) -> Integer.compare(o1,o2);
        System.out.println(comparator1.compare(10, 20)); // -1

        Comparator<Integer> comparator2 = Integer::compare;
        System.out.println(comparator2.compare(10, 20)); // -1

        System.out.println("------------------------------------------------");
        // 10.使用匿名内部类的方式通过类名来非静态方法
        // 其中一个参数对象作为调用对象来调用方法时，可以使用上述方式  更抽象
        Comparator<Integer> comparator3 = new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1.compareTo(o2);
            }
        };
        System.out.println(comparator3.compare(10, 20)); // -1

        Comparator<Integer> comparator4 = (o1, o2) -> o1.compareTo(o2);
        System.out.println(comparator4.compare(10, 20)); // -1

        Comparator<Integer> comparator5 = Integer::compareTo;
        System.out.println(comparator5.compare(10, 20)); // -1

        System.out.println("------------------------------------------------");
        // 11.使用匿名内部类的方式通过Supplier函数式接口创建Person类型的对象并返回
        Supplier<Person> supplier3 = new Supplier<Person>() {
            @Override
            public Person get() {
                return new Person();
            }
        };
        System.out.println(supplier3.get()); // null 0

        Supplier<Person> supplier4 = () -> new Person();
        System.out.println(supplier4.get()); // null 0

        Supplier<Person> supplier5 = Person::new;
        System.out.println(supplier5.get()); // null 0

        // 12.使用匿名内部类的方式通过BiFunction函数式接口采用有参方式创建Person类型的对象并返回
        BiFunction<String, Integer, Person> biFunction = new BiFunction<String, Integer, Person>() {
            @Override
            public Person apply(String s, Integer integer) {
                return new Person(s,integer);
            }
        };
        System.out.println(biFunction.apply("zhangfei", 30)); // zhangfei 30

        BiFunction<String, Integer, Person> biFunction1 = (s, integer) -> new Person(s, integer);
        System.out.println(biFunction1.apply("zhangfei", 30)); // zhangfei 30

        BiFunction<String, Integer, Person> biFunction2 = Person::new;
        System.out.println(biFunction2.apply("zhangfei", 30)); // zhangfei 30

        System.out.println("------------------------------------------------");
        // 13.使用匿名内部类的方式通过Function函数式接口创建指定数量的Person类型的对象数组并返回
        Function<Integer, Person[]> function3 = new Function<Integer, Person[]>() {
            @Override
            public Person[] apply(Integer integer) {
                return new Person[integer];
            }
        };
        Person[] pArr = function3.apply(3);
        System.out.println(Arrays.toString(pArr));

        Function<Integer, Person[]> function4 = integer -> new Person[integer];
        Person[] pArr1 = function4.apply(4);
        System.out.println(Arrays.toString(pArr1));

        Function<Integer, Person[]> function5 = Person[]::new;
        System.out.println(Arrays.toString(function5.apply(5)));
    }
}
