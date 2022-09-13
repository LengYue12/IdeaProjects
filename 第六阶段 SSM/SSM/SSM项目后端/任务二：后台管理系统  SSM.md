# 任务二：后台管理系统  SSM



# 广告管理模块

## 一、广告模块功能分析

​	在本任务中，首先完成对后台管理系统 的广告管理模块、广告模块包含了广告位列表查询、添加&修改广告位、回显广告位名称、广告分页查询、图片上传接口、新建&修改广告、回显广告信息、广告状态上下线等接口的编写



### 1.1 课程管理

* 实现以下功能：
  * 广告位列表查询
  * 添加&修改广告位
  * 回显广告位名称
  * 广告分页查询
  * 图片上传接口
  * 新建&修改广告接口
  * 回显广告信息
  * 广告状态上下线





## 二、广告管理模块表设计

### 2.1 创建数据库及表



### 2.2 表关系

广告表和广告位表是多对一关系。一个广告只能在一个广告位上，广告表的外键指向广告位表的主键







## 三、广告管理模块接口实现

## 1.广告位列表查询

### 1.1 需求分析：

需求：点击广告位列表按钮进行广告位列表展示



### 1.2 查看接口文档，进行编码

查看接口文档



#### 实体类：PromotionSpace

```java
public class PromotionSpace {

    private Integer id;
    private String name;
    private String spaceKey;
    private Date createTime;
    private Date updateTime;
    private Integer isDel;

    @Override
    public String toString() {
        return "PromotionSpace{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", spaceKey='" + spaceKey + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", isDel=" + isDel +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpaceKey() {
        return spaceKey;
    }

    public void setSpaceKey(String spaceKey) {
        this.spaceKey = spaceKey;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }
}
```



#### Dao层：PromotionSpaceMapper

```java
public interface PromotionSpaceMapper {

    /*
        获取所有广告位
     */
    public List<PromotionSpace> findAllPromotionSpace();
}
```

```xml
<?xml version="1.0" encoding="utf-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.lagou.dao.PromotionSpaceMapper">

<!--    查询所有的广告位-->
    <select id="findAllPromotionSpace" resultType="PromotionSpace">
        select * from promotion_space
    </select>
</mapper>
```



#### Service层：PromotionSpaceService

```java
public interface PromotionSpaceService {

    /*
        获取所有广告位
     */
    public List<PromotionSpace> findAllPromotionSpace();
}
```

```java
@Service
public class PromotionSpaceServiceImpl implements PromotionSpaceService {

    @Autowired
    private PromotionSpaceMapper promotionSpaceMapper;

    /*
    	调用mapper	
    */
    @Override
    public List<PromotionSpace> findAllPromotionSpace() {
        return promotionSpaceMapper.findAllPromotionSpace();
    }
}
```



#### Web层：PromotionSpaceController

```java
@RestController     // 组合注解
@RequestMapping("/PromotionSpace")
public class PromotionSpaceController {


    @Autowired
    private PromotionSpaceService promotionSpaceService;

    /*
        查询广告位列表
     */
    @RequestMapping("/findAllPromotionSpace")
    public ResponseResult findAllPromotionSpace(){
        List<PromotionSpace> list = promotionSpaceService.findAllPromotionSpace();

        // 转为JSON响应给前台
        return new ResponseResult(true,200,"查询所有广告位信息成功",list);
    }
}
```



#### Postman测试接口





## 2.添加&修改广告位

### 1.1 需求分析

添加：点击添加广告位按钮进行广告位添加

修改：点击编辑按钮，在页面回显广告位名称的基础上，点击提交按钮，进行数据修改



### 2.2 查看接口文档，进行编码

查看接口文档



#### Dao层：PromotionSpaceMapper

```java
/*
        添加广告位
     */
    void savePromotionSpace(PromotionSpace promotionSpace);
/*
        修改广告位名称
     */
    void updatePromotionSpace(PromotionSpace promotionSpace);
```

```xml
<!--    添加广告位-->
    <insert id="savePromotionSpace" parameterType="PromotionSpace">
        insert into promotion_space values(null,#{name},#{spaceKey},#{createTime},#{updateTime},#{isDel})
    </insert>

<!--    更新广告位 void updatePromotionSpace(PromotionSpace promotionSpace);-->
    <update id="updatePromotionSpace" parameterType="PromotionSpace">
        update promotion_space set name = #{name},updateTime = #{updateTime} where id = #{id}
    </update>
```



#### Service层：PromotionSpaceService

