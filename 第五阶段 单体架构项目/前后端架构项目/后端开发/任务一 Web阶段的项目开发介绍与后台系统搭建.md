# Web阶段的项目开发介绍与后台系统搭建

# 1. 项目架构

## 1.1 项目介绍

​	后台管理系统

​	后台管理系统中的课程管理模块



## 1.2 功能模块

课程管理模块包含的内容有：

1. 课程信息页面展示
2. 课程营销信息配置（课程的详细信息）
3. 课程内容管理（章节信息）

​	

## 1.3 前后端分离开发

### 1.3.1  前后端分离架构介绍

​		前后端分离开发已成为互联网项目开发的标准使用方式，将前端和后端的开发进行解耦。

这样前后端就可以并行的开发



**前后端分离的核心思想就是前端HTML页面通过AJAX调用后端的API接口，并通过JSON数据格式进行交互**，前端访问后台时通过JSON数据格式，后端响应给前台的数据也是JSON格式



* 在前后端分离的项目中，前后端开发人员是依照一个共同的接口文档来进行开发的。前端干的前端的活，后端干后端的活。都遵循同一套接口规范去编写代码
* 前后端分离的项目中，前端是可以独立部署运行的，后端也一样

### 1.3.2 接口文档

#### 1.3.2.1 什么是接口文档？

​		在项目开发中，web项目的前后端分离开发，APP开发，需要由前后端工程师共同定义接口，编写接口文档，定义好后再同时进行开发，大家都是根据定义好的接口文档进行开发，到项目结束前都要一直维护。

#### 1.3.2.2 为什么要写接口文档？

1. 因为在项目开发中，前后端工程师都遵循同一套规范去进行开发，当对接测试时不会出现大问题，可以更好的进行沟通交流开发
2. 项目维护或项目人员更迭，方便后期人员查看

#### 1.3.2.3 接口文档规范？                                 前后端人员都要遵循接口文档中的规范去进行开发，这样在后期进行对接测试时就不会出现大问题了

至少包含下面几项：

* 功能名称：findByCourseNameOrStatus
* 描述：当前接口的作用               根据条件查询课程信息
* URL：                       前端去访问后端接口的请求地址
* 请求方式：GET还是POST                        定义好了，前端就按照定义好的请求方式进行请求
* 请求参数：前台请求参数是什么类型，携带几个参数，参数的格式？             都要定义好，后台接收参数就按照接口文档定义的去接收
* 响应结果：后台响应的数据是什么类型？



### 1.3.3 前后端分离架构的优势

#### 1.3.3.1 前后端耦合的开发方式

​		Java程序员又要干前端的活，又要干后端的活。后端要把HTML改成JSP，如果HTML发生变更，开发效率低

#### 1.3.3.2 前后端分离耦合的缺点（JSP）

1. Java工程师既要干前台又要干后台，要将HTML改成JSP页面，效率很低
2. JSP页面必须要运行在WEB服务器上，性能低。JSP已经不满足现在互联网复杂的页面了
3. JSP最终会被编译成Servlet，效率低。之后每次请求JSP都是访问Servlet输出的HTML页面，效率没有直接使用HTML高

#### 1.3.3.3 前后端分离的开发方式

​		前后端分离开发的话，前后端人员先把接口文档定义好，要传递什么样的参数，请求数据的格式，接口的地址等等。定好后前后端并行开发，根据定义好的接口文档来开发，如果需求变了，但是接口和参数不变的话，两边都不用修改代码。开发效率高

#### 1.3.3.4 前后端分离的优势

1. 前后端分离的模式下，因为前端有独立的项目，可以独立运行，只是发送请求到后台的接口，如果出现了bug，可以定位到是谁的问题
2. 前后端分离可以减少后端服务器的压力。除了接口请求以外的其他所有请求全部转移到前端，接口的请求则转发调用Tomcat
3. 前后端分离的模式下，即使后台的服务器宕机了，前端因为是独立运行的项目，所有前端页面还是会正常访问，不会报错，只是数据刷不出来。
4. 前后端分离开发分配工作量合理了，减轻了后端人员的工作量，提高了性能和效率



## 1.4 后台管理系统的技术选型                   

##  前后端分离

### 1.4.1 前端技术选型     

| 前端技术      | 说明                                                         |
| ------------- | ------------------------------------------------------------ |
| Vue.js        | 构建用户界面渐进式的**JavaScript框架**                       类似JQuery |
| Element  UI库 | 饿了么前端出品的基于Vue.js 的后台组件库，<br>方便程序员进行**页面快速的布局和构建**                         类似Bootstrap |
| node.js       | Node.js 就是**运行在服务器端的JavaScript 运行环境**       前端项目独立运行的运行环境 |
| axios         | 对Ajax的封装，Ajax**实现了局部数据的刷新**，axios实现了对Ajax的封装 |

