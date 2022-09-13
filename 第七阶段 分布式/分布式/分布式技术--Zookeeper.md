# 分布式技术--Zookeeper

# 1.Zookeeper概述

## 1.1 概述

服务平台

Zookeeper是一个开源的分布式（多台服务器干一件事）的，为分布式应用**提供协调服务**的Apache项目





## 1.2 工作机制

* 是一个基于<b style="red">观察者模式</b>（一个人干活，有人盯着他）设计的分布式服务管理框架
* 它负责存储和管理大家都关心的数据
  * 然后接受观察者的**注册**，一旦这些数据发生变化
  * Zookeeper就将负责**通知**已经注册的那些观察者做出相应的反应
  * 从而实现集群中类似Master/Slave管理模式
* Zookeeper=**文件系统+通知机制**



1. 商家营业并入住
2. 获取到当前营业的饭店列表
3. 服务器节点下线
4. 服务器节点上下线事件通知
5. 重新再去获取服务器列表，并注册监听

```
服务型应用程序注册到zookeeper，zookeeper负责通知通知给客户端应用程序。
zookeeper对注册的服务器节点进行监听，服务节点出现了上下线的变化就会得到通知给到客户端。zookeeper服务平台的工作机制
```







## 1.3 特点

* 分布式和集群的区别：
  * 无论分布式和集群，都是很多人在做事情，具体区别：
  * 分布式：**服务器负责的工作不一样**，但是在一起合作的最终目的一样
  * 集群：**服务器的工作一样**



1. 是一个leader和多个follower来组成的集群
2. 集群中只要有半数以上的节点存活，Zookeeper就能正常工作（5台服务器挂2台，没问题；4台服务器挂2台，就停止）
3. 全局数据一致性，每台服务器都保存一份相同的数据副本，无论client连接哪台server，数据都是一致的
4. 数据更新原子性，一次数据要么成功，要么失败
5. 实时性，在一定时间范围内，client能读取到最新数据
6. 更新的请求按照顺序执行，会按照发送过来的顺序，逐一执行（发来123，执行123）







## 1.4 数据结构

* Zookeeper数据模型的结构与Linux文件系统很类似，树状结构，每个节点称作一个ZNode（ZookeeperNode）
* 每一个ZNode默认能够存储1MB的数据（元数据），每个ZNode的路径都是唯一的
  * 元数据（Metadata),描述数据的数据，主要是描述数据属性的信息，用来支持如指示存储位置，历史数据，资源查询，文件记录等功能





## 1.5 应用场景

* 提供的服务包括：统一命名服务、统一配置管理、统一集群管理、服务器节点动态上下线，软负载均衡



### 1.5.1 统一命名服务

* 在分布式环境下，通常要对应用或服务进行统一的命名，便于识别
* 如：服务器的IP地址不容易记，但域名很容易记



### 1.5.2 统一的配置管理

* 将配置管理交给Zookeeper
  * 将配置信息写入到Zookeeper的某个节点上
  * 每个客户端都监听这个节点
  * 一旦节点中的数据文件被修改，Zookeeper这个话匣子就会通知每台客户端服务器



### 1.5.3 服务器节点动态上下线

* 客户端能实时获取服务器上下线的变化



### 1.5.4 软负载均衡

* Zookeeper会记录每台服务器的访问数，让访问数最少的服务器去处理最新的客户请求（雨露均沾）





## 1.6 下载







# 2、Zookeeper本地模式安装

## 2.1 本地模式安装

### 2.1.1 安装前准备

1. 安装JDK
2. 拷贝Apache-Zookeeper-3.6.0-bin.tar.gz 到opt目录
3. 解压安装包
4. 重命名





### 2.1.2 配置修改

1. 在/opt/zookeeper/这个目录上创建zkData和zkLog目录

```
[root@localhost zookeeper]# mkdir zkData
[root@localhost zookeeper]# mkdir zkLog
```

