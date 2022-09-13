# Mybatis任务三：加载策略及注解开发

# 一、Mybatis加载策略

## 1.1 什么是延迟加载？

​	如果在查询用户信息的同时把关联对象的订单信息也立刻查询出来了，这就是立即加载。

​	如果在查询用户信息的同时没有立即把关联对象的订单信息也查询出来，而是什么时候用，什么时候查询。这就是延迟加载。

**例：**

```markdown
* 问题
* 在当前一对多中，有一个用户，它有100个订单
	从用户角度出发，在查询用户信息的时候，要不要把关联的对象 订单信息也查出来？
	从订单角度出发，在查询订单的时候，要不要把关联的用户查出来？
	
* 回答
	当仅仅需要用户信息时，就不用查询出该用户所关联的订单。所以在查询用户时，什么时候用到该用户所关联的订单信息时，什么时候再根据用户信息发起查询。		实现的效果就是延迟加载
	
	在查询订单信息时，订单所属的用户信息应该随着订单一起查询出来。		实现的效果就是立即加载
```



**延迟加载**

​	就是在需要用到数据时才进行加载，不需要用到数据时就不加载数据。延迟加载也称为懒加载。

```markdown
* 优点：
	先从单表查询，需要时再从关联表去关联查询，大大提高数据库性能。如果是延迟加载，就是查询单表了，查询单表要比关联查询多张表速度要快。
	
	
* 缺点：
	因为只有当需要用数据时，才会进行数据库查询，这样在大批量查询时，查询工作消耗时间，所以可能造成用户等待时间变长
	
* 在多表中：
	一对多，多对多：通常采用延迟加载。	因为只需要用户信息时，没必要把用户关联的所有订单信息查询出来	
	一对一（多对一）：通常采用立即加载。	从订单角度出发，一个订单只属于一个用户，所以可以采用立即加载
	
* 注意：
	延迟加载是基于嵌套查询来实现的
```

---

立即加载和延迟加载的区别：

* 主要看当前对象的关联对象的加载时机
  * 如果在查询当前对象时，关联对象立刻被查询出来，就是立即加载
  * 如果关联对象是什么时候用，什么时候查询。就是延迟加载



立即加载和延迟加载分别在什么情况下采用？

* 一对多，多对多，采用延迟加载
* 一对一，采用立即加载





## 1.2 实现延迟加载

### 1.2.1 局部延迟加载		在association或collection标签 中设置 fetchType="lazy"  就是延迟加载

在association和collection标签中有一个fetchType属性，通过修改它的值，可以修改局部的加载策略

```xml
<!--   一对多嵌套查询：查询所有的用户，同时还要查询出每个用户所关联的订单信息

        一对多配置：
            使用<resultMap>和<collection>标签做配置，通过column条件来执行select查询
                select 属性是定位到哪条要执行的sql的

 -->

<!-- 开启一对多 延迟加载 -->
<resultMap id="userMap" type="user">
    <id column="id" property="id"></id>
  <result column="username" property="username"></result>
  <result column="password" property="password"></result>
  <result column="birthday" property="birthday"></result>
  <!--
fetchType="lazy": 就是延迟加载策略
fetchType="eager": 立即加载策略
-->
  <collection property="orderList" ofType="order" column="id"
        select="com.lagou.dao.OrderMapper.findByUid" fetchType="lazy">
  </collection>
</resultMap>
<select id="findAll" resultMap="userMap">
 SELECT * FROM `user`
</select>
```



### 1.2.2 设置触发延迟加载的方法

​	在配置了延迟加载策略后，即使没有调用关联对象的任何方法，但是在调用当前对象的equlas、clone、hashCode、toString方法时也会触发关联对象的查询。

---



**toString这个方法表示当前不仅要去查询当前用户信息，默认会认为还需要查询当前用户所关联的信息。所以要配置lazyLoadTriggerMethods这个属性，告知mybatis 调用toString时还是进行延迟加载**

---



​	所以可以在核心配置文件中使用配置settings标签的 **lazyLoadTriggerMethods** 配置项 覆盖掉上面的四个方法。

```xml
<settings>
<!--        所有方法都会延迟加载-->
    <!-- 表示调用当前对象的toString方法时，会进行延迟加载 -->
        <setting name="lazyLoadTriggerMethods" value="toString()"/>

    </settings>
```



这样在查询测试中就会只有 user用户信息了，没有orderList 订单信息了。等到什么时候用到用户所关联的订单信息时，再发起查询。



user.getOrderList(),	当这个get方法调用，此时就会再次发起查询，根据用户id向数据库再次发送SQL查询出当前用户所关联的订单信息