### 1.4.2 后端技术选型                   三层架构			

| 后端技术                              | 说明                                                         |
| ------------------------------------- | ------------------------------------------------------------ |
| Web层                                 | Servlet：前端控制器，接收前台请求，并作出响应<br>Filter:过滤器<br>BeanUtils：工具类，进行数据封装 |
| Service层                             | 业务处理                                                     |
| dao层    数据访问层，对数据进行操作的 | MySQL：数据库<br>Druid：数据库连接池<br>DBUtils：操作数据库  |



## 1.5 项目开发环境的介绍

* 开发工具
  * 后台：IDEA  2019
  * 前台：VS   code
  * 数据库：SQLYog
* 开发环境
  * JDK  11
  * Maven  3.6.3
  * MySQL  5.7

# 2. Maven 项目管理工具

## 2.1 Maven介绍

### 2.1.1  什么是Maven

​		Maven是一个跨平台的项目管理工具。其主要是对Java平台的项目进行构建，依赖管理和项目信息管理。

### 2.1.2  Maven的作用

* 依赖管理
  * 依赖指的就是项目中需要使用的第三方jar包，之前使用的方式就是把需要的jar包导入到工程中，还要解决jar冲突的问题
  * Maven可以对Jar包进行统一管理，快速引入Jar包，在Maven中配置就ok	
* 一键构建项目
  * 之前我们创建项目，需要确定项目的结构，哪个包放什么，还要配置环境，十分麻烦	
  * Maven为我们提供了一个标准化的Java项目结构，我们可以通过Maven快速地创建一个标准的Java项目，目录结构都创建好

## 2.3 Maven仓库

* Maven仓库用来存放Maven构建的项目和各种依赖的 

### 2.3.1 Maven的仓库分类

* 本地仓库：位于自己计算机的仓库，存储从远程仓库或中央仓库下载的jar
* 远程仓库：联网访问的，阿里的Maven远程仓库
* 中央仓库：Maven的

构建Maven工程的时候，首先要保证有本地仓库，如果用的jar包。默认去本地仓库，没有的话就回去中央仓库，但是中央仓库下载的慢。所以要配置阿里提供的Maven仓库，去阿里的下载

### 2.3.2 Maven 本地仓库的配置

1. Maven默认本地仓库在C盘.m2目录
2. 重新配置仓库
3. 在maven安装目录中,进入 conf文件夹,  可以看到一个 settings.xml 文件中, 我们在这个文件中, 进行本地仓库的配置
4. 打开 settings.xml文件，进行配置

### 2.3.3 配置阿里仓库

1. 打开 settings.xml,找到 <mirrors> 标签 , 下面的内容复制到  <mirrors> 中 即可

```xml
<mirror>
    <id>alimaven</id>
    <name>aliyun maven</name>
    <url>
        http://maven.aliyun.com/nexus/content/groups/public/
    </url>
    <mirrorOf>central</mirrorOf>        
</mirror>
```



## 2.4 创建Maven项目

### 2.4.1 IDEA中配置Maven

1) 打开IDEA 创建一个新的空的project

2) 起名为web_work

3) 首先打开IDEA 选择File --> Settings  --> 搜索maven,就会看到如下界面

2) 修改默认配置配置                 本地Maven的安装目录，Maven的settings文件，Maven的本地仓库配置

---



### 2.4.2  创建Maven工程

**在IDEA中配置好Maven后，适应Maven快速的构建一个JavaWeb项目**

1) project创建好以后, 选择创建module

2) 选中创建一个 maven 工程

3) 点击 Next填写项目信息 		工程名，Maven项目存的位置，公司或组织的名称，工程名，版本信息

进行一下修改

Maven目录说明: 

```java
src/main/java 		 —— 源代码目录
src/main/resources 	 —— 资源文件目录，配置文件 
src/test/java 		—— 测试目录，测试代码 
target 			    —— 保存编译后的class文件的 
pom.xml              ——maven 项目核心配置文件 
```

***



### 2.4.3 Maven工程改造                             MavenWeb工程的创建

当前创建的Maven项目是一个普通的Java项目，不是web项目，需要改造

1) 在main目录下创建一个webapp文件夹

2) 选择  project Structure ---> facets---> 点击+号 添加web ---> 选择当前工程hello_maven

3) 修改路径信息

4)修改为 我们的 webapp目录

5) 点击ok 后，项目就变为了web项目, 在webapp目录下再创建一个 index.jsp,就OK了

---



### 2.4.4 pom核心配置文件			引入依赖

**一个 maven 工程都有一个 pom.xml 文件，通过 pom.xml 文件定义项目的信息、项目依赖、引入插件等等**           