```java
/*
        添加广告位
     */
    void savePromotionSpace(PromotionSpace promotionSpace);

/*
        修改广告位名称
     */
    void updatePromotionSpace(PromotionSpace promotionSpace);
```

```java
/*
        添加广告位
     */
    @Override
    public void savePromotionSpace(PromotionSpace promotionSpace) {

        // 1.封装数据
        promotionSpace.setSpaceKey(UUID.randomUUID().toString());
        Date date = new Date();
        promotionSpace.setCreateTime(date);
        promotionSpace.setUpdateTime(date);
        promotionSpace.setIsDel(0);

        // 2.调用mapper方法
       promotionSpaceMapper.savePromotionSpace(promotionSpace);
    }

/*
        修改广告位名称
     */
    @Override
    public void updatePromotionSpace(PromotionSpace promotionSpace) {

        // 封装数据
        promotionSpace.setUpdateTime(new Date());

        // 调用mapper
        promotionSpaceMapper.updatePromotionSpace(promotionSpace);
    }
```





#### Web层：PromotionSpaceController

```java
/*
        添加&修改广告位
     */
    @RequestMapping("/saveOrUpdatePromotionSpace")
    // 前台传递的是JSON，所以使用RequestBody 注解，来获取到请求体中的内容并封装到实体中
    public ResponseResult saveOrUpdatePromotionSpace(@RequestBody PromotionSpace promotionSpace){

        if (promotionSpace.getId() == null) {
            // 新增
            promotionSpaceService.savePromotionSpace(promotionSpace);
            return new ResponseResult(true,200,"新增广告位成功",null);
        } else {
            promotionSpaceService.updatePromotionSpace(promotionSpace);
            return new ResponseResult(true,200,"更新广告位名称成功",null);

        }
        
    }
```



#### Postman测试接口







## 3. 回显广告位名称

### 3.1 需求分析：

需求：当点击广告位的编辑按钮时，会进行广告位名称信息的回显



### 3.2 查看接口文档，进行编码

查看接口文档





#### Dao层：PromotionSpaceMapper

```java

    /*
        根据ID查询广告位信息
     */
    PromotionSpace findPromotionSpaceById(int id);
```

```xml
<!--    根据ID查询对应广告位信息-->
    <select id="findPromotionSpaceById" parameterType="int" resultType="PromotionSpace">
        select id,name from promotion_space where id = #{id}
    </select>
```





#### Service层：PromotionSpaceService

```java
/*
        根据ID查询广告位信息
     */
    PromotionSpace findPromotionSpaceById(int id);
```

```java
/*
        根据ID查询广告位信息
     */
    @Override
    public PromotionSpace findPromotionSpaceById(int id) {
        return promotionSpaceMapper.findPromotionSpaceById(id);
    }
```



#### Web层：PromotionSpaceController

```java
/*
        根据ID查询广告位
     */
    @RequestMapping("/findPromotionSpaceById")
    public ResponseResult findPromotionSpaceById(int id){

        PromotionSpace promotionSpace = promotionSpaceService.findPromotionSpaceById(id);

        return new ResponseResult(true,200,"查询具体广告位成功",promotionSpace);
    }
```



#### Postman测试接口





## 4. 广告分页查询

### 4.1 需求分析：

需求：点击广告列表按钮，对广告信息进行分页列表展示



### 4.2 查看接口文档，进行编码

查看接口文档



#### 实体类：PromotionAd

```java
public class PromotionAd {

    // 标识
    private Integer id;
    // 广告名
    private String name;
    // 广告位id
    private Integer spaceId;
    // 精确搜索关键词
    private String keyword;
    // 静态广告的内容
    private String htmlContent;
    // 文字一
    private String text;
    // 链接一
    private String link;
    // 开始时间

    private Date startTime;
    // 结束时间
    private Date endTime;
    private Date createTime;
    private Date updateTime;
    private Integer status;
    // 优先级
    private Integer priority;
    private String img;

    // 声明一方关系  PromotionSpace
    // 一个广告只能在一个广告位上
    private PromotionSpace promotionSpace;

    // getter/setter...
}
```

#### 接收前台传过来参数的实体对象 PromotionAdVo

```java
/*
    VO:View Object  表现层对象：在表现层接收前台参数的
        封装由浏览器端发送给表现层的请求参数的对象
 */
public class PromotionAdPageVO {

    // 当前页
    private int currentPage;

    // 每页显示的条数
    private int pageSize;

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
```



### Dao层：PromotionAdMapper

