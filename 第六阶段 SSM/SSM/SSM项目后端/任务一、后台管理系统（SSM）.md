# 任务一、后台管理系统（SSM）

# 1.项目架构

## 1.1 项目介绍

​	后台管理系统，是提供给相关业务人员用的一个后台管理系统，业务人员可以在这个后台管理系统中，对课程信息、广告信息、用户信息、权限信息等数据进行维护。

​	基于SSM框架完成课程信息模块，广告信息模块，用户信息模块，权限信息模块



## 1.2 页面原型

http://eduboss.lagou.com

密码：111111



## 1.3 技术选型

### 1.3.1 前端技术选型

| 前端技术   | 说明                   |
| ---------- | ---------------------- |
| Vue.js     | JavaScript框架         |
| Element UI | 后台组件库             |
| node.js    | JavaScript运行环境     |
| axios      | 局部刷新，对ajax的封装 |



### 1.3.2 后端技术选型

| 后端技术  | 说明                            |
| --------- | ------------------------------- |
| Web层     | Springmvc接收请求，进行视图跳转 |
| Service层 | Spring 进行IOC、AOP、及事务管理 |
| dao层     | mybatis进行数据库交互           |



## 1.4 项目开发环境

* 开发工具
  * 后端：IDEA 2019
  * 前端：Vs code
  * 数据库客户端工具：SQLYog
* 开发环境
  * JDK11
  * MAVEN 3.6.3
  * MySql 5.7





# 2. MAVEN进阶使用（MAVEN聚合工程）

## 2.1 MAVEN基础知识回顾

### 2.1.1 maven介绍

maven是一个项目管理工具，主要在项目开发阶段对Java项目进行依赖管理和项目构建

依赖管理：就是对jar包的管理。通过导入maven坐标，就相当于将仓库的jar包导入了当前项目中

项目构建：通过maven的一个命令就可以完成项目从清理、编译、测试、报告、打包，部署整个过程



### 2.1.2 maven的仓库类型

1. 本地仓库
2. 远程仓库

* maven中央仓库
* maven私服
* 其他公共远程仓库



找jar包的顺序：本地仓库 --》maven私服 --》 maven中央仓库



### 2.1.3 maven常用命令

clean：清理

compile：编译

test：测试

package：打包

install：安装



### 2.1.4 maven坐标书写规范

```xml
<dependency>
	<groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
    <version>5.1.32</version>
</dependency>
```





## 2.2 maven的依赖传递

### 2.2.1 什么是依赖传递

在maven中，依赖是可以传递的，如存在三个项目，项目a，项目b及项目c。假设c依赖b，b依赖a，那么根据maven项目依赖推出c也依赖a。

项目c直接依赖了项目b，而项目b又直接依赖了项目a，那么项目c就间接依赖了项目a。这就是maven的依赖传递



**依赖冲突**

由于依赖传递现象的存在，spring-webmvc 依赖了 spirng-beans-5.1.5，spring-aop 依赖 spring-beans-5.1.6，但是发现 spirng-beans-5.1.5 加入到了工程中，而我们希望 spring-beans-5.1.6 加入工程。这就造成了依赖冲突



### 2.2.2 如何解决依赖冲突

1.使用maven提供的依赖调解原则

 第一声明优先原则（了解）

路径优先原则

2.排除依赖

3.锁定版本（常用）



### 2.2.3 依赖调节原则--- 第一声明者优先原则

在pom.xml  文件中定义依赖，以先声明的依赖为准。就是根据坐标导入的顺序来确定最终使用哪个传递过来的依赖



spring-aop和spring-webmvc都传递过来了spring-beans，但是因为spring-aop在前面，所以最终使用的spring-beans是由spring-aop传递过来的，而spring-webmvc传递过来的spring-beans则被忽略了



### 2.2.4 依赖调节原则-- 路径近者优先原则

就是直接依赖大于依赖传递。



### 2.2.5 排除依赖

可以使用exclusions标签将传递过来的依赖排除出去

```xml
<!--   引入spring mvc  -->
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-webmvc</artifactId>
            <version>5.1.5.RELEASE</version>
<!--            排除依赖，排除spring-webmvc 所依赖的spring-beans -->
            <exclusions>
                <exclusion>
                    <groupId>org.springframework</groupId>
                    <artifactId>spring-beans</artifactId>
                </exclusion>
            </exclusions>
        </dependency>

        <!--        spring aop-->
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-aop</artifactId>
            <version>5.1.6.RELEASE</version>
        </dependency>
```



### 2.2.6 版本锁定	常用

采用直接锁定版本的方法确定依赖jar包的版本，版本锁定后则不考虑依赖的声明顺序或依赖的路径，以锁定的版本为准添加到工程中。	

版本锁定的使用方式：

第一步：在dependencyManagement标签中锁定依赖的版本

第二步：在dependencies标签中声明需要导入的maven坐标

1.在dependencyManagement标签中锁定依赖的版本

```xml
<!--  版本锁定方式解决依赖冲突问题，常用  -->
<!--  dependencyManagement 标签：只能锁定jar包版本，不能引入jar包  -->
<!--     锁定jar包版本，把spring-beans jar包版本 已经锁定死了。不管直接依赖还是依赖传递都是锁死的版本了-->
<dependencyManagement>
<!--    可以锁定多个依赖版本-->
    <dependencies>
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-beans</artifactId>
            <version>5.1.6.RELEASE</version>
        </dependency>
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-context</artifactId>
            <version>5.1.6.RELEASE</version>
        </dependency>
    </dependencies>
</dependencyManagement>
```

注意：pom文件中使用dependencyManagement标签进行依赖jar的版本锁定，并不会真正将jar包导入到项目中，只是对这些的jar版本进行锁定。项目中使用哪些jar包，还要在dependencies标签中进行声明

---



2.在dependencies标签中声明需要导入的maven坐标

```xml
<!--spring-beans版本已经锁定死了，那么引入jar包时无需配置 version了-->
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-webmvc</artifactId>
            <version>5.1.5.RELEASE</version>
        </dependency>
		<dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-beans</artifactId>
        </dependency>
        <dependency>
            	<groupId>org.springframework</groupId>
            <artifactId>spring-context</artifactId>
    </dependency>
    </dependencies>
```

注意：由于前面已经在dependencyManagement标签中锁定了依赖jar包的版本，后面需要导入依赖时只需要指定groupId和artifactId，无需指定version了

----

#### 笔试

**dependencyManagement标签只有锁定jar包版本的作用，不能引入jar包。**

**引入jar包还要配置dependencies标签**

---



### 2.2.7 properties标签的使用

```xml
<!--    声明版本，把多个jar包共同用到的版本号进行抽取-->
    <properties>
<!--        配置版本号-->
        <spring.version>5.1.7.RELEASE</spring.version>
    </properties>


<!--  版本锁定方式解决依赖冲突问题，常用  -->
<!--  dependencyManagement 标签：只能锁定jar包版本，不能引入jar包  -->
<!--     锁定jar包版本，把spring-beans jar包版本 已经锁定死了。不管直接依赖还是依赖传递都是锁死的版本了-->
<dependencyManagement>
<!--    可以锁定多个依赖版本-->
    <dependencies>
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-beans</artifactId>
<!--            通过el表达式对版本号进行引用-->
            <version>${spring.version}</version>
        </dependency>
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-context</artifactId>
            <version>${spring.version}</version>
        </dependency>
    </dependencies>
</dependencyManagement>


    <dependencies>

<!--spring-beans版本已经锁定死了，那么引入jar包时无需配置 version了-->
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-beans</artifactId>
        </dependency>

		<dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-context</artifactId>
        </dependency>

    </dependencies>
```





## 2.3 maven聚合工程（分模块）

### 2.3.1 分模块构建maven工程