**如果要使用Maven工程的话，就要在pom.xml文件中引入依赖包的坐标**

一个Maven工程就是由**`groupId`，`artifactId `和 `version`** 作为唯一标识, 我们在引用其他第三方库的时候，也是通过这3个变量确定。

* **Maven坐标**主要组成(GAV) - 确定一个jar在互联网位置

| 标签           | 含义                                                         |
| -------------- | ------------------------------------------------------------ |
| **groupId**    | 定义当前Maven组织名称,通常是公司名                           |
| **artifactId** | 定义实际项目名称                                             |
| **version**    | 定义当前项目的当前版本                                       |
| **packaging**  | 打包类型 jar：执行 package命令 会打成 jar 包       war：执行 package 会打成 war 包 |
| **dependency** | 使用 ``声明一个依赖后，Maven就会自动下载这个依赖包           |

4)  maven 的依赖管理, 是对项目所依赖的 jar 包进行统一管理。

| 标签             | 含义                                               |
| ---------------- | -------------------------------------------------- |
| **dependencies** | 表示依赖关系                                       |
| **dependency**   | 使用 ``声明一个依赖后，Maven就会自动下载这个依赖包 |

**Maven工程都是通过坐标去找到引入的jar包**

```xml
<!-- 引入依赖包的坐标 -->
<dependencies>
    
    <!-- 依赖 -->
    <dependency>
        <!-- 组织或者公司的名称 -->
        <groupId>javax.servlet</groupId>
        <!-- 实际项目的名称 -->
        <artifactId>servlet-api</artifactId>
        <!-- 项目的版本 -->
        <version>3.1.0</version>
    </dependency>
</dependencies>	
```

5) 坐标的来源方式 

**idea右键生成dependency，搜索要引入的依赖**

---



### 2.4.5 添加插件                      添加编译插件，设置编译版本

1) 添加编译插件,  设置 jdk 编译版本 

本教程使用 jdk11，需要设置编译版本为 11，这里需要使用 maven 的插件来设置

在pom中加入如下配置:

```xml
	<!-- properties 是全局设置,可以设置整个maven项目的编译器 JDK版本 -->
    <properties>
        <!-- 字符集设置 -->
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <!-- 重点  -->
        <!-- 源码编译设置 -->
        <maven.compiler.source>11</maven.compiler.source>
        <!-- target里的class文件的编译版本 -->
        <maven.compiler.target>11</maven.compiler.target>
    </properties>

    <!-- 在build中 我们需要指定一下项目的JDK编译版本,maven默认使用1.5版本进行编译
    注意 build 与 dependencies是平级关系,标签不要写错位置  -->
    <build>
        
        <plugins>
        	<!-- maven 编译插件  -->    
            <plugin>
                <!-- 组织或公司  maven 提供的 -->
                <groupId>org.apache.maven.plugins</groupId>
                <!-- 当前插件的具体名称 -->
                <artifactId>maven-compiler-plugin</artifactId>
                <!-- 版本 -->
                <version>3.8.1</version>
                <configuration>
                    <release>11</release>
                </configuration>
            </plugin>
        </plugins>
    </build>
```



### 2.4.6 运行Maven项目

1) 完善项目代码

2) 配置tomcat ,部署项目

3) 运行项目, 默认访问 index.jsp

4) 访问Servlet

### 2.4.7 Maven的常用命令

1. 一个maven项目的生命周期

   如果maven构建项目的话，项目生命周期：

   * 清理         清除当前项目下的编译文件
   * 编译          
   * 测试
   * 报告
   * 打包
   * 部署

2.  maven每个阶段都提供了一些命令对应生命周期的各个过程

| 命令         | 说明                                                         |
| ------------ | ------------------------------------------------------------ |
| mvn  compile | 编译操作，执行后，生成target目录                             |
| mvn  clean   | 执行后，删除target目录                                       |
| mvn  test    | 执行后，生成测试报告                                         |
| mvn  package | 完成打包操作，会target目录中生成一个文件，可能是jar、war     |
| mvn  install | 将打好的jar包安装到本地仓库的。是将当前工程安装到本地仓库中，方便其他工程引用 |

---



注意：**如果创建的是web工程，得在pom.xml中指定packaging标签打包类型为war**

---



### 2.4.8 依赖范围介绍

1. 如果A工程依赖B，需要在A的pom.xml文件中导入B的坐标，引入。可以指定以依赖范围：

| 依赖范围     | 说明                                                         |
| ------------ | ------------------------------------------------------------ |
| compile      | 当前项目在编译测试运行阶段，都需要用到所依赖的jar包，默认    |
| **provided** | 只在编译和测试用，运行打包不用，**导入servlet api 时 加这个标签** |
| runtime      | 运行和测试需要，编译不用                                     |
| test         | 编译和运行都不要，测试要                                     |
| system       | 表示maven仓库没有jar包，本地引入使用                         |