```java
public interface PromotionAdMapper {

    /*
        分页查询广告信息
     */
    List<PromotionAd> findAllPromotionAdByPage();
}
```

```xml
<?xml version="1.0" encoding="utf-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">

<mapper namespace="com.lagou.dao.PromotionAdMapper">


    <resultMap id="ad_space" type="PromotionAd">
        <id property="id" column="id"/>
        <result property="name" column="name"/>
        <result property="spaceId" column="spaceId"/>
        <result property="keyword" column="keyword"/>
        <result property="htmlContent" column="htmlContent"/>
        <result property="text" column="text"/>
        <result property="link" column="link"/>
        <result property="startTime" column="startTime"/>
        <result property="endTime" column="endTime"/>
        <result property="createTime" column="createTime"/>
        <result property="updateTime" column="updateTime"/>
        <result property="status" column="status"/>
        <result property="priority" column="priority"/>
        <result property="img" column="img"/>


        <association property="promotionSpace" select="com.lagou.dao.PromotionSpaceMapper.findPromotionSpaceById" column="spaceId" javaType="PromotionSpace"/>
    </resultMap>



<!--    分页查询广告信息            多对一嵌套查询
            查询PromotionAd 信息的同时，还要查询出广告所关联的广告位的信息
-->
    <select id="findAllPromotionAdByPage" resultMap="ad_space">
        select * from promotion_ad
    </select>
</mapper>
```



#### Service层：PromotionAdService

在 applicationContext-dao.xml  配置 分页插件

```xml
<!--    2.sqlSessionFactory 工厂对象交给spring-->
<bean id="sqlSessionFactory" class="org.mybatis.spring.SqlSessionFactoryBean">
<!--    注入数据源-->
    <property name="dataSource" ref="dataSource"/>
<!--    指定类型别名-->
    <property name="typeAliasesPackage" value="com.lagou.entity"/>

<!--    配置 pageHelper 分页插件-->
    <property name="plugins">
        <array>
            <bean class="com.github.pagehelper.PageHelper">
<!--                指定方言为 mysql-->
                <property name="properties">
                    <value>helperDialect=mysql</value>
                </property>
            </bean>
        </array>
    </property>

<!--    引入加载sqlMapperConfig.xml 核心配置文件-->
    <property name="configLocation" value="classpath:sqlMapperConfig.xml"/>
</bean>
```

```java
public interface PromotionAdService {

    /*
            分页查询广告信息
         */
    PageInfo<PromotionAd> findAllPromotionAdByPage(PromotionAdPageVO promotionAdPageVO);
}
```

```java
@Service
public class PromotionAdServiceImpl implements PromotionAdService {

    @Autowired
    private PromotionAdMapper promotionAdMapper;

    @Override
    public PageInfo<PromotionAd> findAllPromotionAdByPage(PromotionAdPageVO promotionAdPageVO) {

        // 分页查询     在调用mapper方法之前进行     传入当前页 和 每页显示的条数
        PageHelper.startPage(promotionAdPageVO.getCurrentPage(), promotionAdPageVO.getPageSize());

        // 这是查询所有操作，分页查询了.list集合就已经是经过分页查询所查询出来的数据封装了
        List<PromotionAd> list = promotionAdMapper.findAllPromotionAdByPage();

        PageInfo<PromotionAd> pageInfo = new PageInfo<>(list);

        return pageInfo;
    }
}
```





#### Web层：PromotionAdController

```java
@RestController
@RequestMapping("/PromotionAd")
public class PromotionAdController {

    @Autowired
    private PromotionAdService promotionAdService;


    /*
        广告分页查询
     */
    @RequestMapping("/findAllPromotionAdByPage")
    // 使用 PromotionAdVO 这个对象来获取前台传递过来的请求参数
    public ResponseResult findAllPromotionAdByPage(PromotionAdPageVO promotionAdPageVO){

        PageInfo<PromotionAd> pageInfo = promotionAdService.findAllPromotionAdByPage(promotionAdPageVO);

        return new ResponseResult(true,200,"广告分页查询成功",pageInfo);
    }
}
```



#### Postman测试接口





## 5. 图片上传接口

### 5.1 需求分析

需求：在添加广告页面，点击上传按钮，需完成图片上传





### 5.2 查看接口文档，进行编码

查看接口文档



#### 在ssm-utils 子工程下创建了一个 文件上传工具类，用于上传图片

