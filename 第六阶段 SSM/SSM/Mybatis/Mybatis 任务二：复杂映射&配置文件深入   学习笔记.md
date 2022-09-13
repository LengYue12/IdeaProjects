# Mybatis 任务二：复杂映射&配置文件深入   学习笔记



# 一、Mybatis高级查询

## 1.1 ResultMap 属性		

建立对象关系映射		**手动配置映射关系，来实现SQL结果 手动封装到实体类中**，主要用在实体类的属性名和表中字段名不一致的情况

```markdown
* resultType	要求：
		如果实体的属性名和表中字段名一致，Mybatis才会将sql语句的查询结果自动封装到实体类中
	
* resultMap		要求：
		如果实体的属性名和表中字段名不一致，可以使用ResultMap 来实现手动封装到实体类中。手动来配置实体类的属性和表中字段的映射关系，手动实现封装。
```



1）编写UserMapper接口

```java
public interface UserMapper {
/*
        查询所有用户
     */
    public List<User> findAllResultMap();
}
```



2）编写UserMapper.xml

```xml
<!--    id:属于标签的唯一标识
        type: 封装后的实体类型-->
    <resultMap id="userResultMap" type="com.lagou.domain.User">
<!--        手动配置映射关系，这个映射关系是可以给多个配置进行引用-->
        
<!--        id：用来配置主键的-->
<!--        property：对应实体中的属性名  column：数据库表的主键字段名-->
        <id property="id" column="id"></id>
<!--        result:对表中普通字段的封装
		column： 表中的字段名
		property：实体中的属性名

-->
        <result property="usernameabc" column="username"></result>
        <result property="birthdayabc" column="birthday"></result>
        <result property="sexabc" column="sex"></result>
        <result property="addressabc" column="address"></result>
    </resultMap>

<!--    查询所有用户-->
<!--    resultMap：手动配置实体属性与表中字段的映射关系，来完成手动封装-->
<!--    表示当前这条sql语句的查询结果要按照id为userResultMap的resultMap这个映射关系来进行封装-->
    <select id="findAllResultMap" resultMap="userResultMap">
        select * from user
    </select>
```



3）代码测试

```java
@Test
    public void test2() throws IOException {

        // 模版代码
        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();

        // 拿到UserMapper接口的代理对象
        // 当前返回的 其实是基于UserMapper所产生的代理对象:底层；JDK动态代理 实际类型：proxy
        // 实现类是由Mybatis框架生成的代理实现类，借助代理实现类调用方法得到的结果
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        List<User> allResultMap = mapper.findAllResultMap();
        for (User user : allResultMap) {
            System.out.println(user);
        }

        // 释放资源
        sqlSession.close();
    }
```





## 1.2 多条件查询（三种）

**要根据多个条件进行组合查询**

**需求**

根据id和username来查询user表





### 1）方式一

使用#{arg0}-#{argn} 	或者	#{param1}-#{paramn}	 这样的形式来获取参数值

UserMapper接口

```java
/*
        多条件查询方式一,根据id和username来查询user表
     */
    public User findByIdAndUsername1(int id,String username);
```



UserMapper.xml

```xml
<!--    多条件查询方式一-->
<!--    #{arg0}表示第一个占位符要引用的是mapper接口方法中第一个形参的值
        #{arg1}表示引用第二个形参的值-->
    <!--    #{param1}表示第一个占位符要引用的是mapper接口方法中第一个形参的值
        #{param2}表示引用第二个形参的值-->

    <select id="findByIdAndUsername1" resultMap="userResultMap">
        <!--   select * from user where id = #{arg0} and username = #{arg1} -->
        select * from user where id = #{param1} and username = #{param2}
    </select>
```



测试

```java
/*
        多条件查询：方式一
     */
    @Test
    public void test3() throws IOException {

        // 模版代码
        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();

        // 拿到UserMapper接口的代理对象
        // 当前返回的 其实是基于UserMapper所产生的代理对象:底层；JDK动态代理 实际类型：proxy
        // 实现类是由Mybatis框架生成的代理实现类，借助代理实现类调用方法得到的结果
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        User user = mapper.findByIdAndUsername1(1, "孙悟空");
        System.out.println(user);


        // 释放资源
        sqlSession.close();
    }
```



### 2）方式二

由于方式一获取参数不够直观

所以方式二		就是使用注解，引入**@Param()**	注解获取参数

UserMapper接口

```java
/*
        多条件查询方式二
     */
    // @Param这个注解，就相当于给int id这个形参的值起一个标识
    // 这样的话，在映射配置文件中sql语句的#{}占位符中的值写@Param注解里起的标识就ok
    public User findByIdAndUsername2(@Param("id") int id, @Param("username") String username);
```



UserMapper.xml

```xml
<!--    多条件查询方式二-->
    <!--    #{id}里面的内容就和mapper接口方法中的@Param这个注解里面写的value值保持一致
        就可以根据@Param这个注解里面的标识来获取到指定参数的值-->
    <select id="findByIdAndUsername2" resultMap="userResultMap">
        select * from user where id = #{id} and username = #{username}
    </select>
```



测试

```java
/*
        多条件查询：方式二
     */
    @Test
    public void test4() throws IOException {

        // 模版代码
        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();

        // 拿到UserMapper接口的代理对象
        // 当前返回的 其实是基于UserMapper所产生的代理对象:底层；JDK动态代理 实际类型：proxy
        // 实现类是由Mybatis框架生成的代理实现类，借助代理实现类调用方法得到的结果
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        User user = mapper.findByIdAndUsername2(1, "孙悟空");
        System.out.println(user);


        // 释放资源
        sqlSession.close();
    }
```



### 3）方式三(推荐)

使用**pojo对象(实体对象)**传递参数

既然进行多参数传递，那么就可以把多个参数封装成一个实体，再把实体当做一个完整的参数进行传递



UserMapper接口

```java
/*
        多条件查询方式三
     */
    public User findByIdAndUsername3(User user);
```



UserMapper.xml

```xml
<!--    多条件查询方式三-->
<!--    sql里的#{}占位符的值要和parameterType当前实体中get方法后面的首字母小写保持一致-->
    <select id="findByIdAndUsername3" resultMap="userResultMap" parameterType="user">
        select * from user where id = #{id} and username = #{usernameabc}
    </select>
```



测试

```java
/*
        多条件查询：方式三
     */
    @Test
    public void test5() throws IOException {

        // 模版代码
        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();

        // 拿到UserMapper接口的代理对象
        // 当前返回的 其实是基于UserMapper所产生的代理对象:底层；JDK动态代理 实际类型：proxy
        // 实现类是由Mybatis框架生成的代理实现类，借助代理实现类调用方法得到的结果
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        User user1 = new User();
        user1.setUsernameabc("孙悟空");
        user1.setId(1);

        User user = mapper.findByIdAndUsername3(user1);
        System.out.println(user);


        // 释放资源
        sqlSession.close();
    }
```





