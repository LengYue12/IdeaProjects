package com.lagou.task22;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ListPersonTest {

    public static void main(String[] args) {

        // 1.准备一个List集合并放入Person类型的对象后打印
        List<Person> list = new LinkedList<>();
        list.add(new Person("zhangfei",30));
        list.add(new Person("xiaoqiao", 17));
        list.add(new Person("zhouyu", 20));
        list.add(new Person("zhangfei",30));
        list.add(new Person("guanyu", 35));
        list.add(new Person("liubei",40));
        for (Person tp: list){
            System.out.println(tp);
        }

        System.out.println("----------------------------------------------------");
        // 2.将List集合中所有成年人过滤出来并放入另外一个集合中打印
        List<Person> list1 = new LinkedList<>();
        for (Person tp: list) {
            if (tp.getAge() >= 18){
                list1.add(tp);
            }
        }
        for (Person tp: list1){
            System.out.println(tp);
        }

        System.out.println("----------------------------------------------------");
        // 3.使用Stream接口实现上述功能
        list.stream().filter(new Predicate<Person>() {
            @Override
            public boolean test(Person person) {
                return person.getAge() >= 18;
            }
        }).forEach(new Consumer<Person>() {
            @Override
            public void accept(Person person) {
                System.out.println(person);
            }
        });

        System.out.println("----------------------------------------------------");
        // 4.使用Lambda表达式对上述代码进行优化
//        list.stream().filter(person -> person.getAge() >= 18).forEach(person -> System.out.println(person));
        list.stream().filter(person -> person.getAge() >= 18).forEach(System.out::println);

        System.out.println("----------------------------------------------------");
        // 5.实现对集合中元素通过流跳过2个元素后再取3个元素后打印
        list.stream().skip(2).limit(3).forEach(System.out::println);

        System.out.println("----------------------------------------------------");
        // 6.实现将集合中所有元素中的年龄获取出来并打印
        list.stream().map(new Function<Person, Integer>() {
            @Override
            public Integer apply(Person person) {
                return person.getAge();
            }
        }).forEach(System.out::println);

        //list.stream().map(person -> person.getAge()).forEach(System.out::println);
        list.stream().map(Person::getAge).forEach(System.out::println);

        System.out.println("----------------------------------------------------");
        // 7.实现集合中所有元素的自然排序并打印
        list.stream().sorted().forEach(System.out::println);

        System.out.println("----------------------------------------------------");
        // 8.判断集合中是否没有元素的年龄是大于45岁的
        boolean b1 = list.stream().noneMatch(new Predicate<Person>() {
            @Override
            public boolean test(Person person) {
                return person.getAge() > 45;
            }
        });
        System.out.println(b1); // true

        b1 = list.stream().noneMatch(person -> person.getAge() > 45);
        System.out.println(b1); // true

        System.out.println("----------------------------------------------------");
        // 9.按照指定的比较器规则获取集合所有元素中的最大值
        Optional<Person> max = list.stream().max(new Comparator<Person>() {
            @Override
            public int compare(Person o1, Person o2) {
                return o1.getAge() - o2.getAge();
            }
        });
        System.out.println("按照年龄排序后的最大值是：" + max);

        max = list.stream().max(((o1, o2) -> o1.getAge()-o2.getAge()));
        System.out.println("按照年龄排序后的最大值是：" + max);

        System.out.println("----------------------------------------------------");
        // 10.实现将集合中所有元素的年龄映射出来并进行累加后打印
        Optional<Integer> reduce = list.stream().map(Person::getAge).reduce(new BinaryOperator<Integer>() {
            @Override
            public Integer apply(Integer integer, Integer integer2) {
                return integer + integer2;
            }
        });
        System.out.println("最终所有年龄的累加和是：" + reduce); // 172

        reduce = list.stream().map(Person::getAge).reduce(Integer::sum);
        System.out.println("最终所有年龄的累加和是：" + reduce); // 172

        System.out.println("----------------------------------------------------");
        // 11.实现将集合中所有元素的姓名映射出来并收集到集合中打印
        list.stream().map(Person::getName).collect(Collectors.toList()).forEach(System.out::println);
    }
}