```java
/*
        一对多嵌套查询：查询所有的用户，同时还要查询出每个用户所关联的订单信息
     */

    @Test
    public void test5() throws IOException {

        // 模版代码
        // 加载核心配置文件
        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");

        // 获取SqlSessionFactory工厂对象
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);

        // 获取sqlSession会话对象
        SqlSession sqlSession = sqlSessionFactory.openSession();

        // 获取接口的代理对象
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);

        // 使用代理对象调用方法
        List<User> allWithOrder2 = mapper.findAllWithOrder2();

        for (User user : allWithOrder2) {
            System.out.println(user);

            // 要用到该用户的订单信息了
            //System.out.println(user.getOrdersList());

        }

        // 关闭资源
        sqlSession.close();
    }
```



### 1.2.3 全局延迟加载

​	**在Mybatis的核心配置文件中使用settings 标签修改全局的加载策略**，**只要完成了全局延迟加载的话。所有的关联对象在进行查询时，默认都会进行延迟加载**，无需手动添加局部延迟加载了



MyBatis官网地址：http://www.mybatis.org/mybatis-3/

```xml
<settings>
<!--        开启全局延迟加载功能-->
    <!-- lazyLoadingEnabled：延迟加载的全局开关
 			开启时，表明所有关联对象都会延迟加载
			默认false，不开启-->
        <setting name="lazyLoadingEnabled" value="true"/>
</settings>
```

**注意：**

​	**局部的加载策略优先级要高于全局的加载策略。**

---



**一对一查询时，建议使用立即加载**，所以在association 标签里设置  fetchType="eager":在查询订单及关联的用户信息的时候，采用立即加载策略

```xml
<resultMap id="orderMap2" type="orders">
        <id property="id" column="id"/>
        <!--        result标签：对于普通属性的映射配置-->
        <result property="ordertime" column="ordertime"/>
        <result property="total" column="total"/>
        <result property="uid" column="uid"/>

<!--        把两条sql嵌套起来，查询出对应的用户信息并封装到Orders实体中的user属性上-->


        <!--        在Orders实体中当前表示对方关系是一个实体对象，就要用到 association标签-->

<!--        发起第二条sql语句的查询，根据第二条sql语句的查询结果把user对应的信息封装到user属性上-->


        <!--   问题：1.怎么去执行第二条sql     2.如何执行第二条sql的时候，把uid作为参数进行传递
             select:写namespace.id    这样的话，mybatis就可以根据statementid 能够定位到第二条要去执行的sql
             由于执行第二条sql语句需要传递参数，就是根据第一条sql查询出来的uid
             uid作为参数传递就可以使用 column
             就写当前第一条sql语句查询出来的结果，要把哪个字段当做参数进行传递。当前要把uid进行传递，所以 column值就写 uid


                column ： 表示要将查询结果中的哪个字段作为参数进行传递
                column="uid"：其实就是把查询出来的uid的值作为参数进行传递

                当前要去执行的第二条sql语句怎么定位到，就要根据select标签的值来定位
                select标签的值就相当于Statementid
             -->

<!--        fetchType="eager":在查询订单及关联的用户信息的时候，采用立即加载策略-->
        <association property="user" javaType="user" select="com.lagou.mapper.UserMapper.findById" column="uid" fetchType="eager"/>


    </resultMap>


<!-- 一对一嵌套查询
    一对一配置中
        使用<resultMap>和<association>标签做配置。
            通过column条件来传递参数，执行对应的select查询
            select是定位到哪条要执行的sql 属性

        -->
    <select id="findAllWithUser2" resultMap="orderMap2">
        SELECT * FROM orders
    </select>

```



# 二、Mybatis缓存

## 2.1 为什么使用缓存

​	要将用户频繁查询，且不经常修改的，固定的数据 第一次 从数据库中查询出来，存放到缓存中，以减少与数据库的交互次数，从而提高性能。  当用户再次查询这些数据时，不用再去通过数据库查询，而是去缓存里查询。

**概括：**就是要把一些经常查询且不经常发生变化的数据，存到缓存中，使用缓存来提高查询效率。

Mybatis提供了缓存策略，通过缓存策略来减少数据库的查询次数，从而提高性能。

在Mybatis中**缓存分为一级缓存，二级缓存。**



## 2.2 一级缓存

### 2.2.1 介绍

​	**一级缓存是SqlSession级别的缓存，默认开启的**(面试)

​	当参数和SQL完全一样的情况下，我们使用同一个SqlSession对象调用一个Mapper方法，往往**只执行一次SQL**，因为使用SqlSession对象进行**第一次查询后，查询数据库，**当从**数据库中查询出记录后，Mybatis会将数据放到缓存中。**以**后再进行相同的方法时**，如果没有声明需要刷新，并且缓存没有超时的情况下。**SqlSession对象就会去查询缓**存，而不会去查询数据库了



### 2.2.2 验证

