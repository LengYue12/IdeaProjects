# Spring Boot

# 1、约定优于配置

Spring Boot 是所有基于 Spring 开发的项目的起点。Spring
Boot 的设计是为了让你尽可能快的跑起来 Spring 应用程序并且尽可能减少你的配置文件



Spring Boot是基于约定优于配置的软件设计范式的

约定优于配置是一种软件设计范式，就是遵循约定

Spring Boot 会 提供大量的默认的配置，如果遵循默认配置，什么都不需要动，不用写配置文件。就不影响功能的实现。但是如果想有特殊的配置，还是需要单独配置的



**Spring Boot并没有引入新的功能，而是简化原有的开发方式**



## 1.2 Spring Boot概念

### 1.2.1 Spring优缺点分析

**优点**

Spring为企业级Java开发提供了一种相对简单的方法，通过IOC（实现组件的解耦）和AOP（实现组件的增强处理）

**缺点**

**Spring的组件代码是轻量级的，但它的配置却是重量级的。**

Spring是一个 重配置而轻代码的框架，配置比较繁重，影响开发效率，虽然引入了基于注解的组件扫描，但是配置文件里要写的代码还是很多



**项目的依赖管理很麻烦**。引入依赖的数量繁多，容易存在版本冲突。



### 1.2.2 Spring Boot就解决了上述Spring的问题

Spring Boot对上述Spring的缺点进行的改善和优化，基于约定优于配置的思想，可以让开发人员不必在配置与逻辑 业务之间进行思维的切换，全身心的投入到逻辑业务的代码编写中，从而大大提高了开发的效率，一定程度上缩短 了项目周期

**起步依赖**

简单的说，起步依赖就是将具备某种功能的依赖坐标打包到一起，并提供一些默认的功能

**自动配置**

springboot的自动配置，指的是springboot，会自动将一些配置类的bean注册进ioc容器，我们可以需要的地方使用@autowired或者@resource等注解来使用它。

也就是说以后想使用什么依赖，不用配置了，直接引jar包，Spring Boot 自动配置

springboot: 简单、快速、方便地搭建项目；对主流开发框架的无配置集成；极大提高了开发、部署效率



## 1.3 Spring Boot 入门案例

案例需求：请求Controller中的方法，并将返回值响应到页面

### 1）依赖管理

```xml
<!--
    所有的springBoot项目都会直接或者间接地继承 spring-boot-starter-parent
        1. 指定项目的编码格式为UTF-8
        2. 指定JDK版本为1.8
        3. 对项目依赖的版本进行管理，当前项目再引入其他常用的依赖时，就不需要再指定版本号了，避免版本冲突的问题
        4. 默认的资源过滤和插件管理
    -->
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>2.7.2</version>
    </parent>

    <dependencies>
<!--        引入Spring Web 及 Spring MVC相关的依赖-->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
    </dependencies>

<!--  可以将project打包为一个可以执行的jar  -->
    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
        </plugins>
    </build>
```

### 2）启动类

```java
/**
 * @BelongsProject springbootdemo
 * @Author lengy
 * @CreateTime 2022/8/12 19:46
 * @Description SpringBoot的启动类，通常放在二级包中，比如  com.lagou.SpringBootDemo1Application
 * 因为SpringBoot项目在做包扫描时，会扫描启动类所在的包及其子包下的所有内容
 */

// 标识当前类为SpringBoot项目的启动类
@SpringBootApplication
public class SpringBootDemo1Application {

    public static void main(String[] args) {
        // 样板代码
        // SpringApplication.run(当前类的字节码文件,main函数的方法参数);
        SpringApplication.run(SpringBootDemo1Application.class,args);
    }
}
```

### 3）Controller

```java
@RestController     // 标识这个Controller中返回JSON
@RequestMapping("/hello")
public class HelloController {

    @RequestMapping("/boot")
    public String helloBoot(){

        return "Hello Spring Boot";
    }
}
```



## 1.4 Spring Boot 快速构建

案例需求：请求Controller中的方法，并将返回值响应到页面

### 1）使用Spring Initializr方式构建Spring Boot项目

​		本质上说，Spring Initializr是一个Web应用，它提供了一个基本的项目结构，能够帮助我们快速构建一个基础的Spring Boot 项目	

使用Spring Initializr方式构建的Spring Boot项目会默认生成项目启动类、存放前端静态资源和页
面的文件夹、编写项目配置的配置文件以及进行项目单元测试的测试类



### 2）创建一个用于 Web访问的Controller

com.lagou包下创建名称为controller的包，在该包下创建一个请求处理控制类HelloController，
并编写一个请求处理方法 (**注意：将项目启动类SpringBootDemoApplication移动到com.lagou包下**)

```java
@RestController	// 该注解为组合注解，等同于Spring中@Controller+@ResponseBody注解,标识当前controller所返回的所有信息都是JSON
@RequestMapping("/hello")
public class HelloController {

    @RequestMapping("/boot")
    public String hello(){

        return "hello Spring Boot!";
    }
```

### 3）运行项目