## 1.3 模糊查询

**案列需求**

根据username来进行模糊查询user表



### 1）方式一

UserMapper接口

```java
/*
        模糊查询：方式一
     */
    public List<User> findByUsername(String username);
```



UserMapper.xml

```xml
<!--    模糊查询：方式一-->
<!--    id要和mapper接口中对应的方法名保持一致-->
<!--    由于是模糊查询，返回结果类型是List集合，泛型是user实体类型
        表明模糊查询的结果会有多条记录，每一条记录都要封装成一个user实体
        由于user实体与表中字段不一致，所以要采用resultMap。属性值为配置好的映射配置，id为userResultMap-->

<!--    如果parameterType，传入参数是一个实体类型的话，#{}里面的值是要写当前实体里面的属性名，或当前实体里面get方法后面的首字母小写
        但如果传入参数parameterType为基本数据类型或者是String，且传入的个数只有一个参数的情况下。那么#{}里面的值可以随便写-->


<!--    parameterType是基本类型或者String时，且参数个数只有一个时。那么#{}里面的值是随便写的
            #{}：在mybatis中是占位符，引用参数值的时候，会自动添加''单引号-->
    <select id="findByUsername" resultMap="userResultMap" parameterType="string">
        select * from user where username like #{username}
    </select>
```



测试

```java
/*
        模糊查询：方式一
     */
    @Test
    public void test6() throws IOException {

        // 模版代码
        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();

        // 拿到UserMapper接口的代理对象
        // 当前返回的 其实是基于UserMapper所产生的代理对象:底层；JDK动态代理 实际类型：proxy
        // 实现类是由Mybatis框架生成的代理实现类，借助代理实现类调用方法得到的结果
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);

        // 模糊查询
        List<User> users = mapper.findByUsername("%孙悟%");
        for (User user : users) {
            System.out.println(user);
        }

        // 释放资源
        sqlSession.close();
    }
```



### 2）方式二

UserMapper接口

```java
/*
        模糊查询：方式二
     */
    public List<User> findByUsername2(String username);
```



UserMapper.xml

```xml
<!--    模糊查询：方式二-->
<!--    parameterType是基本类型或者String时，且参数个数只有一个时。那么${}里面的值只能写value，不能随便写
            ${}:是sql原样拼接，也就是在传递参数的时候，传递的是什么，就原样转换，是不会自动添加''单引号的
-->

<!-- 不推荐使用，因为会出现sql注入问题 -->
    <select id="findByUsername2" resultMap="userResultMap" parameterType="string">
        select * from user where username like '${value}'
    </select>
```



测试

```java
/*
        模糊查询：方式二
     */
    @Test
    public void test7() throws IOException {

        // 模版代码
        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();

        // 拿到UserMapper接口的代理对象
        // 当前返回的 其实是基于UserMapper所产生的代理对象:底层；JDK动态代理 实际类型：proxy
        // 实现类是由Mybatis框架生成的代理实现类，借助代理实现类调用方法得到的结果
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);

        List<User> users = mapper.findByUsername2("%孙悟%");
        for (User user : users) {
            System.out.println(user);
        }

        // 释放资源
        sqlSession.close();
    }
```





### 3）${} 与 #{}  的区别(笔试题)

**#{}:表示一个占位符**:

* 通过#{} 可以实现PrepareStatement向占位符中设置值，自动进行java类型(实体中属性的类型)和jdbc类型(表中字段类型)转换，#{}可以有效防止sql注入
* ..      #{} 可以接收简单类型参数值或pojo属性值 (实体中的属性值)
* 如果parameterType传输的是基本数据类型或者String，并且个数只有一个的情况下，#{} 中值随便写
* 如果parameterType，传入参数是一个实体类型的话，#{}里面的值是要写当前实体里面的属性名，或当前实体里面get方法后面的首字母小写





**${}：表示sql的原样拼接**

* 通过${} 可以将parameterType 传入的内容直接拼接在sql中，且不进行jdbc类型转换。会出现sql注入问题
* ${} 可以接收简单类型值或pojo实体属性值
* 如果parameterType传输的是基本数据类型或者String，并且个数只有一个的情况下，${} 中值只能是value
* 如果parameterType，传入参数是一个实体类型的话，${}里面的值是要写当前实体里面的属性名，或当前实体里面get方法后面的首字母小写







# 二、Mybatis映射配置文件深入

## 2.1 返回主键

**应用场景**

​	很多时候有这种需求，就是在向数据库中插入一条记录后，希望立即拿到这条记录在数据库中对应的主键值

### 2.1.1 	useGeneratedKeys属性		有局限性  	返回主键方式一

使用useGeneratedKeys属性来获取插入成功记录后，所对应生成的主键值



UserMapper接口

```java
/*
        添加用户：获取返回主键：方式一
     */
    public void saveUser(User user);
```



UserMapper.xml

```xml
<!--    添加用户：获取返回主键：方式一-->
<!--    若parameterType传递的参数类型为实体类型，#{}里面写的就是该实体里面get方法后面的首字母小写-->

<!--
        useGeneratedKeys:其实就是声明返回主键
            值为true时，sql添加成功后的记录所对应的主键值要返回
        keyProperty：把返回主键的值，封装到实体中哪个属性上。
            当前写的是id，就表示把返回主键的值封装到user实体中的id属性上


        注：只适用于主键自增的数据库，mysql和sqlserver支持，Oracle不支持
 -->
    <insert id="saveUser" parameterType="user" useGeneratedKeys="true" keyProperty="id">
        insert into user(username,birthday,sex,address) values(#{username},#{birthday},#{sex},#{address})
    </insert>
```



测试

```java
/*
        添加用户：返回主键方式一
     */
    @Test
    public void test8() throws IOException {

        // 模版代码
        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();

        // 拿到UserMapper接口的代理对象
        // 当前返回的 其实是基于UserMapper所产生的代理对象:底层；JDK动态代理 实际类型：proxy
        // 实现类是由Mybatis框架生成的代理实现类，借助代理实现类调用方法得到的结果
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);

        User user = new User();
        user.setUsername("潘金莲");
        user.setBirthday(new Date());
        user.setSex("女");
        user.setAddress("水浒");

        // 添加之前id值为null
        System.out.println(user);
        mapper.saveUser(user);

        // 添加后，有id值了，添加成功同时也获取到了id值
        System.out.println(user);

        // 手动提交事务
        sqlSession.commit();
        // 释放资源
        sqlSession.close();
    }
```

---



**注意：只适用于主键自增的数据库，mysql和sqlserver支持，Oracle不支持**