```java
/*
        验证Mybatis中的一级缓存：sqlSession级别的缓存
     */
    @Test
    public void testOneCache() throws IOException {

        // 模版代码
        // 加载核心配置文件
        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");

        // 获取SqlSessionFactory工厂对象
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);

        // 获取sqlSession会话对象
        SqlSession sqlSession = sqlSessionFactory.openSession();

        // 拿到UserMapper的代理对象
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);

        // 根据id查询用户信息
        // 两次查询，但是SQL语句只执行了一次
        // 第一次查询，查询的是数据库	打印了sql
        User user1 = userMapper.findById(1);
        System.out.println(user1);

        // clearCache:手动清空缓存
        // 调用完clearCache之后，一级缓存被清空了。所以第二次查询时，会再次执行SQL，去查询数据库
        //sqlSession.clearCache();


        // 第二次查询，查询的是一级缓存		没有打印sql
        User user2 = userMapper.findById(1);
        System.out.println(user2);

        sqlSession.close();
    }
```

​	可以发现，虽然上面的代码查询了两次，但最后只执行了一次数据库操作，这就是Mybatis提供的一级缓存的作用。因为一级缓存，导致第二次查询，没有去数据库查询，而是去缓存中查询。



### 2.2.3 分析

​	一级缓存是sqlSession范围的缓存，**执行SqlSession的增删改操作时，或者调用clearCache()、commit()、close()方法、都会清空一级缓存**。 就是在两次查询之间进行了事务操作，都会清空一级缓存。



一级缓存的失效情况：**事务操作，增删改，或者调用clearCache()、commit()、close() 方法，都会清空缓存。**(面试)



```markdown
1. 当第一次发起查询时，首先会查询一级缓存，就是sqlSession缓存区域，sqlSession底层就是一个map。	缓存中没有数据，那么第一次查询就会查询数据库。

2. 从数据库查询出信息后，会放到一级缓存中。同时map里的key就是当前执行的SQL+参数，value就是查询出的信息

3. 执行SqlSession的增删改操作时，或者调用clearCache()、commit()、close()方法、都会清空一级缓存，这样做的目的为了让缓存中存储的是最新的信息，避免脏读。 

4. 第二次查询时，sql和参数没有改变。就会先查询一级缓存，此时一级缓存有数据，map里的key匹配成功，返回value的信息。
```



### 2.2.4 清除

```java
/*
        验证Mybatis中的一级缓存：sqlSession级别的缓存
     */
    @Test
    public void testOneCache() throws IOException {

        // 模版代码
        // 加载核心配置文件
        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");

        // 获取SqlSessionFactory工厂对象
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);

        // 获取sqlSession会话对象
        SqlSession sqlSession = sqlSessionFactory.openSession();

        // 拿到UserMapper的代理对象
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);

        // 根据id查询用户信息
        // 第一次查询，查询的是数据库	
        User user1 = userMapper.findById(1);
        System.out.println(user1);

        // clearCache:手动清空缓存
        // 调用完clearCache之后，一级缓存被清空了。所以第二次查询时，会再次执行SQL，去查询数据库
        sqlSession.clearCache();


        // 第二次查询，查询的是数据库		
        User user2 = userMapper.findById(1);
        System.out.println(user2);

        sqlSession.close();
    }
```

---

```xml
<!-- 可以在select标签上，添加上flushCache属性 -->

<!--    flushCache="true"：表示每次查询时，都会清除一级缓存-->
<select id="findById" resultType="user" parameterType="int" flushCache="true">
        SELECT * FROM user WHERE id = #{id}
    </select>
```

---

**一级缓存是SqlSession级别的缓存，默认是开启的。**

**失效情况：当执行SqlSession的增删改操作，或者手动调用SqlSession的clearCache()、commit()、close()方法，都会清空缓存**





## 2.3 二级缓存

### 2.3.1 介绍

**二级缓存是namespace级别(跨sqlSession)的缓存，默认不开启**

是一种Mapper映射级别的缓存。如果有多个sqlSession对同一个Mapper(namespace)进行操作的话，那么现在这些多个sqlSession可以共享该namespace下的二级缓存区域。	二级缓存就是一个跨sqlSession的缓存



二级缓存开启需要配置，实现二级缓存时，Mybatis要求返回的POJO(查询出的实体类)必须是可序列化的，也就是实体类需要实现Serializable接口，才能把查询出来的实体类对象存到二级缓存。	配置开启二级缓存，在映射配置文件XML 中 配置<cache/> 就可以开启二级缓存了



### 2.3.2 验证

1）配置核心配置文件

```xml
<settings>
<!--       
		 cacheEnabled：缓存的开关，默认值为true

			因为cacheEnabled的取值默认就为true，所以这一步可以省略不配置
            为true代表开启二级缓存：为false代表不开启二级缓存
            -->
        <setting name="cacheEnabled" value="true"/>

    </settings>
```