在项目开发中，由于项目规模大，业务复杂，参与的人员比较多，一般会通过合理的模块拆分将一个大型的项目分为N多个小模块，分别进行开发。而且拆分出的模块可以非常容易的被其他模块复用

常见的拆分方式：

第一种：按照**业务模块**进行拆分，每个模块拆分成一个maven工程，例如将一个项目分为用户模块，订单模块，购物车模块，每个模块对应就是一个maven工程

第二种：按照**层**进行拆分，例如持久层，业务层，表现层。每个层对应就是一个maven工程

不管上面哪种拆分方式，通常都会提供一个父工程，将一些公共的代码和配置提取到父工程中进行统一管理和配置



如：maven_pojo ，maven_dao，maven_service和maven_web 都继承maven_parent ,而这些子工程又互相依赖



### 2.3.2 maven工程的继承

maven工程之间可以继承，子工程继承父工程后，就可以使用在父工程中引入的依赖。继承的目的就是为了消除重复代码

* 被继承的maven工程通常被称为父工程，父工程的打包方式必须为pom，所以区分某个maven工程是否为父工程就看这个工程的打包方式是否pom

```xml
<!-- 被继承的maven项目中的pom的部分定义是 -->
<groupId>com.lagou</groupId>
    <artifactId>maven_parent</artifactId>
    <version>1.0-SNAPSHOT</version>
    <!--    声明当前工程为父工程-->
    <packaging>pom</packaging>
```



* 继承其他maven父工程的工程通常称为子工程，在pom.xml 文件中通过parent 标签进行父工程的继承

```xml
<!-- 继承的maven项目中的pom的关键部分是 -->
<!--    继承了父工程-->
    <parent>
        <artifactId>maven_parent</artifactId>
        <groupId>com.lagou</groupId>
        <version>1.0-SNAPSHOT</version>
    </parent>
    <modelVersion>4.0.0</modelVersion>

    <artifactId>maven_children</artifactId>
```



### 2.3.3 maven工程的聚合

在maven工程的pom.xml 文件中可以使用标签将其他maven工程聚合到一起，聚合的目的是为了进行统一操作

例如拆分后的maven工程有多个，如果要进行打包，就需要针对每个工程分别执行打包命令，操作起来非常繁琐。这时就可以使用标签将这些工程统一聚合到maven父工程中，需要打包的时候，只需要在此工程中执行一次打包命令，其下被聚合的工程就都会被打包了

```xml
<groupId>com.lagou</groupId>
    <artifactId>maven_parent</artifactId>
    <version>1.0-SNAPSHOT</version>


    <!--聚合就是通过配置将多个子工程聚合在一起进行统一操作，使用modules标签
            当聚合完成之后，对于父工程的执行命令，子工程同样也会执行
    -->

<!--    当前的父工程对子工程进行了聚合-->
    <modules>
        <module>maven_children</module>
<!--        聚合其他工程-->
        <module>../maven_advanced</module>
    </modules>
```





### 2.3.4 maven聚合工程_搭建拉勾教育后台管理系统

**工程整体结构：**

1）lagou_edu_home_parent为父工程，其余工程为子工程，都继承父工程lagou_edu_home_parent 

2）lagou_edu_home_parent 工程将其子工程都进行了聚合

3）子工程之间存在依赖关系：

​	ssm-domain依赖ssm-utils

​	ssm-dao依赖ssm-domain

​	ssm-service依赖ssm-dao

​	ssm-web依赖ssm-service



#### 1. 父工程lagou_edu_home_parent构建

修改pom.xml ，添加依赖

```xml
<!--    在父工程中进行资源的统一管理
                    依赖管理和版本锁定
        -->

    <!--    声明jar 版本 -->
    <properties>
        <spring.version>5.1.5.RELEASE</spring.version>
        <springmvc.version>5.1.5.RELEASE</springmvc.version>
        <mybatis.version>3.5.1</mybatis.version>
    </properties>


    <!--    锁定jar版本-->
    <dependencyManagement>
        <dependencies>
            <!--        Mybatis-->
            <dependency>
                <groupId>org.mybatis</groupId>
                <artifactId>mybatis</artifactId>
                <version>${mybatis.version}</version>
            </dependency>
            <!--        springMVC-->
            <dependency>
                <groupId>org.springframework</groupId>
                <artifactId>spring-webmvc</artifactId>
                <version>${springmvc.version}</version>
            </dependency>
            <!--        spring-->
            <dependency>
                <groupId>org.springframework</groupId>
                <artifactId>spring-context</artifactId>
                <version>${spring.version}</version>
            </dependency>
            <dependency>
                <groupId>org.springframework</groupId>
                <artifactId>spring-core</artifactId>
                <version>${spring.version}</version>
            </dependency>
            <dependency>
                <groupId>org.springframework</groupId>
                <artifactId>spring-aop</artifactId>
                <version>${spring.version}</version>
            </dependency>
            <dependency>
                <groupId>org.springframework</groupId>
                <artifactId>spring-web</artifactId>
                <version>${spring.version}</version>
            </dependency>
            <dependency>
                <groupId>org.springframework</groupId>
                <artifactId>spring-expression</artifactId>
                <version>${spring.version}</version>
            </dependency>
            <dependency>
                <groupId>org.springframework</groupId>
                <artifactId>spring-beans</artifactId>
                <version>${spring.version}</version>
            </dependency>
            <dependency>
                <groupId>org.springframework</groupId>
                <artifactId>spring-aspects</artifactId>
                <version>${spring.version}</version>
            </dependency>
            <dependency>
                <groupId>org.springframework</groupId>
                <artifactId>spring-context-support</artifactId>
                <version>${spring.version}</version>
            </dependency>
            <dependency>
                <groupId>org.springframework</groupId>
                <artifactId>spring-test</artifactId>
                <version>${spring.version}</version>
            </dependency>
            <dependency>
                <groupId>org.springframework</groupId>
                <artifactId>spring-jdbc</artifactId>
                <version>${spring.version}</version>
            </dependency>
            <dependency>
                <groupId>org.springframework</groupId>
                <artifactId>spring-tx</artifactId>
                <version>${spring.version}</version>
            </dependency>
        </dependencies>
    </dependencyManagement>


    <dependencies>
        <!--    mybatis坐标-->
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <version>5.1.47</version>
        </dependency>
        <dependency>
            <groupId>com.alibaba</groupId>
            <artifactId>druid</artifactId>
            <version>1.1.15</version>
        </dependency>
        <dependency>
            <groupId>org.mybatis</groupId>
            <artifactId>mybatis</artifactId>
        </dependency>
        <dependency>
            <groupId>junit</groupId>
            <artifactId>junit</artifactId>
            <version>4.12</version>
        </dependency>


        <!--    spring坐标-->
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-context</artifactId>
        </dependency>
        <dependency>
            <groupId>org.aspectj</groupId>
            <artifactId>aspectjweaver</artifactId>
            <version>1.8.13</version>
        </dependency>
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-jdbc</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-tx</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-test</artifactId>
        </dependency>

        <!--    mybatis整合spring坐标-->
        <dependency>
            <groupId>org.mybatis</groupId>
            <artifactId>mybatis-spring</artifactId>
            <version>1.3.1</version>
        </dependency>

        <!--    springMVC坐标-->
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-webmvc</artifactId>
        </dependency>
        <dependency>
            <groupId>javax.servlet</groupId>
            <artifactId>javax.servlet-api</artifactId>
            <version>3.1.0</version>
            <scope>provided</scope>
        </dependency>
        <dependency>
            <groupId>javax.servlet.jsp</groupId>
            <artifactId>jsp-api</artifactId>
            <version>2.2</version>
            <scope>provided</scope>
        </dependency>
        <dependency>
            <groupId>jstl</groupId>
            <artifactId>jstl</artifactId>
            <version>1.2</version>
        </dependency>

        <dependency>
            <groupId>com.fasterxml.jackson.core</groupId>
            <artifactId>jackson-databind</artifactId>
            <version>2.9.8</version>
        </dependency>
        <dependency>
            <groupId>com.fasterxml.jackson.core</groupId>
            <artifactId>jackson-core</artifactId>
            <version>2.9.8</version>
        </dependency>
        <dependency>
            <groupId>com.fasterxml.jackson.core</groupId>
            <artifactId>jackson-annotations</artifactId>
            <version>2.9.0</version>
        </dependency>


        <dependency>
            <groupId>com.github.pagehelper</groupId>
            <artifactId>pagehelper</artifactId>
            <version>4.1.6</version>
        </dependency>

        <!--    BeanUtils-->
        <dependency>
            <groupId>commons-beanutils</groupId>
            <artifactId>commons-beanutils</artifactId>
            <version>1.8.3</version>
        </dependency>

        <dependency>
            <groupId>commons-fileupload</groupId>
            <artifactId>commons-fileupload</artifactId>
            <version>1.3.1</version>
        </dependency>

        <!--    解决跨域问题所需依赖-->
        <dependency>
            <groupId>com.thetransactioncompany</groupId>
            <artifactId>cors-filter</artifactId>
            <version>2.5</version>
        </dependency>

<!--        使用log4j 所依赖的两个jar包-->
        <dependency>
            <groupId>org.slf4j</groupId>
            <artifactId>slf4j-log4j12</artifactId>
            <version>1.7.7</version>
        </dependency>
        <dependency>
            <groupId>log4j</groupId>
            <artifactId>log4j</artifactId>
            <version>1.2.17</version>
        </dependency>

    </dependencies>

    <!--配置插件，编译级别和对应编码-->
    <build>
        <plugins>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-compiler-plugin</artifactId>
                <version>3.1</version>
                <configuration>
                    <source>1.8</source>
                    <target>1.8</target>
                    <encoding>UTF-8</encoding>
                </configuration>
            </plugin>
        </plugins>
    </build>
```