2. 项目中添加的坐标 ,并指定依赖范围

```xml
	<dependencies>
        <dependency>
            <!-- 项目名称 -->
            <groupId>javax.servlet</groupId>
            <!-- 模块名称 -->
            <artifactId>servlet-api</artifactId>
            <!-- 版本信息 -->
            <version>3.1.0</version>
        <!-- 依赖范围, 指定依赖范围是编译与测试时有效,运行时无效,运行时使用tomcat中的依赖,避免冲突 -->
            <scope>provided</scope>
        </dependency>

        <dependency>
            <groupId>javax.servlet.jsp</groupId>
            <artifactId>jsp-api</artifactId>
            <version>2.1.2</version>
            <scope>provided</scope>
        </dependency>

        <dependency>
            <groupId>junit</groupId>
            <artifactId>junit</artifactId>
            <version>4.13</version>

            <!-- 在测试时有效 -->
            <scope>test</scope>
        </dependency>
    </dependencies>
```

# 3. 后台系统搭建

## 3.1 课程管理模块功能的分析

​		在本次项目中，完成后台管理系统的 课程管理模块，课程管理模块包括了添加课程，配置课程相关信息，以及管理课程章节功能。

---



根据产品需求文档分析一下开发工作都包什么？

---



### 3.1.1 课程管理模块

* 要实现的功能有：
  * 新建课程            插入操作，添加新的课程
  * 条件查询            根据课程名称  状态
  * 课程信息列表展示：查询的内容
    * ID         课程名称          价格          排序            状态
  * 课程状态的切换      上架下架
  * 点击营销信息跳转到营销信息页面，点击配置课时跳转到配置课时页面



### 3.1.2 营销信息页面               其实就是对课程的详细信息修改的操作

* 营销信息，其实就是设置课程的详细信息
  * 回显现有对应课程的详细信息
  * 修改课程信息，包含了图片上传



### 3.1.3 配置课时              当选择了某一个课程的配置课时跳转到的页面

* 其实配置课时指的就是对课程内容的  配置       
* 课程内容就包括了：章节信息和课时信息         一个课程对应多个章节，一个章节对应多个课时
  * 添加对应课程的章节信息
  * 以树形结构的下拉列表方式    展示课程对应的章节与课时信息
  * 修改章节
  * 章节状态的设置



## 3.2 课程管理模块表设计

表关系介绍：

* 课程表与课程章节表一对多关系
* 章节表与课时表一对多关系       一个章节下面有多个课时
* 课时表与媒体表一对一关系       一个课时对应一个视频



## 3.3 环境搭建

### 3.3.1 创建项目

使用Maven快速构建web项目

1) 选择maven ,直接next

2) 填写项目相关信息,创建maven项目

3) 当前maven项目还不是 一个web项目,进行一下改造             在pom.xml中添加packaging标签打包为war包

### 3.3.2 项目目录        包结构

###    java目录下：base通用类，dao数据访问包，pojo实体包，service业务处理，utils工具包，web目录下：filter过滤器，listener监听器，servlet

### 3.3.3 导入pom.xml

### 3.3.4 导入工具类及配置文件

### 3.3.5 导入实体类

1）Lombok介绍

在项目中使用Lombok减少重复代码。生成getter，setter方法的

2）IDEA中安装 Lombok插件

3）添加依赖

在项目中添加Lombok依赖jar，在pom文件中添加

4）Lombok常用注解                 简化开发的

* @Getter/@Setter：作用类上，生成getter和setter方法
* @ToString：生成toString
* @AllArgsConstructor：全参构造
* @NotArgsConstructor：无参构造
* @Data：**该注解使用在类上，提供getter、setter、equals、hashCode、toString**方法



### 3.4  编写通用Servlet						

### 自己所写的Servlet都要去继承BaseServlet，去简化开发，让我们的Servlet里只保存业务相关的代码

由于每个模块下有很多功能，每一个功能就写一个Servlet，然后发送请求访问到对应的Servlet。这种方式不好，一个Servlet对应一个功能。

**所以我们使用一个Servlet对应一个模块的方式进行开发**

要求前台页面必须携带请求方法名，才能知道要访问什么方法

doGet方法作为调度器来使用，根据前台传过来的参数获取到methodName去调用对应的功能，使用反射去对代码进行优化，动态的调用方法

通过反射的机制优化：

第一步：先获取请求携带方法的参数值

第二步：获取指定类的字节码对象，就是本Servlet的Class实例