2）配置UserMapper.xml 映射

```xml

<mapper namespace="com.lagou.mapper.UserMapper">

<!-- 表示当前映射开启二级缓存 -->
    <cache/>
    
<!-- <select>标签中设置useCache=”true”代表当前这个statement要使用二级缓存 -->

    <!--使用 useCache="true"  开启二级缓存的Statement 配置-->
    
<!--    useCache="true":代表当前这个Statement要使用二级缓存，在调用findById方法时，其实就会去开启二级缓存，查询出的内容存到二级缓存中

        useCache="false":表示 每一次调用findById这个方法的时候，都去查询数据库，不去查询缓存。 禁用二级缓存

		注意：
      针对每次查询都需要最新的数据sql，要设置成useCache="false"，禁用二级缓存。

-->
    
    <!-- 在开启二级缓存的Statement配置上，使用useCache属性，为true -->
    <select id="findById" resultType="user" parameterType="int" useCache="true">
        SELECT * FROM user WHERE id = #{id}
    </select>
</mapper>
```



3）修改User实体

```java
// 实体类需要实现Serializable      才能使这个实体对象的内容 实现二级缓存
public class User implements Serializable {

    private Integer id;
    private String username;
    private Date birthday;
    private String sex;
    private String address;

    // 在一方实体中想表示多方关系    就用集合，泛型是多方实体类型

    // 表示多方关系：集合:ordersList     代表了当前用户所具有的订单列表
    // 如果在实体中表示一对多用到集合      那么在映射配置文件中用的子标签是 collection
    private List<Orders> ordersList;

    // 表示多方关系：集合:roleList     代表了当前用户所具有的角色列表   collection
    private List<Role> roleList;
```



4）测试

```java
/*
        验证Mybatis中的二级缓存
            跨sqlSession的，两个sqlSession共享了二级缓存，因为都是对同一个namespace进行操作。都是对UserMapper 进行的操作

            二级缓存是 namespace级别，mapper映射级别的缓存。跨sqlSession的级别，当多个sqlSession去操作同一个Mapper映射的sql语句时，
            多个sqlSession可以共用二级缓存
     */
    @Test
    public void testTwoCache() throws IOException {

        // 模版代码
        // 加载核心配置文件
        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");

        // 获取SqlSessionFactory工厂对象
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);

        // 获取sqlSession会话对象
        SqlSession sqlSession1 = sqlSessionFactory.openSession();

        UserMapper userMapper1 = sqlSession1.getMapper(UserMapper.class);

        // 第一次查询    从数据库进行查询，将结果放到一级缓存中
        User user = userMapper1.findById(1);

        System.out.println(user);

        // 只有执行sqlSession.commit 或者 sqlSession.close，才会清空一级缓存同时把一级缓存的内容刷新到二级缓存

        // 只有执行sqlSession.commit 或者 sqlSession.close， 那么一级缓存中内容才会刷新到二级缓存
        sqlSession1.close();


        SqlSession sqlSession2 = sqlSessionFactory.openSession();

        UserMapper userMapper2 = sqlSession2.getMapper(UserMapper.class);

        User user2 = userMapper2.findById(1);

        // 从二级缓存中查询出来的user信息
        System.out.println(user2);

        sqlSession2.close();

    }
```

---

配置二级缓存：

* 在核心配置文件中配置cacheEnabled属性
* 在UserMapper.xml中 添加cache标签，以及在  在开启二级缓存的Statement配置上，使用useCache属性，为true
* 实体类需要实现 Serializable接口
* 测试

验证了二级缓存是跨sqlSession的，两个sqlSession共享了二级缓存区域，因为都是对同一个namespace 进行操作



### 2.3.3 分析

 	二级缓存是一个mapper映射级别的缓存，当多个sqlSession去操作同一个Mapper映射的sql语句时，多个sqlSession可以共用二级缓存，二级缓存是跨SqlSession的。



* 当前如果多个SqlSession都对同一个UserMapper进行操作的话，sqlSession1会先执行查询。
* 第一次查询去查询二级缓存，二级缓存中没有内容，所以会去查询数据库。
* 查询完数据库后把查询结果放到一级缓存中
* 当sqlSession1执行完close后，查询结果就会被写入到二级缓存中
* 当sqlSession2再去执行查询时，直接去读取二级缓存中的内容，不会发送sql语句
* 当有sqlSession 对当前namespace执行了事务操作，会清空该Mapper的二级缓存



```markdown
1. 映射语句文件中的所有select语句将会被缓存

2. 映射语句文件中的所有insert、update和delete语句会刷新缓存
```



### 2.3.4 注意问题（脏读）

​	mybatis的二级缓存因为是**namespace级别，所以当开启了二级缓存，在进行多表查询时会产生脏读问题**