#### 2. 子工程ssm-utils构建



#### 3. 子工程ssm-domain构建

```xml
<!--    添加ssm-utils 依赖-->
    <dependencies>
        <dependency>
            <groupId>com.lagou</groupId>
            <artifactId>ssm-utils</artifactId>
            <version>1.0-SNAPSHOT</version>
        </dependency>
    </dependencies>
```



#### 4. 子工程ssm-dao构建

配置ssm-dao工程的pom.xml 文件

```xml
<!--添加ssm-domain-->
<dependencies>
    <dependency>
        <groupId>com.lagou</groupId>
        <artifactId>ssm-domain</artifactId>
        <version>1.0-SNAPSHOT</version>
    </dependency>
</dependencies>
```

创建DAO接口和Mapper映射文件

```java
package com.lagou.dao;

import com.lagou.entity.Test;

import java.util.List;

public interface TestMapper {

    /*
        对test表进行查询所有操作
     */
    public List<Test> findAllTest();
}
```

```xml
<?xml version="1.0" encoding="utf-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">

<!--命名空间的值与接口的全路径保持一致-->
<mapper namespace="com.lagou.dao.TestMapper">

<!--    对test进行查询所有操作-->
    <select id="findAllTest" resultType="test">
        select * from test
    </select>
</mapper>
```

在resources目录下创建dao层 的 spring配置文件applicationContext-dao.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xmlns:tx="http://www.springframework.org/schema/tx"
       xmlns:aop="http://www.springframework.org/schema/aop"
       xsi:schemaLocation="
       	http://www.springframework.org/schema/beans
		http://www.springframework.org/schema/beans/spring-beans.xsd
       	http://www.springframework.org/schema/context
		http://www.springframework.org/schema/context/spring-context.xsd
		http://www.springframework.org/schema/tx
		http://www.springframework.org/schema/tx/spring-tx.xsd
		http://www.springframework.org/schema/aop
		http://www.springframework.org/schema/aop/spring-aop.xsd
">

<!--    配置文件模块化：
            把spring的核心配置文件进行拆分，这个是dao层的-->


<!--    spring整合mybatis-->
<!--  引入jdbc.properties 文件  -->
<context:property-placeholder location="classpath:jdbc.properties"/>
<!--    1.数据源配置-->
<bean id="dataSource" class="com.alibaba.druid.pool.DruidDataSource">
    <property name="driverClassName" value="${jdbc.driver}"/>
    <property name="url" value="${jdbc.url}"/>
    <property name="username" value="${jdbc.username}"/>
    <property name="password" value="${jdbc.password}"/>
</bean>

<!--    2.sqlSessionFactory 工厂对象交给spring-->
<bean id="sqlSessionFactory" class="org.mybatis.spring.SqlSessionFactoryBean">
<!--    注入数据源-->
    <property name="dataSource" ref="dataSource"/>
<!--    指定类型别名-->
    <property name="typeAliasesPackage" value="com.lagou.entity"/>

<!--    引入加载sqlMapperConfig.xml 核心配置文件-->
    <property name="configLocation" value="classpath:sqlMapperConfig.xml"/>
</bean>

<!--    3.mapper映射扫描，扫描所有的mapper接口-->
<bean class="org.mybatis.spring.mapper.MapperScannerConfigurer">
<!--    对dao包进行扫描，包下的接口生成代理对象存到IOC -->
    <property name="basePackage" value="com.lagou.dao"/>
</bean>


</beans>
```

sqlMapConfig.xml

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE configuration
        PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>
    <settings>
<!--   是否开启自动驼峰命名规则（camel case）映射，即从数据库列名 A_COLUMN 到属性名
aColumn 的类似映射 a_name aName     -->
        <setting name="mapUnderscoreToCamelCase" value="true"/>
    </settings>
</configuration>
```



#### 5. 子工程ssm-service构建

第一步：创建ssm-service工程

第二部：配置ssm-service工程的pom.xml 文件

```xml
<!--    对ssm-dao 这个子工程做 直接依赖
            而ssm-dao 又对ssm-domain这个 工程做了直接依赖
                所以ssm-service 就对 ssm-domain这个 工程做了间接依赖
-->
<dependencies>
    <dependency>
        <groupId>com.lagou</groupId>
        <artifactId>ssm-dao</artifactId>
        <version>1.0-SNAPSHOT</version>
    </dependency>
</dependencies>
```

第三步：创建TestService接口和实现类

```java
public interface TestService {

    /*
        对test表进行查询所有
     */
    public List<Test> findAllTest();
}
```

```java
@Service        // 生成该类实例对象存到IOC
public class TestServiceImpl implements TestService {

    @Autowired
    private TestMapper testMapper;

    @Override
    public List<Test> findAllTest() {
        return testMapper.findAllTest();
    }
}
```

第四步：创建service层的 spring配置文件applicationContext-service.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xmlns:tx="http://www.springframework.org/schema/tx"
       xmlns:aop="http://www.springframework.org/schema/aop"
       xsi:schemaLocation="
       	http://www.springframework.org/schema/beans
		http://www.springframework.org/schema/beans/spring-beans.xsd
       	http://www.springframework.org/schema/context
		http://www.springframework.org/schema/context/spring-context.xsd
		http://www.springframework.org/schema/tx
		http://www.springframework.org/schema/tx/spring-tx.xsd
		http://www.springframework.org/schema/aop
		http://www.springframework.org/schema/aop/spring-aop.xsd
">

    <!--    配置文件模块化：
                把spring的核心配置文件进行拆分，这个是service层的-->


<!--    进行service的相关配置:IOC-->
<!--开启IOC注解扫描，只扫描service层-->
    <context:component-scan base-package="com.lagou.service"/>


<!--   加载applicationContext-service.xml 时  也加载dao层的 applicationContext-dao.xml 核心配置文件-->
<import resource="classpath:applicationContext-dao.xml"/>