---





### 2.1.2 selectKey标签		返回主键方式二				建议使用

借助selectKey标签 来获取添加成功记录后，所生成的主键值。

**建议使用，因为这种方式的适用范围更广，支持所有类型的数据库**



UserMapper接口

```java
/*
        添加用户：获取返回主键：方式二
     */
    public void saveUser2(User user);
```



UserMapper.xml

```xml
<!--    添加用户：获取返回主键：方式二-->

    <insert id="saveUser2" parameterType="user">
<!--
        selectKey标签：适用范围更广，支持所有类型的数据库的

            order="AFTER"：表示设置在sql语句执行前（后），执行此语句，mysql和sqlsever支持主键自增的数据库 写AFTER
	Oracle数据库 写BEFORE

            keyColumn="id":表示指定主键对应的列名，就是主键所对应的字段是哪个字段

            keyProperty="id":把返回主键的值，封装到实体中哪个属性上.写id就会把返回主键的值封装user实体中id属性上

            resultType="int":指定主键类型
 -->
        <selectKey order="AFTER" keyColumn="id" keyProperty="id" resultType="int">
            <!-- mysql内置函数，可以获取到最后一次添加记录所生成的主键值 -->
            SELECT LAST_INSERT_ID();
        </selectKey>
        insert into user(username,birthday,sex,address) values(#{username},#{birthday},#{sex},#{address})
    </insert>
```



测试

```java
/*
        添加用户：返回主键方式二
     */
    @Test
    public void test9() throws IOException {

        // 模版代码
        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();

        // 拿到UserMapper接口的代理对象
        // 当前返回的 其实是基于UserMapper所产生的代理对象:底层；JDK动态代理 实际类型：proxy
        // 实现类是由Mybatis框架生成的代理实现类，借助代理实现类调用方法得到的结果
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);

        User user = new User();
        user.setUsername("唐嫣");
        user.setBirthday(new Date());
        user.setSex("女");
        user.setAddress("北京");

        // 添加之前 id为null
        System.out.println(user);
        mapper.saveUser2(user);

        // 添加数据后  借助selectKey 这种方式 也获取到了添加成功记录后，所返回的主键值
        System.out.println(user);

        // 手动提交事务
        sqlSession.commit();
        // 释放资源
        sqlSession.close();
    }
```



## 2.2.1 动态SQL 之 	if标签

动态SQL**就是当前要去执行的SQL是需要动态拼接的**，

---



**当在	做一些多条件查询 的需求的时候经常用到if标签**

---



**需求**：

​	根据id和username查询，但是不确定两个都有值   

​	参数个数不确定的情况。所以使用if标签来解决多条件查询问题



**<if>标签就用在多条件查询中，并且不确定参数的情况**



UserMapper接口

```java
/*
        动态sql的if标签：多条件查询
     */
    public List<User> findByIdAndUsernameIf(User user);
```



UserMapper.xml映射

```xml
<!--    动态sql之if：多条件查询-->
<!--    id和mapper接口的方法名保持一致-->
<!--    #{}里面的值就是传递过来的实体中的属性名-->
    <select id="findByIdAndUsernameIf" parameterType="user" resultType="user">
        select * from user
        <!-- test 里面写的就是表达式
            id != null  id就是传递过来的实体对象里面的属性名
             <where>标签：相当于where 1=1，但是如果没有条件的话，不会拼接上where关键字
             如果where标签里面有条件需要拼接，就会在sql语句中自动添加where
             如果没有条件需要拼接，就不会在sql中添加where
            -->
        <where>
            <if test="id != null">
                and id = #{id}
            </if>
            <if test="username != null">
                and username = #{username}
            </if>
        </where>
    </select>
```



测试

```java
/*
        动态sql之if：多条件查询
     */
    @Test
    public void test10() throws IOException {

        // 模版代码
        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();

        // 拿到UserMapper接口的代理对象
        // 当前返回的 其实是基于UserMapper所产生的代理对象:底层；JDK动态代理 实际类型：proxy
        // 实现类是由Mybatis框架生成的代理实现类，借助代理实现类调用方法得到的结果
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);

        User user = new User();
        user.setUsername("唐嫣");
        user.setId(8);

        List<User> users = mapper.findByIdAndUsernameIf(user);
        for (User user1 : users) {
            System.out.println(user1);
        }
        // 释放资源
        sqlSession.close();
    }
```





## 2.2.2 动态SQL之 	set标签

**借助set标签 	就是来实现动态更新数据的**

**需求**

​	动态更新user表数据，如果该属性有值就更新，没有值不做处理



UserMapper接口

```java
/*
        动态sql的set标签：动态更新
     */
    public void updateIf(User user);
```



UserMapper.xml

```xml
<!--    动态sql之set：动态更新-->
    <update id="updateIf" parameterType="user">
        update user
        <!-- <set>：在更新的时候，会自动添加set关键字,还会去掉最后一个条件的逗号 -->
        <set>
            <if test="username != null">
                username = #{username},
            </if>
            <if test="birthday != null">
                birthday = #{birthday},
            </if>
            <if test="sex != null">
                sex = #{sex},
            </if>
            <if test="address != null">
                address = #{address},
            </if>

        </set>
            where id = #{id}
    </update>
```



测试

```java
/*
        动态sql之set：动态更新
     */
    @Test
    public void test11() throws IOException {

        // 模版代码
        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();

        // 拿到UserMapper接口的代理对象
        // 当前返回的 其实是基于UserMapper所产生的代理对象:底层；JDK动态代理 实际类型：proxy
        // 实现类是由Mybatis框架生成的代理实现类，借助代理实现类调用方法得到的结果
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);

        User user = new User();
        user.setUsername("超级赛亚人孙悟空");
        user.setAddress("地球");
        user.setId(1);

        mapper.updateIf(user);

        // 手动提交事务
        sqlSession.commit();
        // 释放资源
        sqlSession.close();
    }
```





## 2.2.3 动态SQL之 	foreach 标签

**foreach 主要是用来做数据的循环遍历**

例如：select * from user where id in(1,2,3)		当前传入的参数部分要依靠 foreach遍历才能实现



借助foreach标签去 实现多值查询



```markdown
* <foreach> 标签用于遍历集合，它的属性：
	
	collection：代表要遍历的集合元素
	
	open：代表语句的开始部分
	
	close：代表结束部分
	
	item：代表遍历集合的每个元素，生成的变量名
	
	sperator：代表分隔符
```



### a）当传递参数 容器类型是 集合时，foreach的使用

UserMapper接口

```java
/*
        动态sql的foreach标签：多值查询：集合
        根据多个id进行查询
     */
    public List<User> findByList(List<Integer> ids);
```



UserMapper.xml 映射