运行主程序启动类SpringbootDemoApplication，项目启动成功后，在控制台上会发现Spring
Boot项目默认启动的端口号为8080，此时，可以在浏览器上访问“http://localhost:8080/hello”

页面输出的内容是“hello Spring Boot”，至此，构建Spring Boot项目就完成了





## 1.5 单元测试与热部署

### 1.5.1 单元测试

开发中，每当完成一个功能接口或业务方法的编写后，通常都会借助单元测试验证该功能是否正
确。Spring Boot对项目的单元测试提供了很好的支持，在使用时，需要提前在项目的pom.xml文件中添加spring-boot-starter-test测试依赖启动器，可以通过相关注解实现单元测试

演示：
1．添加spring-boot-starter-test测试依赖启动器
在项目的pom.xml文件中添加spring-boot-starter-test测试依赖启动器，示例代码如下 ：

```xml
<dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
</dependency>
```

注意：使用Spring Initializr方式搭建的Spring Boot项目，会自动加入spring-boot-starter-test测试依赖启动器，无需再手动添加

2. 编写单元测试类和测试方法

使用Spring Initializr方式搭建的Spring Boot项目，会在src.test.java测试目录下自动创建与项目主程序启动类对应的单元测试类

```java
/**
* SpringJUnit4ClassRunner.class:Spring运行环境
* JUnit4.class:JUnit运行环境
* SpringRunner.class:Spring Boot运行环境
*/
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
}
```

上述代码中，先使用@Autowired注解注入了helloController实例对象，然后在contextLoads()方
法中调用了helloController类中对应的请求控制方法contextLoads()，并输出打印结果

---

**若想在Spring Boot 项目中完成单元测试**

* 引入依赖
* 定义类，并加注解 @SpringBootTest  表示标记当前类为SpringBoot的测试类，会加载项目的ApplicationContext上下文环境
* 指定运行器，标识当前以Spring Boot的运行环境来运行
  * @RunWith(SpringRunner.class)  



### 1.5.2 热部署

在开发过程中，通常会对一段业务代码不断地修改测试，在修改之后往往需要重启服务，有些服务
需要加载很久才能启动成功，这种不必要的重复操作极大的降低了程序开发效率。为此，Spring Boot框架专门提供了进行热部署的依赖启动器，用于进行项目热部署，而无需手动重启项目 。

热部署：在修改完代码之后，不需要重新启动容器，就可以实现更新。

使用步骤：

1）添加Spring Boot的热部署依赖启动器

2）开启Idea的自动编译

3）开启Idea的在项目运行中自动编译的功能





1.**添加热部署依赖**

在Spring Boot项目进行热部署测试之前，需要先在项目的pom.xml文件中添加spring-boot-devtools热部署依赖启动器:

```xml
<!--        引入热部署依赖-->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-devtools</artifactId>
        </dependency>
```



## 1.6 全局配置文件

全局配置文件能够对一些默认配置值进行修改。Spring Boot使用一个application.properties或者
application.yaml的文件作为全局配置文件，该文件存放在src/main/resource目录或者类路径
的/config，一般会选择resource目录

Spring Boot配置文件的命名及其格式：

* application.properties
* application.yaml
* application.yml



### 1.6.1 application.properties配置文件

使用Spring Initializr方式构建Spring Boot项目时，会在resource目录下自动生成一个空的
application.properties文件，Spring Boot项目启动时会自动加载application.properties文件。
我们可以在application.properties文件中定义Spring Boot项目的相关属性，当然，这些相关属性可以是系统属性、环境变量、命令参数等信息，也可以是自定义配置文件名称和位置

```properties
# 修改Tomcat的版本号
server.port=8080
# 定义数据库的连接信息    JdbcTemplate
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.datasource.url=jdbc:mysql://localhost:3306/lagou
spring.datasource.username=root
spring.datasource.password=123456
```

准备了两个实体类文件，后续会演示将application.properties配置文件中的自定义配置属性注入到
Person实体类的对应属性中

1）先在项目的com.lagou包下创建一个pojo包，并在该包下创建两个实体类Pet和Person

```java
/**
 * @BelongsProject springbootdemo
 * @Author lengy
 * @CreateTime 2022/8/12 21:23
 * @Description 宠物类
 */
public class Pet {

    private String type;    // 品种
    private String name;    // 名称
```

```java
/**
 * @BelongsProject springbootdemo
 * @Author lengy
 * @CreateTime 2022/8/12 21:25
 * @Description 人类
 */
@Component
// 将配置文件中所有以person开头的配置信息注入到当前类中
// 前提1：必须保证配置文件中person.xxx与当前person类的属性名一致
// 前提2：必须保证当前person类中的属性都具有set方法
@ConfigurationProperties(prefix = "person")
public class Person {
    private int id; //id
    private String name;    // 名称
    private List hobby;     // 爱好
    private String[] family;    // 家庭成员
    private Map map;
    private Pet pet;    // 宠物

    // getter和setter..
}
```

@ConfigurationProperties(prefix = "person")注解的作用是将配置文件中以person开头的属性值通过setXX()方法注入到实体类对应属性中