```java
/*
    图片上传工具类
 */
public class FileUploadUtils {

    // 参数名要与 前台请求参数 的key 保持一致
    public static Map<String, String> fileUploadUtils(MultipartFile file, HttpServletRequest request) throws IOException {
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
        if (!filePath.getParentFile().exists()) {
            filePath.getParentFile().mkdirs();
            System.out.println("创建目录:" + filePath);
        }

        // 图片就进行了上传
        file.transferTo(filePath);

        // 6.将文件名和文件路径返回，进行响应
        Map<String, String> map = new HashMap<>();
        map.put("fileName", newFileName);
        // "filePath": "http://localhost:8080/upload/1597112871741.JPG"
        // 根据路径可以在Tomcat 的webapps 目录下的 upload目录下 找到文件，并回显
        map.put("filePath", "http://localhost:8080/upload/" + newFileName);

        return map;
    }
}
```



#### Web层：PromotionAdController

```java
/*
        图片上传
     */
    @RequestMapping("/PromotionAdUpload")
    // 参数名要与 前台请求参数 的key 保持一致
    public ResponseResult fileUpload(@RequestParam("file") MultipartFile file, HttpServletRequest request) throws IOException {

        // 调用图片上传工具类
        Map<String, String> map = FileUploadUtils.fileUploadUtils(file,request);

        // 对象转为JSON响应给前台
        return new ResponseResult(true,200,"图片上传成功",map);
    }
```



#### Postman测试接口







## 6. 新建&修改广告

### 6.1 需求分析：

新建：在点击添加广告时，输入完内容后，再点击提交按钮，将页面内容保存到数据库

修改：点击编辑按钮，由前端实现数据回显，在回显页面进行数据修改，将修改后的值更新到数据库中



### 6.2 查看接口文档，进行编码

查看接口文档





#### Dao层：PromotionAdMapper

```java
/*
        添加广告
     */
    void savePromotionAd(PromotionAd promotionAd);

/*
        修改广告
     */
    void updatePromotionAd(PromotionAd promotionAd);
```

```xml
<!--    添加广告  void savePromotionAd(PromotionAd promotionAd);-->
    <insert id="savePromotionAd" parameterType="promotionAd">
        INSERT INTO promotion_ad (
  NAME,
  spaceId,
  startTime,
  endTime,
  createTime,
  updateTime,
  STATUS,
  img,
  link,
  TEXT,
  priority
)
VALUES
  (
    #{name},#{spaceId},#{startTime},#{endTime},#{createTime},#{updateTime},#{status},#{img},#{link},#{text},#{priority}
  )
    </insert>
    
    
    <!--    修改广告  void updatePromotionAd(PromotionAd promotionAd);-->
    <update id="updatePromotionAd" parameterType="promotionAd">
        UPDATE promotion_ad
        <trim prefix="SET" suffixOverrides=",">
            <if test="name != null and name != ''">
                name = #{name},
            </if>

            <if test="spaceId != null and spaceId != ''">
                spaceId=#{spaceId},
            </if>

            <if test="link != null">
                link=#{link},
            </if>

            <if test="status != null and status != '' or status == 0">
                status=#{status},
            </if>

            <if test="img != null">
                img=#{img},
            </if>

            <if test="text != null">
                text=#{text},
            </if>

            <if test="startTime != null">
                startTime=#{startTime},
            </if>

            <if test="endTime != null">
                endTime=#{endTime},
            </if>

            <if test="updateTime != null">
                updateTime=#{updateTime},
            </if>

        </trim>

        <where>
            <if test="id!=null and id != '' ">id=#{id}</if>
        </where>

    </update>
```



#### Service层：PromotionAdService

```java
/*
        新建广告
     */
    void savePromotionAd(PromotionAd promotionAd);

/*
        修改广告
     */
    void updatePromotionAd(PromotionAd promotionAd);
```

```java
/*
        新建广告
     */
    @Override
    public void savePromotionAd(PromotionAd promotionAd) {

        // 封装数据
        Date date = new Date();
        promotionAd.setCreateTime(date);
        promotionAd.setUpdateTime(date);

        // 调用mapper
        promotionAdMapper.savePromotionAd(promotionAd);
    }

/*
        修改广告
     */
    @Override
    public void updatePromotionAd(PromotionAd promotionAd) {

        // 封装数据
        promotionAd.setUpdateTime(new Date());

        // 调用mapper
        promotionAdMapper.updatePromotionAd(promotionAd);
    }
```





#### Web层：PromotionAdController