2. 进入/opt/zookeeper/conf这个路径，复制一份 zoo_sample.cfg 文件并命名为 zoo.cfg

```
[root@localhost conf]# cp zoo_sample.cfg zoo.cfg
```

3. 编辑zoo.cfg文件，修改dataDir路径:

```
dataDir=/opt/zookeeper/zkData
dataLogDir=/opt/zookeeper/zkLog
```





### 2.1.3 操作Zookeeper

1. 启动Zookeeper

```
[root@localhost bin]# ./zkServer.sh start
```

2. 查看进程是否启动

```
[root@localhost bin]# jps
```

3. 查看状态：

```
[root@localhost bin]# ./zkServer.sh status
```

4. 启动客户端

```
[root@localhost bin]# ./zkCli.sh
```

5. 退出客户端

```
[zk: localhost:2181(CONNECTED) 0] quit
```

6. 停止Zookeeper

```
[root@localhost bin]# ./zkServer.sh stop
```





## 2.2 配置参数

Zookeeper中的配置文件zoo.cfg中参数含义解读如下：

* **tickTime =2000**：通信心跳数，Zookeeper服务器与客户端心跳时间，单位毫秒
  * Zookeeper使用的基本时间，服务器之间或客户端与服务器之间维持心跳的时间间隔，也就
    是每个tickTime时间就会发送一个心跳，时间单位为毫秒。
* initLimit =10：LF初始通信时限
  * 集群中的Follower跟随者服务器与Leader领导者服务器之间，启动时能容忍的最多心跳数
  * 10*2000（10个心跳时间）如果领导和跟随者没有发出心跳通信，就视为失效的连接，领导
    和跟随者彻底断开
* syncLimit =5：LF同步通信时限
  * 集群启动后，Leader与Follower之间的最大响应时间单位，假如响应超过syncLimit * 
    tickTime->10秒，Leader就认为Follwer已经死掉，会将Follwer从服务器列表中删除
* dataDir：数据文件目录+数据持久化路径
  * 主要用于保存Zookeeper中的数据。
* dataLogDir：日志文件目录
* clientPort =2181：客户端连接端口
  * 监听客户端连接的端口







# 3、Zookeeper内部原理

## 3.1 选举机制（面试重点）

* <b style='color:red'>半数机制：集群中半数以上机器存活，集群可用。所以Zookeeper适合安装奇数台服务器</b>
* 虽然在配置文件中并没有指定Master和Slave。但是，Zookeeper工作时，是有一个节点为
  Leader，其他则为Follower，Leader是通过内部的选举机制临时产生的,**有一个节点得到半数以上的票就是leader**



1. Server1先投票，**投给自己**，自己为1票，没有超过半数，根本无法成为leader，顺水推舟将票数
   投给了id比自己大的Server2

2. Server2也把自己的票数投给了自己，再加上Server1给的票数，总票数为2票，没有超过半数，也
无法成为leader，也学习Server1，顺水推舟，将自己所有的票数给了id比自己大的Server3
3. Server3得到了Server1和Server2的两票，再加上自己投给自己的一票。**3票超过半数，顺利成为**
**leader**
4. Server4和Server5都投给自己，但是无法改变Server3的票数，只好听天由命，承认Server3是
leader





## 3.2 节点类型

* 持久型：
  * **持久化目录节点**：客户端与Zookeeper断开链接后，该节点依旧存在
  * **持久化顺序**编号目录节点：客户端与zookeeper断开连接后，该节
    点依旧存在，创建znode时设置顺序标识，znode名称后会附加一个值，顺序号是一个单调
    递增的计数器，由父节点维护，例如：Znode001，Znode002...
* 短暂型：
  * **临时目录节点**：客户端和服务器端断开连接后，创建的节点自动删除
  * **临时顺序编号**目录节点：客户端与zookeeper断开连接后，该节点
    被删除，创建znode时设置顺序标识，znode名称后会附加一个值，顺序号是一个单调递增
    的计数器，由父节点维护，例如：Znode001，Znode002...