@Component注解的作用是将当前注入属性值的Person类对象作为Bean组件放到Spring容器中，只有这样才能被@ConfigurationProperties注解进行赋值



2）打开项目的resources目录下的application.properties配置文件，在该配置文件中编写需要对
Person类设置的配置属性

```properties
# 自定义配置信息注入到Person对象中
person.id=100
person.name=蔡徐坤
# list
person.hobby=唱跳,Rap,篮球
# String[]
person.family=粉丝,荔枝
# map
person.map.k1=v1
person.map.k2=v2
# 宠物对象
person.pet.type=dog
person.pet.name=狗剩
```



3）查看application.properties配置文件是否正确，同时查看属性配置效果，打开通过IDEA工具创建
的项目测试类，在该测试类中引入Person实体类Bean，并进行输出测试

```java
@RunWith(SpringRunner.class) // 测试启动器，并加载Spring Boot测试注解
@SpringBootTest  // 标记为Spring Boot单元测试类，并加载项目的ApplicationContext上下文环境
class Springbootdemo2ApplicationTests {
    
	// 配置测试
    @Autowired
    private Person person;

    @Test
    void showPersonInfo(){
        System.out.println(person);
    }
```



4）中文乱码问题

调整文件编码格式

设置Tomcat及Http编码

```properties
#解决中文乱码
server.tomcat.uri-encoding=UTF-8
spring.http.encoding.force=true
spring.http.encoding.charset=UTF-8
spring.http.encoding.enabled=true
```



### 1.6.2 application.yaml 配置文件

YAML文件格式是Spring Boot支持的一种JSON文件格式，相较于传统的Properties配置文件，YAML文件以数据为核心，是一种更为直观且容易被电脑识别的数据序列化格式。application.yaml配置文件的工作原理和application.properties是一样的，只不过yaml格式配置文件看起来更简洁一些

* YAML文件的扩展名可以使用.yml或者.yaml。
* application.yml文件使用 “key:（空格）value”格式配置属性，使用缩进控制**层级关系**。



Spring Boot可以存在0-N个配置文件，但是名称都是application，后缀yml，yaml，properties

**Spring Boot的三种配置文件是可以共存的**

这里，针对不同数据类型的属性值，介绍一下YAML

```yml
person:
  id: 1000
  name: 吴亦凡
  hobby:
    - 抽烟
    - 强奸
    - 大碗
  family:
    - 粉丝
    - 宽面
  map:
    k1: 这是k1对应的value
    k2: 这是k2对应的value
  pet:
    type: dog
    name: 金毛
```

1）value值为普通数据类型（例如数字、字符串、布尔等）

当YAML配置文件中配置的属性值为普通数据类型时，可以直接配置对应的属性值，同时对于字符
串类型的属性值，不需要额外添加引号，示例代码如下

```yaml
server:
 port: 8080
 servlet:
  	context-path: /hello
```

2）value值为数组和单列集合

**当YAML配置文件中配置的属性值为数组或单列集合类型时，主要有两种书写方式：缩进式写法和行内式写法**

推荐使用：

```yaml
person:
 hobby:
  - play
  - read
  - sleep
```

（3）value值为Map集合和对象

```yaml
person:
 map:
  k1: v1
  k2: v2
```

接下来，在Properties配置文件演示案例基础上，通过配置application.yaml配置文件对Person对象进行赋值，具体使用如下
（1）在项目的resources目录下，新建一个application.yaml配置文件，在该配置文件中编写为Person类设置的配置属性

```yaml
#对实体类对象Person进行属性配置
person:
  id: 1000
  name: 吴亦凡
  hobby:
    - 唱歌
    - 牙签
    - 大碗
  family:
    - 粉丝
    - 宽面
  map:
    k1: 这是k1对应的value
    k2: 这是k2对应的value
  pet:
    type: dog
    name: 金毛
```

2）再次执行测试

本次使用application.yaml配置文件进行测试时需要提前将application.properties配置
文件中编写的配置注释，这是因为application.properties配置文件会覆盖application.yaml配置文件



## 1.7 配置文件属性值的注入

配置文件的优先级如下，从低到高，谁在最后，谁的优先级最高

```xml
<includes>
     <include>**/application*.yml</include>
     <include>**/application*.yaml</include>
     <include>**/application*.properties</include>
    </includes>
```



使用Spring Boot全局配置文件设置属性时：

如果配置属性是Spring Boot已有属性，例如服务端口server.port，那么Spring Boot内部会自动扫描并读取这些配置文件中的属性值并覆盖默认属性。

如果配置的属性是用户自定义属性，例如刚刚自定义的Person实体类属性，还必须在程序中注入这些配置属性方可生效。

Spring Boot支持多种注入配置文件属性的方式，下面来介绍如何使用注@ConfigurationProperties和 @Value注入属性



**在Spring Boot中，如何将配置文件中的值注入到程序中？**

使用@ConfigurationProperties注入多个属性，@Value注入单个属性

### 1.7.1 使用@ConfigurationProperties注入属性