```xml
<!-- 
		如果查询条件为普通类型 List集合，collection属性值为：collection 或者 list 
-->






<!--    动态sql的foreach标签：多值查询：根据多个id值查询记录-->
    <select id="findByList" parameterType="list" resultType="user">
<!-- 使用foreach标签去遍历传递过来的list集合，动态取出里面的值，完成sql的动态拼接 -->
<!-- include标签来进行引用sql片段
     refid表示当前要去引用的id标识为selectUser所对应的sql语句-->
        <include refid="selectUser"/>
        <where>
        <!--
        foreach:就是对传递过来的list集合进行遍历,同时进行sql的动态拼接。item就是当前正在遍历中的每个元素

            collection:代表要遍历的集合元素，只要传递过来的集合的泛型是基本数据类型或String的话
            通常写collection或者list
            open:代表语句的开始部分
            close:代表语句的结束部分
            item:代表遍历集合中的每个元素，所生成的变量名
            separator:分隔符
        -->
            <foreach collection="list" open="id in (" close=")" item="id" separator=",">
                #{id}
            </foreach>
        </where>
    </select>
```



测试

```java
/*
        动态sql之foreach：多值查询：集合
     */
    @Test
    public void test12() throws IOException {

        // 模版代码
        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();

        // 拿到UserMapper接口的代理对象
        // 当前返回的 其实是基于UserMapper所产生的代理对象:底层；JDK动态代理 实际类型：proxy
        // 实现类是由Mybatis框架生成的代理实现类，借助代理实现类调用方法得到的结果
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);

        List<Integer> ids = new ArrayList<>();
        ids.add(1);
        ids.add(6);
        ids.add(2);

        List<User> users = mapper.findByList(ids);
        for (User user : users) {
            System.out.println(user);
        }

        // 释放资源
        sqlSession.close();
    }
```



### b）当传递参数 容器类型是 数组时，foreach的使用

UserMapper接口

```java
/*
        动态sql的foreach标签：多值查询：数组
     */
    public List<User> findByArray(Integer[] ids);
```



UserMapper.xml

```xml
<!-- 
	如果查询条件为普通类型  Array数组，collection属性值为：array 
-->





<!--    动态sql的foreach标签：多值查询：根据多个id值查询记录-->
    <select id="findByArray" parameterType="int" resultType="user">
        <!-- 使用foreach标签去遍历传递过来的array数组，动态取出里面的值，完成sql的动态拼接 -->
        <!-- 引入sql片段 -->
        <include refid="selectUser"/>
        <where>
            <!--
            foreach:就是对传递过来的array数组进行遍历,同时进行sql的动态拼接。item就是当前正在遍历中的每个元素
                collection:代表要遍历数组元素，写array就ok
                open:代表语句的开始部分
                close:代表语句的结束部分
                item:代表遍历数组中的每个元素，所生成的变量名
                separator:分隔符
            -->
            <foreach collection="array" open="id in (" close=")" item="id" separator=",">
                #{id}
            </foreach>
        </where>
    </select>
</mapper>
```



测试

```java
/*
        动态sql之foreach：多值查询：数组形式
     */
    @Test
    public void test13() throws IOException {

        // 模版代码
        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();

        // 拿到UserMapper接口的代理对象
        // 当前返回的 其实是基于UserMapper所产生的代理对象:底层；JDK动态代理 实际类型：proxy
        // 实现类是由Mybatis框架生成的代理实现类，借助代理实现类调用方法得到的结果
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);

        Integer[] ids = {2,5,8};

        List<User> byArray = mapper.findByArray(ids);
        for (User user : byArray) {
            System.out.println(user);
        }

        // 释放资源
        sqlSession.close();
    }
```





## 2.3 SQL片段抽取		

**应用场景**：

映射文件中**可将重复的 sql 提取出来**，**使用 时 用 include 引用，最终达到 sql  重复使用的目的**



UserMapper.xml

```xml
<!--    抽取的sql片段-->
    <sql id="selectUser">
<!-- 抽取出的重复的sql语句 -->
        select * from user
    </sql>



<select id="findByList" parameterType="list" resultType="user">
<!-- include标签来进行引用sql片段
     refid表示当前要去引用的id标识为selectUser所对应的sql语句-->
        <include refid="selectUser"/>
        <where>
            <foreach collection="list" open="id in (" close=")" item="id" separator=",">
                #{id}
            </foreach>
        </where>
    </select>



    <select id="findByArray" parameterType="int" resultType="user">
        <!-- 引入sql片段 -->
        <include refid="selectUser"/>
        <where>
            <foreach collection="array" open="id in (" close=")" item="id" separator=",">
                #{id}
            </foreach>
        </where>
    </select>
```





## 2.4 知识小结

Mybatis映射配置文件 中 的标签配置

```xml
<select>:查询
    
<insert>：插入
    
<update>：修改
    
<delete>：删除
    
    
<selectKey>：返回主键  用到的标签
    
    
<where>：where条件，会自动在sql语句 中添加where关键字，同时去掉第一个and
    

<if>：if判断
    

<foreach>:for循环，用来进行循环遍历的 
    
    
<set>：实现动态更新，会自动在sql语句 中添加set关键字，同时去掉最后一个条件后面的逗号
    
 
<sql>：完成sql片段抽取的，一些重复的sql  可以借助sql标签来进行sql片段抽取，可以使sql重复使用
```







# 三、Mybatis核心配置文件深入

## 3.1 plugins标签

使用Mybatis整合第三方的插件来对功能进行扩展。分页助手**PageHelper** 是将分页的复杂操作进行了封装，使用简单的即可获得分页的相关的数据

---

使用分页助手，开发更高效



开发步骤：

1. **在pom.xml中 导入通用PageHelper的坐标**
2. **在Mybatis核心配置文件中借助plugins 标签 来配置PageHelper插件**
3. 测试，获取分页数据



### 1. 导入通用PageHelper坐标

```xml
<!--        分页助手-->
        <dependency>
            <groupId>com.github.pagehelper</groupId>
            <artifactId>pagehelper</artifactId>
            <version>3.7.5</version>
        </dependency>

        <dependency>
            <groupId>com.github.jsqlparser</groupId>
            <artifactId>jsqlparser</artifactId>
            <version>0.9.1</version>
        </dependency>
```



### 2. 在Mybatis核心配置文件中配置PageHelper插件

```xml
<!-- plugins标签要在properties标签和typeAliases标签的后面，environments标签的前面 -->

<!--    配置分页助手的插件-->
    <plugins>
        <!-- interceptor:PageHelper的全路径 -->
        <plugin interceptor="com.github.pagehelper.PageHelper">

            <!--      dialect:指定方言 进行分页的关键字：limit
                        指明当前数据库是mysql，支持mysql 特有的方言-->
            <property name="dialect" value="mysql"/>
        </plugin>
    </plugins>
```



