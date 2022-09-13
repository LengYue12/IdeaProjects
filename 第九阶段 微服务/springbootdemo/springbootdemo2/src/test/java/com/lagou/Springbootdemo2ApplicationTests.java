package com.lagou;

import com.lagou.controller.HelloController;
import com.lagou.pojo.Person;
import com.lagou.pojo.Product;
import com.lagou.pojo.Student;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)  // @RunWith：运行器   SpringJUnit4ClassRunner.class:Spring运行环境   junit：junit测试环境
@SpringBootTest // 标记当前类为SpringBoot的测试类，会加载项目的ApplicationContext上下文环境
class Springbootdemo2ApplicationTests {

    /**
     * 需求：调用HelloController的hello方法
     */

    @Autowired
    private HelloController helloController;

    @Test
    void contextLoads() {
        String hello = helloController.hello();
        System.out.println("hello = " + hello);
    }

    @Autowired
    private Person person;

    @Test
    void showPersonInfo(){
        System.out.println(person);
    }

    @Autowired
    private Student student;

    @Test
    public void showStudentInfo(){

        System.out.println(student);
    }

    @Autowired
    private Product product;
    @Test
    public void showProductInfo(){

        System.out.println(product);
    }


    @Autowired
    private ApplicationContext applicationContext;

    @Test
    public void testConfig(){
        System.out.println(applicationContext.containsBean("myService"));
        System.out.println(applicationContext.containsBean("service_"));
    }
}