Spring Boot提供的@ConfigurationProperties注解用来快速、方便地将配置文件中的自定义属性值批量注入到某个Bean对象的多个对应属性中。假设现在有一个配置文件，如果使用
@ConfigurationProperties注入配置文件的属性，示例代码如下：

```java
@Component
//将配置文件中所有以person开头的配置信息注入当前类中
//前提1：必须保证配置文件中person.xx与当前Person类的属性名一致
//前提2：必须保证当前Person中的属性都具有set方法
@ConfigurationProperties(prefix = "person")
public class Person {
  private int id;       //id
  private String name;    //名称
  private List hobby;    //爱好
  private String[] family;  //家庭成员
  private Map map;
  private Pet pet;      //宠物
}
```

上述代码使用@Component和@ConfigurationProperties(prefix = “person”)将配置文件中的每个属性映射到person类组件中。



### 1.7.2 使用@Value属性

@Value注解是Spring框架提供的，用来读取配置文件中的属性值并逐个注入到Bean对象的对应属性
中，Spring Boot框架从Spring框架中对@Value注解进行了默认继承，所以在Spring Boot框架中还可以使用该注解读取和注入配置文件属性值。使用@Value注入属性的示例代码如下

```java
@Component
public class Person {
@Value("${person.id}")
  private int id;   
}
```

上述代码中，使用@Component和@Value注入Person实体类的id属性。其中，@Value不仅可以
将配置文件的属性注入Person的id属性，还可以直接给id属性赋值，这点@ConfigurationProperties不支持的



演示@Value注解读取并注入配置文件属性的使用:
（1）在com.lagou.pojo包下新创建一个实体类Student，并使用@Value注解注入属性

```java
@Component
public class Student {

    @Value("${person.id}")
    private String number;
    @Value("${person.name}")
    private String name;
}
```

Student类使用@Value注解将配置文件的属性值读取和注入。
从上述示例代码可以看出，使用@Value注解方式需要对每一个属性注入设置，同时又免去了属性的
setXX()方法

（2）再次打开测试类进行测试

```java
@Autowired
private Student student;
@Test
public void studentTest() {
System.out.println(student);
}
```

**@Value注解对于包含Map集合、对象以及YAML文件格式的行内式写法的配置文件的属性注入都不支持，如果赋值会出现错误**





## 1.8 自定义配置

spring Boot免除了项目中大部分的手动配置，对于一些特定情况，我们可以通过修改全局配置文
件以适应具体生产环境，可以说，几乎所有的配置都可以写在application.yml文件中，Spring Boot会自动加载全局配置文件从而免除我们手动加载的烦恼。但是，如果我们自定义配置文件，Spring Boot是无法识别这些配置文件的，此时就需要我们手动加载。接下来，将针对Spring Boot的自定义配置文件及其加载方式进行讲解



### 1.8.1 使用@PropertySource加载配置文件

对于这种加载自定义配置文件的需求，可以使用@PropertySource注解来实现。
@PropertySource注解用于指定自定义配置文件的具体位置和名称

当然，如果需要将自定义配置文件中的属性值注入到对应类的属性中，可以使用
@ConfigurationProperties或者@Value注解进行属性值注入

（1）打开Spring Boot项目的resources目录，在项目的类路径下新建一个my.properties自定义配
置文件，在该配置文件中编写需要设置的配置属性

```properties
#对实体类对象MyProperties进行属性配置
product.id=99
product.name=华为
```

2）在com.lagou.pojo包下新创建一个配置类MyProperties，提供my.properties自定义配置文件
中对应的属性，并根据@PropertySource注解的使用进行相关配置

```java
@Component	// 自定义配置类
@PropertySource("classpath:my.properties") // 通过该注解加载外部的自定义的配置文件，参数是配置文件的路径
@ConfigurationProperties(prefix = "product")	// 指定配置文件注入属性前缀
public class Product {

    private int id;
    private String name;
  // 省略属性getXX()和setXX()方法
  // 省略toString()方法
}
```

主要是一个自定义配置类，通过相关注解引入了自定义的配置文件，并完成了自定义属性值的注
入。针对示例中的几个注解，具体说明如下

* @PropertySource("classpath:my.properties")注解指定了自定义配置文件的位置和名称，此示例表示自定义配置文件为classpath类路径下的my.properties文件；
* @ConfigurationProperties(prefix = "product")注解将上述自定义配置文件my.properties中以product开头的属性值注入到该配置类属性中。



3）进行测试

```java
@Autowired
 private MyProperties myProperties;
 @Test
 public void myPropertiesTest() {
  System.out.println(myProperties);
}
```



### 1.8.2 使用@Configuration编写自定义配置类

在Spring Boot框架中，**推荐使用配置类的方式向容器中添加和配置组件**



在Spring Boot框架中，通常使用@Configuration注解定义一个配置类，Spring Boot会自动扫描和识别配置类，从而替换传统Spring框架中的XML配置文件



当定义一个配置类后，还需要在类中的方法上使用@Bean注解进行组件配置，将方法的返回对象注入到Spring容器中，并且组件名称默认使用的是方法名，当然也可以使用@Bean注解的name或value属性自定义组件的名称