</beans>
```



#### 6. 子工程ssm-web构建

第一步：创建maven_web 工程，注意打包方式为war

第二步：配置maven_web工程的pom.xml 文件

```xml
<!--    web工程-->

    <packaging>war</packaging>

    <dependencies>
        <dependency>
            <groupId>com.lagou</groupId>
            <artifactId>ssm-service</artifactId>
            <version>1.0-SNAPSHOT</version>
        </dependency>
    </dependencies>
```

第三步：创建Controller

```java
@RestController // 组合注解，相当于@Controller + @ResponseBody
@RequestMapping("/test")    // 一级目录
public class TestController {

    // 注入service
    @Autowired
    private TestService testService;

    // 当访问到这个方法时，就会查询数据库中的test表的全部记录，并封装成list集合，再把list集合转为JSON响应给页面
    @RequestMapping("/findAllTest")
    public List<Test> findAllTest(){

        return testService.findAllTest();
    }
}
```

第四步：创建springmvc配置文件springmvc.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:mvc="http://www.springframework.org/schema/mvc"
       xmlns:context="http://www.springframework.org/schema/context"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
		http://www.springframework.org/schema/beans/spring-beans.xsd
		http://www.springframework.org/schema/mvc
		http://www.springframework.org/schema/mvc/spring-mvc.xsd
        http://www.springframework.org/schema/context
		http://www.springframework.org/schema/context/spring-context.xsd
">


<!-- 1.组件扫描 只扫描controller这个包，让springmvc容器去管理controller-->
   <context:component-scan base-package="com.lagou.controller"/>

<!-- 2.mvc注解增强，显示的配置了处理器映射器和处理器适配器-->
<mvc:annotation-driven/>

<!-- 3.视图解析器：暂时不用配置。因为这个是前后端分离项目，后端只需要给前台返回个JSON就ok了-->
<!--  <bean id="resourceViewResolve"
class="org.springframework.web.servlet.view.InternalResourceViewResolver">
    <property name="prefix" value="/"></property>
    <property name="suffix" value=".html"></property>
  </bean>-->
    

<!-- 4.静态资源放行  全部的都放行-->
    <mvc:default-servlet-handler/>


</beans>
```

第五步：创建web层的 spring配置文件applicationContext.xml，并引入其他层的配置文件

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xmlns:tx="http://www.springframework.org/schema/tx"
       xmlns:aop="http://www.springframework.org/schema/aop"
       xsi:schemaLocation="
       	http://www.springframework.org/schema/beans
		http://www.springframework.org/schema/beans/spring-beans.xsd
       	http://www.springframework.org/schema/context
		http://www.springframework.org/schema/context/spring-context.xsd
		http://www.springframework.org/schema/tx
		http://www.springframework.org/schema/tx/spring-tx.xsd
		http://www.springframework.org/schema/aop
		http://www.springframework.org/schema/aop/spring-aop.xsd
">


<!--   引入加载 applicationContext-service.xml  配置文件
        当加载了 applicationContext.xml 时，就把service层和dao层 配置文件都加载了-->
<import resource="classpath:applicationContext-service.xml"/>


</beans>
```

第六步：配置web.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<web-app xmlns="http://xmlns.jcp.org/xml/ns/javaee"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/javaee http://xmlns.jcp.org/xml/ns/javaee/web-app_4_0.xsd"
         version="4.0">


<!--    配置前端控制器-->
<servlet>
    <servlet-name>dispatcherServlet</servlet-name>
    <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
<!--    初始化参数
            指定该servlet在执行初始化方法时，获取到初始化参数，并去加载springmvc的核心配置文件
-->
    <init-param>
        <param-name>contextConfigLocation</param-name>
        <param-value>classpath:springmvc.xml</param-value>
    </init-param>
<!--    让服务器启动时就完成servlet的实例化并执行初始化操作，去加载springmvc.xml
            所以添加load-on-startup 标签
                这样的话，在服务器启动时，就会对该servlet进行实例化并初始化，初始化时区加载 springmvc.xml
-->
    <load-on-startup>2</load-on-startup>
</servlet>
    <servlet-mapping>
        <servlet-name>dispatcherServlet</servlet-name>
<!--        表示对所有请求都进行拦截，不包含静态资源-->
        <url-pattern>/</url-pattern>
    </servlet-mapping>


<!--    中文乱码过滤器，解决post请求乱码问题-->
<filter>
    <filter-name>characterEncodingFilter</filter-name>
    <filter-class>org.springframework.web.filter.CharacterEncodingFilter</filter-class>
<!--    初始化参数 指定编码-->
    <init-param>
        <param-name>encoding</param-name>
        <param-value>UTF-8</param-value>
    </init-param>
</filter>
    <filter-mapping>
        <filter-name>characterEncodingFilter</filter-name>
<!--        对所有资源都进行拦截-->
        <url-pattern>/*</url-pattern>
    </filter-mapping>



<!--    spring的监听器 contextLoaderListener
            监听servletContext 对象的创建，服务器启动时就创建了
                然后监听器就会去加载applicationContext.xml
                    而在applicationContext.xml 中又通过import标签 引入了 service层和dao层的 applicationContext.xml
                        所以在服务器启动时，这些配置文件同时都被加载了
    -->
<listener>
    <listener-class>org.springframework.web.context.ContextLoaderListener</listener-class>
</listener>
<!--    全局参数-->
    <context-param>
        <param-name>contextConfigLocation</param-name>
<!--        在服务器启动时，加载 applicationContext.xml ，并把拆分过后的 applicationContext.xml 文件都加载-->
        <param-value>classpath:applicationContext.xml</param-value>
    </context-param>




<!--    配置跨域过滤器，由于前后端分离会产生跨域问题-->
<filter>
    <filter-name>corsFilter</filter-name>
    <filter-class>com.thetransactioncompany.cors.CORSFilter</filter-class>
</filter>
    <filter-mapping>
        <filter-name>corsFilter</filter-name>
        <url-pattern>/*</url-pattern>
    </filter-mapping>


</web-app>
```







# 3. 拉勾教育后台管理系统研发

## 3.1 课程管理模块功能分析

​	在本次项目中，首先来完成后台管理系统的课程管理模块，课程管理模块包含了多条件查询、图片上传、新建&修改课程、课程状态管理、课程内容展示、回显章节对应的课程信息、新建&修改章节信息、修改章节状态、新建&修改课时信息等接口的编写

### 3.1.1 课程管理

* 实现以下功能：
  * 多条件查询
  * 图片上传
  * 新建课程信息
  * 回显课程信息
  * 修改课程信息
  * 课程状态管理
  * 课程内容展示
  * 回显章节对应的课程信息
  * 新建&修改章节信息
  * 修改章节状态
  * 新建课时信息



## 3.2 课程管理模块表设计

### 3.2.1 创建数据库及表

### 3.2.2 表关系介绍

课程表（course）和章节表（course_section）是一对多关系，一个课程有多个章节

章节表（course_section）和课时表（course_lesson）是一对多关系，一个章节下有多个课时

课时表（course_lesson）和课程媒体表（course_media）是一对一关系



## 3.3 课程管理模块接口实现

## 多条件课程列表查询

### 3.1 需求分析

根据课程名及课程状态进行多条件查询。而这两个条件是不确定的，所以dao层要使用动态SQL，而且还得是模糊查询



### 3.2 查看接口文档，进行编码

查看接口文档

#### 实体类：Course

```java
/**
 * 课程类
 */
public class Course {

    //主键
    private int id;

    //课程名称
    private String courseName;

    //课程一句话简介
    private String brief;

    //原价
    private double price;

    //原价标签
    private String priceTag;

    //优惠价
    private double discounts;

    //优惠价标签
    private String discountsTag;

    //课程内容markdown
    private String courseDescriptionMarkDown;

    //课程描述
    private String courseDescription;

    //课程分享图片url
    private String courseImgUrl;

    //是否新品
    private int isNew;

    //广告语
    private String isNewDes;

    //最后操作者
    private int lastOperatorId;

    //自动上架时间
    private Date autoOnlineTime;

    //创建时间
    private Date createTime;

    //更新时间
    private Date updateTime;

    //是否删除
    private int isDel;

    //总时长
    private int totalDuration;

    //课程列表展示图片
    private String courseListImg;

    //课程状态，0-草稿，1-上架
    private int status;

    //课程排序
    private int sortNum;

    //课程预览第一个字段
    private String previewFirstField;

    //课程预览第二个字段
    private String previewSecondField;

    //销量
    private int sales;

    // getter/setter...
}
```