第三步：根据方法名去反射的获取到对应的方法对象，再通过Class对象获取指定的方法对象Method

第四步：调用Method对象的 invoke()方法，执行

用了反射优化后依然有问题：

​	每个Servlet都需要写一份相同的反射代码

所以将反射代码和doGet，doPost方法提取到BaseServlet中，让BaseServlet继承HttpServlet，让Servlet类中只留下业务相关的代码，继承BaseServlet

当有请求访问到Servlet时，发现没有doGet和doPost方法，就会去父类中找，从而执行BaseServlet中的doGet方法，也就是

**前台请求访问的是哪一个Servlet的路径时，this就代表哪一个Servlet，从而根据反射机制动态的调用对应的功能**

---

**如果我们项目是maven项目的话，每次编写好代码后，要进行clean和compile**

取保当前maven项目中编译过后的class文件都是最新的，避免错误

---

# 4. JSON

## 4.1 JSON概述

​		JSON是一种数据交换的格式。源于js的。

JSON的特点：

* JSON 是轻量级的数据交互格式
* JSON 采用完全独立于语言的文本格式，不管任何语言都可以用JSON来作数据交换的格式
* JSON易于人的阅读和编写，网络传输速度快

## 4.2  XML与JSON的区别

* XML：可扩展标记语言
* JSON：是一种轻量级的数据交换格式
* 相同点：
  * 都可以做数据交换格式
* 二者区别：
  * XML是重量级的，JSON是轻量级的。格式简单，易于压缩
  * XML和JSON都可以用在项目里，XML多用于做配置文件，JSON用于数据交互
  * 采用前后端分离开发的方式，更多是使用JSON方式去传输和响应数据
  * JSON是独立于编程语言的，任何编程语言都可以使用JSON

## 4.3  JSON语法

```json
{
	"id":120,
    "name":"赵子龙",
    "age":20
}
```

语法格式：

1. **外面用{}括起来**
2. 数据以"键:值"对形式出现，键和值之间使用:作为分隔
3. 每个键值对之间使用逗号作为分隔，最后一个键值对省略逗号
4. **键用双引号包裹，值如果是字符串就是引号，如果是其他类型，可以不加引号**



代码示列：

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <script typet="text/javascript" src="http://libs.baidu.com/jquery/1.9.1/jquery.min.js"></script>
</head>
<script>

    // 常用的JSON数据格式
    // 自定义JSON数据格式  (Java 中的对象)
    var person = {"name":"李狗蛋","sex":"男","age":20};
    console.log(person);

    // 数组格式
    var persons = {"person":[{"name":"李狗蛋","sex":"男","age":20},{"name":"翠花","sex":"女","age":23}]};
    console.log(persons);

    // 集合
    var list = [{"name":"张百万","sex":"男","age":29},{"name":"侯国玉","sex":"男","age":3}];
    console.log(list);

</script>
<body>

</body>
</html>
```



## 4.4 JSON数据的转换

目前，前后端的通讯用的都是JSON格式。所以在开发中，我们会经常涉及到JSON数据的转换

* JSON数据与Java对象的转换
  * **浏览器发送请求  以JSON格式的数据          服务器端把JSON数据转为对象，保存到数据库**
* 对象转JSON               服务器端响应一个JSON格式的数据
  * **后台从数据库中查询出数据，转为JSON数据格式再返回给浏览器             浏览器取出JSON数据，对页面渲染**



## 4.4.1  FastJson介绍

* FastJson 是一个Java库，可以将java对象转为JSON格式，也可以将JSON转为java对象
* FastJson特点：
  * 支持java bean序列化成JSON字符串，也可以将JSON反序列化成java bean
  * FastJson操作JSON速度快
  * 无其他包的依赖，使用方便



## 4.4.2  FastJson使用

​	在maven项目中若想要使用FastJson，需要在pom.xml中引入依赖

```xml
<!--fastjson工具包 -->
        <dependency>
            <groupId>com.alibaba</groupId>
            <artifactId>fastjson</artifactId>
            <version>1.2.3</version>
        </dependency>

        <dependency>
            <groupId>com.colobu</groupId>
            <artifactId>fastjson-jaxrs-json-provider</artifactId>
            <version>0.3.1</version>
        </dependency>
```



## 4.4.3  将Java对象转换为JSON格式

1）定义一个名为 Person 的JavaBean类

```java
// 代表getter和setter等方法
@Data
// 有全参构造方法
@AllArgsConstructor
// 有空参构造方法
@NoArgsConstructor
public class Person {

    private String userName;

    private int age;

    private String birthday;

}
```



2) 使用 **JSON.toJSONString() ** 将Java对象转为JSON对象：

```java
public class TestFastjson {