1）在项目下新建一个com.lagou.service包，并在该包下新创建一个类MyService，该类中不需要编写任何代码

```java
public class MyService {
}
```

创建了一个空的MyService类，而该类目前没有添加任何配置和注解，因此还无法正常被Spring Boot扫描和识别



2） 在项目的com.lagou.config包下，新建一个类MyConfig，并使用@Configuration注解将该类声明一个配置类，内容如下

```java
@Configuration  // 标识当前类是一个配置类，SpringBoot会扫描该类，将所有标识@Bean注解的方法的返回值注入到容器中
public class MyConfig {


    // 将返回值对象作为组件添加到Spring容器中，该组件id默认为方法名
    @Bean   // 注入的名称就是方法的名称,注入的类型就是返回值的类型
    public MyService myService(){
        return new MyService();
    }

    @Bean("service_")
    public MyService myService2(){
        return new MyService();
    }
}
```

MyConfig是@Configuration注解声明的配置类（类似于声明了一个XML配置文件），该配置类会
被Spring Boot自动扫描识别；使用@Bean注解的myService()方法，其返回值对象会作为组件添加到了Spring容器中（类似于XML配置文件中的标签配置），并且该组件的id默认是方法名myService



3）测试类

```java
@Autowired
private ApplicationContext applicationContext;
@Test
public void iocTest() {
System.out.println(applicationContext.containsBean("myService"));
}
```

上述代码中，先通过@Autowired注解引入了Spring容器实例ApplicationContext，然后在测试方法
iocTest()中测试查看该容器中是否包括id为myService的组件



从测试结果可以看出，测试方法iocTest()运行成功，显示运行结果为true，表示Spirng的IOC容器中也已经包含了id为myService的实例对象组件，说明使用自定义配置类的形式完成了向Spring容器进行组件的添加和配置





# 2、Spring Boot原理深入及源码

## 2.1 依赖管理

问题：（1）为什么导入dependency时不需要指定版本？

因为我们自己的pom.xml中有一个spring-boot-starter-parent父依赖，而它又继承了spring-boot-dependencies。在spring-boot-dependencies已经把常用的依赖的版本号都定义好了，所以我们导入依赖时就不需要指定版本了



**从spring-boot-dependencies底层源文件可以看出，该文件通过标签对一些常用技术框架的依赖文件进行了统一版本号管理，例如activemq、spring、tomcat等，都有与Spring Boot 2.2.2版本相匹配的版本，这也是pom.xml引入依赖文件不需要标注依赖文件版本号的原因**。

需要说明的是，如果pom.xml引入的依赖文件不是 spring-boot-starter-parent管理的，那么在
pom.xml引入依赖文件时，需要使用标签指定依赖文件的版本号



（2）问题2： spring-boot-starter-parent父依赖启动器的主要作用是进行版本统一管理，那么项目运行依赖的JAR包是从何而来的？

**以spring-boot-starter-web 依赖为例，里面已经有了Web开发需要的一堆依赖，所以导入这个依赖启动器，所需的依赖都有了**



查看spring-boot-starter-web依赖文件源码

spring-boot-starter-web依赖启动器的主要作用是提供Web开发场景所需的底层所有依赖

正是如此，在pom.xml中引入spring-boot-starter-web依赖启动器时，就可以实现Web场景开发，而不需要额外导入Tomcat服务器以及其他Web依赖文件等。当然，这些引入的依赖文件的版本号还是由spring-boot-starter-parent父依赖进行的统一管理





## 2.2 自动配置

概念：能够在我们添加jar包依赖的时候，自动为我们配置一些组件的相关配置，我们无需配置或者只需要少量配置就能运行编写的项目

问题：**Spring Boot到底是如何进行自动配置的，都把哪些组件进行了自动配置**？

Spring Boot应用的启动入口是@SpringBootApplication注解标注类中的main()方法，
@SpringBootApplication ： SpringBoot 应用标注在某个类上说明这个类是 SpringBoot 的主配置
类， SpringBoot 就应该运行这个类的 main() 方法启动 SpringBoot 应用。



@SpringBootApplication注解是一个组合注解，前面 4 个是注解的元数据
信息， 我们主要看后面 3 个注解：@SpringBootConfiguration、@EnableAutoConfiguration、
@ComponentScan三个核心注解



### 1. @SpringBootConfiguration注解

@SpringBootConfiguration ： SpringBoot 的配置类，标注在某个类上，表示这是一个 SpringBoot
的配置类。**@SpringBootConfiguration注解的作用与@Configuration注解相同，都是**
**标识一个可以被组件扫描器扫描的配置类，只不过@SpringBootConfiguration是被Spring Boot进行了重新封装命名而已**

### 2. @EnableAutoConfiguration注解

@EnableAutoConfiguration ：开启自动配置功能，以前由我们需要配置的东西，现在由 SpringBoot
帮我们自动配置，这个注解就是 Springboot 能实现自动配置的关键。



**自动配置的总结**

SpringBoot底层实现的自动配置的步骤：