### 3. 测试，分页代码的实现

```java
/*
        核心配置文件深入：plugins标签：整合pageHelper插件
     */
    @Test
    public void test14() throws IOException {

        // 模版代码
        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();

        // 拿到UserMapper接口的代理对象
        // 当前返回的 其实是基于UserMapper所产生的代理对象:底层；JDK动态代理 实际类型：proxy
        // 实现类是由Mybatis框架生成的代理实现类，借助代理实现类调用方法得到的结果
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);

       
        // 设置分页参数
        // 这个方法要写在调用数据库方法的上面，中间不要加代码
        // 参数1：当前页      就是当前要显示第几页的数据
        // 参数2：每页显示的条数
        PageHelper.startPage(1,2);

        List<User> users = mapper.findAllResultMap();
        for (User user : users) {
            System.out.println(user);
        }

        // 获取分页相关的其他参数
        // 把分页数据List集合当做参数传递，PageInfo的有参构造 就可以根据分页数据计算出总条数和总页数 等等数据。封装到PageInfo对象中
        
        PageInfo<User> pageInfo = new PageInfo<User>(users);
        System.out.println("总条数：" + pageInfo.getTotal());   // 总条数：7
        System.out.println("总页数：" + pageInfo.getPages());   // 总页数：4
        System.out.println("判断当前是否是第一页：" + pageInfo.isIsFirstPage());   // 判断当前是否是第一页：true

        // 释放资源
        sqlSession.close();
    }
```



## 3.2 知识小结

Mybatis核心配置文件常用标签：

1. properties标签：该标签可以加载外部的properties文件
2. typeAliases标签：设置类型别名
3. environments标签：数据源环境配置标签
4. plugins标签：配置Mybatis的插件。    
   1. 可以借助这个标签，去完成自定义插件配置，也可以去整合第三方的插件







# 四、Mybatis多表查询

## 4.1 数据库表关系介绍

**关系型数据库表关系分为**：

```markdown
* 一对一

* 一对多

* 多对多
```

例

```markdown
* 人和身份证好就是一对一

* 用户和订单就是一对多，订单和用户就是多对一
	一个用户可以下多个订单
	多个订单属于同一个用户
	
	
* 学生和课程就是多对多
	一个学生可以选择多个课程
	一个课程可以被多个学生选择
	

* 特例
	mybatis就会将多对一看成一对一。
	在数据层面来看，一个订单只属于一个用户，就是一对一
	所以mybatis就把多对一这种关系看出一对一
```



案列环境准备，创建表

```sql
DROP TABLE IF EXISTS `orders`;
CREATE TABLE `orders` (
`id` INT(11) NOT NULL AUTO_INCREMENT,
`ordertime` VARCHAR(255) DEFAULT NULL,
`total` DOUBLE DEFAULT NULL,
`uid` INT(11) DEFAULT NULL,
PRIMARY KEY (`id`),
KEY `uid` (`uid`),
CONSTRAINT `orders_ibfk_1` FOREIGN KEY (`uid`) REFERENCES `user` (`id`)
) ENGINE=INNODB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
-- ----------------------------
-- Records of orders
-- ----------------------------
INSERT INTO `orders` VALUES ('1', '2020-12-12', '3000', '1');
INSERT INTO `orders` VALUES ('2', '2020-12-12', '4000', '1');
INSERT INTO `orders` VALUES ('3', '2020-12-12', '5000', '2');
-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
`id` INT(11) NOT NULL AUTO_INCREMENT,
`rolename` VARCHAR(255) DEFAULT NULL,
`roleDesc` VARCHAR(255) DEFAULT NULL,
PRIMARY KEY (`id`)
) ENGINE=INNODB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES ('1', 'CTO', 'CTO');
INSERT INTO `sys_role` VALUES ('2', 'CEO', 'CEO');
-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
`userid` INT(11) NOT NULL,
`roleid` INT(11) NOT NULL,
PRIMARY KEY (`userid`,`roleid`),
KEY `roleid` (`roleid`),
CONSTRAINT `sys_user_role_ibfk_1` FOREIGN KEY (`userid`) REFERENCES `user` (`id`),
CONSTRAINT `sys_user_role_ibfk_2` FOREIGN KEY (`roleid`) REFERENCES `sys_role` (`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8;
-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES ('1', '1');
INSERT INTO `sys_user_role` VALUES ('2', '1');
INSERT INTO `sys_user_role` VALUES ('1', '2');
INSERT INTO `sys_user_role` VALUES ('2', '2');
```





## 4.2 一对一（多对一）

### 4.2.1 介绍

**一对一查询模型**

用户表和订单表的关系为，一个用户可以有多个订单（在用户角度来看就是一对多），一个订单只属于一个用户（而在订单角度来看就是一对一）。

**订单表的外键指向用户表的主键**



一对一关联查询的需求：从订单的角度出发，查询所有订单，同时查询出每个订单所属的用户



**一对一查询语句**	准备sql

```sql
-- 查询所有订单，与此同时查询出每个订单所属的用户信息
-- 因为当前要查询所有订单
-- 不管订单信息与用户有没有关联关系，当前都要把所有状态的订单查出来，所以要使用左外连接

SELECT * FROM orders o LEFT JOIN USER u ON o.`uid` = u.`id`
```





### 4.2.2 代码实现

1）Orders实体

```java
/**
 * 在多方实体中怎么表示一方？
 *      在多方实体中创建一个一方实体的对象作为属性
 *          在多方实体表示多对一关系时，用到实体对象。
 *          那么在映射配置文件中就使用<resultMap>和<association>标签来做配置
 */
public class Orders {

    // 一个实体对象就对应查询结果中的一条记录
    
    // Orders信息
    private Integer id;
    private String ordertime;
    private Double total;
    private Integer uid;

    // 在多方实体中如果想表示一方关系  就用一方的实体对象作为属性

    // 表示当前订单属于哪个用户 association
    
    // 一条订单记录所对应的user信息，可以封装到Orders实体里面的user对象中
    private User user;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrdertime() {
        return ordertime;
    }

    public void setOrdertime(String ordertime) {
        this.ordertime = ordertime;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    @Override
    public String toString() {
        return "Orders{" +
                "id=" + id +
                ", ordertime='" + ordertime + '\'' +
                ", total=" + total +
                ", uid=" + uid +
                ", user=" + user +
                '}';
    }
}
```



2）OrderMapper接口

```java
/*
        多表查询的方式
        一对一 关联查询：查询所有订单，与此同时还要查询出每个订单所属的用户信息
     */