注意：序号相当于i++，和数据库中的自增长类似





## 3.3 监听器原理（面试重点）

1. 在main方法中创建Zookeeper客户端的同时就会创建两个线程，一个负责网络连接通信，一个负
责监听
2. 监听事件就会通过网络通信发送给zookeeper
3. zookeeper获得注册的监听事件后，立刻将监听事件添加到监听列表里
4. zookeeper监听到 数据变化 或 路径变化，就会将这个消息发送给监听线程

* 常见的监听
  1. 监听节点数据的变化：get path [watch]
  2. 监听子节点增减的变化：ls path [watch]

5. 监听线程就会在内部调用process方法（需要我们实现process方法内容） 





## 3.4 写数据流程

1. Client 想向 ZooKeeper 的 Server1 上写数据，必须的先发送一个写的请求
2. 如果Server1不是Leader，那么Server1 会把接收到的请求进一步转发给Leader。
3. 这个Leader 会将写请求广播给各个Server，各个Server写成功后就会通知Leader。
4. 当Leader收到半数以上的 Server 数据写成功了，那么就说明数据写成功了。
5. 随后，Leader会告诉Server1数据写成功了。
6. Server1会反馈通知 Client 数据写成功了，整个流程结束









# 4、Zookeeper实战（开发重点）

## 4.1 分布式安装部署

集群思路：搞定一台服务器，再克隆出两台，形成集群！

### 4.1.1 安装Zookeeper

### 4.1.2 配置服务器编号

* 在/opt/zookeeper/zkData创建myid文件

```shell
[root@localhost zkData]# vim myid
```

* 在文件中添加与server对应的编号：1
* 其余两台服务器分别对应2和3

### 4.1.3 配置zoo.cfg 文件

打开zoo.cfg文件，增加如下配置

```shell
######################cluster##########################
server.1=192.168.114.128:2888:3888
server.2=192.168.114.129:2888:3888
server.3=192.168.114.130:2888:3888
```

配置参数解读 server.A=B:C:D
A：一个数字，表示第几号服务器
集群模式下配置的/opt/zookeeper/zkData/myid文件里面的数据就是A的值
B：服务器的ip地址
C：与集群中Leader服务器交换信息的端口
D：选举时专用端口，万一集群中的Leader服务器挂了，需要一个端口来重新进行选举，选
出一个新的Leader，而这个端口就是用来执行选举时服务器相互通信的端口。



### 4.1.4 配置其余两台服务器

1. 在虚拟机数据目录vms下，创建zk02
2. 将本台服务器数据目录下的.vmx文件和所有的.vmdk文件分别拷贝zk02下
3. 虚拟机->文件->打开 （选择zk02下的.vmx文件）
4. 开启此虚拟机，弹出对话框，选择“我已复制该虚拟机”
5. 进入系统后，修改linux中的ip，修改/opt/zookeeper/zkData/myid中的数值为2
第三台服务器zk03，重复上面的步骤



### 4.1.5 集群操作

1. 每台服务器的防火墙必须关闭

```
[root@localhost bin]# systemctl stop firewalld.service
```

2. 启动第1台

```
[root@localhost bin]# ./zkServer.sh start
```

3. 查看状态

```
[root@localhost bin]# ./zkServer.sh status
```

```
ZooKeeper JMX enabled by default
Using config: /opt/zookeeper/bin/../conf/zoo.cfg
Client port found: 2181. Client address: localhost.
Error contacting service. It is probably not running.
```

注意：因为没有超过半数以上的服务器，所以集群失败 （防火墙没有关闭也会导致失败）

4. 当启动第2台服务器时

* 查看第1台的状态：Mode: follower
* 查看第2台的状态：Mode: leader



## 4.2 客户端命令操作