1. springBoot应用启动
2. 在启动类上标识了@SpringBootApplication，表示当前类为配置类
3. @EnableAutoConfiguration ，表示启用自动配置
4. @AutoConfigurationPackage，这个组合注解主要是将Registrar类导入到容器中，而
   Registrar类作用是**扫描主配置类同级目录以及子包，并将相应的组件导入到springboot创建管理的容器中；**
5.  @Import(AutoConfigurationImportSelector.class)，它会将符合条件的配置类执行自动注入



### 3. @ComponentScan注解

@ComponentScan注解具体扫描的包的根路径由Spring Boot项目主程序启动类所在包位置决定，
在扫描过程中由前面介绍的@AutoConfigurationPackage注解进行解析，从而得到Spring Boot项目主程序启动类所在包的具体位置



正是因为以下的注解才能实现Spring Boot 的自动配置

```java
|- @SpringBootConfiguration	// 封装了Configuration，作用就是把当前配置类，添加到IOC
 |- @Configuration  //通过javaConfig的方式来添加组件到IOC容器中
|- @EnableAutoConfiguration	// 启用自动配置
 |- @AutoConfigurationPackage //自动配置包，与@ComponentScan扫描到的添加到IOC
 |- @Import(AutoConfigurationImportSelector.class) //到META-INF/spring.factories中定义的bean添加到IOC容器中
|- @ComponentScan //包扫描
```



# 3、SpringBoot数据访问

## 3.1 Spring Boot 整合Mybatis

​	因为Spring Boot框架开发的便利性，所以实现Spring Boot与数据访问层框架（例如MyBatis）的

整合非常简单，主要是引入对应的依赖启动器，并进行数据库相关参数设置即可



### 环境搭建

#### 1）数据准备

#### 2）创建项目，引入相应的启动器

#### 3）编写实体类

```java
public class Comment {
    private Integer id;
    private String content;
    private String author;
    private Integer aId;
}
```

```java
public class Article implements Serializable {
    /**
     * 文章id
     */
    private Integer id;

    /**
     * 文章标题
     */
    private String title;

    /**
     * 文章内容
     */
    private String content;
}
```

#### 4）编写配置文件

1）在application.properties配置文件中进行数据库连接配置

```yml
# MySQL数据库连接配置
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/springbootdata?serverTimezone=UTC&characterEncoding=UTF-8
    username: root
    password: 123456
```



### 注解方式整合Mybatis

需求：实现通过ID查询Comment信息

#### 1）创建接口CommentMapper

```java
/**
 * @BelongsProject springbootdemo
 * @Author lengy
 * @CreateTime 2022/8/14 20:58
 * @Description 通过注解实现根据id查找评论信息
 */
public interface CommentMapper {

    @Select("select * from t_comment where id = #{id}")
    Comment findById(Integer id);
}
```

#### 2）在Spring Boot项目启动类上添加@MapperScan("xxx")注解

```java
@SpringBootApplication
// 添加这个注解后，当启动类启动时，就会去mapper包下查找对应的mapper接口，并且生成对应的动态代理类
@MapperScan("com.lagou.mapper") // 指定扫描mapper的包名
public class BootmybatisApplication {

    public static void main(String[] args) {
        SpringApplication.run(BootmybatisApplication.class, args);
    }

}
```

#### 3）编写测试

```java
@RunWith(SpringRunner.class)
@SpringBootTest
class BootmybatisApplicationTests {

    @Autowired
    private CommentMapper commentMapper;

    @Test
    void findCommentById() {
        Comment byId = commentMapper.findById(1);
        System.out.println("byId = " + byId);
    }
}
```

在Spring Boot全局配置文件application.properties中添加开启驼峰命名匹配映射配置

```yaml
mybatis:
  configuration:
    map-underscore-to-camel-case: true #开启驼峰命名匹配映射
```



### 配置文件的方式整合Mybatis

使用插件生成了接口和映射文件

#### 1）创建接口ArticleMapper

```java
public interface ArticleMapper {
    Article selectByPrimaryKey(Integer id);
}
```

#### 2）创建XML映射文件

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.lagou.mapper.ArticleMapper">
  <resultMap id="BaseResultMap" type="com.lagou.pojo.Article">
    <id column="id" jdbcType="INTEGER" property="id" />
    <result column="title" jdbcType="VARCHAR" property="title" />
    <result column="content" jdbcType="VARCHAR" property="content" />
  </resultMap>
  <sql id="Base_Column_List">
    id, title, content
  </sql>
  <select id="selectByPrimaryKey" parameterType="java.lang.Integer" resultMap="BaseResultMap">
    select 
    <include refid="Base_Column_List" />
    from t_article
    where id = #{id,jdbcType=INTEGER}
  </select>