#### ResponseResult

创建一个响应给前台的结果集对象，用来封装前台需要的结果

```java
/**
 * 响应结果封装对象
 * */
public class ResponseResult {

    private Boolean success;
    private Integer state;
    private String message;
    private Object content;

    public ResponseResult() {
    }

    public ResponseResult(Boolean success, Integer state, String message, Object content) {
        this.success = success;
        this.state = state;
        this.message = message;
        this.content = content;
    }

    // getter/setter...
}
```



#### 实体类：CourseVO(View Object 表现层对象：主要用于接收表现层传递过来的参数的)

```java
/*
    VO:View Object  表现层对象：在表现层接收前台参数的
        封装由浏览器端发送给表现层的请求参数的对象
 */
public class CourseVO {
  /**
  * 课程名称
  * */
  private String courseName;
  /**
  * 课程状态
  * */
  private Integer status;
 
  // getter/setter....
}
```



#### Dao层：CourseMapper

```java
public interface CourseMapper {

    /*
        多条件课程列表查询
     */

    public List<Course> findCourseByCondition(CourseVO courseVO);
}
```

```xml
<?xml version="1.0" encoding="utf-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">

<mapper namespace="com.lagou.dao.CourseMapper">

<!--    多条件课程列表查询
            动态SQL
-->
    <select id="findCourseByCondition" parameterType="com.lagou.entity.CourseVO" resultType="Course">
        select * from course
        <where>
            <if test="courseName != null and courseName != '' ">
                and course_name like concat('%',#{courseName},'%')
            </if>
            <if test="status != null and status != ''">
                and status = #{status}
            </if>
            <!-- 查询出没有被逻辑删除的记录 -->
            <if test="true">
                and is_del != 1
            </if>
        </where>
    </select>
</mapper>
```



#### Service层：CourseService

```java
public interface CourseService {

    /*
        多条件课程列表查询
     */
    public List<Course> findCourseByCondition(CourseVO courseVO);
}
```

```java
@Service    // 生成该类实例存到IOC
public class CourseServiceImpl implements CourseService {

    // 注入代理对象
    @Autowired
    private CourseMapper courseMapper;

    @Override
    public List<Course> findCourseByCondition(CourseVO courseVO) {
        return courseMapper.findCourseByCondition(courseVO);
    }
}
```



#### Web层：CourseController

```java
@RestController     // 组合注解，组合了 @Controller 和 @ResponseBody
@RequestMapping("/course")  // 一级目录
public class CourseController {

    @Autowired
    private CourseService courseService;


    /*
        多条件课程列表查询
     */
    @RequestMapping("/findCourseByCondition")    // 二级目录
    // 把前台传递过来的JSON解析封装到CourseVO
    public ResponseResult findCourseByCondition(@RequestBody CourseVO courseVO){

        // 调用service
        List<Course> list = courseService.findCourseByCondition(courseVO);

        // 把封装好的ResponseResult 对象返回给前台
        return new ResponseResult(true, 200, "响应成功", list);
    }
}
```



#### Postman测试接口





## 课程图片上传

### 需求分析：

需求：在新增课程页面需要进行图片上传，并回显图片信息



### 查看接口文档，进行编码

查看接口文档

springmvc.xml

```xml
<!--    5.配置文件解析器-->
<!--    id值为固定写法，不能随便写-->
<bean id="multipartResolver" class="org.springframework.web.multipart.commons.CommonsMultipartResolver">
    <property name="maxUploadSize" value="1048576"/>
</bean>
```



#### Web层：CourseController

```java
/*
        课程图片上传
     */

    @RequestMapping("/courseUpload")
    // 参数名要与 前台请求参数 的key 保持一致
    public ResponseResult fileUpload(@RequestParam("file") MultipartFile file, HttpServletRequest request) throws IOException {

        // 1.判断接收到的上传文件是否为空
        if (file.isEmpty()) {
            throw new RuntimeException();
        }

        // 2.获取项目部署路径
        // F:\software\apache-tomcat-8.5.55\webapps\ssm_web
        String realPath = request.getServletContext().getRealPath("/");
        // F:\software\apache-tomcat-8.5.55\webapps\
        String webappsPath = realPath.substring(0, realPath.indexOf("ssm-web"));

        // 3.获取原文件名
        //lagou.jpg
        String originalFilename = file.getOriginalFilename();

        // 4.生成新文件名
        // 1234.jpg
        String newFileName = System.currentTimeMillis() + originalFilename.substring(originalFilename.lastIndexOf("."));

        // 5.文件上传

        String uploadPath = webappsPath + "upload\\";
        File filePath = new File(uploadPath, newFileName);

        // 如果目录不存在就创建目录
        if (!filePath.getParentFile().exists()){
            filePath.getParentFile().mkdirs();
            System.out.println("创建目录:" + filePath);
        }

        // 图片就进行了上传
        file.transferTo(filePath);

        // 6.将文件名和文件路径返回，进行响应
        Map<String, String> map = new HashMap<>();
        map.put("fileName",newFileName);
        // "filePath": "http://localhost:8080/upload/1597112871741.JPG"
        // 根据路径可以在Tomcat 的webapps 目录下的 upload目录下 找到文件，并回显
        map.put("filePath","http://localhost:8080/upload/" + newFileName);

        // 对象转为JSON响应给前台
        return new ResponseResult(true,200,"图片上传成功",map);
    }
```



#### Postman测试接口





## 新建课程信息

### 需求分析：

填写好新增课程信息后，点击保存，会将表单信息保存到数据库中



### 查看接口文档，进行编码

查看接口文档



#### Dao层：CourseMapper

```java
/*
        新增课程信息
     */
    public void saveCourse(Course course);

    /*
        新增讲师信息
     */
    public void saveTeacher(Teacher teacher);
```

CourseMapper.xml

```xml
<!--新增课程信息-->
    <insert id="saveCourse" parameterType="Course">
        INSERT INTO course(
        course_name,
        brief,
        preview_first_field,
        preview_second_field,
        course_img_url,
        course_list_img,
        sort_num,
        price,
        discounts,
        sales,
        discounts_tag,
        course_description_mark_down,
        create_time,
        update_time
        ) VALUES(#{courseName},#{brief},#{previewFirstField},#{previewSecondField},#{courseImgUrl},
        #{courseListImg},#{sortNum},#{price},#{discounts},#{sales},#{discountsTag},#{courseDescriptionMarkDown},
        #{createTime},#{updateTime});

        <!--获取添加成功记录返回的ID值赋值给Course实体中ID属性-->
        <selectKey resultType="int" order="AFTER" keyProperty="id">
            select LAST_INSERT_ID()
        </selectKey>

    </insert>


    <!--新增讲师信息-->
    <insert id="saveTeacher" parameterType="Teacher">
        INSERT INTO teacher(
        course_id,
        teacher_name,
        POSITION,
        description,
        create_time,
        update_time
    ) VALUES(#{courseId},#{teacherName},#{position},#{description},#{createTime},#{updateTime});
    </insert>
```



#### Service层：CourseService

```java
/*
        添加课程及讲师信息
     */
    public void saveCourseOrTeacher(CourseVO courseVO) throws InvocationTargetException, IllegalAccessException;
```