    public List<Orders> findAllWithUser();
```



3）OrderMapper.xml映射

**如果实体中表示对方关系，用到的是实体对象。那么在编写映射配置文件时，要用到association标签**

```xml
<!--    一对一 关联查询：查询所有订单，与此同时还要查询出每个订单所属的用户信息-->
<!--    resultType 是由mybatis自动映射封装的
            是根据字段名和实体中的属性名进行匹配，从而调用属性的set方法，进行值的设置
            但是连表查询的话就不能把连接的表的属性封装到对象中的-->

<!--    所以想要完成映射封装那么就要用到resultMap来完成手动映射配置-->
    
    <resultMap id="orderMap" type="orders">
<!--        property：实体中的id属性
            column：表中的id字段-->
        <id property="id" column="id"/>
<!--        result标签：对于普通属性的映射配置-->
        <result property="ordertime" column="ordertime"/>
        <result property="total" column="total"/>
        <result property="uid" column="uid"/>
<!--        要将查询结果的User表记录封装到Orders实体中的user对象上-->

<!--        association:在进行一对一关联查询配置时，使用association标签进行关联
                property="user":要封装实体的属性名
                javaType="com.lagou.domain.User" ：要封装的实体的属性类型-->
        <association property="user" javaType="com.lagou.domain.User">
<!--            配置查询结果中的字段与要封装的实体的属性 的映射关系
                    column="uid":因为主表和从表的主外键一致，所以可以使用从表的外键字段来代替主表的主键字段-->
            <id property="id" column="uid"/>
            <result property="username" column="username"></result>
            <result property="birthday" column="birthday"></result>
            <result property="sex" column="sex"></result>
            <result property="address" column="address"></result>
        </association>
    </resultMap>

<!--    要在select标签上指明sql语句的查询结果要按照手动的映射配置来进行封装-->
    <select id="findAllWithUser" resultMap="orderMap">
        SELECT * FROM orders o LEFT JOIN USER u ON o.`uid` = u.`id`
    </select>
```



4）测试

```java
/*
        一对一 关联查询：查询所有订单，与此同时还要查询出每个订单所属的用户信息
     */

    @Test
    public void test1() throws IOException {

        // 模版代码
        // 加载核心配置文件
        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");

        // 获取SqlSessionFactory工厂对象
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);

        // 获取sqlSession会话对象
        SqlSession sqlSession = sqlSessionFactory.openSession();

        // 获取接口的代理对象
        OrderMapper mapper = sqlSession.getMapper(OrderMapper.class);

        // 调用方法
        List<Orders> orders = mapper.findAllWithUser();

        for (Orders order : orders) {
            // 所有的订单信息
            System.out.println(order);
        }

        // 关闭资源
        sqlSession.close();
    }
```





## 4.3 一对多

### 4.3.1 介绍

**一对多查询模型**

用户表和订单表的关系为，一个用户可以有多个订单（在用户角度来看就是一对多），一个订单只属于一个用户（而在订单角度来看就是一对一）。

**订单表的外键指向用户表的主键**



一对多关联查询的需求：从用户的角度出发，查询所有用户，同时查询出每个用户具有的订单信息



**一对多查询SQL语句**

```sql
-- 一对多查询的需求，查询所有用户，与此同时查询出该用户所具有的订单信息
SELECT u.*,o.id oid, o.`ordertime`,o.`total`,o.`uid` FROM orders o RIGHT JOIN USER u ON o.`uid` = u.`id`
```



### 4.3.2 代码实现

1）User实体

```java
/**
 * 在一方实体中怎么表示多方？
 *      在一方实体中创建一个集合，泛型是多方实体的类型
 *          在一方实体表示一对多关系时，用到集合。
 *          那么在映射配置文件中就使用<resultMap>和<collection>标签来做配置
 */
public class User {

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

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", birthday=" + birthday +
                ", sex='" + sex + '\'' +
                ", address='" + address + '\'' +
                ", ordersList=" + ordersList +
                ", roleList=" + roleList +
                '}';
    }

    public List<Role> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<Role> roleList) {
        this.roleList = roleList;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<Orders> getOrdersList() {
        return ordersList;
    }

    public void setOrdersList(List<Orders> ordersList) {
        this.ordersList = ordersList;
    }
}
```



2）UserMapper接口

```java
/*
        一对多关联查询:查询所有的用户，同时还要查询出每个用户所关联的订单信息
     */

    public List<User> findAllWithOrder();
```



3）UserMapper.xml 映射

**如果在实体中表示对方关系，用的是集合。那映射配置文件中，用到collection标签**

```xml
<!--    一对多关联查询:查询所有的用户，同时还要查询出每个用户所关联的订单信息-->

<!--    手动编写映射关系      resultMap标签
            sql语句查询出的结果，要把普通字段封装到user实体中的属性上
            把关于orders的一些信息是要封装到user对象里面的list集合里面的每个order上
            所以要配置字段和属性的映射关系-->
<resultMap id="userMap" type="user">
    <id property="id" column="id"/>
    <result property="username" column="username"/>
    <result property="birthday" column="birthday"/>
    <result property="sex" column="sex"/>
    <result property="address" column="address"/>

<!--
    collection标签：一对多使用collection标签进行关联
        property:一对多的实体中的list集合属性名
        ofType：list集合的泛型

        配置user实体中的Order信息
-->
    <collection property="ordersList" ofType="orders">
        <id property="id" column="oid"/>
        <result property="ordertime" column="ordertime"/>
        <result property="total" column="total"/>
        <result property="uid" column="uid"/>
    </collection>
</resultMap>
<!--    给orders表的id起别名-->
    <select id="findAllWithOrder" resultMap="userMap">
        SELECT u.*,o.id oid, o.`ordertime`,o.`total`,o.`uid` FROM orders o RIGHT JOIN USER u ON o.`uid` = u.`id`
    </select>
```



4）测试

```java
/*
        一对多关联查询:查询所有的用户，同时还要查询出每个用户所关联的订单信息
     */

    @Test
    public void test2() throws IOException {

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
        List<User> allWithOrder = mapper.findAllWithOrder();

        for (User user : allWithOrder) {
            System.out.println(user);
        }

        // 关闭资源
        sqlSession.close();
    }
```





## 4.4 多对多

多对多其实就是两个一对多

### 4.4.1 介绍

**多对多查询的模型**

用户表和角色表的关系为：一个用户有多个角色，一个角色可以被多个用户使用



**多对多关系：有一张中间表，中间表有两个字段作为外键分别指向另外两张表的主键**



多对多查询的需求：查询出所有用户同时查询出每个用户所具有的角色。



**多对多查询sql语句**

```sql
-- 多对多查询的需求：查询所有用户同时查询出该用户的所有角色

-- 先做user表和sys_user_role 中间表  的关联查询，查询出用户的角色id

-- 拿到角色id后 再与sys_role 角色表 做关联查询。查询到角色表 具体的角色信息