</mapper>
```

#### 3）配置XML映射文件路径

在项目中编写的XML映射文件，Spring Boot并无从知晓，所以无法扫描到该自定义编写的XML配置文件，还必须在全局配置文件application.properties中添加MyBatis映射文件路径的配置，同时需要添加实体类别名映射路径，示例代码如下

```yaml
mybatis:
  configuration:
    map-underscore-to-camel-case: true #开启驼峰命名匹配映射
  mapper-locations: classpath:mapper/*.xml  # 就会resource下的mapper文件夹下的所有mapper文件加载
  type-aliases-package: com.lagou.pojo  # 配置xml映射文件中指定的实体类别名的扫描路径
```

#### 4）测试

```java
@Autowired
    private ArticleMapper articleMapper;
    @Test
    void findArticleById() {
        Article article = articleMapper.selectByPrimaryKey(1);
        System.out.println("article = " + article);
    }
```



## 3.2 Spring Boot 整合Redis

### 1）添加Redis依赖

```xml
<!-- redis依赖包 -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-redis</artifactId>
        </dependency>
```

### 2）配置Redis数据库连接

在application.properties中配置redis数据库连接信息，如下：

```yaml
spring:
  redis:
    host: 192.168.200.141 # redis主机地址
    port: 6379            # 端口号
    jedis:
      pool:
        max-active: 18  # 设置连接池的最大连接数
        max-wait: 3000  # 连接池最大阻塞等待时间
        max-idle: 20    # 连接池中最大的空闲连接数
        min-idle: 2     # 连接池中最小的空闲连接数
    timeout: 3000 # 连接的超时时间
```

### 3）编写Redis操作工具类

将RedisTemplate实例包装成一个工具类，便于对redis进行数据操作。

```java
/**
 * @BelongsProject springbootdemo
 * @Author lengy
 * @CreateTime 2022/8/15 18:15
 * @Description
 */
@Component
public class RedisUtils {

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 读取缓存
     * @param key
     * @return
     */
    public Object get(final String key){
        return redisTemplate.opsForValue().get(key);
    }


