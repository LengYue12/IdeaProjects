# RabbitMQ详解

# 1、什么是 RabbitMQ

## 1.1 MQ（Message Queue）消息队列

* 消息队列中间件，是分布式系统中的重要组件
* 主要解决，异步处理，应用解耦，流量削峰等问题 
* 从而实现高性能，高可用，可伸缩和最终一致性的架构 
* 使用较多的消息队列产品： RabbitMQ， RocketMQ， ActiveMQ， ZeroMQ， Kafka等



### 1.1.1 异步处理

* 用户注册后，需要发送验证邮箱和手机验证码；
*  将注册信息写入数据库，发送验证邮件，发送手机，三个步骤全部完成后，返回给客户端



### 1.1.2 应用解耦

* 场景：订单系统需要通知库存系统 
* 如果库存系统异常，则订单调用库存失败，导致下单失败 
  * 原因：订单系统和库存系统耦合度太高

* 订单系统：用户下单后，订单系统完成持久化处理，将消息写入消息队列，返回用户，下单成功；
* 库存系统：订阅下单的消息，获取下单信息，库存系统根据下单信息，再进行库存操作； 
* 假如：下单的时候，库存系统不能正常运行，也不会影响下单，因为下单后，订单系统写入消息队 列就不再关心其他的后续操作了，实现了订单系统和库存系统的应用解耦； 
* 所以说，消息队列是典型的：**生产者消费者模型**
* 生产者不断的向消息队列中生产消息，消费者不断的从队列中获取消息 
* 因为消息的生产和消费都是异步的，而且只关心消息的发送和接收，没有业务逻辑的入侵，这样就 实现了生产者和消费者的解耦



### 1.1.3 流量削峰

* 抢购，秒杀等业务，针对高并发的场景 
* 因为流量过大，暴增会导致应用挂掉，为解决这个问题，在前端加入消息队列
* 用户的请求，服务器接收后，首先写入消息队列，如果超过队列的长度，就抛弃，甩一个秒杀结束 的页面！ 
* 说白了，秒杀成功的就是进入队列的用户；





## 1、2 背景知识

### 1.2.1 AMQP高级消息队列协议

* 即Advanced Message Queuing Protocol，一个提供统一消息服务的应用层标准高级消息队列协 议 
* 协议：数据在传输的过程中必须要遵守的规则 
* 基于此协议的客户端可以与消息中间件传递消息 
* 并不受产品、开发语言等条件的限制



### 1.2.2 JMS

* Java Message Server， Java消息服务应用程序接口， 一种规范，和JDBC担任的角色类似 
* 是一个Java平台中关于面向消息中间件的API，用于在两个应用程序之间，或分布式系统中发送消 息，进行异步通信



### 1.2.3 二者的联系

* JMS是定义了统一接口，**统一消息操作**； AMQP通过协议**统一数据交互格式**
* JMS必须是java语言； AMQP只是协议，与语言无关



### 1.2.4 Erlang语言



## 1.3 为什么选择RabbitMQ

* 我们开篇说消息队列产品那么多，为什么偏偏选择RabbitMQ呢？ 

* 先看命名：兔子行动非常迅速而且繁殖起来也非常疯狂，所以就把Rabbit用作这个分布式软件的 命名（就是这么简单）

* Erlang开发， AMQP的最佳搭档，安装部署简单，上手门槛低 

* 企业级消息队列，经过大量实践考验的高可靠，大量成功的应用案例，例如阿里、网易等一线大厂 都有使用 

* 有强大的WEB管理页面 

* 强大的社区支持，为技术进步提供动力 

* 支持消息持久化、支持消息确认机制、灵活的任务分发机制等，支持功能非常丰富 

* 集群扩展很容易，并且可以通过增加节点实现成倍的性能提升

* 总结：如果你希望使用一个可靠性高、功能强大、易于管理的消息队列系统那么就选择

  RabbitMQ，如果你想用一个性能高，但偶尔丢点数据不是很在乎可以使用kafka或者zeroMQ

  kafka和zeroMQ的性能爆表，绝对可以压RabbitMQ一头！



## 1.4 RabbitMQ各组件功能

* Broker：消息队列服务器实体
* Virtual Host：虚拟主机 
  * 标识一批交换机、消息队列和相关对象，形成的整体 
  * 虚拟主机是共享相同的身份认证和加密环境的独立服务器域 
  * 每个vhost本质上就是一个mini版的RabbitMQ服务器，拥有自己的队列、交换器、绑定和权 限机制
  * vhost是AMQP概念的基础， RabbitMQ默认的vhost是 /，必须在链接时指定
* Exchange：交换器（路由） 
  * 用来接收生产者发送的消息并将这些消息路由给服务器中的队列
* Queue：消息队列 
  * 用来保存消息直到发送给消费者。 
  * 它是消息的容器，也是消息的终点。 
  * 一个消息可投入一个或多个队列。 
  * 消息一直在队列里面，等待消费者连接到这个队列将其取走。
* Banding：绑定，用于消息队列和交换机之间的关联。

* Channel：通道（信道）
  * 多路复用连接中的一条独立的双向数据流通道。 
  * 信道是建立在真实的TCP连接内的 虚拟链接
  * AMQP命令都是通过信道发出去的，不管是发布消息、订阅队列还是接收消息，都是通过信 道完成的 
  * 因为对于操作系统来说，建立和销毁TCP连接都是非常昂贵的开销，所以引入了信道的概 念，用来复用TCP连接。
* Connection：网络连接，比如一个TCP连接。
* Publisher：消息的生产者，也是一个向交换器发布消息的客户端应用程序。
* Consumer：消息的消费者，表示一个从消息队列中取得消息的客户端应用程序。
* Message：消息 
  * 消息是不具名的，它是由消息头和消息体组成。 
  * 消息体是不透明的，而消息头则是由一系列的可选属性组成，这些属性包括routing-key(路由 键)、 priority(优先级)、 delivery-mode(消息可能需要持久性存储[消息的路由模式])等。





# 2、怎么用RabbitMQ

想要安装RabbitMQ，必须先安装erlang语言环境，类似安装tomcat，必须先安装JDK

要看RabbitMQ的版本和erlagn的版本匹配



## 2.1 RabbitMQ安装启动

安装erlang，socat，RabbitMQ



### 2.1.1 安装



### 2.1.2 启动后台管理插件



### 2.1.5 测试

关闭防火墙： systemctl stop fi rewal l d

\2. 浏览器输入： http://ip:1 5672

\3. 默认帐号密码： guest， guest用户默认不允许远程连接





管理界面介绍

overview：概览

connections：查看链接情况

channels：信道（通道）情况

Exchanges：交换机（路由）情况，默认4类7个

Queues：消息队列情况

Admin：管理员列表 

端口：

* **5672**： RabbitMQ提供给编程语言客户端链接的端口
* **15672**： RabbitMQ管理界面的端口
* **25672**： RabbitMQ集群的端口



## 2.2 RabbitMQ快速入门

### 2.2.1 依赖