```java
// 根据controller 传递过来的 coursevo 对象，来进行两个实体的封装
    // 再分别调用dao层方法 进行 课程信息和讲师信息的保存
    @Override
    public void saveCourseOrTeacher(CourseVO courseVO) throws InvocationTargetException, IllegalAccessException {

        // 封装课程信息，获取到返回的id值
        Course course = new Course();

        // 就是吧courseVO 里面 名称相同的 属性值封装到 course对象中
        BeanUtils.copyProperties(course,courseVO);

        // 补全课程信息
        Date date = new Date();
        course.setCreateTime(date);
        course.setUpdateTime(date);

        // 保存课程
        courseMapper.saveCourse(course);

        // 获取新插入数据的id值
        int id = course.getId();

        // 封装讲师信息
        Teacher teacher = new Teacher();
        BeanUtils.copyProperties(teacher,courseVO);

        // 补全讲师信息
        teacher.setCreateTime(date);
        teacher.setUpdateTime(date);
        teacher.setIsDel(0);
        teacher.setCourseId(id);

        // 保存讲师信息
        courseMapper.saveTeacher(teacher);

    }
```



#### Web层：CourseController

```java
/*
        新增课程信息及讲师信息
        新增课程信息和修改课程信息要写在同一个方法中
     */
    @RequestMapping("/saveOrUpdateCourse")
    // 把前台发来的JSON转为courseVo 实体
    public ResponseResult saveOrUpdateCourse(@RequestBody CourseVO courseVO) throws InvocationTargetException, IllegalAccessException {

        ResponseResult responseResult = null;

        // 判断courseVO 里面的id值为不为空，从而决定当前执行的是更新操作还是新增操作
        // 更新操作，前台会传递id值的
        if (courseVO.getId() == null){
            // 调用service    把从前台接收并封装好的courseVo 进行传递
            courseService.saveCourseOrTeacher(courseVO);
            responseResult = new ResponseResult(true, 200, "新增成功", null);
        } else {
            // 修改
            courseService.updateCourseOrTeacher(courseVO);
            responseResult = new ResponseResult(true, 200, "修改成功", null);
        }

        // 给前台 ResponseResult 转为JSON的响应
        return responseResult;
    }
```



#### Postman测试接口





## 回显课程信息

### 需求分析：

点击编辑按钮，回显课程信息

​	

### 查看接口文档，进行编码

查看接口文档



#### Dao层：CourseMapper

```java
/*
        回显课程信息(根据id查询对应的课程信息及关联的讲师信息)
     */
    // 返回值是CourseVO，它既能封装课程信息，也能封装讲师信息
    public CourseVO findCourseById(Integer id);
```

CourseMapp.xml

```xml
<!--    课程信息回显      public CourseVO findCourseById(Integer id);-->
    <select id="findCourseById" parameterType="int" resultType="courseVo">
        SELECT
          c.*,
          t.`teacher_name` teacher_name,
          t.`position` POSITION,
          t.`description` description
    FROM
            course c
  LEFT JOIN teacher t
    ON c.`id` = t.`course_id`
WHERE c.`id` = #{id}
    </select>
```



#### Service层：CourseService

```java
/*
        根据ID查询课程信息
     */
    public CourseVO findCourseById(Integer id);
```

```java
@Override
    public CourseVO findCourseById(Integer id) {
        return courseMapper.findCourseById(id);
    }
```



#### Web层：CourseController

```java
/*
        根据id查询具体的课程信息及关联的讲师信息
     */
    @RequestMapping("/findCourseById")
    public ResponseResult findCourseById(Integer id){

        CourseVO courseVO = courseService.findCourseById(id);

        return new ResponseResult(true,200,"根据ID查询课程信息成功",courseVO);
    }
```



#### Postman测试接口





## 修改课程信息

### 需求分析：

点击保存按钮，将修改后的课程信息保存到数据库中



### 查看接口文档，进行编码

查看接口文档



#### Dao层：CourseMapper

```java
/*
        更新课程信息
     */
    public void updateCourse(Course course);

    /*
        更新讲师信息
     */
    public void updateTeacher(Teacher teacher);
```

CourseMapper.xml

```xml
<!--更新课程信息-->
    <update id="updateCourse" parameterType="Course">
        UPDATE course
        <trim prefix="SET" suffixOverrides=",">
            <if test="courseName != null and courseName != ''">
                course_name = #{courseName},
            </if>

            <if test="brief != null and brief != ''">
                brief=#{brief},
            </if>

            <if test="previewFirstField != null and previewFirstField != ''">
                preview_first_field=#{previewFirstField},
            </if>

            <if test="previewSecondField != null and previewSecondField != ''">
                preview_second_field=#{previewSecondField},
            </if>

            <if test="courseImgUrl != null and courseImgUrl != ''">
                course_img_url=#{courseImgUrl},
            </if>

            <if test="courseListImg != null and courseListImg != ''">
                course_list_img=#{courseListImg},
            </if>

            <if test="sortNum != null and sortNum != ''">
                sort_num=#{sortNum},
            </if>

            <if test="price != null and price != ''">
                price=#{price},
            </if>

            <if test="discounts != null and discounts != ''">
                discounts=#{discounts},
            </if>

            <if test="sales != null and sales != '' or sales==0">
                sales=#{sales},
            </if>

            <if test="discountsTag != null and discountsTag != ''">
                discounts_tag=#{discountsTag},
            </if>

            <if test="courseDescriptionMarkDown != null and courseDescriptionMarkDown != ''">
                course_description_mark_down=#{courseDescriptionMarkDown},
            </if>

            <if test="updateTime != null">
                update_time=#{updateTime},
            </if>

        </trim>

        <where>
            <if test="id!=null and id != '' ">id=#{id}</if>
        </where>

    </update>

    <!--更新讲师信息-->
    <update id="updateTeacher" parameterType="Teacher">
        UPDATE teacher
        <trim prefix="SET" suffixOverrides=",">
            <if test="teacherName != null and teacherName != ''">
                teacher_name = #{teacherName},
            </if>

            <if test="position != null and position != ''">
                position = #{position},
            </if>

            <if test="description != null and description != ''">
                description = #{description},
            </if>

            <if test="updateTime != null">
                update_time=#{updateTime}
            </if>
        </trim>
        <where>
            <if test="courseId != null and courseId != '' ">course_id = #{courseId}</if>
        </where>
    </update>
```



#### Service层：CourseService

```java
/*
        更新课程及讲师信息
     */
    public void updateCourseOrTeacher(CourseVO courseVO) throws InvocationTargetException, IllegalAccessException;
```

```java
@Override
    public void updateCourseOrTeacher(CourseVO courseVO) throws InvocationTargetException, IllegalAccessException {

        // 封装课程信息
        Course course = new Course();

        BeanUtils.copyProperties(course,courseVO);

        // 补全课程信息
        Date date = new Date();
        course.setUpdateTime(date);

        // 更新课程信息
        courseMapper.updateCourse(course);


        // 封装讲师信息
        Teacher teacher = new Teacher();

        BeanUtils.copyProperties(teacher,courseVO);

        // 补全讲师信息
        // 在封装讲师信息时，要设置讲师对应的 课程id
        teacher.setCourseId(course.getId());
        teacher.setUpdateTime(date);

        // 更新讲师信息
        courseMapper.updateTeacher(teacher);

    }
```



#### Web层：CourseController

```java
/*
        新增课程信息及讲师信息
        新增课程信息和修改课程信息要写在同一个方法中
     */
    @RequestMapping("/saveOrUpdateCourse")
    // 把前台发来的JSON转为courseVo 实体
    public ResponseResult saveOrUpdateCourse(@RequestBody CourseVO courseVO) throws InvocationTargetException, IllegalAccessException {

        ResponseResult responseResult = null;

        // 判断courseVO 里面的id值为不为空，从而决定当前执行的是更新操作还是新增操作
        // 更新操作，前台会传递id值的
        if (courseVO.getId() == null){
            // 调用service    把从前台接收并封装好的courseVo 进行传递
            courseService.saveCourseOrTeacher(courseVO);
            responseResult = new ResponseResult(true, 200, "新增成功", null);
        } else {
            // 修改
            courseService.updateCourseOrTeacher(courseVO);
            responseResult = new ResponseResult(true, 200, "修改成功", null);
        }

        // 给前台 ResponseResult 转为JSON的响应
        return responseResult;
    }
```