    // Java对象转换为JSON
    @Test
    public void javaBeanToJsON(){

        // 1.创建Person对象
        Person p = new Person("大雄",15, DateUtils.getDateFormart());

        // 2.使用JSON对象 将person对象转为 JSON数据
        String jsonString = JSON.toJSONString(p);

        System.out.println(jsonString); // {"age":15,"birthday":"2022-05-07 20:02:25","userName":"大雄"}
    }

    // Java 中的集合转JSON
    @Test
    public void listToJSON(){

        // 1.创建Person对象
        Person p1 = new Person("大雄",15, DateUtils.getDateFormart());
        Person p2 = new Person("大雄",15, DateUtils.getDateFormart());
        Person p3 = new Person("大雄",15, DateUtils.getDateFormart());

        // 2.创建ArrayList集合
        List<Person> list = new ArrayList<>();

        // 3.将对象封装到集合
        Collections.addAll(list,p1,p2,p3);

        // 4.使用JSON对象的 toJSONString的方法
        String jsonString = JSON.toJSONString(list);

        // [{"age":15,"birthday":"2022-05-07 20:06:15","userName":"大雄"},{"age":15,"birthday":"2022-05-07 20:06:16","userName":"大雄"},{"age":15,"birthday":"2022-05-07 20:06:16","userName":"大雄"}]
        System.out.println(jsonString);
    }
}
```



3) Fastjson中的 **@JSONField** 注解

* 通过 **@JSONField** 注解可以自定义字段的名称进行输出，并控制字段的排序，还可以进行序列化标记。
  * 指定name属性，字段的名称
  * 使用 ordinal属性，指定字段的顺序
  * 使用JSONField(serialize=false)排除不需要转换的字段

```java
@Data
public class Person {

   //可以通过 name 去指定输出的名称
   //可以使用 ordinal 属性，指定输出字段的顺序
    @JSONField(name="USERNAME",ordinal = 1)
    private String userName;


    @JSONField(name="AGE",ordinal = 2)
    private int age;

    //使用serialize属性，指定字段是否序列化
    @JSONField(serialize = false)
    private String birthday;

}
```



## 4.4.3 JSON 转为Java对象

* **JSON.parseObject()**
  * 可以使用 **JSON.parseObject()** 将JSON转为Java对象
* **JSON.parseArray()**
  * 可以使用 **JSON.parseArray()** 将 JSON 转为 集合对象

```json
// JSON转为对象
    @Test
    public void JSONToJavaBean(){

        String json = "{\"age\":15,\"birthday\":\"2022-05-07 20:02:25\",\"userName\":\"大雄\"}";

        // 使用JSON对象的 parseObject(json格式的字符串，json要转换的是什么样的对象类型 就是要转换的类的Class实例)     
        Person person = JSON.parseObject(json, Person.class);
        System.out.println(person);
    }

    // JSON 转为List集合
    @Test
    public void JSONToList(){

        String json = "[{\"age\":15,\"birthday\":\"2022-05-07 20:06:15\",\"userName\":\"大雄\"},{\"age\":15,\"birthday\":\"2022-05-07 20:06:16\",\"userName\":\"大雄\"},{\"age\":15,\"birthday\":\"2022-05-07 20:06:16\",\"userName\":\"大雄\"}]";

        // 使用parseArray(json,集合中保存的对象的Class实例) 方法，将JSON转为 集合
        List<Person> list = JSON.parseArray(json, Person.class);

        System.out.println(list);
    }