```java
/*
        新建&修改广告
     */
    @RequestMapping("/saveOrUpdatePromotionAd")
    public ResponseResult saveOrUpdatePromotionAd(@RequestBody PromotionAd promotionAd){
        if (promotionAd.getId() == null) {
            // 新建
            promotionAdService.savePromotionAd(promotionAd);
            return new ResponseResult(true,200,"新建广告成功",null);
        } else {
            // 修改
            promotionAdService.updatePromotionAd(promotionAd);
            return new ResponseResult(true,200,"修改广告成功",null);
        }
    }
```



### Postman测试接口





## 7. 回显广告信息

### 7.1 需求分析：

需求：点击编辑按钮时，进行广告信息的回显



### 7.2 查看接口文档，进行编码

查看接口文档





#### Dao层：PromotionAdMapper

```java
/*
        根据ID查询广告信息
     */
    PromotionAd findPromotionAdById(int id);
```

```xml
<!--    根据ID查询广告 PromotionAd findPromotionAdById(int id);-->
    <select id="findPromotionAdById" parameterType="int" resultType="promotionAd">
        select id,name,spaceId,startTime,endTime,status,img,link,text from promotion_ad where id = #{id}
    </select>
```



#### Service层：PromotionAdService

```java
/*
        根据ID查询广告信息
     */
    PromotionAd findPromotionAdById(int id);
```

```java
/*
        根据ID查询广告信息
     */
    @Override
    public PromotionAd findPromotionAdById(int id) {
        return promotionAdMapper.findPromotionAdById(id);
    }
```



#### Web层：PromotionAdController

```java
/*
        根据ID查询广告信息
     */
    @RequestMapping("/findPromotionAdById")
    public ResponseResult findPromotionAdById(int id){

        PromotionAd promotionAd = promotionAdService.findPromotionAdById(id);

        return new ResponseResult(true,200,"查询具体广告成功",promotionAd);
    }
```



#### Postman测试接口





## 8. 广告状态上下线

### 8.1 需求分析：

需求：当点击上下线按钮时，实现状态的动态上下线



### 8.2 查看接口文档，进行编码

查看接口文档



#### Dao层：PromotionAdMapper

```java
/*
        广告动态上下线
     */
    void updatePromotionAdStatus(PromotionAd promotionAd);
```

```xml
<!--  广告动态上下线 void updatePromotionAdStatus(PromotionAd promotionAd);  -->
    <update id="updatePromotionAdStatus" parameterType="promotionAd">
        update promotion_ad set status = #{status},updateTime = #{updateTime} where id = #{id}
    </update>
```



#### Service层：PromotionAdService

```java
/*
        广告动态上下线
     */
    void updatePromotionAdStatus(int id,int status);
```

```java
/*
        广告动态上下线
     */
    @Override
    public void updatePromotionAdStatus(int id, int status) {

        // 封装数据
        PromotionAd promotionAd = new PromotionAd();
        promotionAd.setUpdateTime(new Date());
        promotionAd.setId(id);
        promotionAd.setStatus(status);

        // 调用mapper
        promotionAdMapper.updatePromotionAdStatus(promotionAd);
    }
```



#### Web层：PromotionAdController

```java
/*
        广告动态上下线
     */
    @RequestMapping("/updatePromotionAdStatus")
    public ResponseResult updatePromotionAdStatus(int id,int status){

        promotionAdService.updatePromotionAdStatus(id,status);

        return new ResponseResult(true,200,"广告动态上下线成功",null);
    }
```



#### Postman测试接口







# 用户管理模块

## 一、用户模块功能分析

​	再来完成后台管理系统的用户管理模块，用户管理模块包含了用户分页&条件查询、用户状态设置，（登录、权限控制）等接口的编写



### 1.1 用户管理模块

* 实现以下功能：
  * 登录（权限模块）
  * 权限控制（权限模块）
  * 用户分页&多条件查询
  * 用户状态管理
  * 分配角色（权限模块）



## 二、用户管理模块表设计

### 2.1 创建数据库及表



## 三、用户管理模块接口实现

## 1. 用户分页&条件查询

### 1.1 需求分析：

需求：点击用户管理按钮，实现多条件分页组合查询，展示在页面上



### 1.2 查看接口文档，进行编码

查看接口文档



#### UserVo 用来接收前台传递过来的参数的实体对象