SELECT u.*,r.id rid,r.rolename,r.roleDesc 
FROM USER u 	-- 用户表
LEFT JOIN sys_user_role ur	 -- 左外连接中间表 
	ON ur.userid = u.`id`
LEFT JOIN sys_role r 		-- 左外连接角色表
	ON ur.roleid = r.id
```





### 4.4.2 代码实现

1）User 和 Role实体

```java
public class User {
private Integer id;
private String username;
private Date birthday;
private String sex;
private String address;
    
// 表示多方关系：集合:roleList     代表了当前用户所具有的角色列表   collection
    private List<Role> roleList;
}

public class Role {
private Integer id;
private String rolename;
private String roleDesc;
}
```



2）UserMapper接口

```java
/*
        多对多关联查询:查询所有的用户，同时还要查询出每个用户所关联的角色信息
     */
    public List<User> findAllWithRole();
```



3）UserMapper.xml 映射

```xml
<!--    多对多关联查询:查询所有的用户，同时还要查询出每个用户所关联的角色信息-->
    
<!--    配置resultMap-->
    <resultMap id="userRoleMap" type="user">
        <id property="id" column="id"/>
        <result property="username" column="username"/>
        <result property="birthday" column="birthday"/>
        <result property="sex" column="sex"/>
        <result property="address" column="address"/>

<!--       
			因为user实体表示角色对方关系 用的是List 集合，所以使用collection标签来 配置roleList 角色信息的映射关系	

			collection标签：要放user实体里面的角色列表信息
                property="roleList":表示要封装user实体里面的roleList属性
                ofType="Role"：写的就是user实体里的roleList 这个集合的泛型-->
        <collection property="roleList" ofType="Role">
            <id property="id" column="rid"/>
            <result property="rolename" column="rolename"/>
            <result property="roleDesc" column="roleDesc"/>
        </collection>

    </resultMap>

<!--    select标签：中的 resultMap="userRoleMap"
            表明SQL语句查询出的结果就按照id为 userRoleMap 的resultMap 映射关系 手动配置进行封装-->
<select id="findAllWithRole" resultMap="userRoleMap">
        SELECT u.*,r.id rid,r.rolename,r.roleDesc FROM USER u LEFT JOIN sys_user_role ur ON ur.userid = u.`id`
		     LEFT JOIN sys_role r ON ur.roleid = r.id
</select>
```



4）测试

```java
/*
        多对多关联查询:查询所有的用户，同时还要查询出每个用户所关联的角色信息
     */

    @Test
    public void test3() throws IOException {

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
        List<User> allWithRole = mapper.findAllWithRole();

        for (User user : allWithRole) {
            System.out.println(user);
        }

        // 关闭资源
        sqlSession.close();
    }
```





## 4.5 小结

**Mybatis多表查询配置方式**

```markdown
* 多对一（一对一）配置：那么在映射配置文件中使用 <resultMap>+<association>做配置
	在多方实体中要表示一方，就在多方实体中创建一方实体对象。在多方实体表示对方关系用到实体对象	
	
	
* 一对多配置：那么在映射配置文件中使用 <resultMap>+<collection> 做配置
	在一方实体中要表示多方，就在一方实体中创建一个集合，泛型是多方的类型。
	
	
* 多对多配置：那么在映射配置文件中使用 <resultMap>+<collection> 做配置
	就是两个一对多，所以在多对多任何一个实体中表示对方关系，都用集合
	

* 多对多配置和一对多配置很相似，区别在于sql
	一对多可能两张表做关联查询
	多对多要加上中间表进行三表联查
```





# 五、Mybatis嵌套查询

## 5.1 什么是嵌套查询

​	嵌套查询就是将原来多表查询中的联合查询语句进行拆分，把联合查询语句拆成对于单个表的查询。最后再使用Mybatis 语法嵌套在一起

---

**拆分，把多表查询的联合查询语句进行拆分，拆成对于单个表的查询**



如：

```markdown
* 需求：查询一个订单，与此同时查询出该订单所属的用户
1. 联合查询
		SELECT * FROM orders o LEFT JOIN USER u ON o.`uid`=u.`id`;
2. 嵌套查询
	2.1 先查询订单
		SELECT * FROM orders
	2.2 再根据订单uid 外键，查询用户
		SELECT * FROM `user` WHERE id = #{根据订单查询的uid}
	2.3 最后使用mybatis，将以上二步嵌套起来
		...
```



## 5.2 一对一嵌套查询

### 5.2.1 介绍

**需求**：查询订单信息，同时查询出订单所属的用户信息



**现在使用嵌套查询的方式来实现需求**



**一对一查询sql语句**

```sql
-- 先查询订单信息
SELECT * FROM orders

-- 再根据订单 uid外键，查询用户信息
SELECT * FROM `user` WHERE id = #{根据订单查询的uid}
```



### 5.2.2 代码实现

1）OrderMapper接口

```java
/*
        嵌套查询的方式
        一对一 嵌套查询：查询所有订单，与此同时还要查询出每个订单所属的用户信息
     */

    public List<Orders> findAllWithUser2();
```



2）OrderMapper.xml 映射

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
        <association property="user" javaType="user" select="com.lagou.mapper.UserMapper.findById" column="uid"/>


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



3）要编写第二条SQL，因为第二条SQL是查询用户信息。	所在写在 	UserMapper接口

```java
/*
        根据id查询用户
     */
    public User findById(Integer id);
```



4）UserMapper.xml 映射

```xml
<!--    第二条要去执行的sql语句，column的uid的值就作为参数进行了传递，放到#{}里面作为实际参数
        查询出结果后，就会封装成一个user对象，最终把user对象放到Orders实体中的user属性上

        根据id查询用户
            只需要根据传过来的id值去查询到具体的用户信息，并不是一个关联查询，所以用resultType
            当传递过来的参数是基本数据类型或者String时，且个数只有一个的情况下。那么不写parameterType也ok
            -->
    <select id="findById" resultType="user" parameterType="int">
        SELECT * FROM user WHERE id = #{id}
    </select>
```



5）测试

```java
/*
        一对一嵌套查询:查询订单及关联的用户信息
     */

    @Test
    public void test4() throws IOException {

        // 模版代码
        // 加载核心配置文件
        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");

        // 获取SqlSessionFactory工厂对象
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);

        // 获取sqlSession会话对象
        SqlSession sqlSession = sqlSessionFactory.openSession();

        // 获取接口的代理对象
        OrderMapper mapper = sqlSession.getMapper(OrderMapper.class);

        // 使用代理对象调用方法
        List<Orders> allWithUser2 = mapper.findAllWithUser2();

        for (Orders orders : allWithUser2) {
            System.out.println(orders);
        }

        // 关闭资源
        sqlSession.close();
    }