    /**
     * 写入缓存
     * @param key
     * @param value
     * @return
     */
    public boolean set(String key,Object value){
        boolean result = false;
        try {
            redisTemplate.opsForValue().set(key,value,1, TimeUnit.DAYS);
            result = true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }


    /**
     * 更新缓存
     * @param key
     * @param value
     * @return
     */
    public boolean getAndSet(final String key,String value){
        boolean result = false;
        try {
            redisTemplate.opsForValue().getAndSet(key,value);
            result = true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }


    /**
     * 删除缓存
     * @param key
     * @return
     */
    public boolean delete(final String key){
        boolean result = false;
        try {
            redisTemplate.delete(key);
            result = true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }
}
```

### 4）测试

```java
@RunWith(SpringRunner.class)
@SpringBootTest
class BootmybatisApplicationTests {

    // 写入，key:1,value:mysql数据库中id为1的article记录
    @Autowired
    private RedisUtils redisUtils;
    
    @Autowired
    private ArticleMapper articleMapper;

    @Test
    void writeRedis(){
        redisUtils.set("1",articleMapper.selectByPrimaryKey(1));
        System.out.println("success");
    }

    @Test
    void readRedis(){
        Article article = (Article) redisUtils.get("1");
        System.out.println(article);
    }


    @Test
    void deleteRedis(){
        boolean delete = redisUtils.delete("1");
        System.out.println("delete = " + delete);
    }
}
```



# 4、SpringBoot视图技术



## Thymeleaf







# 5、Spring Boot 实战演练

lombok

```xml
		<dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <version>1.18.12</version>
            <!--只在编译阶段生效-->
            <scope>provided</scope>
        </dependency>
```

需求：实现用户的CRUD

### 1）创建SpringBoot工程

### 2）导入pom.xml

```xml
<!--        引入朱迪连接池-->
        <dependency>
            <groupId>com.alibaba</groupId>
            <artifactId>druid</artifactId>
            <version>1.2.11</version>
        </dependency>
```

### 3）User实体类编写

```java
@Data
public class User implements Serializable {
    private Integer id;

    private String username;

    private String password;

    private String birthday;

    private static final long serialVersionUID = 1L;
}
```

### 4）UserMapper和Mapper映射

```java
public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    List<User> queryAll();
}
```

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.lagou.mapper.UserMapper">
  <resultMap id="BaseResultMap" type="User">
    <id column="id" jdbcType="INTEGER" property="id" />
    <result column="username" jdbcType="VARCHAR" property="username" />
    <result column="password" jdbcType="VARCHAR" property="password" />
    <result column="birthday" jdbcType="VARCHAR" property="birthday" />
  </resultMap>
  <sql id="Base_Column_List">
    id, username, `password`, birthday
  </sql>
  <select id="selectByPrimaryKey" parameterType="int" resultMap="BaseResultMap">
    select 
    <include refid="Base_Column_List" />
    from user
    where id = #{id,jdbcType=INTEGER}
  </select>
  <select id="queryAll" resultType="com.lagou.pojo.User">
    select
    <include refid="Base_Column_List" />
    from user
  </select>
  <delete id="deleteByPrimaryKey" parameterType="int">
    delete from user
    where id = #{id,jdbcType=INTEGER}
  </delete>
  <insert id="insert" keyColumn="id" keyProperty="id" parameterType="User" useGeneratedKeys="true">
    insert into user (username, `password`, birthday
      )
    values (#{username,jdbcType=VARCHAR}, #{password,jdbcType=VARCHAR}, #{birthday,jdbcType=VARCHAR}
      )
  </insert>
  <insert id="insertSelective" keyColumn="id" keyProperty="id" parameterType="User" useGeneratedKeys="true">
    insert into user
    <trim prefix="(" suffix=")" suffixOverrides=",">
      <if test="username != null">
        username,
      </if>
      <if test="password != null">
        `password`,
      </if>
      <if test="birthday != null">
        birthday,
      </if>
    </trim>
    <trim prefix="values (" suffix=")" suffixOverrides=",">
      <if test="username != null">
        #{username,jdbcType=VARCHAR},
      </if>
      <if test="password != null">
        #{password,jdbcType=VARCHAR},
      </if>
      <if test="birthday != null">
        #{birthday,jdbcType=VARCHAR},
      </if>
    </trim>
  </insert>
  <update id="updateByPrimaryKeySelective" parameterType="User">
    update user
    <set>
      <if test="username != null">
        username = #{username,jdbcType=VARCHAR},
      </if>
      <if test="password != null">
        `password` = #{password,jdbcType=VARCHAR},
      </if>
      <if test="birthday != null">
        birthday = #{birthday,jdbcType=VARCHAR},
      </if>
    </set>
    where id = #{id,jdbcType=INTEGER}
  </update>
  <update id="updateByPrimaryKey" parameterType="User">
    update user
    set username = #{username,jdbcType=VARCHAR},
      `password` = #{password,jdbcType=VARCHAR},
      birthday = #{birthday,jdbcType=VARCHAR}
    where id = #{id,jdbcType=INTEGER}
  </update>
</mapper>
```

### 5）UserService接口及实现类

```java
public interface UserService {

    /**
     * 查询所有
     * @return
     */
    List<User> queryAll();


    /**
     * 通过ID查询
     * @param id
     * @return
     */
    User findById(Integer id);


    /**
     * 新增
     * @param user
     */
    void insert(User user);


    /**
     * 通过ID删除
     * @param id
     */
    void delete(Integer id);


    /**
     * 修改
     * @param user
     */
    void update(User user);
}
```

```java
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public List<User> queryAll() {
        return userMapper.queryAll();
    }

    @Override
    public User findById(Integer id) {
        return userMapper.selectByPrimaryKey(id);
    }

    @Override
    public void insert(User user) {
        //userMapper.insert(user);    // 将除id所有的列都拼sql
        userMapper.insertSelective(user);   // 只是将不为空的列才拼sql
    }

    @Override
    public void delete(Integer id) {
        userMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void update(User user) {
        userMapper.updateByPrimaryKeySelective(user);   // 只有不为空的列才拼sql
    }
}
```

### 6）UserController

```java
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * RestFul风格进行访问
     *  查询：GET
     *  新增：POST
     *  修改：PUT
     *  删除：DELETE
     */

    /**
     * 查询所有
     * @return
     */
    @GetMapping("/query")
    public List<User> queryAll(){
        return userService.queryAll();
    }


    /**
     * 通过ID查询
     */
    @GetMapping("/query/{id}")
    public User queryById(@PathVariable Integer id){
        return userService.findById(id);
    }


    /**
     * 删除
     * @param id
     * @return
     */
    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable Integer id){
        userService.delete(id);
        return "删除成功";
    }


    /**
     * 新增
     * @param user
     * @return
     */
    @PostMapping("/insert")
    public String insert(User user){
        userService.insert(user);
        return "新增成功";
    }


    /**
     * 修改
     * @param user
     * @return
     */
    @PutMapping("/update")
    public String update(User user){
        userService.update(user);
        return "修改成功";
    }
}
```

### 8）Application.yaml

```yaml
# 服务器配置
server:
  port: 8090

# 数据源配置
spring:
  datasource:
    name: druid
    type: com.alibaba.druid.pool.DruidDataSource
    url: jdbc:mysql:///springbootdata?characterEncoding=utf-8&serverTimezone=UTC
    username: root
    password: 123456

# 整合mybatis
mybatis:
  mapper-locations: classpath:mapper/*Mapper.xml # 声明Mybatis映射文件所在的位置
  type-aliases-package: com.lagou.pojo	# 给返回的实体类型起别名
```

### 9）启动类

```java
@SpringBootApplication
// 使用的Mybatis，扫描com.lagou.mapper
@MapperScan("com.lagou.mapper")
public class Springbootdemo5Application {

    public static void main(String[] args) {
        SpringApplication.run(Springbootdemo5Application.class, args);
    }
}
```

### 10）使用POSTman测试





# 6、Spring Boot项目部署

需求：将Spring Boot项目使用maven指令打成jar包并运行测试

1. 需要添加打包组件将项目中的资源、配置、依赖包打到一个jar包中；可以使用maven的
   package ；

2. 部署：java -jar 包名



实现：

* 添加打包组件：

```xml
<build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
        </plugins>
    </build>
```

* 部署运行
  * 在命令窗口中：

java -jar 包名