```java
public class UserVo {

    // 当前页
    private Integer currentPage;
    // 每页显示条数
    private Integer pageSize;

    // 多条件查询：用户名（手机号）
    private String username;
    // 注册起始时间
    // springMVC 只能默认接收这种格式的字符串 2020/11/11 转为Date类型
    // 但前台传递的是 2020-11-11 这种格式，那么springMVC就不能自动类型转了
    // 所以要使用自定义类型转换器   或者注解

    // 指定前台传递过来的日期字符串对应的格式是 yyyy-MM-dd 这样的
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startCreateTime;
    // 注册结束时间

    // 前台传递过来的日期格式 的字符串 是以横杠 分隔的
    // 所以使用 注解
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endCreateTime;

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getStartCreateTime() {
        return startCreateTime;
    }

    public void setStartCreateTime(Date startCreateTime) {
        this.startCreateTime = startCreateTime;
    }

    public Date getEndCreateTime() {
        return endCreateTime;
    }

    public void setEndCreateTime(Date endCreateTime) {
        this.endCreateTime = endCreateTime;
    }
}
```



#### Dao层：UserMapper

```java
public interface UserMapper {

    /*
        用户分页&多条件组合查询
     */
    public List<User> findAllUserByPage(UserVo userVo);
}
```

```xml
<?xml version="1.0" encoding="utf-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">

<mapper namespace="com.lagou.dao.UserMapper">

<!--    用户分页&多条件组合查询   public List<User> findAllUserByPage(UserVo userVo);-->
    <select id="findAllUserByPage" resultType="User" parameterType="UserVo">
        <!-- 动态SQL -->
        select id,name,portrait,phone,password,status,create_time from user
        <where>
            <if test="true">
                and is_del !=1
            </if>
            <if test="username != null and username != ''">
                and name = #{username}
            </if>
            <if test="startCreateTime != null and endCreateTime != null">
                and create_time BETWEEN #{startCreateTime} AND #{endCreateTime}
            </if>
        </where>
    </select>
</mapper>
```



#### Service层：UserService

```java
public interface UserService {

    /*
        用户分页&多条件查询
     */
    PageInfo findAllUserByPage(UserVo userVo);
}
```

```java
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;


    /*
        用户分页&多条件查询
     */
    @Override
    public PageInfo findAllUserByPage(UserVo userVo) {

        // 实现分页效果       参数：当前页，每页显示多少条
        // 使用pageHelper
       	 PageHelper.startPage(userVo.getCurrentPage(),userVo.getPageSize());

        // 这样的话，在调用mapper方法时，就可以分页查询了
        // list 集合就是分页数据
        List<User> list = userMapper.findAllUserByPage(userVo);

        // 拿到pageInfo，里面就有分页的相关参数了
        PageInfo<User> pageInfo = new PageInfo<>(list);

        return pageInfo;
    }
}
```



#### Web层：UserController

```java
@RestController
@RequestMapping("/user")
public class UserController {


    @Autowired
    private UserService userService;

    /*
        用户分页&多条件查询方法
     */
    @RequestMapping("/findAllUserByPage")
    // 接收到请求体中的参数并封装到 UserVo对象中
    public ResponseResult findAllUserByPage(@RequestBody UserVo userVo){

        PageInfo pageInfo = userService.findAllUserByPage(userVo);

        return new ResponseResult(true,200,"分页多条件查询成功",pageInfo);

    }
}
```



#### Postman测试接口





## 2.用户状态设置

### 2.1 需求分析

需求：点击禁用按钮，实现用户的状态变更 

用户状态：ENABLE能登录，DISABLE不能登录



### 2.2 查看接口文档，进行编码

查看接口文档



#### Dao层：UserMapper

```java
/*
        修改用户状态
     */
    void updateUserStatus(User user);
```

```xml
<!--    修改用户状态  void updateUserStatus(User user);-->
    <update id="updateUserStatus" parameterType="user">
        update user set status = #{status},update_time = #{update_time} where id = #{id}
    </update>
```



#### Service层：UserService

```java
/*
        修改用户状态
     */
    void updateUserStatus(int id, String status);
```

```java
/*
        修改用户状态
     */
    @Override
    public void updateUserStatus(int id, String status) {

        // 封装数据
        User user = new User();
        user.setId(id);
        user.setStatus(status);
        user.setUpdate_time(new Date());

        // 调用mapper
        userMapper.updateUserStatus(user);
    }
```



#### Web层：UserController

```java
/*
        修改用户状态
            ENABLE能登录，DISABLE不能登录
     */
    @RequestMapping("/updateUserStatus")
    public ResponseResult updateUserStatus(int id,String status){

        userService.updateUserStatus(id,status);

        return new ResponseResult(true,200,"修改用户状态成功",status);

    }
```



#### Postman测试接口