​	**建议**：**不要使用Mybatis的二级缓存**

​	**实际开发中，会使用Redis来做第三方缓存！！**



## 2.4 小结

```markdown
1. mybatis的缓存，都不需要我们手动存储和获取数据。mybatis会自动维护

2. 在mybatis手动开启二级缓存后，那么查询顺序：二级缓存 --》一级缓存 --》数据库
	而从数据库查询出数据后，也是首先放到一级缓存中，当执行sqlSession.close或commit后，一级缓存的信息才会刷新到二级缓存中
	
3. 注意：mybatis的二级缓存会存在脏读问题。主要是因为级别是namespace级别，所以当多表查询时，会产生脏读。	所以需要使用第三方缓存技术（redis)来解决缓存问题。
```







# 三、Mybatis注解

## 3.1 Mybatis常用注解

​	Mybatis也可以使用注解开发方式，这样就可以不用编写Mapper映射文件了。

```markdown
* 完成对表的一些增删改查操作，代替了xml映射的标签
* 对表进行基本的CRUD

* @Insert：实现新增，代替<insert></insert>

* @Delete：实现删除，代替<delete></delete>

* @Update：实现更新，代替<update></update>

* @Select：实现查询，代替<select></select>


* 完成多表的复杂映射的

* @Result：实现结果集封装，代替了<result></result>

* @Results：可以与@Result 一起使用，封装多个结果集，代替了<resultMap></resultMap>

* @One：实现一对一结果集封装，代替了<association></association>

* @Many：实现一对多结果集封装，代替了<collection></collection>
```



## 3.2 Mybatis注解的单表的增删改查（重点）

### 3.2.1 创建UserMapper接口

```java
public interface UserMapper {

    /*
        查询用户
            注解中属性只有一个值，且是value的话，可以省略不写
     */
    @Select("select * from user")
    public List<User> findAll();


    /*
        添加用户
     */
    @Insert("insert into user(username,birthday,sex,address) values(#{username},#{birthday},#{sex},#{address})")
    public void save(User user);

    /*
        更新用户
     */
    @Update("update user set username = #{username},birthday=#{birthday} where id = #{id}")
    public void update(User user);

    /*
        删除用户
     */
    @Delete("delete from user where id = #{id}")
    public void delete(Integer id);
}
```



### 3.2.2 编写核心配置文件

```xml
<!--我们使用了注解替代的映射文件，所以我们只需要加载使用了注解的Mapper接口即可-->
<mappers>
  <!--扫描使用注解的Mapper类-->
  <mapper class="com.lagou.mapper.UserMapper"></mapper>
</mappers>

<!--或者指定扫描包含映射关系的接口所在的包也可以-->
<mappers>
  <!--扫描使用注解的Mapper类所在的包-->
  <package name="com.lagou.mapper"></package>
</mappers>
```



### 3.2.3 测试

```java
public class MybatisTest {

    private SqlSessionFactory sqlSessionFactory;
    private SqlSession sqlSession;

    //在 @Test方法标注的方法执行之前来执行
    // 被before所标注的方法最先执行
    @Before
    public void before() throws IOException {
        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");

        sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);

        sqlSession = sqlSessionFactory.openSession();
    }

    // after所标注的方法最后执行
    @After
    public void after(){

        sqlSession.commit();
        sqlSession.close();
    }

    /*
        测试查询方法
     */
    /*
        其次是被test标注的方法
     */
    @Test
    public void testSelect() throws IOException {


        // 业务调用
        // 获取返回的代理对象
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);

        List<User> all = mapper.findAll();

        for (User user : all) {
            System.out.println(user);
        }
    }

    /*
        测试添加方法
     */
    @Test
    public void testInsert(){

        UserMapper mapper = sqlSession.getMapper(UserMapper.class);

        User user = new User();
        user.setUsername("周淑怡");
        user.setSex("女");
        user.setBirthday(new Date());
        user.setAddress("上海");

        mapper.save(user);
    }

    /*
        测试更新方法
     */
    @Test
    public void testUpdate(){

        UserMapper mapper = sqlSession.getMapper(UserMapper.class);

        User user = new User();
        user.setUsername("唐嫣真美");
        user.setBirthday(new Date());
        user.setId(8);

        mapper.update(user);
    }


    /*
        测试删除方法
     */
    @Test
    public void testDelete(){

        UserMapper mapper = sqlSession.getMapper(UserMapper.class);

        mapper.delete(8);
    }
}
```



## 3.3 使用注解实现复杂映射开发

​	之前在xml映射文件中通过配置<resultMap>、<association>、<collection>标签 来实现 复杂关系映射配置