```



# 5. Ajax

## 5.1 Ajax概述

​	传统网页如果需要更新内容，就必须重载整个页面。每当用户向服务器发送请求，哪怕只是需要更新一点内容，服务器都会将整个页面进行刷新。缺点：

* 性能低
* 用户的操作会被页面刷新中断

## 1）什么是Ajax

实现异步刷新，一种创建交互式网页应用的网页开发技术

Ajax=异步JavaScript和XML

**Ajax是客户端与服务器进行交互时，可以不用刷新整个浏览器的情况下，与服务器进行异步通讯的技术**

## 2）Ajax的作用

​	Ajax 可以使网页实现**异步更新**。在重新加载整个网页的情况下，对网页的**某部分进行更新（局部更新）**，其他部分不受影响

**所有操作都是在不刷新窗口的情况下完成的**

## 3)Ajax的好处

* 减轻服务器负担，按需求获取数据，异步请求
* 无刷新更新页面，方便用户
* 只更新部分页面，有效利用带宽
* 主流浏览器都支持Ajax

## 4）异步与同步

* 浏览器访问服务器的方式
  * 同步访问：客户端必须等待服务器端的响应，**在等待过程中不能进行其他操作**，**啥也不能干，只能等服务端响应完才能操作**
  * 异步访问：**客户端不需要等待服务器端的响应，浏览器可以进行其他操作**

## 5.2 JS方式Ajax的实现（了解）

JS的ajax：ajax的最早的实现方式用js完成。使用XmlHttpRequest对象，进行ajax请求发送，和响应接收。使用ajax发请求，使用ajax接收响应，使用JS进行页面刷新。

* 缺点：
  * 若使用js的AJax，要写大量代码
  * js的AJax代码，浏览器兼容性差

前端JS代码，复制

**login.jsp**

```html
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>

    <script>

        // 原生JS方式发送 Ajax请求
        function run() {
            //1.创建 核心对象
            var xmlhttp;

            //2.判断浏览器类型
            if (window.XMLHttpRequest) {
                xmlhttp=new XMLHttpRequest();
            } else {
                xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
            }



            //3.建立连接
            /**
             * 三个参数:
             *      1.请求方式 get post
             *      2.请求资源的路径
             *      3.是否为异步 是 or 否
             */
            xmlhttp.open("GET","/login?username=tom",true);

            //4.发送请求
            xmlhttp.send();

            //5.获取响应结果
            /**
             * 什么时候获取响应数据?
             *      在服务器响应成功后获取
             */
            //监听readyState状态改变
            xmlhttp.onreadystatechange=function() {
                //readyState==4 响应已经就绪, 200 访问成功
                if (xmlhttp.readyState==4 && xmlhttp.status==200) {
                    //获取响应数据
                    var text = xmlhttp.responseText;
                    alert("响应结果：" + text);
                }
            }
        }
        
    </script>
</head>
<body>
    <input type="button" value="原生JS发送异步请求" onclick="run()"/><br/>
    局部刷新 <input type="text"><br/>

</body>
</html>
```

后端Servlet

```java
@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //1.获取请求数据
        String username = req.getParameter("username");

        //打印 username
        System.out.println(username);
        resp.getWriter().write(username);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
```





## 5.3 JQuery框架的ajax 

### 5.3.1 JQuery框架的ajax简介

jQuery是一个js框架，对js原生的ajax进行了封装，封装后的ajax操作方法更加简洁，功能强大

**与ajax操作相关的JQuery方法有三种**，使用JQuery去发送异步请求是最常用的，三种方式：

* POST
* GET
* AJAX

### 5.3.2 GET请求方式

通过远程 HTTP GET 请求载入信息。简单的GET请求功能，复杂的使用ajax

**Get请求方式语法**

**$.get(url,data,callback,type)**

* $              jQuery对象
* 参数1：url          请求路径
* 参数2：data        请求时携带的数据
  * 格式：key=value         或者JSON格式的：{username:'baby',pwd:666}
* 参数3：callback          服务器端响应成功后的回调函数
* 参数4：type                 响应的数据类型  text  html   xml  json

示列

```html
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>

    <script type="text/javascript" src="http://code.jquery.com/jquery-latest.js"></script>
    <script>


        // JQuery GET请求方式
        function run2() {

            // 1.准备url
            var url = "/login";

            // 2.数据
            var data = {username:"xiaoming"};

            // 3.使用JQuery发送GET请求
            $.get(url,data,function (param) {

                alert("GET异步请求 响应成功:" + param)
            },"text");
        }

        
    </script>
</head>
<body>
    
    局部刷新 <input type="text"><br/>

    <input type="button" value="JQuery Get方式发送异步请求" onclick="run2()"/><br/>

</body>
</html>
```

### 5.3.3 POST请求方式

通过远程HTTP POST 请求载入信息。简单的POST请求功能，复杂用ajax

Post请求方式语法：

**$.post(url,data,callback,type)**

里面的四个参数和get方式一样，不一样的是请求方式的不同。

**Post如果传参是在请求体中，不会在url后**

示列

```html
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>

    <script type="text/javascript" src="http://code.jquery.com/jquery-latest.js"></script>
    <script>       

        // JQuery POST请求方式
        function run3() {

            // 1.准备url
            var url = "/login";

            // 2.数据
            var data = {username:"tangxin"};

            // 3.使用JQuery发送GET请求
            $.post(url,data,function (param) {

                alert("POST异步请求 响应成功:" + param)
            },"text");
        }

        
    </script>
</head>
<body>
    
    局部刷新 <input type="text"><br/>   

    <input type="button" value="JQuery Post方式发送异步请求" onclick="run3()"/><br/>
   
