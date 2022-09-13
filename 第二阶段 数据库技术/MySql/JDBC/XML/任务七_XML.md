# 任务七_XML

## 1.1XML基本介绍

XML即可扩展标记语言

W3C(万维网联盟)推出的1.0版本

特点

* 可扩展的，标签都是自定义的
* 语法十分严格



## 1.2XML能做什么

1. 可以存储数据
2. 作为配置文件使用
3. 使用XML在网络中传递数据



## 2.XML的语法

<! -- XML的注释 -->

1.**XML中必须进行文档声明**

​	version 版本信息

​	encoding 编码

2**.XML中的文档声明必须写在第一行**



3.XML 中的元素标签	命名规则

​	1.**标签定义	不能使用空格	或者	冒号**

​	2.XML标签的名称区分大小写

​	

4.**XML中 有且只有一个根元素**



5.元素体 标签和标签中间的内容 可以是文本	或者	还是一个标签	

<!-- 空元素 没有结束标签 -->

<close/>



6.属性是元素的一部分 只能出现在元素的开始标签中	以键值对形式保存

​	属性值必须使用单引号或者双引号包裹

​	一个元素标签可以定义多个属性



## 3.XML约束

* 可以创建一个约束文件去约束XML中的书写规范，称之为XML约束

* 常见的XML约束

  * DTD
  * Schema

* 作为程序员掌握

  * 会阅读
  * 会引入约束
  * 不用自己写

  XML由客户程序员编写

  XML由软件(框架)解析	

  XML中引入约束，规定了XML中标签的书写规则



### 3.1DTD约束

文档类型定义，用来约束XML文档。规定XML文档中元素的名称，标签，属性等

<!ELEMENT students (student+) >
    <!ELEMENT student (name,age,sex)>
    <!ELEMENT name (#PCDATA)>
    <!ELEMENT age (#PCDATA)>
    <!ELEMENT sex (#PCDATA)>
    <!ATTLIST student number ID #REQUIRED>



<!-- 

​	ELEMENT 定义元素	

​	students (student+)：students	代表根元素	

​	student+：根标签中 至少有一个student子元素

​	student (name,age,sex)：student标签中可以 包含的子元素	按顺序出现

	# PCDATA：普通的文本内容

​	ATTLIST: 用来定义属性

​	student number ID：student标签中 有一个ID属性 叫做number

#REQUIRED：number 的属性必须填写

​	ID 唯一的值	不能重复	值只能是字母或者是下划线开头

-->



## 3.2 Schema约束	对文档控制更加细致

1. 新的XML文档约束，比DTD强大很多
2. Schema本身也是XML文档，但Schema文档后缀名是xsd,而不是XML，内容是XML格式的
3. Schema功能强大，内置多种简单和复杂的数据类型，可以定义某一个标签的类型
4. Schema支持命名空间，可以引入多个不同的约束



## 4.XML解析

### 4.1 解析方式

* DOM方式	将整个XML读取到内存 生产一个document对象
  * 优点：元素与元素之间有结构关系	可以进行CRUD
  * 缺点：占用的内存太多 容易内存溢出
* SAX方式    一边扫描一边解析，速度更快
  * 优点：占用内存少 速度快
  * 缺点：只能解析操作(读)



### 4.2常见的解析器

​	DOM4j	支持DOM和SAX，主要用来解析XML，XPath

导入jar包

使用核心类	SaxReader将XML文档加载到内存，并且获得Document对象，通过Document对象获取文档元素

* SaxReader对象
  * read(..)加载执行XML文档    返回Document对象
* 通过Document对象获取根元素
  * getRootElement()获得根元素
* Element对象表示元素 
  * elements(…) 获得指定名称的所有子元素。可以不指定名称
  * element(…) 获得指定名称的第一个子元素。可以不指定名称
  * getName() 获得当前元素的元素名
  * attributeValue(…) 获得指定属性名的属性值
  * elementText(…) 获得指定名称子元素的文本值
  * getText() 获得当前元素的文本内容



### 4.3XPath方式读取xml

XPath是一门在XML文档中查询信息的语言，可以是使用XPath查询xml中的内容

导入jar包



XPath语法			使用dom4j支持XPath的操作的几种主要形式

* /AAA/DDD/BBB              表示一层一层的，AAA下面DDD下面的BBB
* //BBB                                表示和这个名称相同，表示只要名称是BBB，都得到
* //*                                      所有元素
* BBB[1],BBB[last()]           第一种表示第一个BBB元素，第二种表示最后一个BBB元素
* //BBB[@id]                       表示只要BBB元素上面有id属性，都得到
* //BBB[@id='b1']               表示元素名称是BBB，在BBB上面有id属性，并且id的属性值是b1 



常用方法	

* selectSingleNode(query): 查找和 XPath 查询匹配的一个节点。   查询单个节点
  * 参数是Xpath 查询串。
* selectNodes(query): 得到的是xml根节点下的所有满足 xpath 的节点；        查询所有满足语法的节点
  * 参数是Xpath 查询串。
* Node: 节点对象

 



<!DOCTYPE students SYSTEM "student.dtd">