​	使用注解开发后，我们可以使用 **@Results、@Result、@One、@Many** 这四个注解可以替换掉以前在xml写的标签。 **使用它们组合完成复杂关系的配置**。



| 注解          | 说明                                                         |
| ------------- | ------------------------------------------------------------ |
| @Results      | 代替了标签<resultMap>。该注解可以使用单个@Result注解，也可以使用@Result集合。使用格式：@Results({@Result(),@Result()}) 或 @Results(@Result()) |
| @Result       | 代替了<resultMap>标签里面配置的 <id>和<result>子标签。借助了@Result配置了实体中属性与表中字段的映射关系。<br>@Result中属性介绍：column：数据库的列名，字段名<br> property：实体中属性名 <br>one：需要使用的@One 注解(@Result(one = @One) ()) <br>many：需要使用的@Many 注解(@Result(many = @Many) ()) |
| @One(一对一)  | 代替了<association> 标签，是多表查询的关键。 **在注解中用来指定子查询返回单一对象**。 <br>@One注解属性介绍：<br>select：指定用来多表查询的 sqlmapper <br>使用格式：@Result(column = " ", property = " ",one = @One(select = " ")) |
| @Many(一对多) | 代替了 <collection>标签，是多表查询的关键。 **在注解中用来指定子查询返回对象集合**。 <br>使用格式：@Result(property = " ", column = " ", many = @Many(select = " ")) |



## 3.4 使用注解方式完成一对一查询

### 3.4.1 介绍

​	**需求：**从订单角度出发，查询一个订单，同时查询出该订单所属的用户信息

**一对一查询语句**

使用注解方式进行多表查询时，其实还是把多表操作拆分成多次对单表的操作

```sql
-- 先查询单表信息
select * from orders;

-- 再根据主外键关系，查询该订单所属的用户信息
select * from user where id = #{订单的uid};
```



### 3.4.2 代码实现

1）OrderMapper接口

```java
public interface OrderMapper {

    /*
        查询所有订单，同时查询订单所属的用户信息
     */

    /*
        先查询出所有的订单信息，再根据订单信息查询出对应的用户信息
     */
    /*
        使用注解方式完成复杂映射开发一对一查询，从订单角度出发，立即加载
     */

    @Select("select * from orders")
    @Results({  // 代替的就是ResultMap标签
            // 完成实体中属性与表中字段的映射关系配置
            // property当前实体中的属性名    id = true  表示当前配置Result注解为主键字段
            @Result(property = "id",column = "id",id = true),
            @Result(property = "ordertime",column = "ordertime"),
            @Result(property = "total",column = "total"),
            @Result(property = "uid",column = "uid"),
                // property = "user" 表示要配置orders实体里的user对象的映射关系
                // javaType = ""    表示当前要去封装的user对象的类型
                // one = @One(select = "namespace.id")  通过namespace.id 来定位到要执行的SQL
                // com.lagou.mapper.UserMapper.findById  通过这个namespace.id 就可以找到对应的SQL 执行
        		// column = "uid" ：把uid当做参数传递
                // fetchType = FetchType.EAGER
                // 一对一采用立即加载
                // 通过这个属性 可以进行局部延迟加载配置，  配置FetchType.EAGER，表示当前查询为立即加载，不进行延迟加载局部优先    这样就会查询出订单信息及关联的用户信息
                @Result(property = "user", javaType = User.class, column = "uid",one = @One(select = "com.lagou.mapper.UserMapper.findById",fetchType = FetchType.EAGER))
    })
    public List<Orders> findAllWithUser();
}
```



2）UserMapper接口

```java
/*
        根据id查询用户
     */
    /*
        根据传递过来的uid查询出关联的用户信息
     */
    @Select("select * from user where id = #{uid}")
    public User findById(Integer uid);
```



3）测试

```java
/*
        一对一查询（注解方式）

     */
    @Test
    public void testOneToOne(){

        OrderMapper mapper = sqlSession.getMapper(OrderMapper.class);

        List<Orders> allWithUser = mapper.findAllWithUser();

        for (Orders orders : allWithUser) {
            System.out.println(allWithUser);
        }
    }
```





## 3.5 使用注解方式完成一对多查询

### 3.5.1 介绍

 **需求：**从用户角度出发，查询一个用户，同时查询出该用户所具有的订单信息



**一对多查询语句**

```sql
-- 先把user用户表的记录查出来
select * from user;

-- 查询出用户信息后，借助查询出来的用户id 从而再查询出该用户所具有的订单信息
select * from orders where uid = #{用户id}
```



### 3.5.2 代码实现

1）UserMapper接口