#### Postman测试接口





## 课程状态管理

### 需求分析：

在课程列表展示页面中，可以通过点击 上架/下架 按钮，修改课程状态



### 查看接口文档，进行编码

查看接口文档



#### Dao层：CourseMapper

```java
/*
        课程状态管理
     */
    public void updateCourseStatus(Course course);
```

CourseMapper.xml

```xml
<!--    课程状态管理
            根据传递过来的id值 确定要修改Course表中的 哪条记录的 status值
                上架操作，status值为1
                下架操作，status值为0
-->
    <update id="updateCourseStatus" parameterType="Course">
        update course set status = #{status},update_time = #{updateTime} where id = #{id}
    </update>
```



#### Service层：CourseService

```java
/*
        修改课程状态
     */
    public void updateCourseStatus(int courseId,int status);
```

```java
/*
        课程状态管理
     */
    @Override
    public void updateCourseStatus(int courseId, int status) {

        // 1.封装数据
        Course course = new Course();
        course.setId(courseId);
        course.setStatus(status);
        course.setUpdateTime(new Date());

        // 2.调用mapper
        courseMapper.updateCourseStatus(course);
    }
```



#### Web层：CourseController

```java
/*
        修改课程状态
     */
    @RequestMapping("/updateCourseStatus")
    public ResponseResult updateCourseStatus(Integer id,Integer status){

        // 调用service，传递参数，完成课程状态的修改
        courseService.updateCourseStatus(id,status);

        // 响应数据
        Map<String, Object> map = new HashMap<>();
        map.put("status",status);

        return new ResponseResult(true,200,"课程状态修改成功",map);
    }
```



#### Postman测试接口







## 课程内容展示

### 需求分析：

需求：点击内容管理，展示课程对应的课程内容（章节+课时）



### 查看接口文档，进行编码：

查看接口文档



#### CourseSection实体类

```java
/**
 * 章节类
 * */
public class CourseSection {

    //id
    private Integer id;

    //课程id
    private int courseId;

    //章节名
    private String sectionName;

    //章节描述
    private String description;

    //创建时间
    private Date createTime;

    //更新时间
    private Date updateTime;

    //是否删除
    private int isDe;

    //排序
    private int orderNum;

    //状态
    private int status;

    // 在一方实体中表示对方关系 课时集合
    private List<CourseLesson> lessonList;

    // getter/setter...
}
```



#### Dao层：CourseContentMapper

```java
public interface CourseContentMapper {
    /*
        根据课程id查询关联的章节信息及章节信息关联的课时信息
     */
    public List<CourseSection> findSectionAndLessonByCourseId(int courseId);
}
```

```xml
<!-- 一对多配置,一个章节下有多个课时 -->
<!--    手动映射封装-->
    <resultMap id="SectionAndLessonResultMap" type="courseSection">
        <id property="id" column="id"/>
        <result property="courseId" column="course_id"/>
        <result property="sectionName" column="section_name"/>
        <result property="description" column="description"/>
        <result property="orderNum" column="order_num"/>
        <result property="status" column="status"/>

        <!-- 使用 collection,配置一对多关系 -->
<!--        封装课时信息-->
        <collection property="lessonList" ofType="CourseLesson">
            <id property="id" column="lessonId"/>
            <result property="courseId" column="course_id"/>
            <result property="sectionId" column="section_id"/>
            <result property="theme" column="theme"/>
            <result property="duration" column="duration"/>
            <result property="isFree" column="is_free"/>
            <result property="orderNum" column="order_num"/>
            <result property="status" column="status"/>
        </collection>
    </resultMap>

    <!-- 课时表字段信息  -->
    <sql id="lesson_column_list">
      cl.id lessonId,
      cl.course_id,
      cl.section_id,
      cl.theme,
      cl.duration,
      cl.is_free,
      cl.order_num,
      cl.status
    </sql>



<!--    根据课程ID查询课程内容(章节-课时)-->
<select id="findSectionAndLessonByCourseId" parameterType="int" resultMap="SectionAndLessonResultMap">
    SELECT
  cs.*,
  <include refid="lesson_column_list"></include>
FROM
  course_section cs
  LEFT JOIN course_lesson cl
    ON cl.section_id = cs.id
WHERE cs.course_id = #{id}
ORDER BY cs.order_num
</select>
```



#### Service层：CourseContentService

```java
public interface CourseContentService {

    /*
        根据课程id查询对应的课程内容（章节信息及课时信息）
     */
    public List<CourseSection> findSectionAndLessonByCourseId(int courseId);
}
```

```java
@Service
public class CourseContentServiceImpl implements CourseContentService {

    // 注入mapper对象
    @Autowired
    private CourseContentMapper courseContentMapper;

    @Override
    public List<CourseSection> findSectionAndLessonByCourseId(int courseId) {
        return courseContentMapper.findSectionAndLessonByCourseId(courseId);
    }
}
```



#### Web层：CourseContentController

```java
@RestController
@RequestMapping("/courseContent")
public class CourseContentController {

    @Autowired
    private CourseContentService courseContentService;


    /**
  * 查询课程内容
  * */
    @RequestMapping("/findSectionAndLesson")
    public ResponseResult findSectionAndLessonByCourseId(int courseId){

        // 调用service
        List<CourseSection> list = courseContentService.findSectionAndLessonByCourseId(courseId);

        return new ResponseResult(true,200,"章节及课时内容查询成功",list);
    }
}
```





#### Postman测试接口





## 回显章节对应的课程信息

### 需求分析：

需求：在课程内容界面回显课程信息



### 查看接口文档，进行编码：

查看接口文档



#### Dao层：CourseContentMapper

```java
/*
        回显章节对应的课程信息
            根据课程id查询课程信息
     */
    public Course findCourseByCourseId(int courseId);
```

```xml
<!--    回显课程信息-->
<select id="findCourseByCourseId" resultType="course" parameterType="int">
    select id,course_name from course where id = #{courseId}
</select>
```



#### Service层：CourseContentService

```java
/*
        回显章节对应的课程信息
            根据课程id查询课程信息
     */
    public Course findCourseByCourseId(int courseId);
```

```java
 @Override
    public Course findCourseByCourseId(int courseId) {
        return courseContentMapper.findCourseByCourseId(courseId);
```



#### Web层：CourseContentController

```java
 /*
        回显章节对应的课程信息
     */
    @RequestMapping("/findCourseByCourseId")
    public ResponseResult findCourseByCourseId(int courseId){

        // 查询出来的具体的course信息
        Course course = courseContentService.findCourseByCourseId(courseId);

        // 转为JSON响应给前台
        return new ResponseResult(true,200,"查询课程信息成功",course);

    }
```



#### Postman测试接口





## 新建章节信息

### 需求分析：

在课程内容展示页面中，可以通过点击添加阶段按钮，添加章节信息



### 查看接口文档，进行编码

查看接口文档



#### Dao层：CourseContentMapper

```java
/*
        新增章节信息
     */
    public void saveSection(CourseSection courseSection);
```

```xml
<!--    保存章节-->
<insert id="saveSection" parameterType="courseSection">
    insert into course_section(
        course_id,
        section_name,
        description,
        order_num,
        STATUS,
        create_time,
        update_time
    )values(#{courseId},#{sectionName},#{description},#{orderNum},#{status},#{createTime},#{updateTime})
</insert>
```



#### Service层：CourseContentService

```java
/*
        新增章节信息
     */
    public void saveSection(CourseSection courseSection);
```