```
启动客户端
[root@localhost bin]# ./zkCli.sh

显示所有操作命令
help

查看当前znode中所包含的内容
ls /


查看当前节点详细数据
zookeeper老版本使用 ls2 / ，现在已经被新命令替代
ls -s /


分别创建2个普通节点
在根目录下，创建中国和美国两个节点
create /china
create /usa


在根目录下，创建俄罗斯节点，并保存“普京”数据到节点上
create /ru "pujing"


多级创建节点
在日本下，创建东京 “热”
japan必须提前创建好，否则报错 “节点不存在”
create /japan/Tokyo "hot"




获得节点的值
get /japan/Tokyo


创建短暂节点：创建成功之后，quit退出客户端，重新连接，短暂的节点消失
create -e /uk
ls /
quit
ls /



创建带序号的节点
在俄罗斯ru下，创建3个city
create -s /ru/city   # 执行三次
ls /ru
[city0000000000, city0000000001, city0000000002]
如果原来没有序号节点，序号从0开始递增。
如果原节点下已有2个节点，则再排序时从2开始，以此类推



修改节点数据值
set /japan/Tokyo "too hot"



监听 节点的值变化 或 子节点变化（路径变化）
在server3主机上注册监听/usa节点的数据变化
addWatch /usa

在Server1主机上修改/usa的数据
set /usa "telangpu"

Server3会立刻响应
WatchedEvent state:SyncConnected type:NodeDataChanged path:/usa

如果在Server1的/usa下面创建子节点NewYork
create /usa/NewYork

Server3会立刻响应
WatchedEvent state:SyncConnected type:NodeCreatedpath:/usa/NewYork

删除节点
delete /usa/NewYork

递归删除节点 （非空节点，节点下有子节点）
deleteall /ru
不仅删除/ru，而且/ru下的所有子节点也随之删除

```





## 4.3 API应用

### 4.3.1 IDEA环境搭建

1. 创建一个Maven工程
2. 添加pom文件

```xml
<dependencies>
        <dependency>
            <groupId>org.apache.logging.log4j</groupId>
            <artifactId>log4j-core</artifactId>
            <version>2.8.2</version>
        </dependency>
        <dependency>
            <groupId>org.apache.zookeeper</groupId>
            <artifactId>zookeeper</artifactId>
            <version>3.6.0</version>
        </dependency>
        <dependency>
            <groupId>junit</groupId>
            <artifactId>junit</artifactId>
            <version>4.12</version>
        </dependency>
    </dependencies>
```

3. 在resource下创建log4j.properties

```properties
log4j.rootLogger=INFO, stdout
log4j.appender.stdout=org.apache.log4j.ConsoleAppender
log4j.appender.stdout.layout=org.apache.log4j.PatternLayout
log4j.appender.stdout.layout.ConversionPattern=%d %p [%c] - %m%n

log4j.appender.logfile=org.apache.log4j.FileAppender
log4j.appender.logfile.File=target/zk.log
log4j.appender.logfile.layout=org.apache.log4j.PatternLayout
log4j.appender.logfile.layout.ConversionPattern=%d %p [%c] - %m%n
```

### 4.3.2 创建Zookeeper客户端

```java
public class TestZK {

    // Zookeeper  集群的ip和端口
    private String connectString = "192.168.114.128:2181,192.168.114.129:2181,192.168.114.130:2181";

    /* session超时的时间 ：时间不宜设置太小，因为Zookeeper和加载集群环境会因为性能等原因而延迟略高
        如果时间太少，还没有创建客户端，就开始操作节点，会报错的
     *
     */
    private int sessionTimeout = 60*1000;

    // 声明客户端
    // Zookeeper的客户端对象
    private ZooKeeper zkClient;

    @Before
    // 创建客户端的
    public void init() throws Exception {

        zkClient = new ZooKeeper(connectString, sessionTimeout, new Watcher() {
            // 负责监听的线程处理代码
            @Override
            public void process(WatchedEvent watchedEvent) {
                System.out.println("得到监听反馈，进行业务处理的代码！");
                // NodeChildrenChanged
                // WatchedEvent state:SyncConnected type:NodeCreatedpath:/usa/NewYork
                System.out.println(watchedEvent.getType());
            }
        });
    }
}
```