```xml
<dependencies>
        <dependency>
            <groupId>com.rabbitmq</groupId>
            <artifactId>amqp-client</artifactId>
            <version>5.7.3</version>
        </dependency>
        <dependency>
            <groupId>org.slf4j</groupId>
            <artifactId>slf4j-log4j12</artifactId>
            <version>1.7.25</version>
            <scope>compile</scope>
        </dependency>
        <dependency>
            <groupId>org.apache.commons</groupId>
            <artifactId>commons-lang3</artifactId>
            <version>3.9</version>
        </dependency>
    </dependencies>
```

### 2.2.2 日志依赖log4j

```properties
log4j.appender.stdout=org.apache.log4j.ConsoleAppender
log4j.appender.stdout.Target=System.out
log4j.appender.stdout.layout=org.apache.log4j.PatternLayout
log4j.appender.stdout.layout.ConversionPattern=%d{yyyy-MM-dd HH:mm:ss} %m%n

log4j.appender.file=org.apache.log4j.FileAppender
log4j.appender.file.File=rebbitmq.log
log4j.appender.file.layout=org.apache.log4j.PatternLayout
log4j.appender.file.layout.ConversionPattern=%d{yyyy-MM-dd HH:mm:ss} %l %m%n

log4j.rootLogger=debug,stdout,file
```



### 2.2.3 创建连接

* 先创建好虚拟主机

```java
/**
 * @BelongsProject lagou-rabbitmq
 * @Author lengy
 * @CreateTime 2022/7/27 16:52
 * @Description 专门与RabbitMQ获得连接
 */
public class ConnectionUtil {

    public static Connection getConnection() throws Exception {

        // 1.创建 连接工厂
        ConnectionFactory connectionFactory = new ConnectionFactory();

        // 2.在工厂对象中设置MQ的连接信息（ip，port，vhost，username，password）
        connectionFactory.setHost("192.168.114.128");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("/lagou");
        connectionFactory.setUsername("lengyue");
        connectionFactory.setPassword("123456");

        // 3.通过工厂获得与MQ的连接
        Connection connection = connectionFactory.newConnection();
        return connection;

    }


    public static void main(String[] args) throws Exception {

        Connection connection = getConnection();

        System.out.println("connection = " + connection);

        connection.close();
    }
}
```



## 2.3 RabbitMQ模式

* RabbitMQ提供了6种消息模型，但是第6种其实是RPC，并不是MQ，因此我们只学习前5种

* 5种消息模型，分为两类：

  * 1和2属于点对点
  * 3、4、5属于发布订阅模式（一对多）

* 点对点模式： P2P（ point to point）模式包含三个角色： 消息队列（ queue），发送者（ sender），接收者（ receiver） 每个消息发送到一个特定的队列中，接收者从中获得消息 队列中保留这些消息，直到他们被消费或超时 特点：

  1 . 每个消息只有一个消费者，一旦消费，消息就不在队列中了

  \2. 发送者和接收者之间没有依赖性，发送者发送完成，不管接收者是否运行，都不会影响 消息发送到队列中（我给你发微信，不管你看不看手机，反正我发完了）

  \3. 接收者成功接收消息之后需向对象应答成功（确认） 如果希望发送的每个消息都会被成功处理，那需要P2P

* 发布订阅模式： publish（ Pub） /subscribe（ Sub）

  pub/sub模式包含三个角色：交换机（ exchange），发布者（ publisher），订阅者 （ subcriber） 多个发布者将消息发送交换机，系统将这些消息传递给多个订阅者

  特点：

  1 . 每个消息可以有多个订阅者

  \2. 发布者和订阅者之间在时间上有依赖，对于某个交换机的订阅者，必须创建一个订阅 后，才能消费发布者的消息

  \3. 为了消费消息，订阅者必须保持运行状态；类似于，看电视直播。 如果希望发送的消息被多个消费者处理，可采用本模式







### 2.3.1 简单模式

RabbitMQ本身只是接收，存储和转发消息，并不会对信息进行处理！类似邮局，处理信件的应该是收件人而不是邮局！



#### 2.3.1.1 生产者

```java
/**
 * @BelongsProject lagou-rabbitmq
 * @Author lengy
 * @CreateTime 2022/7/27 17:18
 * @Description 消息生产者
 */
public class Sender {

    public static void main(String[] args) throws Exception {

        String msg = "曹操说：Hello,Java!";

        // 1.获得连接
        Connection connection = ConnectionUtil.getConnection();

        // 2.在连接中创建通道（信道）
        Channel channel = connection.createChannel();

        // 3.创建消息队列(1,2,3,4,5)

        /*
            参数1：队列的名称
            参数2：队列中的数据是否持久化
            参数3：是否排外（是否支持扩展，当前队列只能自己用，不能给别人用）
            参数4：是否自动删除（当队列的连接数为0时，队列会销毁，不管队列是否还保存数据）
            参数5：队列参数（没有参数为空）
         */
        channel.queueDeclare("queue1",false,false,false,null);

        // 4.向指定的队列发送消息(1,2,3,4)
        /*
            参数1：交换机名称：当前时简单模式，也就是p2p模式，没有交换机，所以名称为""
            参数2：目标队列的名称
            参数3：设置消息的属性（没有属性则为空）
            参数4：消息内容(只接受字节数组)
         */
        channel.basicPublish("","queue1",null,msg.getBytes());
        System.out.println("发送" + msg);

        // 5.释放资源
        channel.close();
        connection.close();

    }
}
```

#### 2.3.1.2 消费者

```java
/**
 * @BelongsProject lagou-rabbitmq
 * @Author lengy
 * @CreateTime 2022/7/27 17:33
 * @Description 消息接收者
 */
public class Recer {
    public static void main(String[] args) throws Exception {

        // 1.获得连接
        Connection connection = ConnectionUtil.getConnection();
        // 2.获得通道（信道）
        Channel channel = connection.createChannel();
        // 3.从信道中获得消息
        DefaultConsumer consumer = new DefaultConsumer(channel){
            @Override   // 交付处理（收件人信息，包裹上的快递标签，协议的配置，消息）
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                // body就是从队列中获取的消息
                String s = new String(body);
                System.out.println("接收 = " + s);
            }
        };
        // 4.监听队列   true:自动消息确认
        channel.basicConsume("queue1",true,consumer);
    }
}
```

启动消费者，前往管理端查看队列中的信息，所有信息都已经处理和确认，显示0



#### 2.3.1.3 消息确认机制ACK

通过刚才的案例可以看出，消息一旦被消费，消息就会立刻从队列中移除

RabbitMQ如何得知消息被消费者接收？ 如果消费者接收消息后，还没执行操作就抛异常宕机导致消费失败，但是RabbitMQ无从得 知，这样消息就丢失了 因此， RabbitMQ有一个ACK机制，当消费者获取消息后，会向RabbitMQ发送回执ACK，告 知消息已经被接收