```



---

**就是把联合查询拆分成了两次对于数据库的单表操作，对数据库操作了两次，分别的单表操作**

---





## 5.3 一对多嵌套查询

### 5.3.1 介绍

**需求**：从用户的角度出发、查询所有用户信息，同时查询出每个用户所具有的订单信息



**一对多嵌套查询sql语句**

```sql
-- 需求：查询所有用户，与此同时查询出该用户具有的订单

	-- 先查询用户信息
SELECT * FROM USER

	-- 根据用户的id主键，来查询 该用户的 订单信息
SELECT * FROM orders WHERE uid = 1
```



### 5.3.2 代码实现

1）UserMapper 接口

```java
/*
        一对多嵌套查询：查询所有的用户，同时还要查询出每个用户所关联的订单信息
     */
    public List<User> findAllWithOrder2();
```



2）UserMapper.xml 映射

```xml
<!--   一对多嵌套查询：查询所有的用户，同时还要查询出每个用户所关联的订单信息

        一对多配置：
            使用<resultMap>和<collection>标签做配置，通过column条件来执行select查询
                select 属性是定位到哪条要执行的sql的

 -->

    <resultMap id="userOrderMap" type="user">
        <id property="id" column="id"/>
        <result property="username" column="username"/>
        <result property="birthday" column="birthday"/>
        <result property="sex" column="sex"/>
        <result property="address" column="address"/>

<!--    如何去封装user实体里面的ordersList
        也就是第一条sql执行完毕后，要配置id查询出每个用户所对应的订单信息并封装到user实体里面的ordersList 集合中-->

<!--        所以要用的collection 标签，property 对应属性名
            ofType：集合中泛型的全路径
            column：表示要传递的参数，是当前sql查询出来的哪个字段值
            select：表示要去调用的sql，定位到哪条sql语句进行执行。   值写Statementid     namespace.id
            namespace:接口全路径     id值：和mapper接口的方法名相同-->
        <collection property="ordersList" ofType="orders" column="id" select="com.lagou.mapper.OrderMapper.findByUid"/>

    </resultMap>
    
<!--    先把用户表的信息查询出来-->
    <select id="findAllWithOrder2" resultMap="userOrderMap">
        SELECT * FROM USER
    </select>
```



3）OrderMapper接口

```java
/*
        根据uid查询对应订单
     */
    public List<Orders> findByUid(Integer uid);
```



4）OrderMapper.xml 映射

```xml
<!-- 根据uid查询对应订单 -->
    <select id="findByUid" parameterType="int" resultType="orders">
        SELECT * FROM orders WHERE uid = #{uid}
    </select>
```



5）测试

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
        }

        // 关闭资源
        sqlSession.close();
    }
```



----

**一对多嵌套查询和一对一嵌套查询 在代码实现上基本一样。主要借助两个属性：**

* column：表示要传递的参数是哪个字段的值
* select：表示要定位到哪条SQL语句进行执行



就是SQL语句的区别



**所以要分析需求，如果使用嵌套查询。SQL语句先执行什么，再执行什么，传递参数是哪个字段值**

---



## 5.4 多对多嵌套查询

### 5.4.1 介绍

**需求**：查询用户信息，同时查询出每个用户所具有的角色信息



**多对多嵌套查询 SQL语句**

```sql
-- 需求：查询用户 同时查询出该用户的所有角色信息
	
	-- 先查询用户信息
SELECT * FROM USER

	-- 根据查询出来的用户id，查询角色信息
SELECT * FROM sys_role r INNER JOIN sys_user_role ur ON ur.roleid = r.id 
	WHERE ur.userid = 1
```



### 5.4.2 代码实现

1）UserMapper接口

```java
/*
        多对多嵌套查询：查询所有的用户，同时还要查询出每个用户所关联的角色信息
     */
    public List<User> findAllWithRole2();
```



2）UserMapper.xml 映射

```xml
<resultMap id="userRoleMap2" type="user">
    <id property="id" column="id"/>
    <result property="username" column="username"/>
    <result property="birthday" column="birthday"/>
    <result property="sex" column="sex"/>
    <result property="address" column="address"/>

<!--    是要把查询出来的id值作为参数传递。
        所以使用 column="id"-->

<!--   select:表示要定位到哪条sql去执行
        值为Statementid：
            Statementid就是由namespace的值.id的值-->
    <collection property="roleList" ofType="role" column="id" select="com.lagou.mapper.RoleMapper.findByUid"/>
</resultMap>

<!-- 多对多嵌套查询：查询所有的用户，同时还要查询出每个用户所关联的角色信息

        多对多配置：
            使用<resultMap>和<collection>标签做配置，通过column条件，执行select查询
                select 属性 是用来定位到要执行的哪条sql的属性

 -->
    <select id="findAllWithRole2" resultMap="userRoleMap2">
        SELECT * FROM USER
    </select>
```



3）RoleMapper接口

```java
public interface RoleMapper {

    /*
        根据用户id查询对应角色
     */
    public List<Role> findByUid(Integer uid);

}
```



4）RoleMapper.xml 映射

```xml
<mapper namespace="com.lagou.mapper.RoleMapper">

<!--    第二条sql
        要根据查询出来的用户id，去查询对应的角色信息-->

<!--   当接口方法传递的形参是基本数据类型或者String，并且参数只有一个。那么parameterType 可以不用写 -->
    <select id="findByUid" resultType="role" parameterType="int">
        SELECT * FROM sys_role r INNER JOIN sys_user_role ur ON ur.roleid = r.id
	WHERE ur.userid = #{uid}
    </select>
</mapper>
```



5）测试

```java
/*
        多对多嵌套查询：查询所有的用户，同时还要查询出每个用户所关联的角色信息
     */

    @Test
    public void test6() throws IOException {

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
        List<User> allWithRole2 = mapper.findAllWithRole2();

        for (User user : allWithRole2) {
            System.out.println(user);
        }

        // 关闭资源
        sqlSession.close();
    }
```



---

一对一，一对多和多对多配置 基本一样，区别在SQL

主要是 select属性和column属性

---





## 5.5 嵌套查询小结

```markdown
一对一配置中：使用<resultMap>+<association>做配置，通过column来传递参数，执行对应的select查询

一对多配置：使用<resultMap>+<collection>做配置，通过column条件，执行select查询

多对多配置：使用<resultMap>+<collection>做配置，通过column条件，执行select查询
	区别在于SQL的编写

嵌套查询优点：简化多表查询操作，可以把多表的关联查询变成对单表的操作

缺点：执行多次SQL语句，浪费数据库性能。	
	因为相当于是 对数据库的一次查询拆分成了两次查询，会和数据库交互两次，所以会浪费数据库性能。
```