### 4.3.3 创建节点

一个ACL对象就是一个Id和permission对
表示哪个/哪些范围的Id（Who）在通过了怎样的鉴权（How）之后，就允许进行那些操作
（What）：Who How What；
permission（What）就是一个int表示的位码，每一位代表一个对应操作的允许状态。
类似linux的文件权限，不同的是共有5种操作：CREATE、READ、WRITE、DELETE、
ADMIN(对应更改ACL的权限)
OPEN_ACL_UNSAFE：创建开放节点，允许任意操作 （用的最少，其余的权限用的很
少）
READ_ACL_UNSAFE：创建只读节点
CREATOR_ALL_ACL：创建者才有全部权限

```java
// 创建节点
    @Test
    public void createNode() throws Exception{

        // 使用Zookeeper的客户端创建节点
        // 在根目录下 创建lagou目录，是持久节点
        String str = zkClient.create("/lagou","laosun".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        // 参数1：要创建的节点的路径
    // 参数2：节点数据 
    // 参数3：节点权限 
    // 参数4：节点的类型
        System.out.println("已创建节点:" + str);
    }
```

### 4.3.4 查询节点的值

```java
// 获取节点上的值
    @Test
    public void getNodeData() throws Exception{
        // 返回的字节数组
        byte[] bytes = zkClient.getData("/lagou", false, new Stat());

        System.out.println("/lagou节点的数据：" + new String(bytes));
    }
```

### 4.3.5 修改节点的值

```java
// 修改节点上的数据
    @Test
    public void updateDate() throws Exception {
        Stat stat = zkClient.setData("/lagou", "laosunA".getBytes(), 0);
        System.out.println(stat);
    }
```

### 4.3.6 删除节点

```java
// 删除节点上的数据
    @Test
    public void deleteDate() throws Exception {
        zkClient.delete("/lagou",1);	// 先查看节点详情，获得dataVersion = 1
        System.out.println("删除成功！");
    }
```

### 4.3.7 获取子节点

```java
// 获取子节点
    @Test
    public void getChildren() throws Exception{
        List<String> children = zkClient.getChildren("/china", false);	// false:不监听

        for (String child : children) {
            System.out.println(child);
        }
    }
```

### 4.3.8 监听子节点的变化

```java
// 监听根节点下面的变化
    @Test
    public void watchNode() throws Exception{
        List<String> children = zkClient.getChildren("/", true);	// true：注册监听
        for (String child : children) {
            System.out.println(child);
        }
            // 让线程不停止，等待监听的响应
            System.in.read();

            // 线程睡眠
            //Thread.sleep(2000000000);
    }
```

程序在运行的过程中，我们在linux下创建一个节点
IDEA的控制台就会做出响应：NodeChildrenChanged--/

### 4.3.9 判断Znode是否存在

```java
// 判断节点是否存在
    @Test
    public void exists() throws Exception{
        Stat exists = zkClient.exists("/lagou", false);
        if (exists == null) {
            System.out.println("不存在");
        } else {
            System.out.println("存在");
        }
    }
```





## 4.4 案例-，模拟美团商家上下线

### 4.4.1 需求

* 模拟美团服务平台，商家营业通知，商家打样通知
* 提前在根节点下，创建好/ meituan 节点

### 4.4.2 商家服务类