```java
/*
        查询所有用户，及关联的订单信息
     */

    /*
        使用注解完成复杂映射开发，一对多查询。从用户角度出发，延迟加载
     */


    /*
        先把用户信息查询出来
     */
    @Select("select * from user")   // 第一次发送的sql语句，查询出用户的全部信息
    /*
        配置SQL语句的执行结果 与当前实体属性的映射关系，
        并且在映射的过程中再次发起查询，查询该用户所关联的订单信息并封装到用户实体中的ordersList属性里
     */
    /*
        配置一对多关系映射
     */
    @Results({
            // id = true，表明当前配置的是主键
            // 完成sql语句的查询结果 与user实体中普通属性的映射关系配置

            // 配置了字段与实体中属性的映射关系
            @Result(property = "id", column = "id",id = true),
            @Result(property = "username", column = "username"),
            @Result(property = "birthday", column = "birthday"),
            @Result(property = "sex", column = "sex"),
            @Result(property = "address", column = "address"),

            // 根据用户的id值 查询出该用户所具有的订单信息，并封装到user实体中的 ordersList属性中
            // 对ordersList进行查询封装

            // column = "id"：表示当前把id值作为参数进行传递，去查询订单信息

            // 查询一对多的，多方
            // many = @Many(select = "namespace.id"):
            // 通过namespace.id 定位到方法 进行执行SQL，SQL需要参数，则借助column属性 把当前查询结果中 id字段值作为参数进行传递
            // fetchType    表示配置延迟加载，不配置就默认是延迟加载。因为在核心配置文件中 开启了全局延迟加载
            // 一对多，多对多通常采用延迟加载，所以现在是一对多查询，就采用延迟加载策略，不用配置 fetchType了

            // 表示当前要封装user实体中的ordersList 属性
            // property = "ordersList" 属性名
            // javaType = List.class  表明该属性的类型
            // column = "id":要传递参数  也就是把哪个字段的值作为参数进行传递
            // many = @Many(select = "com.lagou.mapper.OrderMapper.findOrderByUid") 发起对多的查询
            // 根据select="namespace.id" 的值 就可以定位到要执行的方法
            @Result(property = "ordersList", javaType = List.class,column = "id",many = @Many(select = "com.lagou.mapper.OrderMapper.findOrderByUid"))
    })
    public List<User> findAllWithOrder();
```



2）OrderMapper接口

```java
/*
        根据传递过来的用户id，查询该用户所具有的订单信息，查询到后把List集合封装到user实体中的ordersList 属性中
     */

    @Select("select * from orders where uid = #{uid}")
    public List<Orders> findOrderByUid(Integer uid);
```



3）测试

```java
/*
        一对多查询（注解方式）

     */
    @Test
    public void testOneToMany(){

        UserMapper mapper = sqlSession.getMapper(UserMapper.class);

        List<User> allWithOrder = mapper.findAllWithOrder();

        // 因为实现了延迟加载，所以查询结果只有用户信息，并不会立刻把该用户所具有的的订单信息也查询出来
        for (User user : allWithOrder) {
            System.out.println(user);

            // 获取当前用户所具有的订单信息
            // user.getOrdersList()：表示要用到该用户 所具有的订单信息了
            // 此时就会再次发起查询，根据该用户的id 查询出该用户所具有的订单信息

            // 发起第二次查询，表名 namespace.id 对应的方法要被调用了
            System.out.println(user.getOrdersList());
        }
    }
```





## 3.6 使用注解方式完成多对多查询

### 3.6.1 介绍		用户和角色多对多

**需求：**查询所有用户，同时查询出该用户的所有角色信息



多对多拆分来看，就是由两个一对多组成的。所以配置和一对多差不多，主要就是SQL语句

**多对多查询语句**

```SQL
-- 先查询所有的用户信息
select * from user;

-- 用户和角色是一对多关系，所以当查询到用户信息后。就要让角色表和中间表进行关联查询，条件就是中间表的uid  = 第一条SQL语句查询出的用户id。这样就可以根据用户id，在中间表中找出对应的角色id，再根据据角色id找到角色信息
SELECT * FROM sys_role r INNER JOIN sys_user_role ur ON ur.roleid = r.id WHERE ur.userid = #{用户id}
```



### 3.6.2 代码实现，和一对多差不多

1）UserMapper接口

```java
/*
        查询所有用户及关联的角色信息
     */


    /*
        使用注解完成复杂映射开发，多对多查询。延迟加载
     */

    // 先查询出用户信息
    @Select("select * from user")
    @Results({
            @Result(property = "id", column = "id",id = true),
            @Result(property = "username", column = "username"),
            @Result(property = "birthday", column = "birthday"),
            @Result(property = "sex", column = "sex"),
            @Result(property = "address", column = "address"),

            // 配置user实体中， 根据查询出来的用户id 去关联查询该用户所具备的 角色信息
            // 把角色信息查询出来封装到用户实体中的roleList 属性上

            // column = "id",当前在进行后续的select查询时，所需要传递的参数
            @Result(property = "roleList", javaType = List.class, column = "id", many = @Many(select = "com.lagou.mapper.RoleMapper.findAllByUid"))

    })
    public List<User> findAllWithRole();
```