</body>
</html>
```



### 5.3.4 AJax请求方式

$.ajax()方法可以更加详细的设置底层的参数。通常用于其他方法不能完成的请求

**更复杂的操作，设置更加详细的参数用$.ajax()**

ajax请求方式的语法：

* 方式1：JQuery.ajax({[settings]})
* **方式2：$.ajax({})**                      一般使用这个

**settings是一个js字面量形式的对象，格式是键值对{name:value,name:value }，常用的name属性名如下：**



示列

```html
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>

    <script type="text/javascript" src="http://code.jquery.com/jquery-latest.js"></script>
    <script>
  
        // JQuery Ajax请求方式
        function run4() {

            $.ajax({
                url:"/login",
                async:true, // 是否异步,true是异步
                data:{username:"ikun"}, // 请求参数
                type:"POST", // 请求的方式
                dataType:"text", // 返回数据的数据类型   text 普通文本

                // 响应成功的函数
                success:function (param) {
                    alert("响应成功！" + param);
                },
                // 响应失败的函数
                error:function () {
                    alert("响应失败了！")
                }
            });

        }
        
    </script>
</head>
<body>
  
    局部刷新 <input type="text"><br/>

    <input type="button" value="JQuery Ajax方式发送异步请求" onclick="run4()"/><br/>
</body>
</html>
```



## 5.4 案列:使用AJax去发送异步请求，检测用户名是否已经被注册

需求：当用户输入完用户名后，一旦鼠标移出了，绑定鼠标移出事件，对当前用户输入的用户名进行判断，将用户输入的用户名发送到后台，判断当前用户有没有被注册，如果当前用户名被注册了，页面显示用户名已经被占用！



步骤：

1. 准备Servlet，对用户名进行校验，并返回结果（是否可用）
2. 为页面输入框，绑定鼠标移出事件
3. 进行异步请求，获取响应结果
4. 根据结果，动态添加HTML代码

### 后台的CheckNameServlet

```java
@WebServlet("/checkName")
public class CheckNameServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // 设置编码，防止乱码
        req.setCharacterEncoding("utf-8");
        resp.setContentType("text/html;charset=utf-8");

        /**
         * 获取前台输入的用户名
         * 判断用户名是否存在
         * 不管存不存在，都把信息封装到map里
         * 最终把map转换成JSON格式并响应回去
         */

        // 1.获取用户输入的用户名
        String userName = req.getParameter("username");


        // 2.Map封装数据
        HashMap<String,Object> map = new HashMap<>();


        // 3.判断用户是否存在
        if ("蔡徐坤".equals(userName)){
            // 蔡徐坤用户名已经存在
            map.put("flag",true);
            map.put("msg","用户名已经被占用，请更换！");

            // 响应JSON格式的数据
            String param = JSON.toJSONString(map);
            resp.getWriter().print(param);

        } else {
            // 用户名不存在
            map.put("flag",false);
            map.put("msg","用户名可以使用！");

            // 响应JSON格式的数据
            String param = JSON.toJSONString(map);
            resp.getWriter().print(param);
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
```



### 前台JSP页面

```jsp
<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<!DOCTYPE html>
<head>
    <meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
    <title>new jsp</title>
    <script type="text/javascript" src="http://code.jquery.com/jquery-latest.js"></script>
</head>
<script>

    /*给用户名输入框元素绑定失去焦点的表单事件
    * 将此函数绑定到输入用户名的input框元素的blur事件*/
    $(function () {
        // 通过id选择器给input框绑定失去焦点事件
        $("#username").blur(function () {

            // 用户输入完用户名，失去焦点后。
            // 获取用户名
            let name = $(this).val();

            // 判断用户名不为空和空串
            if (name != null && name != ""){
                // 向后台发送请求，验证用户名是否可用
                // 使用JQuery ajax请求方式发送异步请求
                $.ajax({
                   url:"/checkName",
                   async:true,  // 是否异步,true是异步
                   type:"GET",  // 请求的方式
                   data:{username:name}, // // 请求参数
                   dataType:"json", // 响应回数据的数据类型   json
                   success:function (param) {
                        // 如果响应成功，需要给span标签加内容，设置颜色
                       // true表示用户名已经存在
                       if (param.flag){
                       // 设置span的内容体
                           // 数据在后台map转换的JSON中，也就是响应回的param
                       $("#spanMsg").html("<font color='red'>" + param.msg + "</font>");
                       } else if (!param.flag){
                           // false，取反 表示用户名不存在
                           // 设置span的内容体
                       $("#spanMsg").html("<font color='yellow'>" + param.msg + "</font>");
                       }
                   },
                   error:function () {
                        alert("请求处理失败！");
                   }
                });
            }
        })
    })


</script>

<body>
    <form action="#" method="post">
        用户名：<input type="text" id="username" name="username" placeholder="请输入用户名"/>
        <%-- 进行提示的文本 --%>
        <span id="spanMsg" style="color: red"></span><br/>
        密码：<input type="text" id="password" name="password" placeholder="请输入密码"/>
    </form>
</body>
</html>
```