```java
public class ShopServer {

    private String connectString = "192.168.114.128:2181,192.168.114.129:2181,192.168.114.130:2181";
    private int sessionTimeout = 60*1000;
    private ZooKeeper zkClient;

    // 创建客户端，连接到Zookeeper
    public void connect() throws Exception{
        zkClient = new ZooKeeper(connectString, sessionTimeout, new Watcher() {
            @Override
            public void process(WatchedEvent watchedEvent) {

            }
        });
    }

    // 注册到Zookeeper
    public void register(String shopName) throws Exception{
        // 一定要创建EPHEMERAL_SEQUENTIAL 临时有序的节点(营业)
        // 一来可以自动编号，二来断开时，节点自动删除(打烊)
        String s = zkClient.create("/meituan/shop", shopName.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
        System.out.println("【"+ shopName + "】开始营业了"+s);
    }

    public static void main(String[] args) throws Exception {
        // 1.开饭店
        ShopServer shopServer = new ShopServer();

        // 2.连接Zookeeper集群（和美团取得联系）
        shopServer.connect();

        // 3.将服务节点注册到Zookeeper（入住美团）
        shopServer.register(args[0]);

        // 4.业务逻辑处理（做生意）
        shopServer.business(args[0]);

    }

    // 做买卖
    private void business(String shopName) throws IOException {
        System.out.println("【" + shopName + "】正在火爆营业中");
        System.in.read();
    }
}
```

### 4.4.3 客户类

```java
/**
 * @author lengy
 * @date 2022/7/16 19:54
 * @description 客户消费者
 */
public class Customers {


    private String connectString = "192.168.114.128:2181,192.168.114.129:2181,192.168.114.130:2181";
    private int sessionTimeout = 60*1000;
    private ZooKeeper zkClient;

    // 创建客户端，连接到Zookeeper
    public void connect() throws Exception{
        zkClient = new ZooKeeper(connectString, sessionTimeout, new Watcher() {
            @Override
            public void process(WatchedEvent watchedEvent) {

                // 监听到了数据变化，或者子节点的变化
                // 当有数据变化时再次获取商家列表
                try {
                    getShopList();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }


    // 获取子节点列表（获取商家列表）
    private void getShopList() throws KeeperException, InterruptedException {
        // 1.获取服务器的子节点信息，并且对父节点进行监听
        List<String> shops = zkClient.getChildren("/meituan", true);
        // 2.声明存储服务器信息的集合
        ArrayList<String> shopList = new ArrayList<>();

        for (String s : shops) {
            // 获取meituan下每个子节点的数据
            byte[] bytes = zkClient.getData("/meituan/" + s, false, new Stat());
            // 获取到节点中的数据并存到集合里
            shopList.add(new String(bytes));
        }

        System.out.println("目前正在营业的商家：" + shopList);
    }
    public static void main(String[] args) throws Exception {
        Customers client = new Customers();
        // 1.获得Zookeeper的连接（用户打开美团）
        client.connect();
        // 2.获取美团下的所有子节点列表（获取商家列表）
        client.getShopList();
        // 3.业务逻辑处理（对比商家，下单点餐）
        client.busines();
    }

    private void busines() throws IOException {
        System.out.println("用户正在浏览商家...");
        System.in.read();
    }


}
```

1. 运行客户类，就会得到商家列表
2. 首先在Linux中添加一个商家，然后观察客户端的控制台输出（商家列表会立刻更新出最新商家），多添加几个，也会实时输出商家列表

```
create /meituan/KFC "KFC"
create /meituan/BKC "BurgerKing"
create /meituan/baozi "baozi"
```

3. 在Linux中删除商家，在客户端的控制台也会实时看到商家移除后的最新商家列表

```
delete /meituan/baozi
```



## 4.5 分布式锁，商品秒杀

锁：我们在多线程中接触过，作用就是让当前的资源不会被其他线程访问！

在zookeeper中使用传统的锁引发的 “羊群效应” ：1000个人创建节点，只有一个人能成功，999
人需要等待！

避免“羊群效应”，zookeeper采用分布式锁



1. 所有请求进来，在/lock下创建 <b style='color:red'>临时顺序节点</b> ，放心，zookeeper会帮你编号排序
2. 判断自己是不是/lock下**最小的节点**
1. 是，获得锁（创建节点）
2. 否，对前面小我一级的节点进行监听
3. 获得锁请求，处理完业务逻辑，释放锁（删除节点），后一个节点得到通知（比你年轻的死了，你
成为最嫩的了）
4. 重复步骤2