2）RoleMapper接口

```java
public interface RoleMapper {


    /*
        根据传递过来的用户id，查询该用户所具有的角色信息
     */

    /*
        第二次执行的SQL，角色表和中间表的关联查询。根据用户id 查询出对应的角色信息
     */
    @Select("SELECT * FROM sys_role r INNER JOIN sys_user_role ur ON ur.roleid = r.id WHERE ur.userid = #{uid}")
    public List<Role> findAllByUid(Integer uid);
}
```



3）测试

```java
/*
        多对多查询（注解方式）

     */
    @Test
    public void testManyToMany(){

        UserMapper mapper = sqlSession.getMapper(UserMapper.class);

        List<User> allWithRole = mapper.findAllWithRole();

        // 因为实现了延迟加载，所以查询结果只有用户信息，并不会立刻把该用户所具有的的角色信息也查询出来
        for (User user : allWithRole) {
            System.out.println(user);

            // 获取当前用户所具有的角色信息
            // user.getRoleList()：表示要用到该用户 所具有的角色信息了
            // 此时就会再次发起查询，根据该用户的id 查询出该用户所具有的角色信息

            // 发起第二次查询，表名 namespace.id 对应的方法要被调用了
            System.out.println(user.getRoleList());
        }
    }
```





## 3.7 基于注解的二级缓存

### 3.7.1 配置SqlMapConfig.xml 核心配置文件开启二级缓存的支持

```xml
<settings>
  <!--
因为cacheEnabled的取值默认就为true，所以这一步可以省略不配置。
为true代表开启二级缓存；为false代表不开启二级缓存。
  -->
  <setting name="cacheEnabled" value="true"/>
</settings>
```



### 3.7.2 在Mapper接口中使用注解配置二级缓存

```java
@CacheNamespace	// 配置了二级缓存
public interface userMapper{...}
```



测试

```java
/*
        测试注解实现二级缓存

        基于注解实现二级缓存
     */
    @Test
    public void cacheTest(){

        SqlSession sqlSession1 = sqlSessionFactory.openSession();

        SqlSession sqlSession2 = sqlSessionFactory.openSession();

        UserMapper userMapper1 = sqlSession1.getMapper(UserMapper.class);

        UserMapper userMapper2 = sqlSession2.getMapper(UserMapper.class);

        User user1 = userMapper1.findById(1);

        System.out.println(user1);

        // user1从数据库查询出来后放到一级缓存中，当执行 sqlSession1.close()  就会从一级缓存中把user1刷新到二级缓存中
        // 才能将内容从一级缓存刷新到二级缓存
        sqlSession1.close();

        // 这次查询时查询二级缓存中的内容
        User user2 = userMapper2.findById(1);

        // 从二级缓存中拿出user
        System.out.println(user2);

        sqlSession2.close();

    }
```

----

**如何在Mybatis中基于注解实现二级缓存？**

**在Mapper接口中使用@CacheNamespace 注解配置二级缓存**

---



## 3.8 基于注解实现延迟加载

在不同的场景下要去选择不同的加载策略。

* 一对一查询的加载策略：立即加载
* 一对多，多对多查询的加载策略：延迟加载

**不管是一对一还是一对多，在注解配置中都有fetchType属性**,也就是不管在配置@One还是@Many，里面都有fetchType属性

```markdown
* fetchType = FetchType.LAZY	表示懒加载

* fetchType = FetchType.EAGER	表示立即加载

* fetchType = FetchType.DEFAULT		看核心配置文件中的全局延迟加载的开关，若为true，开启了全局延迟加载，那就是延迟加载。若为false，关闭全局延迟加载，那就是立即加载
```

---

所以基于注解实现延迟加载，其实就是在@One 或者 @Many 注解里 添加fetchType属性，根据不同场景选择不同的策略。一对一通常是立即加载(也就是FetchType.EAGER)，一对多 或者多对多 是延迟加载(也就是FetchType.LAZY)

---



## 3.9 小结

```markdown
* 注解开发和xml配置优劣分析
	1. 注解开发和xml配置相比，从开发效率来说，肯定是注解开发编写更简单，效率更高
    2. 从可维护性来说，注解里的SQL如果要修改，必须要修改源码，会让项目重新打包发布部署。会导致维护成本增加。	如果把SQL写在xml里，因为xml是配置文件，所以修改sql，直接修改xml就行。xml 维护性更强。
    
    
    所以注解和xml 如何使用？
    	可以结合使用，单表操作用注解开发，因为注解效率高。
    	如果是多表操作，复杂映射开发，建议使用xml，因为xml更加灵活
```