```java
@Override
    public void saveSection(CourseSection courseSection) {
        // 1.补全信息
        Date date = new Date();
        courseSection.setCreateTime(date);
        courseSection.setUpdateTime(date);

        // 2.调用方法
        courseContentMapper.saveSection(courseSection);
    }
```



#### Web层：CourseContentController

```java
/*
        新增&更新章节信息
     */
    @RequestMapping("/saveOrUpdateSection")
    public ResponseResult saveOrUpdateSection(@RequestBody CourseSection courseSection){

        ResponseResult responseResult= null;
        // 判断是否携带了章节id
        if (courseSection.getId() == null) {
            // 新增
            courseContentService.saveSection(courseSection);
            responseResult = new ResponseResult(true,200,"新增章节成功",null);
        }else {
            // 更新
            courseContentService.updateSection(courseSection);
            responseResult =  new ResponseResult(true,200,"新增章节成功",null);
        }
        return responseResult;
    }
```



#### Postman测试接口





## 修改章节信息

### 需求分析：

点击确定按钮，将修改后的章节信息保存到数据库中



### 查看接口文档，进行编码

查看接口文档



#### Dao层：CourseContentMapper

```java
/*
        更新章节信息
     */
    void updateSection(CourseSection courseSection);
```

```xml
<!--更新章节 void updateSection(CourseSection courseSection);-->
    <update id="updateSection" parameterType="courseSection">
        UPDATE course_section
        <trim prefix="SET" suffixOverrides=",">
            <if test="sectionName != null and sectionName != ''">
                section_name = #{sectionName},
            </if>

            <if test="description != null and description != ''">
                description = #{description},
            </if>

            <if test="orderNum != null and orderNum != '' or orderNum == 0">
                order_num = #{orderNum},
            </if>

            <if test="updateTime != null">
                update_time=#{updateTime},
            </if>
        </trim>
        <where>
            <if test="id != null and id != '' ">id = #{id}</if>
        </where>
    </update>
```



#### Service层：CourseContentService

```java
/*
        更新章节信息
     */
    void updateSection(CourseSection courseSection);
```

```java
/*
        更新章节信息
     */
    @Override
    public void updateSection(CourseSection courseSection) {

        // 1.补全信息
        courseSection.setUpdateTime(new Date());

        // 2.调用方法
        courseContentMapper.updateSection(courseSection);
    }
```



#### Web层：CourseContentController

```java
/*
        新增&更新章节信息
     */
    @RequestMapping("/saveOrUpdateSection")
    public ResponseResult saveOrUpdateSection(@RequestBody CourseSection courseSection){

        ResponseResult responseResult= null;
        // 判断是否携带了章节id
        if (courseSection.getId() == null) {
            // 新增
            courseContentService.saveSection(courseSection);
            responseResult = new ResponseResult(true,200,"新增章节成功",null);
        }else {
            // 更新
            courseContentService.updateSection(courseSection);
            responseResult =  new ResponseResult(true,200,"新增章节成功",null);
        }
        return responseResult;
    }
```



#### Postman测试接口







## 修改章节状态

### 需求分析：

需求：点击修改章节按钮，将当前章节信息的状态进行修改



### 查看接口文档，进行编码

查看接口文档



#### Dao层：CourseContentMapper

```java
/*
        修改章节状态
     */
    void updateSectionStatus(CourseSection courseSection);
```

CourseContentMapper.xml

```xml
<!--    修改章节状态 void updateSectionStatus(CourseSection courseSection);
            根据id修改指定记录的status的值
-->
    <update id="updateSectionStatus" parameterType="courseSection">
        update course_section set status = #{status},update_time = #{updateTime} where id = #{id}
    </update>
```



#### Service层：CourseContentService

```java
 /*
        修改章节状态
     */
    void updateSectionStatus(int id,int status);
```

```java
// 修改章节状态
    @Override
    public void updateSectionStatus(int id, int status) {

        // 封装数据
        CourseSection courseSection = new CourseSection();
        courseSection.setStatus(status);
        courseSection.setUpdateTime(new Date());
        courseSection.setId(id);

        // 调用mapper
        courseContentMapper.updateSectionStatus(courseSection);
    }
```



#### Web层：CourseContentController

```java
/*
        修改章节状态
        状态，0:隐藏；1：待更新；2：已发布
     */
    @RequestMapping("/updateSectionStatus")
    public ResponseResult updateSectionStatus(int id,int status){

        courseContentService.updateSectionStatus(id,status);

        
        //封装最新的状态信息
        Map<String,Object> map = new HashMap<>();
        map.put("status",status);

        // 数据响应
        return new ResponseResult(true,200,"修改章节状态成功",map);
    }
```



#### Postman测试接口







## 新建&修改课时信息

### 需求分析：

需求：点击添加课时按钮，将弹出页面填写的课时信息保存到数据库中。

点击编辑按钮，修改课时信息



### 查看接口文档，进行编码

查看接口文档



#### Dao层：CourseContentMapper

```java
/*
        新增课时信息
     */
    void saveLesson(CourseLesson courseLesson);

    /*
        更新课时信息
     */
    void updateLesson(CourseLesson courseLesson);
```

```xml
<!--    新增课时信息 void saveLesson(CourseLesson courseLesson);-->
    <insert id="saveLesson" parameterType="courseLesson">
        insert into course_lesson(
            course_id,
            section_id,
            theme,
            duration,
            is_free,
            order_num,
            create_time,
            update_time
        ) values (#{courseId},#{sectionId},#{theme},#{duration},#{isFree},#{orderNum},#{createTime},#{updateTime})
    </insert>


<!--    更新课时信息  void updateLesson(CourseLesson courseLesson);-->
    <update id="updateLesson" parameterType="courseLesson">
        UPDATE course_lesson
        <trim prefix="SET" suffixOverrides=",">
            <if test="theme != null and theme != ''">
                theme = #{theme},
            </if>

            <if test="duration != null and duration != ''">
                duration = #{duration},
            </if>

            <if test="isFree != null and isFree != ''">
                is_free = #{isFree},
            </if>

            <if test="orderNum != null and orderNum != '' or orderNum == 0">
                order_num = #{orderNum},
            </if>

            <if test="updateTime != null">
                update_time=#{updateTime},
            </if>
        </trim>
        <where>
            <if test="id != null and id != '' ">id = #{id}</if>
        </where>
    </update>
```



#### Service层：CourseContentService

```java
/*
        新增课时信息
     */
    void saveLesson(CourseLesson courseLesson);

    /*
        修改课时信息
     */
    void updateLesson(CourseLesson courseLesson);
```

```java
/*
        新增课时信息
     */
    @Override
    public void saveLesson(CourseLesson courseLesson) {

        // 1.补全信息
        Date date = new Date();
        courseLesson.setCreateTime(date);
        courseLesson.setUpdateTime(date);

        // 2.调用方法
        courseContentMapper.saveLesson(courseLesson);
    }

    /*
        更新课时信息
     */
    @Override
    public void updateLesson(CourseLesson courseLesson) {

        // 1.补全信息
        courseLesson.setUpdateTime(new Date());

        // 2.调用方法
        courseContentMapper.updateLesson(courseLesson);
    }
```



#### Web层：CourseContentController

```java
/*
        新增&修改课时信息
     */
    @RequestMapping("/saveOrUpdateLesson")
    public ResponseResult saveOrUpdateLesson(@RequestBody CourseLesson courseLesson){

        ResponseResult responseResult = null;
        // 判断是否携带了课时id
        if (courseLesson.getId() == null) {
            // 新增
            courseContentService.saveLesson(courseLesson);
            responseResult = new ResponseResult(true,200,"新增课时成功",null);
        }else {
            // 修改
            courseContentService.updateLesson(courseLesson);
            responseResult = new ResponseResult(true,200,"修改课时成功",null);
        }
        return responseResult;
    }
```



#### Postman测试接口