ACK： (Acknowledge character）即是确认字符，在数据通信中，接收站发给发送站的一种 传输类控制字符。表示发来的数据已确认接收无误我们在使用http请求时， http的状态码200

就是告诉我们服务器执行成功 整个过程就想快递员将包裹送到你手里，并且需要你的签字，并拍照回执 不过这种回执ACK分为两种情况：

自动ACK：消息接收后，消费者立刻自动发送ACK（快递放在快递柜）

手动ACK：消息接收后，不会发送ACK，需要手动调用（快递必须本人签收）

两种情况如何选择，需要看消息的重要性： 如果消息不太重要，丢失也没有影响，自动ACK会比较方便 如果消息非常重要，最好消费完成手动ACK，如果自动ACK消费后， RabbitMQ就会把 消息从队列中删除，如果此时消费者抛异常宕机，那么消息就永久丢失了 

修改手动消息确认

```java
// 4.监听队列   false:手动消息确认
        channel.basicConsume("queue1",false,consumer);
```

```java
/**
 * @BelongsProject lagou-rabbitmq
 * @Author lengy
 * @CreateTime 2022/7/27 17:33
 * @Description 消息接收者，加入ACK确认机制
 */
public class RecerByACK {
    public static void main(String[] args) throws Exception {

        // 1.获得连接
        Connection connection = ConnectionUtil.getConnection();
        // 2.获得通道（信道）
        Channel channel = connection.createChannel();
        // 3.从信道中获得消息
        DefaultConsumer consumer = new DefaultConsumer(channel){
            @Override   // 交付处理（收件人信息，包裹上的快递标签，协议的配置，消息）
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                // body就是从队列中获取的消息
                String s = new String(body);
                System.out.println("接收 = " + s);
                // 手动确认（收件人信息，是否同时确认多个消息）
                channel.basicAck(envelope.getDeliveryTag(),false);
            }
        };
        // 4.监听队列   false:手动消息确认
        channel.basicConsume("queue1",false,consumer);
    }
}
```



### 2.3.2 工作队列模式

之前我们学习的简单模式，一个消费者来处理消息，如果生产者生产消息过快过多，而消费者的能 力有限，就会产生消息在队列中堆积（生活中的滞销） 一个烧烤师傅，一次烤50支羊肉串，就一个人吃的话，烤好的肉串会越来越多，怎么处理？ 多招揽客人进行消费即可。当我们运行许多消费者程序时，消息队列中的任务会被众多消费者共 享，但其中某一个消息只会被一个消费者获取（ 1 00支肉串20个人吃，但是其中的某支肉串只能被 一个人吃）

#### 2.3.2.1 生产者

```java
/**
 * @BelongsProject lagou-rabbitmq
 * @Author lengy
 * @CreateTime 2022/7/27 17:18
 * @Description 消息生产者
 */
public class Sender {

    public static void main(String[] args) throws Exception {

        Connection connection = ConnectionUtil.getConnection();

        Channel channel = connection.createChannel();

        channel.queueDeclare("test_work_queue",false,false,false,null);

        for (int i = 1; i <= 100 ; i++) {
            String msg = "羊肉串 ---》 " + i;
            channel.basicPublish("","test_work_queue",null,msg.getBytes());
            System.out.println("新鲜出炉：" + msg);
        }


        channel.close();
        connection.close();

    }
}
```

#### 2.3.2.2 消费者1

```java
/**
 * @BelongsProject lagou-rabbitmq
 * @Author lengy
 * @CreateTime 2022/7/27 17:33
 * @Description 消费者1
 */
public class Recer1 {

    static int i = 1;   // 统计吃掉羊肉串的数量

    public static void main(String[] args) throws Exception {

        // 1.获得连接
        Connection connection = ConnectionUtil.getConnection();
        // 2.获得通道（信道）
        Channel channel = connection.createChannel();
        // queueDeclare 此方法有双重作用，如果队列不存在则创建，如果队列存在，则获取
        channel.queueDeclare("test_work_queue",false,false,false,null);
        channel.basicQos(1);
        // 3.从信道中获得消息
        DefaultConsumer consumer = new DefaultConsumer(channel){
            @Override   // 交付处理（收件人信息，包裹上的快递标签，协议的配置，消息）
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                // body就是从队列中获取的消息
                String s = new String(body);
                System.out.println("【顾客1】吃掉" + s + "！！总共吃【" + i++ + "】串！");
                // 模拟网络延迟
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                // 手动确认（收件人信息，是否同时确认多个消息）
                channel.basicAck(envelope.getDeliveryTag(),false);
            }
        };
        // 4.监听队列   false:手动消息确认
        channel.basicConsume("test_work_queue",false,consumer);
    }
}
```

#### 2.3.2.3 消费者2

```java
/**
 * @BelongsProject lagou-rabbitmq
 * @Author lengy
 * @CreateTime 2022/7/27 17:33
 * @Description 消费者2
 */
public class Recer2 {

    static int i = 1;   // 统计吃掉羊肉串的数量

    public static void main(String[] args) throws Exception {

        // 1.获得连接
        Connection connection = ConnectionUtil.getConnection();
        // 2.获得通道（信道）
        Channel channel = connection.createChannel();
        // queueDeclare 此方法有双重作用，如果队列不存在则创建，如果队列存在，则获取
        channel.queueDeclare("test_work_queue",false,false,false,null);
        channel.basicQos(1);
        // 3.从信道中获得消息
        DefaultConsumer consumer = new DefaultConsumer(channel){
            @Override   // 交付处理（收件人信息，包裹上的快递标签，协议的配置，消息）
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                // body就是从队列中获取的消息
                String s = new String(body);
                System.out.println("【顾客2】吃掉" + s + "！！总共吃【" + i++ + "】串！");
                // 模拟网络延迟
                try {
                    Thread.sleep(900);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                // 手动确认（收件人信息，是否同时确认多个消息）
                channel.basicAck(envelope.getDeliveryTag(),false);
            }
        };
        // 4.监听队列   false:手动消息确认
        channel.basicConsume("test_work_queue",false,consumer);
    }
}
```

先运行2个消费者，排队等候消费（取餐），再运行生产者开始生产消息（烤肉串） 虽然两个消费者的消费速度不一致（线程休眠时间），但是消费的数量却是一致的，各消费50个 消息 例如：工作中， A同学编码速率高， B同学编码速率低，两个人同时开发一个项目， A1 0天完 成， B30天完成， A完成自己的编码部分，就无所事事了，等着B完成就可以了，这样是不可 以的，应该遵循“能者多劳”

效率高的多干点，效率低的少干点 看下面官网是如何给出解决思路的：

```java
// 声明队列（此处为消费者，不是声明创建队列，而且获取，二者代码相同）出餐口排队
channel.queueDeclare("test_work_queue",false,false,false,null);

// 可以理解为：快递一个一个送，送完一个再送下一个，速度快的送件就多
channel.basicQos(1);
```

能者多劳必须要配合手动的ACK机制才生效



#### 2.3.2.4 面试题：避免消息堆积？

workqueue，多个消费者监听同一个队列

接收到消息后，通过线程池，异步消费





### 2.3.3 发布订阅模式

生活中的案例：就是玩抖音快手，众多粉丝关注一个视频主，视频主发布视频，所有粉丝都可以得到视 频通知





* 整个过程，必须先创建路由
  * 路由在生产者程序中创建 
  * 因为路由没有存储消息的能力，当生产者将信息发送给路由后，消费者还没有运行，所以没 有队列，路由并不知道将信息发送给谁 
  * 运行程序的顺序：

1 . MessageSender 2. MessageReceiver1和MessageReceiver2 3. MessageSender



#### 2.3.3.1 生产者

```java
/**
 * @BelongsProject lagou-rabbitmq
 * @Author lengy
 * @CreateTime 2022/7/27 17:18
 * @Description 消息生产者
 */
public class Sender {

    public static void main(String[] args) throws Exception {

        Connection connection = ConnectionUtil.getConnection();

        Channel channel = connection.createChannel();

        // 声明路由(路由名，路由类型）
        // fanout:不处理路由键（只需要将队列绑定到路由上，发送到路由的消息都会被转发到与该路由绑定的所有队列上）
        channel.exchangeDeclare("test_exchange_fanout","fanout");

       // channel.queueDeclare("test_work_queue",false,false,false,null);

        String msg = "hello,大家好!";
        channel.basicPublish("test_exchange_fanout","",null,msg.getBytes());
        System.out.println("生产者：" + msg);

        channel.close();
        connection.close();

    }
}
```

#### 2.3.3.2 消费者1

```java
/**
 * @BelongsProject lagou-rabbitmq
 * @Author lengy
 * @CreateTime 2022/7/27 17:33
 * @Description 消息消费者1
 */
public class Recer {
    public static void main(String[] args) throws Exception {

        Connection connection = ConnectionUtil.getConnection();
        Channel channel = connection.createChannel();
        // 声明队列
        channel.queueDeclare("test_exchange_fanout_queue_1",false,false,false,null);
        // 绑定路由（关注）
        channel.queueBind("test_exchange_fanout_queue_1","test_exchange_fanout","");
        DefaultConsumer consumer = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                // body就是从队列中获取的消息
                String s = new String(body);
                System.out.println("【消费者1】 = " + s);
            }
        };
        // 4.监听队列   true:自动消息确认
        channel.basicConsume("test_exchange_fanout_queue_1",true,consumer);
    }
}
```

#### 2.3.3.3 消费者2

```java
/**
 * @BelongsProject lagou-rabbitmq
 * @Author lengy
 * @CreateTime 2022/7/27 17:33
 * @Description 消息消费者1
 */
public class Recer2 {
    public static void main(String[] args) throws Exception {

        Connection connection = ConnectionUtil.getConnection();
        Channel channel = connection.createChannel();
        // 声明队列
        channel.queueDeclare("test_exchange_fanout_queue_2",false,false,false,null);
        // 绑定路由（关注）
        channel.queueBind("test_exchange_fanout_queue_2","test_exchange_fanout","");
        DefaultConsumer consumer = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                // body就是从队列中获取的消息
                String s = new String(body);
                System.out.println("【消费者2】 = " + s);
            }
        };
        // 4.监听队列   true:自动消息确认
        channel.basicConsume("test_exchange_fanout_queue_2",true,consumer);
    }
}
```



### 2.3.4 路由模式

路由会根据类型进行**定向分发**消息给不同的队列， 

可以理解为是快递公司的分拣中心，整个小区，东面的楼小张送货，西面的楼小王送货

#### 2.3.4.1 生产者

```java
/**
 * @BelongsProject lagou-rabbitmq
 * @Author lengy
 * @CreateTime 2022/7/27 17:18
 * @Description 消息生产者
 */
public class Sender {

    public static void main(String[] args) throws Exception {

        Connection connection = ConnectionUtil.getConnection();

        Channel channel = connection.createChannel();

        // 声明路由(路由名，路由类型）
        // direct:根据路由键进行定向分发消息
        channel.exchangeDeclare("test_exchange_direct","direct");

        String msg = "查询用户";
        channel.basicPublish("test_exchange_direct","select",null,msg.getBytes());
        System.out.println("【用户系统】：" + msg);

        channel.close();
        connection.close();

    }
}
```

#### 2.3.4.2 消费者1

```java
/**
 * @BelongsProject lagou-rabbitmq
 * @Author lengy
 * @CreateTime 2022/7/27 17:33
 * @Description 消息消费者1
 */
public class Recer {
    public static void main(String[] args) throws Exception {

        Connection connection = ConnectionUtil.getConnection();
        Channel channel = connection.createChannel();
        // 声明队列
        channel.queueDeclare("test_exchange_direct_queue_1",false,false,false,null);
        // 绑定路由（如果路由键的类型是 添加，删除，修改的话，绑定到这个队列上）
        channel.queueBind("test_exchange_direct_queue_1","test_exchange_direct","insert");
        channel.queueBind("test_exchange_direct_queue_1","test_exchange_direct","update");
        channel.queueBind("test_exchange_direct_queue_1","test_exchange_direct","delete");
        DefaultConsumer consumer = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                // body就是从队列中获取的消息
                String s = new String(body);
                System.out.println("【消费者1】 = " + s);
            }
        };
        // 4.监听队列   true:自动消息确认
        channel.basicConsume("test_exchange_direct_queue_1",true,consumer);
    }
}
```

#### 2.3.4.3 消费者2

```java
/**
 * @BelongsProject lagou-rabbitmq
 * @Author lengy
 * @CreateTime 2022/7/27 17:33
 * @Description 消息消费者1
 */
public class Recer2 {
    public static void main(String[] args) throws Exception {

        Connection connection = ConnectionUtil.getConnection();
        Channel channel = connection.createChannel();
        // 声明队列
        channel.queueDeclare("test_exchange_direct_queue_2",false,false,false,null);
        // 绑定路由（如果路由键的类型是 查询的话，绑定到这个队列2上）
        channel.queueBind("test_exchange_direct_queue_2","test_exchange_direct","select");
        DefaultConsumer consumer = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                // body就是从队列中获取的消息
                String s = new String(body);
                System.out.println("【消费者2】 = " + s);
            }
        };
        // 4.监听队列   true:自动消息确认
        channel.basicConsume("test_exchange_direct_queue_2",true,consumer);
    }
}
```

1 . 记住运行程序的顺序，先运行一次sender（创建路由器），

\2. 有了路由器之后，在创建两个Recer1和Recer2，进行队列绑定

\3. 再次运行sender，发出消息





### 2.3.5 通配符模式

* 和路由模式90%是一样的。 

* 唯独的区别就是路由键支持模糊匹配 

* 匹配符号

  * *：只能匹配一个词（正好一个词，多一个不行，少一个也不行）
  * \#：匹配0个或更多个词 

* 看一下官网案例：

  * Q1绑定了路由键**.orange.* *
  * Q2绑定了路由键 *.*.rabbit 和 lazy.#
  * 下面生产者的消息会被发送给哪个队列？

  

```
quick.orange.rabbit # Q1 Q2

lazy.orange.elephant # Q1 Q2

quick.orange.fox # Q1

lazy.brown.fox	# Q2

lazy.pink.rabbit # Q2

quick.brown.fox # 无

orange # 无

quick.orange.male.rabbit # 无
```



#### 2.3.5.1 生产者

```java
/**
 * @BelongsProject lagou-rabbitmq
 * @Author lengy
 * @CreateTime 2022/7/27 17:18
 * @Description 消息生产者
 */
public class Sender {

    public static void main(String[] args) throws Exception {

        Connection connection = ConnectionUtil.getConnection();

        Channel channel = connection.createChannel();

        // 声明路由(路由名，路由类型）
        // topic:模糊匹配的定向分发
        channel.exchangeDeclare("test_exchange_topic","topic");

        String msg = "商品降价";
        channel.basicPublish("test_exchange_topic","product.price", null,msg.getBytes());
        System.out.println("【用户系统】：" + msg);

        channel.close();
        connection.close();

    }
}
```

#### 2.3.5.2 消费者1

```java
/**
 * @BelongsProject lagou-rabbitmq
 * @Author lengy
 * @CreateTime 2022/7/27 17:33
 * @Description 消息消费者1
 */
public class Recer {
    public static void main(String[] args) throws Exception {

        Connection connection = ConnectionUtil.getConnection();
        Channel channel = connection.createChannel();
        // 声明队列
        channel.queueDeclare("test_exchange_topic_queue_1",false,false,false,null);
        // 绑定路由（绑定用户相关的消息）
        channel.queueBind("test_exchange_topic_queue_1","test_exchange_topic","user.#");
        DefaultConsumer consumer = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                // body就是从队列中获取的消息
                String s = new String(body);
                System.out.println("【消费者1】 = " + s);
            }
        };
        // 4.监听队列   true:自动消息确认
        channel.basicConsume("test_exchange_topic_queue_1",true,consumer);
    }
}
```

#### 2.3.5.3 消费者2

```java
/**
 * @BelongsProject lagou-rabbitmq
 * @Author lengy
 * @CreateTime 2022/7/27 17:33
 * @Description 消息消费者1
 */
public class Recer2 {
    public static void main(String[] args) throws Exception {

        Connection connection = ConnectionUtil.getConnection();
        Channel channel = connection.createChannel();
        // 声明队列
        channel.queueDeclare("test_exchange_topic_queue_2",false,false,false,null);
        // 绑定路由（绑定商品和订单相关的消息）   支持模糊匹配
        channel.queueBind("test_exchange_topic_queue_2","test_exchange_topic","product.#");
        channel.queueBind("test_exchange_topic_queue_2","test_exchange_topic","order.#");
        DefaultConsumer consumer = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                // body就是从队列中获取的消息
                String s = new String(body);
                System.out.println("【消费者2】 = " + s);
            }
        };
        // 4.监听队列   true:自动消息确认
        channel.basicConsume("test_exchange_topic_queue_2",true,consumer);
    }
}
```



## 2.4 持久化

* 消息的可靠性是RabbitMQ的一大特色，那么RabbitMQ是如何避免消息丢失？
  *  消费者的ACK确认机制，可以防止消费者丢失消息 
  * 万一在消费者消费之前， RabbitMQ服务器宕机了，那消息也会丢失 
* 想要将消息持久化，那么 路由和队列都要持久化 才可以



### 2.4.1 生产者

```java
public class Sender {

    public static void main(String[] args) throws Exception {

        Connection connection = ConnectionUtil.getConnection();

        Channel channel = connection.createChannel();

        // 声明路由(路由名，路由类型，持久化）
        // topic:模糊匹配的定向分发
        channel.exchangeDeclare("test_exchange_topic","topic",true);

        String msg = "商品降价";
        channel.basicPublish("test_exchange_topic","product.price", MessageProperties.PERSISTENT_TEXT_PLAIN,msg.getBytes());
        System.out.println("【用户系统】：" + msg);

        channel.close();
        connection.close();

    }
}
```

### 2.4.2 消费者

```java
public class Recer {
    public static void main(String[] args) throws Exception {

        Connection connection = ConnectionUtil.getConnection();
        Channel channel = connection.createChannel();
        // 声明队列(第二个参数为true：表示支持持久化)
        channel.queueDeclare("test_exchange_topic_queue_1",true,false,false,null);
        // 绑定路由（绑定用户相关的消息）
        channel.queueBind("test_exchange_topic_queue_1","test_exchange_topic","user.#");
        DefaultConsumer consumer = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                // body就是从队列中获取的消息
                String s = new String(body);
                System.out.println("【消费者1】 = " + s);
            }
        };
        // 4.监听队列   true:自动消息确认
        channel.basicConsume("test_exchange_topic_queue_1",true,consumer);
    }
}
```







## 2.4 Spring整合RabbitMQ

* 五种消息模型，在企业中应用最广泛的就是最后一种：定向匹配topic 
* Spring AMQP 是基于 Spring 框架的AMQP消息解决方案，提供模板化的发送和接收消息的抽象 层，提供基于消息驱动的 POJO的消息监听等，简化了我们对于RabbitMQ相关程序的开发

### 2.4.1 生产端工程

* 依赖

```xml
<dependencies>
        <dependency>
            <groupId>org.springframework.amqp</groupId>
            <artifactId>spring-rabbit</artifactId>
            <version>2.0.1.RELEASE</version>
        </dependency>
        <dependency>
            <groupId>org.slf4j</groupId>
            <artifactId>slf4j-log4j12</artifactId>
            <version>1.7.25</version>
            <scope>compile</scope>
        </dependency>
        <dependency>
            <groupId>org.apache.commons</groupId>
            <artifactId>commons-lang3</artifactId>
            <version>3.9</version>
        </dependency>
    </dependencies>
```

* spring-rabbitmq-producer.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:rabbit="http://www.springframework.org/schema/rabbit"
       xsi:schemaLocation="
       http://www.springframework.org/schema/beans
       http://www.springframework.org/schema/beans/spring-beans.xsd
       http://www.springframework.org/schema/rabbit
       http://www.springframework.org/schema/rabbit/spring-rabbit.xsd
">
<!--    1.配置连接工厂，启动生产者确认机制：publisher-confirms="true"-->
    <rabbit:connection-factory id="connectionFactory"
                               host="192.168.114.128"
                               port="5672"
                               username="lengyue"
                               password="123456"
                               virtual-host="/lagou"                      
    />
<!--    2.配置队列-->
    <rabbit:queue name="test_spring_queue_1"/>
<!--    3.配置rabbitAdmin：主要用于在Java代码中对队列的管理，用来创建，绑定，删除队列与交换机，发送消息等-->
    <rabbit:admin connection-factory="connectionFactory"/>
<!--    4.配置交换机，topic类型-->
    <rabbit:topic-exchange name="spring_topic_exchange">
        <rabbit:bindings>
<!--            绑定队列-->
            <rabbit:binding pattern="msg.#" queue="test_spring_queue_1"></rabbit:binding>
        </rabbit:bindings>
    </rabbit:topic-exchange>
<!--    5.配置json转换的工具-->
    <bean id="jsonMessageConverter" class="org.springframework.amqp.support.converter.Jackson2JsonMessageConverter"/>
<!--    6 . 配置Rabbi tTempl ate（消息生产者）-->
    <rabbit:template id="rabbitTemplate"
                     connection-factory="connectionFactory"
                     exchange="spring_topic_exchange"
                     message-converter="jsonMessageConverter"
                    />

</beans>
```

* 发消息

```java
/**
 * @BelongsProject spring-rabbitmq-producter
 * @Author lengy
 * @CreateTime 2022/7/27 20:24
 * @Description 消息生产者
 */
public class Sender {

    public static void main(String[] args) {

        // 1.创建spring容器
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring/spring-rabbitmq-producter.xml");
        // 2.从容器中获得 rabbit模板对象 拿到模板类，使用模板类进行消息的发送
        RabbitTemplate rabbitTemplate = context.getBean(RabbitTemplate.class);
        // 3.发消息
        Map<String,String> map = new HashMap<>();
        map.put("name","卢布");
        map.put("email","4455588@qq.com");
            rabbitTemplate.convertAndSend("msg.user",map);
            System.out.println("消息已发出...");
        context.close();
    }
}
```

### 2.4.2 消费端工程

* 依赖与生产者一致
* spring-rabbitmq-consumer.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:context="http://www.springframework.org/schema/context"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:rabbit="http://www.springframework.org/schema/rabbit"
       xsi:schemaLocation="
       http://www.springframework.org/schema/beans
       http://www.springframework.org/schema/beans/spring-beans.xsd
       http://www.springframework.org/schema/rabbit
       http://www.springframework.org/schema/rabbit/spring-rabbit.xsd
       http://www.springframework.org/schema/context
       http://www.springframework.org/schema/context/spring-context.xsd
">
<!--    1.配置连接工厂-->
    <rabbit:connection-factory id="connectionFactory" host="192.168.114.128" port="5672" username="lengyue" password="123456" virtual-host="/lagou"/>
<!--    2.配置队列-->
  <rabbit:queue name="test_spring_queue_1"/>
<!--    3.配置rabbitAdmin：主要用于在Java代码中对队列的管理，用来创建，绑定，删除队列与交换机，发送消息等-->
    <rabbit:admin connection-factory="connectionFactory"/>


<!--  4.注解扫描包（springIOC）  -->
<context:component-scan base-package="listener"/>
<!--    5.配置监听-->
    <rabbit:listener-container connection-factory="connectionFactory" >
<!--        监听类和队列名-->
      <rabbit:listener ref="consumerListener" queue-names="test_spring_queue_1"/>
    </rabbit:listener-container>
</beans>
```

消费者

MessageListener接口用于spring容器接收到消息后处理消息 如果需要使用自己定义的类型 来实现 处理消息时，必须实现该接口，并重写onMessage()方 法 当spring容器接收消息后，会自动交由onMessage进行处理

```java
/**
 * @BelongsProject spring-rabbitmq-consumer
 * @Author lengy
 * @CreateTime 2022/7/27 20:39
 * @Description 消费者监听队列
 */
@Component
public class ConsumerListener implements MessageListener {


    // Jackson提供序列化和反序列化中使用最多的类，用来转换json的
    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Override
    public void onMessage(Message message) throws Exception {
        // 将message对象转换成json
        try {
           JsonNode jsonNode = MAPPER.readTree(message.getBody());
           String name = jsonNode.get("name").asText();
           String email = jsonNode.get("email").asText();
           System.out.println("从队列中获取：【" + name + "的邮箱是：" + email + "】");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
```

* 启动项目

```java
/**
 * @BelongsProject spring-rabbitmq-consumer
 * @Author lengy
 * @CreateTime 2022/7/27 20:51
 * @Description 运行项目
 */
public class TestConsumerRunner {
    public static void main(String[] args) throws IOException {
        // 获得容器
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring/spring-rabbitmq-consumer.xml");
        // 让程序一直运行，别终止
        System.in.read();
    }
}
```





## 2.5 消息成功确认机制

在实际场景下，有的生产者发送的消息是必须保证成功发送到消息队列中，那么如何保证成功投递呢？ 事务机制 发布确认机制



### 2.5.1 事务机制

AMQP协议提供的一种保证消息成功投递的方式，通过信道开启 transactional 模式 并利用信道 的三个方法来实现以事务方式 发送消息，若发送失败，通过异常处理回滚事务，确保 消息成功投递

channel.txSelect()： 开启事务

channel.txCommit() ：提交事务

channel.txRollback() ：回滚事务

Spring已经对上面三个方法进行了封装，所以我们只能使用原始的代码演示



#### 2.5.1.1 生产者

```java
/**
 * @BelongsProject lagou-rabbitmq
 * @Author lengy
 * @CreateTime 2022/7/27 17:18
 * @Description 消息生产者
 */
public class Sender {

    public static void main(String[] args) throws Exception {

        Connection connection = ConnectionUtil.getConnection();

        Channel channel = connection.createChannel();

        // 声明路由(路由名，路由类型，持久化）
        // topic:模糊匹配的定向分发
        channel.exchangeDeclare("test_transaction","topic");
        channel.txSelect(); // 开启事务
        try {
            channel.basicPublish("test_transaction","product.price", null,"商品1降价".getBytes());
            System.out.println(1/0);
            channel.basicPublish("test_transaction","product.price", null,"商品2降价".getBytes());
            System.out.println("【生产者：消息已全部发送！】");

            channel.txCommit(); // 提交事务  一起成功
        } catch (Exception e) {
            System.out.println("消息全部撤销!");
            channel.txRollback();   // 事务回滚（一起失败）
            e.printStackTrace();
        } finally {

        channel.close();
        connection.close();
        }

    }
}
```

#### 2.5.1.2 消费者

```java
/**
 * @BelongsProject lagou-rabbitmq
 * @Author lengy
 * @CreateTime 2022/7/27 17:33
 * @Description 消息消费者1
 */
public class Recer {
    public static void main(String[] args) throws Exception {

        Connection connection = ConnectionUtil.getConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare("test_transaction_queue",false,false,false,null);

        channel.queueBind("test_transaction_queue","test_transaction","product.#");
        DefaultConsumer consumer = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                // body就是从队列中获取的消息
                String s = new String(body);
                System.out.println("【消费者】 = " + s);
            }
        };
        // 4.监听队列   true:自动消息确认
        channel.basicConsume("test_transaction_queue",true,consumer);
    }
}
```



### 2.5.2 Confirm发布确认机制

RabbitMQ为了保证消息的成功投递，采用通过AMQP协议层面为我们提供事务机制的方案，但是 采用事务会大大降低消息的吞吐量

那么有没有更加高效的解决方式呢？答案就是采用Confirm模式。 事务效率为什么会这么低呢？试想一下： 1 0条消息，前9条成功，如果第1 0条失败，那么9条消息 要全部撤销回滚。太太太浪费 而confirm模式则采用补发第1 0条的措施来完成1 0条消息的送达



#### 2.5.2.1 在spring中应用

spring-rabbitmq-producer.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:rabbit="http://www.springframework.org/schema/rabbit"
       xsi:schemaLocation="
       http://www.springframework.org/schema/beans
       http://www.springframework.org/schema/beans/spring-beans.xsd
       http://www.springframework.org/schema/rabbit
       http://www.springframework.org/schema/rabbit/spring-rabbit.xsd
">
<!--    1.配置连接工厂，启动生产者确认机制：publisher-confirms="true"-->
    <rabbit:connection-factory id="connectionFactory"
                               host="192.168.114.128"
                               port="5672"
                               username="lengyue"
                               password="123456"
                               virtual-host="/lagou"
                               publisher-confirms="true"
    />
<!--    6.配置rabbitmq的模板，添加确认回调处理类：-->
    <rabbit:template id="rabbitTemplate"
                     connection-factory="connectionFactory"
                     exchange="spring_topic_exchange"
                     message-converter="jsonMessageConverter"
                     confirm-callback="messageConfirm"/>

<!--7.确认机制的处理类-->
    <bean id="messageConfirm" class="confirm.MessageConfirm"></bean>
</beans>
```

消息确认处理类

```java
/**
 * @BelongsProject spring-rabbitmq-producter
 * @Author lengy
 * @CreateTime 2022/7/27 21:18
 * @Description 消息确认处理
 */
public class MessageConfirm implements RabbitTemplate.ConfirmCallback {
    /**
     *
     * @param correlationData   消息相关的数据对象（封装了消息的唯一id）
     * @param b 消息是否确认成功
     * @param s 异常信息
     */
    @Override
    public void confirm(CorrelationData correlationData, boolean b, String s) {
        if (b) {
            System.out.println("消息确认成功!");
        }else {
            System.out.println("xxxxxxxxx消息确认失败xxxxxxxxx!");
            //System.out.println(s);
            // 如果本条消息一定要发送到队列中，例如下订单消息，我们可以采用补发
            // 1.采用递归(限制递归的次数)
            // 2.redis+定时任务
        }
    }
}
```

发送消息

```java
/**
 * @BelongsProject spring-rabbitmq-producter
 * @Author lengy
 * @CreateTime 2022/7/27 20:24
 * @Description 消息生产者
 */
public class Sender {

    public static void main(String[] args) {

        // 1.创建spring容器
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring/spring-rabbitmq-producter.xml");
        // 2.从容器中获得 rabbit模板对象 拿到模板类，使用模板类进行消息的发送
        RabbitTemplate rabbitTemplate = context.getBean(RabbitTemplate.class);
        // 3.发消息
        Map<String,String> map = new HashMap<>();
        map.put("name","卢布");
        map.put("email","4455588@qq.com");
            rabbitTemplate.convertAndSend("x","msg.user",map);
            System.out.println("消息已发出...");
        context.close();
    }
}
```



## 2.6 消费端限流

我们 Rabbitmq 服务器积压了成千上万条未处理的消息，然后随便打开一个消费者客户端，就会 出现这样的情况: 巨量的消息瞬间全部喷涌推送过来，但是单个客户端无法同时处理这么多数据， 就会被压垮崩溃 所以，当数据量特别大的时候，我们对生产端限流肯定是不科学的，因为有时候并发量就是特别 大，有时候并发量又特别少，这是用户的行为，我们是无法约束的 所以我们应该对消费端限流，用于保持消费端的稳定



```java
     for (int i = 1; i <= 10; i++){
            rabbitTemplate.convertAndSend("msg.user",map);
            System.out.println("消息已发出...");
      }
```

* 消费者进行限流处理

```xml
<!--    5.配置监听-->
    <rabbit:listener-container connection-factory="connectionFactory" prefetch="3" acknowledge="manual">
<!--        监听类和队列名-->
    <rabbit:listener ref="consumerListener" queue-names="test_spring_queue_1"/>
    </rabbit:listener-container>
```

```java
/**
 * @BelongsProject spring-rabbitmq-consumer
 * @Author lengy
 * @CreateTime 2022/7/27 20:39
 * @Description 消费者监听队列
 */
@Component
public class ConsumerListener extends AbstractAdaptableMessageListener {


    // Jackson提供序列化和反序列化中使用最多的类，用来转换json的
    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Override
    public void onMessage(Message message, Channel channel) throws Exception {
        // 将message对象转换成json
        try {
//            JsonNode jsonNode = MAPPER.readTree(message.getBody());
//            String name = jsonNode.get("name").asText();
//            String email = jsonNode.get("email").asText();
//            System.out.println("从队列中获取：【" + name + "的邮箱是：" + email + "】");

            // 手动确认消息(参数1，参数2)
            /**
             * 参数1：RabbitMQ向该channel投递的这条消息的唯一标识id，此id是一个单调递增的正整数
             * 参数2：为了减少网络流量，手动确认可以被批量处理，当该参数为true时，则可以一次性确认msgId小于等于传入值的所有消息
             */
            String s = new String(message.getBody());
            System.out.println("s = " + s);
            long msgId = message.getMessageProperties().getDeliveryTag();
            channel.basicAck(msgId,true);

            Thread.sleep(3000);
            System.out.println("休息3秒后，再继续接收消息！");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
```



## 2.7 过期时间TTL

Time To Live：生存时间、还能活多久，单位毫秒

在这个周期内，消息可以被消费者正常消费，超过这个时间，则自动删除（其实是被称为dead  message并投入到死信队列，无法消费该消息）

RabbitMQ可以对消息和队列设置TTL

通过队列设置，队列中所有消息都有相同的过期时间 对消息单独设置，每条消息的TTL可以不同（更颗粒化）



### 2.7.1 设置队列TTL

```xml
<!--    2.配置队列-->
 <rabbit:queue name="test_spring_queue_ttl" auto-declare="true">
      <rabbit:queue-arguments>
         <entry key="x-message-ttl" value-type="long" value="5000"/>
       </rabbit:queue-arguments>
 </rabbit:queue>
```



### 2.7.2 设置消息TTL

设置某条消息的ttl，只需要在创建发送消息时指定即可

```xml
<rabbit:queue name="test_spring_queue_ttl_2"/>
```

```java
/**
 * @BelongsProject spring-rabbitmq-producter
 * @Author lengy
 * @CreateTime 2022/7/27 20:24
 * @Description 消息生产者
 */
public class Sender2 {

    public static void main(String[] args) {

        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring/spring-rabbitmq-producter.xml");
        RabbitTemplate rabbitTemplate = context.getBean(RabbitTemplate.class);

        // 创建消息的配置对象
        MessageProperties properties = new MessageProperties();
        // 设置过期时间3秒
        properties.setExpiration("3000");
        // 创建消息
        Message message = new Message("测试过期时间".getBytes(),properties);
        rabbitTemplate.convertAndSend("msg.user",message);
            System.out.println("消息已发出...");
        context.close();
    }
}
```

如果同时设置了queue和message的TTL值，则二者中较小的才会起作用



## 2.8 死信队列

DLX（ Dead Letter Exchanges）死信交换机/死信邮箱，当消息在队列中由于某些原因没有被及时 消费而变成死信（ dead message）后，这些消息就会被分发到DLX交换机中，而绑定DLX交换机 的队列，称之为：“死信队列”

消息没有被及时消费的原因： 消息被拒绝（ basic.reject/ basic.nack）并且不再重新投递 requeue=false

消息超时未消费 达到最大队列长度

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:rabbit="http://www.springframework.org/schema/rabbit"
       xsi:schemaLocation="
       http://www.springframework.org/schema/beans
       http://www.springframework.org/schema/beans/spring-beans.xsd
       http://www.springframework.org/schema/rabbit
       http://www.springframework.org/schema/rabbit/spring-rabbit.xsd
">
    <rabbit:connection-factory id="connectionFactory"
                               host="192.168.114.128"
                               port="5672"
                               username="lengyue"
                               password="123456"
                               virtual-host="/lagou"
    />
    <rabbit:admin connection-factory="connectionFactory"/>
    <rabbit:template id="rabbitTemplate"
                     connection-factory="connectionFactory"
                     exchange="my_exchange"
    />


<!--  ########################################################################  -->
<!--    声明死信队列-->
<rabbit:queue name="dlx_queue"/>
<!--    声明定向死信交换机-->
<rabbit:direct-exchange name="dlx_exchange">
    <rabbit:bindings>
        <rabbit:binding key="dlx_ttl" queue="dlx_queue"/>
        <rabbit:binding key="dlx_max" queue="dlx_queue"/>
    </rabbit:bindings>
</rabbit:direct-exchange>

<!--声明定向的测试消息的交换机-->
    <rabbit:direct-exchange name="my_exchange">
        <rabbit:bindings>
            <rabbit:binding key="dlx_ttl" queue="test_ttl_queue"/>
            <rabbit:binding key="dlx_max" queue="test_max_queue"/>
        </rabbit:bindings>
    </rabbit:direct-exchange>

<!--    声明 测试过期的消息队列-->
    <rabbit:queue name="test_ttl_queue">
        <rabbit:queue-arguments>
<!--            设置队列的过期时间 TTL-->
            <entry key="x-message-ttl" value-type="long" value="10000"/>
<!--            消息如果超时，将消息投递给 死信交换机-->
            <entry key="x-dead-letter-exchange" value="dlx_exchange"/>
        </rabbit:queue-arguments>
    </rabbit:queue>

    <!--    声明 测试超出长度的消息队列-->
    <rabbit:queue name="test_max_queue">
        <rabbit:queue-arguments>
            <!--            设置队列的额定长度(本队列最多装2个消息)-->
            <entry key="x-max-length" value-type="long" value="2"/>
            <!--            消息如果超出长度，将消息投递给 死信交换机-->
            <entry key="x-dead-letter-exchange" value="dlx_exchange"/>
        </rabbit:queue-arguments>
    </rabbit:queue>
</beans>
```

```java
/**
 * @BelongsProject spring-rabbitmq-producter
 * @Author lengy
 * @CreateTime 2022/7/27 20:24
 * @Description 消息生产者
 */
public class SenderDLX {

    public static void main(String[] args) {

        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring/spring-rabbitmq-producter-dlx.xml");
        RabbitTemplate rabbitTemplate = context.getBean(RabbitTemplate.class);
        rabbitTemplate.convertAndSend("dlx_ttl","超时，关闭订单".getBytes());
//        rabbitTemplate.convertAndSend("dlx_max","测试长度1".getBytes());
//        rabbitTemplate.convertAndSend("dlx_max","测试长度2".getBytes());
//        rabbitTemplate.convertAndSend("dlx_max","测试长度3".getBytes());
        context.close();
    }
}
```



## 2.9 延迟队列

延迟队列： TTL + 死信队列的合体 死信队列只是一种特殊的队列，里面的消息仍然可以消费 在电商开发部分中，都会涉及到延时关闭订单，此时延迟队列正好可以解决这个问题



消费者

```xml
<!--    5.配置监听-->
    <rabbit:listener-container connection-factory="connectionFactory" prefetch="3" acknowledge="manual">
<!--        监听死信队列  -->
        <rabbit:listener ref="consumerListener" queue-names="dlx_queue"/>
    </rabbit:listener-container>
```







# 3、RabbitMQ集群

```properties
#全局配置
global
     #设置日志
     log 127.0.0.1 local0 info
     #当前工作目录
     chroot /usr/local/haproxy
     #用户与用户组
     user haproxy
     group haproxy
     #运行进程ID
     uid 99
     gid 99
     #守护进程启动
     daemon
     #最大连接数
     maxconn 4096

#默认配置
defaults
    #应用全局的日志配置
    log global
    #默认的模式mode {tcp|http|health}，TCP是4层，HTTP是7层，health只返回OK
    mode tcp
    #日志类别tcplog
    option tcplog
    #不记录健康检查日志信息
    option dontlognull
    #3次失败则认为服务不可用
    retries 3
    #每个进程可用的最大连接数
    maxconn 2000
    #连接超时
    timeout connect 5s
    #客户端超时30秒，ha就会发起重新连接
    timeout client 30s
    #服务端超时15秒，ha就会发起重新连接
    timeout server 15s

#绑定配置
listen rabbitmq_cluster
	bind 192.168.114.130:5672
	#配置TCP模式
	mode tcp
 	#简单的轮询
	balance roundrobin
	#RabbitMQ集群节点配置，每隔5秒对mq集群做检查，2次正确证明服务可用，3次失败证明服务不可用
	server A 192.168.114.128:5672 check inter 5000 rise 2 fall 3
	server B 192.168.114.129:5672 check inter 5000 rise 2 fall 3

#haproxy监控页面地址
listen monitor
	bind 192.168.114.130:8100
	mode http
	option httplog
	stats enable
	#监控页面地址 http://192.168.114.130:8100/monitor
	stats uri /monitor
	stats refresh 5s
```

```bash
! Configuration File for keepalived

global_defs {
   router_id C ## 非常重要，标识本机的hostname
}
vrrp_script chk_haproxy{
   script "/etc/keepalived/haproxy_check.sh"  ## 执行的脚本位置
   interval 2 ## 检测时间间隔
   weight -20 ## 如果条件成立则权重减20

}

vrrp_instance VI_1 {
   state MASTER 	## 非常重要，标识主机，备用机143改为 BACKUP

   interface ens33 	## 非常重要，网卡名（ifconfig查看）

   virtual_router_id 66 	## 非常重要，自定义，虚拟路由ID号（主备节点要相同）

   priority 100 	## 优先级（0-254），一般主机的大于备机

   advert_int 1 	## 主备信息发送间隔，两个节点必须一致，默认1秒

   authentication {	 ## 认证匹配，设置认证类型和密码，
       auth_type PASS
       auth_pass 1111
   }
   track_script {
       chk_haproxy 	## 检查hap roxy健康状况的脚本
   }
   virtual_ipaddress { ## 简称“VIP”

        192.168.114.66/24 	## 非常重要，虚拟i p，可以指定多个，以后连接mq就用ip

   }
}

virtual_server 192.168.114.66 5672 { ## 虚拟i p的详细配置

   delay_loop 6 # 健康检查间隔，单位为秒

   lb_algo rr 	

   lb_kind NAT # 负载均衡转发规则。一般包括DR , NAT , TUN 3种

   protocol TCP # 转发协议，有TCP和UDP两种，一般用TCP

   real_server 192.168.114.130 5672 { ## 本机的真实i p

 		weight 1 	# 默认为1 , 0为失效

   }
}
```

```
#!/bin/bash
COUNT=`ps -C haproxy --no-header |wc -l`
if [ $COUNT -eq 0 ];then
   /usr/local/haproxy/sbin/haproxy -f /etc/haproxy/haproxy.cfg
    sleep 2
    if [ `ps -C haproxy --no-header |wc -l` -eq 0 ];then
        killall keepalived
    fi
fi
```

