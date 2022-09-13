# 任务三：后台管理系统 学习笔记（SSM）

# 权限模块

## 一、权限概念介绍

**权限：**权利（能做的）和**限制**（不能做的）

​	认证：验证用户名密码是否正确

​	授权：对用户所能访问的资源进行控制（动态显示菜单、url级别的权限控制）



### 为什么要实现权限系统

首先系统需要进行登录才能访问

不同登录用户要有不同的权利，要有不同的菜单



### 权限控制基本原理

**1.ACL**

就是把具体的权限信息直接赋值给用户

**2.RBAC**

基于角色的访问控制。把用户按角色进行归类，通过用户的角色来确定用户能否针对某项资源进行某项操作。简化了用户与权限的管理，通过对用户进行分类，使得角色与权限关联起来，而用户与权限变成了间接关联

* 规则一：每个登录的用户，可以有多个角色
* 规则二：每个角色又可以拥有多个权限（包含菜单和资源）



**面试：权限的相关概念**

权限就是权利和限制

认证就是验证用户名和密码是否正确的过程

授权就是对用户所能访问的资源进行控制

权限控制基本原理：

ACl是面向资源的访问控制模型，用户直接和权限关联

RBAC是简化了用户与权限的管理，通过对用户进行分类，使得角色与权限关联，而用户与角色关联，那么用户与权限就变成了间接关联





## 二、权限模块功能分析

​	权限模块分为角色模块、菜单模块、资源模块。同时完成用户登录，用户关联角色及动态菜单显示



### 2.1 权限模块管理

* 实现以下
  * 角色列表&条件查询（角色模块）
  * 分配菜单（角色模块）
  * 删除角色（角色模块）
  * 菜单列表查询（菜单模块）
  * 查询菜单信息回显（菜单模块）
  * 资源分页&多条件查询（资源模块）
  * 用户登录（用户模块）
  * 动态菜单展示（权限模块）
  * 用户关联角色（用户模块）





## 三、权限管理模块表设计

把权限表进行了划分：菜单表，资源表，资源分类表

表关系：

* **用户表角色表  多对多**（中间表）
* **角色表和权限表   多对多**（中间表）
  * 角色和菜单多对多
  * 角色和资源多对多

* 资源表和资源分类表  多对一
  * 一个资源分类下有多个资源



**权限系统经典五张表（用户表，角色表，权限表，用户角色中间表，角色权限中间表）**



注意：用户表和权限表没有直接关系，是通过角色表进行了间接关联









## 四、权限管理（角色模块）接口实现

### 1. 角色列表&多条件查询

#### 1.1 需求分析：

需求：当点击角色列表按钮时，把角色表的全部记录进行查询。和条件查询





#### 1.2 查看接口文档，进行编码

查看接口文档



#### 实体类：Role

```java
public class Role {

    private Integer id;
    private String code;
    private String name;
    private String description;
    private Date createdTime;
    private Date updatedTime;
    private String createdBy;
    private String updatedBy;

    // getter/setter..
}
```



#### Dao层：RoleMapper

```java
public interface RoleMapper {

    /*
        查询所有角色&条件进行查询
     */
    List<Role> findAllRole(Role role);
}
```

```xml
<?xml version="1.0" encoding="utf-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">

<mapper namespace="com.lagou.dao.RoleMapper">

<!--    查询所有角色(条件)-->
    <select id="findAllRole" parameterType="role" resultType="role">
        select * from roles
        <where>
            <if test="name != null and name != ''">
                and name = #{name}
            </if>
        </where>
    </select>
</mapper>
```



#### Service层：RoleService

```java
public interface RoleService {

    /*
        查询所有角色&条件进行查询
     */
    List<Role> findAllRole(Role role);
}
```

```java
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public List<Role> findAllRole(Role role) {
        return roleMapper.findAllRole(role);
    }
}
```



#### Web层：RoleController

```java
@RestController
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @Autowired
    private MenuService menuService;

    /*
        查询所有角色（条件）
     */
    @RequestMapping("/findAllRole")
    public ResponseResult findAllRole(@RequestBody Role role){

        List<Role> allRole = roleService.findAllRole(role);
        return new ResponseResult(true,200,"查询所有角色成功",allRole);
    }
}
```



#### Postman测试接口







### 2. 分配菜单

#### 2.1 需求分析：

需求：点击分配菜单按钮，跳转到角色菜单管理页面，回显当前可以关联的菜单信息，并回显选中的关联信息状态



#### 2.2 接口1 查询所有菜单列表

查看接口文档



#### Menu实体

```java
/**
 * 菜单类
 * */
public class Menu {

    //主键id
    private Integer id;

    //父菜单id
    private int parentId;

    //菜单路径
    private String href;

    //菜单图标
    private String icon;

    //菜单名称
    private String name;

    //描述
    private String description;

    //排序号
    private int orderNum;

    //是否展示
    private int shown;

    //菜单层级，从0开始
    private int level;

    //创建时间
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createdTime;

    //更新时间
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updatedTime;

    //创建人
    private String createdBy;

    //更新人
    private String updatedBy;

    // 声明集合：当前父级菜单所关联的子级菜单
    private List<Menu> subMenuList;

   // get/set
}
```



#### Dao层：MenuMapper

通过自连接查询将所有的菜单节点信息进行查询,

当前在页面中可以显示的全部菜单信息

```java
public interface MenuMapper {

    /*
        查询所有父子菜单信息
     */
    List<Menu> findSubMenuListByPid(int pid);
}
```

```xml
<?xml version="1.0" encoding="utf-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">

<mapper namespace="com.lagou.dao.MenuMapper">

    <resultMap id="menuResult" type="menu">
        <id column="id" property="id"></id>
        <result column="href" property="href"></result>
        <result column="icon" property="icon"></result>
        <result column="name" property="name"></result>
        <result column="parent_id" property="parentId"></result>
        <result column="description" property="description"></result>
        <result column="orderNum" property="order_num"></result>
        <result column="shown" property="shown"></result>
        <result column="created_time" property="createdTime"></result>
        <result column="updated_time" property="updatedTime"></result>
        <result column="created_by" property="createdBy"></result>
        <result column="updated_by" property="updatedBy"></result>

<!--        其次要查询顶级菜单所关联的子级菜单信息
                查询出父级菜单信息，再根据父级菜单的id值，查询出父级菜单所关联的子级菜单的信息
-->
        <collection property="subMenuList" ofType="Menu" select="findSubMenuListByPid" column="id"/>
    </resultMap>

    <!-- 自连接查询，一张表拆分成两张表 -->

<!--    查询所有父子菜单信息-->
    <select id="findSubMenuListByPid" resultMap="menuResult" parameterType="int">
<!-- 第一次发送的SQL，根据父级菜单parent_id的值都为-1，查询出的结果对每条记录进行封装时，是走上面的ResultMap
        进行嵌套查询，select 实际还是去调用这条SQL，再次调用的时候所传递的值就是 第一次查询出来的 父级菜单的id值
            根据父级菜单的id值，查询出父级菜单所有关联的子菜单信息。把子菜单信息封装到 Menu实体里的 subMenuList
 -->
        <!-- 首先先查询顶级（父级）菜单（所有顶级菜单，他们的parent_id的值都为-1）-->
SELECT * FROM menu WHERE parent_id = #{id}
    </select>
</mapper>
```



#### Service层：MenuService

```java
public interface MenuService {

    /*
        查询所有父子菜单信息
     */
    List<Menu> findSubMenuListByPid(int pid);
}
```

```java
@Service
public class MenuServiceImpl implements MenuService {

    @Autowired
    private MenuMapper menuMapper;

    @Override
    public List<Menu> findSubMenuListByPid(int pid) {

        // List集合 里不仅有父级菜单信息，还有关联的子级菜单信息
        return menuMapper.findSubMenuListByPid(pid);
    }
}
```



#### Web层：RoleController

```java
/*
        查询所有的父子菜单信息(分配菜单的第一个接口)
     */
    @RequestMapping("/findAllMenu")
    public ResponseResult findSubMenuListByPid(){

        // -1 表示查询所有的父子级菜单
        // 第一次查询是查询所有的父级菜单，第二次查询是该父级菜单所关联的子级菜单也进行查询
        List<Menu> menuList = menuService.findSubMenuListByPid(-1);

        // 向map中设置前台要的 值
        HashMap<String, Object> map = new HashMap<>();
        map.put("parentMenuList",menuList);

        // 响应数据
        return new ResponseResult(true,200,"查询所有的父子菜单信息成功",map);
    }
```



#### Postman测试接口





#### 2.3 接口2 根据角色ID查询关联菜单ID

#### Dao层：RoleMapper

根据当前传递的角色ID所查询出该角色所关联的菜单信息，

根据当前角色ID查询出该角色已经关联的菜单信息

```java
/*
        根据角色ID 查询该角色关联的菜单信息 ID  [1,2,3,4,5]
     */
    List<Integer> findMenuByRoleId(Integer roleId);
```

```xml
<!--    根据角色ID 查询关联的 菜单信息ID  List<Integer> findMenuByRoleId(Integer roleId);-->
<!-- -- 根据角色id查询已经关联的菜单信息
-- 根据角色Id为2 查询关联的菜单信息的id值[1,2,3](多对多：关联维护在中间表的)

SELECT m.id 
FROM roles r INNER JOIN role_menu_relation rm ON r.id = rm.role_id
	     INNER JOIN menu m ON m.id = rm.menu_id WHERE r.id = 2 -->


    <select id="findMenuByRoleId" resultType="int" parameterType="int">
        SELECT m.id
FROM roles r INNER JOIN role_menu_relation rm ON r.id = rm.role_id
	     INNER JOIN menu m ON m.id = rm.menu_id WHERE r.id = #{id}
    </select>
```



#### Service层：RoleService

```java
/*
        根据角色ID 查询该角色关联的菜单信息 ID  [1,2,3,4,5]
     */
    List<Integer> findMenuByRoleId(Integer roleId);
```

```java
@Override
    public List<Integer> findMenuByRoleId(Integer roleId) {
        return roleMapper.findMenuByRoleId(roleId);
    }
```



#### Web层：RoleController

```java
/*
        根据角色ID查询 关联的菜单信息ID
     */
    @RequestMapping("/findMenuByRoleId")
    public ResponseResult findMenuByRoleId(Integer roleId){

        return new ResponseResult(true,200,"查询角色关联的菜单信息成功",roleService.findMenuByRoleId(roleId););
    }
```



#### Postman测试接口





#### 2.4 接口3 为角色分配菜单列表

为当前角色分配菜单信息 功能就是      **本质：向角色菜单中间表role_menu_relation  添加记录**

角色和菜单：多对多（关联关系维护在中间表）

注意：**向中间表添加关联关系时，通常都要先根据角色ID把关联关系清空，再添加关联关系**



#### Dao层：RoleMapper

```java
/*
        根据RoleId 清空中间表的关联关系
     */
    void deleteRoleContextMenu(Integer rid);


    /*
        为角色分配菜单信息
     */
    void roleContextMenu(Role_menu_relation role_menu_relation);
```

```xml
<!--根据RoleId 删除在 中间表 与菜单的关联关系 -->
    <delete id="deleteRoleContextMenu" parameterType="int">
        delete  from role_menu_relation where role_id = #{rid}
    </delete>


<!--    为角色分配菜单
            向中间表添加记录，设置角色与菜单的关联关系
-->
    <insert id="roleContextMenu" parameterType="Role_menu_relation">
        insert into role_menu_relation values(null,#{menuId},#{roleId},#{createdTime},#{updatedTime},#{createdBy},#{updatedby})
    </insert>
```



#### Service层：RoleService

```java
/*
        为角色分配菜单
     */
    public void roleContextMenu(RoleMenuVo roleMenuVo);
```

```java
@Override
    public void roleContextMenu(RoleMenuVo roleMenuVo) {

        // 1.清空中间表的关联关系
        roleMapper.deleteRoleContextMenu(roleMenuVo.getRoleId());

        // 2.为角色分配菜单

        for (Integer mid : roleMenuVo.getMenuIdList()) {

            Role_menu_relation role_menu_relation = new Role_menu_relation();
            role_menu_relation.setMenuId(mid);
            role_menu_relation.setRoleId(roleMenuVo.getRoleId());

            // 封装数据
            Date date = new Date();
            role_menu_relation.setCreatedTime(date);
            role_menu_relation.setUpdatedTime(date);
            role_menu_relation.setCreatedBy("system");
            role_menu_relation.setUpdatedby("system");

            // mapper方法就是获取到 role_menu_relation 里的属性对中间表进行添加
            roleMapper.roleContextMenu(role_menu_relation);
        }

    }
```



#### Web层：RoleController

```java
/*
        为角色分配菜单
     */
    @RequestMapping("/RoleContextMenu")
    public ResponseResult RoleContextMenu(@RequestBody RoleMenuVo roleMenuVo){

        roleService.roleContextMenu(roleMenuVo);

        return new ResponseResult(true,200,"响应成功",null);
    }
```



#### Postman测试接口





### 3. 删除角色

#### 3.1 需求分析

需求：点击删除按钮，将选中的角色信息删除

**角色和菜单是多对多关系，在删除角色信息之前，要先根据角色ID把中间表中的关联关系删除**。就是先清空关联关系，再删除角色



#### 3.2 查看接口文档，进行编码

查看接口文档



#### Dao层：RoleMapper

```java
/*
        删除角色
     */
    void deleteRole(int roleId);
```

```xml
<!--    删除角色  void deleteRole(int roleId);-->
    <delete id="deleteRole" parameterType="int">
        delete from roles where id = #{roleId}
    </delete>
```



#### Service层：RoleService

```java
/*
        删除角色
     */
    void deleteRole(int roleId);
```

```java
@Override
    public void deleteRole(int roleId) {

        // 调用根据roleId 清空中间表关联关系
        roleMapper.deleteRoleContextMenu(roleId);

        // 再根据roleId 删除具体角色信息
        roleMapper.deleteRole(roleId);
    }
```



#### Web层：RoleController

```java
/*
        删除角色
     */
    @RequestMapping("/deleteRole")
    public ResponseResult deleteRole(int id){

        roleService.deleteRole(id);


        return new ResponseResult(true,200,"删除角色成功",null);
    }
```



#### Postman测试接口







## 五、权限管理（菜单模块）接口实现

### 1. 菜单列表查询

#### 1.1 需求分析

需求：点击菜单列表按钮时，发送请求，将菜单表的全部记录进行查询，并显示在页面上



#### 1.2 查看接口文档，进行编码

查看接口文档



#### Dao层：MenuMapper

```java
/*
        查询所有菜单列表
     */
    List<Menu> findAllMenu();
```

```xml
<!--    查询所有菜单信息-->
    <select id="findAllMenu" resultType="menu">
        select * from menu
    </select>
```



#### Service层：MenuService

```java
/*
        查询所有菜单列表
     */
    List<Menu> findAllMenu();
```

```java
@Override
    public List<Menu> findAllMenu() {
        return menuMapper.findAllMenu();
    }
```



#### Web层：MenuController

```java
@RestController
@RequestMapping("/menu")
public class MenuController {

    @Autowired
    private MenuService menuService;


    /*
        查询所有菜单信息
     */
    @RequestMapping("/findAllMenu")
    public ResponseResult findAllMenu(){

        return new ResponseResult(true,200,"查询所有菜单信息成功",menuService.findAllMenu());
    }
}
```



#### Postman测试接口





### 2. 查询菜单信息（回显）

#### 2.1 需求分析

需求：点击添加或编辑按钮，跳转之前都要发送请求去查询回显信息。

判断传递过来的id值为不为-1，为-1是新增操作，那么只查询父级菜单信息

不为-1是修改操作，父级菜单信息和具体的菜单信息都要查询出来并回显



#### 2.2 查看接口文档，进行编码

查看接口文档



#### Dao层：MenuMapper

```java
/*
        回显菜单信息
     */
    Menu finMenuById(int id);

```

```xml
<!--    根据id查询menu-->
    <select id="finMenuById" parameterType="int" resultType="menu">
        select * from menu where id = #{id}
    </select>
```



#### Service层：MenuService

```java
Menu findMenuById(int id);
```

```java
@Override
    public Menu findMenuById(int id) {
        return menuMapper.finMenuById(id);
    }
```



直接复用 查询所有父子菜单信息 方法

#### Web层：MenuController

```java
/*
        回显菜单信息
     */
    @RequestMapping("/findMenuInfoById")
    public ResponseResult findMenuInfoById(int id){

        // 根据id的值判断当前是更新还是添加操作  判断id的值是否为-1
        if (id == -1){
            // 添加  回显信息中就不需要查询menu信息了
            List<Menu> subMenuListByPid = menuService.findSubMenuListByPid(-1);

            // 封装数据
            HashMap<String, Object> map = new HashMap<>();
            map.put("menuInfo",null);
            map.put("parentMenuList",subMenuListByPid);

            return new ResponseResult(true,200,"添加回显成功",map);
        } else {
            // 修改操作 回显所有menu信息
            // 不仅要回显父级菜单的menu信息，还要根据当前传递过来的id 回显当前menu信息
            Menu menu = menuService.findMenuById(id);

            List<Menu> subMenuListByPid = menuService.findSubMenuListByPid(-1);

            // 封装数据
            HashMap<String, Object> map = new HashMap<>();
            map.put("menuInfo",menu);
            map.put("parentMenuList",subMenuListByPid);

            return new ResponseResult(true,200,"修改回显成功",map);

        }
    }
```



#### Postman测试接口





## 六、权限管理（资源模块）接口实现

### 1. 资源分页&多条件查询

#### 1.1 需求分析：

需求：点击资源列表按钮时，资源列表及多条件组合查询



#### 1.2 查看接口文档，进行编码

查看接口文档



#### Dao层：ResourceMapper

​					ResourceCategoryMapper

```java
/*
        资源分页&多条件查询
     */
    List<Resource> findAllResourceByPage(ResourceVo resourceVo);
```

```xml
<?xml version="1.0" encoding="utf-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">

<mapper namespace="com.lagou.dao.ResourceMapper">

<!--    分页&多条件查询资源-->
    <select id="findAllResourceByPage" resultType="resource" parameterType="resourceVo">
        select * from resource
        <where>
            <if test="name != null and name != ''">
                and name like concat('%',#{name},'%')
            </if>
            <if test="url != null and url != ''">
                and url = #{url}
            </if>
            <if test="categoryId != null and categoryId != ''">
                and category_id = #{categoryId}
            </if>
        </where>
    </select>
</mapper>
```

```java
public interface ResourceCategoryMapper {

    /*
        查询所有资源分类
     */
    public List<ResourceCategory> findAllResourceCategory();
}
```

```xml
<?xml version="1.0" encoding="utf-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">

<mapper namespace="com.lagou.dao.ResourceCategoryMapper">

<!--    查询所有资源分类-->
    <select id="findAllResourceCategory" resultType="ResourceCategory">
        select * from resource_category
    </select>
</mapper>
```



#### Service层：ResourceService

​							ResourceCategoryService

```java
public interface ResourceService {

    /*
            资源分页&多条件查询
         */
    PageInfo<Resource> findAllResourceByPage(ResourceVo resourceVo);
}
```

```java
@Service
public class ResourceServiceImpl implements ResourceService {


    @Autowired
    private ResourceMapper resourceMapper;


    @Override
    public PageInfo<Resource> findAllResourceByPage(ResourceVo resourceVo) {
        // 分页查询
        PageHelper.startPage(resourceVo.getCurrentPage(),resourceVo.getPageSize());

        List<Resource> allResourceByPage = resourceMapper.findAllResourceByPage(resourceVo);

        return new PageInfo<>(allResourceByPage);
    }
}
```

```java
public interface ResourceCategoryService {

    /*
            查询所有资源分类
         */
    public List<ResourceCategory> findAllResourceCategory();
}
```

```java
@Service
public class ResourceCategoryServiceImpl implements ResourceCategoryService {


    @Autowired
    private ResourceCategoryMapper resourceCategoryMapper;


    @Override
    public List<ResourceCategory> findAllResourceCategory() {
        return resourceCategoryMapper.findAllResourceCategory();
    }
}
```



#### Web层：ResourceController

​					ResourceCategoryController

```java
@RestController
@RequestMapping("/resource")
public class ResourceController {

    @Autowired
    private ResourceService resourceService;


    /*
        分页查询&多条件
     */
    @RequestMapping("/findAllResource")
    public ResponseResult findAllResource(@RequestBody ResourceVo resourceVo){
        PageInfo<Resource> allResourceByPage = resourceService.findAllResourceByPage(resourceVo);

        return new ResponseResult(true,200,"资源信息分页多条件查询成功",allResourceByPage);
    }
}
```

```java
@RestController
@RequestMapping("/ResourceCategory")
public class ResourceCategoryController {


    @Autowired
    private ResourceCategoryService resourceCategoryService;

    /*
        查询资源分类信息
     */
    @RequestMapping("/findAllResourceCategory")
    public ResponseResult findAllResourceCategory(){

        return new ResponseResult(true,200,"查询所有分类信息成功",resourceCategoryService.findAllResourceCategory());
    }
}
```





#### Postman测试接口







## 七、登录及动态菜单展示

### 1. 登录

#### 1.1 需求分析

需求：输入用户名和密码，点击登录按钮，进行用户登录



#### 加密算法MD5

##### 1、什么是MD5

MD5加密全程是Message-Digest Algoorithm 5（信息-摘要算法），它对信息进行摘要采集，再通过一定的位运算，最终获取加密后的MD5字符串

就是对于用户的密码信息进行加密的



##### 2、MD5有哪些特点

MD5加密的特点主要有以下几点：

1.针对不同长度待加密的数据、字符串等等，其都可以返回一个固定长度的MD5加密字符串。（通常32位的16进制字符串）

2.其加密过程几乎不可逆，除非维护一个庞大的Key-Value数据库来进行碰撞破解，否则几乎无法解开

3.运算简便，且可实现方式多样，通过一定的处理方式也可以避免碰撞算法的破解。（加盐：随机字符串)

4.对于一个固定的字符串。数字等等，MD5加密后的字符串是固定的，也就是说不管MD5加密多少次，都是同样的结果



##### 3、java中如何使用MD5

1) 添加依赖

```xml
<!--        MD5依赖-->
        <dependency>
            <groupId>org.apache.commons</groupId>
            <artifactId>commons-lang3</artifactId>
            <version>3.3.2</version>
        </dependency>
        <dependency>
            <groupId>commons-codec</groupId>
            <artifactId>commons-codec</artifactId>
            <version>1.3</version>
        </dependency>
```

2）添加工具类

```java
public class Md5 {

    public final static  String md5key = "lagou";
    /**
     * MD5方法
     * @param text 明文
     * @param key 密钥
     * @return 密文
     * @throws Exception
     */
    public static String md5(String text, String key) throws Exception {
        //加密后的字符串
        String encodeStr= DigestUtils.md5Hex(text+key);
        System.out.println("MD5加密后的字符串为:encodeStr="+encodeStr);
        return encodeStr;
    }

    /**
     * MD5验证方法
     * @param text 明文
     * @param key 密钥
     * @param md5 密文
     * @return true/false
     * @throws Exception
     */
    public static boolean verify(String text, String key, String md5) throws Exception {
        //根据传入的密钥进行验证
        String md5Text = md5(text, key);
        if(md5Text.equalsIgnoreCase(md5))
        {
            System.out.println("MD5验证通过");
            return true;
        }
        return false;
    }


    public static void main(String[] args) throws Exception {

        // 注册   用户名：tom     密码：123456
        // 添加用户的时候，要将明文密码转换成密文密码
        String lagou = md5("123456", "lagou");
        System.out.println(lagou);


        // 登录：  用户名：tom     密码：123456
        // 1.根据前台传递过来的用户名tom 先在user表 中查询出对应的密文密码
        // select * from user where username = tom

        // 调用verify 方法进行密码的校验
        // tom用户的密文密码查询出来了，在前台接收到了明文密码，所以要把前台的明文密码进行相同的算法的加密，
        // 判断加密后的密文密码和user表中查询出来的密文密码是否一致
        boolean verify = verify("123456", "lagou", "f00485441dfb815c75a13f3c3389c0b9");

        System.out.println(verify);
    }
}
```





#### 1.2 查看接口文档，进行编码

查看接口文档



#### Dao层：UserMapper

```java
/*
        用户登录（根据用户名查询具体的用户信息）
     */
    User login(User user);
```

```xml
<!--    根据用户名查询用户  User login(User user);-->
    <select id="login" resultType="user" parameterType="user">
        select * from user where phone = #{phone}
    </select>
```



#### Service层：UserService

```java
/*
        用户登录（根据用户名查询具体的用户信息）
     */
    User login(User user) throws Exception;
```

```java
/*
        用户登录
     */
    @Override
    public User login(User user) throws Exception {

        // 1.调用mapper   user1：包含了密文密码
        User user1 = userMapper.login(user);

        // 表示根据用户名查询出来的包含了密文密码的对象，的密文密码与前台的加密过后的明文密码 是相同的
        // 那么用户名密码正确，登录成功
        if (user1 != null && Md5.verify(user.getPassword(), "lagou", user1.getPassword())) {
            return user1;
        } else {
            return null;
        }
    }
```



#### Web层：UserController

```java
/*
        用户登录
     */
    @RequestMapping("/login")
    public ResponseResult login(User user, HttpServletRequest request) throws Exception {

        User user1 = userService.login(user);

        if (user1 != null) {

            // 保存用户id及access_token 到 session 中
            // 做登录，就是把用户id和access_token 存到session中
            // 当用户下一次向后台发送请求时，会携带一个请求头，请求头的值就是 access_token 的值
            // 进行和session 中的 access_token 的值进行对比。如果一致就进行后续操作
            HttpSession session = request.getSession();
            String access_token = UUID.randomUUID().toString();
            session.setAttribute("access_token",access_token);
            session.setAttribute("user_id",user1.getId());

            // 将查询出来的信息响应给前台
            Map<String, Object> map = new HashMap<>();
            map.put("access_token",access_token);
            map.put("user_id",user1.getId());

            // 将查询出来的user，也存到map中
            map.put("user",user1);

            return new ResponseResult(true,1,"登录成功",map);

        } else {
            return new ResponseResult(true,400,"用户名密码错误",null);
        }
    }
```



#### Postman测试接口





### 2. 分配角色（回显）

#### 1.1 需求分析:

需求：点击分配角色按钮，将该用户所具有的角色信息进行回显



#### 1.2 查看接口文档，进行编码

查看接口文档



#### Dao层：UserMapper

```java
/*
        1.根据用户ID查询关联的角色信息       多个角色
     */
    List<Role> findUserRelationRoleById(int id);
```

```xml
<!--    1.根据用户id查询关联的角色信息  List<Role> findUserRelationRoleById(int id);
                根据角色表和中间表进行两张表的关联查询就ok，因为已经有user_id 了
    -->
    <select id="findUserRelationRoleById" parameterType="int" resultType="role">
        SELECT * FROM roles r INNER JOIN user_role_relation ur ON r.id = ur.role_id WHERE ur.user_id = #{userId}
    </select>
```



#### Service层：UserService

```java
/*
		获取用户拥有的角色	
        分配角色（回显）
     */
    List<Role> findUserRelationRoleById(int id);
```

```java
/*
        分配角色（回显）
     */
    @Override
    public List<Role> findUserRelationRoleById(int id) {
        return userMapper.findUserRelationRoleById(id);
    }
```



#### Web层：UserController

```java
/*
	根据用户id 获取用户拥有的角色
        分配角色（回显）
     */
    @RequestMapping("/findUserRoleById")
    public ResponseResult findUserRelationRoleById(int id){

        List<Role> roleList = userService.findUserRelationRoleById(id);

        return new ResponseResult(true,200,"分配角色回显成功",roleList);
    }
```



#### Postman测试接口





### 3. 分配角色

其实是向中间表添加记录

#### 1.1 需求分析：

需求：在分配角色页面，点击确定按钮，真正实现用户角色关联



#### 1.2 查看接口文档，进行编码

查看接口文档



#### Dao层：UserMapper

注意：在向中间表添加记录之前，要先根据用户ID先把已经存在的关联关系先删除，再重新建立该用户与角色的关联关系。也就是先删除再建立



```java
/*
        根据用户ID清空中间表
     */
    void deleteUserContextRole(Integer userId);


    /*
        分配角色，就是向中间表添加记录
     */
    void userContextRole(User_Role_relation user_role_relation);

```

```xml
<!--    根据userId清空中间表关联关系  void deleteUserContextRole(Integer userId);-->
    <delete id="deleteUserContextRole" parameterType="int">
        delete from user_role_relation where user_id = #{userId}
    </delete>


<!--    分配角色  本质就是向中间表添加记录-->
    <insert id="userContextRole" parameterType="user_role_relation">
        insert into user_role_relation values (null,#{userId},#{roleId},#{createdTime},#{updatedTime},#{createdBy},#{updatedby});
    </insert>
```



#### Service层：UserService

```java
/*
	用户关联角色
        分配角色，就是向中间表添加记录
     */
    void userContextRole(UserVo userVo);
```

```java
/*
        分配角色，就是向中间表添加记录
     */
    @Override
    public void userContextRole(UserVo userVo) {

        // 1.根据用户ID 清空中间表关联关系
        userMapper.deleteUserContextRole(userVo.getUserId());

        // 2.再重新建立关联关系
        for (Integer roleId : userVo.getRoleIdList()) {

            // 封装数据
            User_Role_relation user_role_relation = new User_Role_relation();

            user_role_relation.setUserId(userVo.getUserId());
            user_role_relation.setRoleId(roleId);
            Date date = new Date();
            user_role_relation.setCreatedTime(date);
            user_role_relation.setUpdatedTime(date);
            user_role_relation.setCreatedBy("system");
            user_role_relation.setUpdatedby("system");

            userMapper.userContextRole(user_role_relation);
        }
    }
```



#### Web层：UserController

```java
/*
        分配角色
     */
    @RequestMapping("/userContextRole")
    public ResponseResult userContextRole(@RequestBody UserVo userVo){

        userService.userContextRole(userVo);

        return new ResponseResult(true,200,"分配角色成功",null);
    }

```



#### Postman测试接口





### 4. 动态菜单显示

#### 1.1 需求分析

需求：用户登录成功后，要根据用户所拥有的的权限信息（菜单列表），进行菜单列表动态展示

分析：用户-菜单：没有直接的关联关系

​		   用户-角色：有直接的关联关系：多对多

​		   角色-菜单：有直接的关联关系：多对多

​    	   角色-资源：有直接的关联关系：多对多



**查询思路：首先要根据用户信息查询关联的角色信息**

​					**其次，根据查询出来的角色信息再去查询角色关联的菜单信息（父子菜单都要进行查询），还要根据查询出来的角色信息查询角色关联的资源信息**



#### 1.2 查看接口文档，进行编码

查看接口文档



#### Dao层：UserMapper

```java
/*
        1.根据用户ID查询关联的角色信息       多个角色
     */
    List<Role> findUserRelationRoleById(int id);



    /*
        2.根据角色ID，查询角色所拥有的顶级菜单(-1)
     */
    // 多个角色对应的ID值
    List<Menu> findParentMenuByRoleId(List<Integer> ids);


    /*
        3.根据PID，父菜单信息，查询子菜单信息
     */
    List<Menu> findSubMenuByPid(Integer pid);


    /*
        4.获取用户拥有的资源权限信息，其实是根据角色信息去查询资源
     */
    // 根据角色Id进行查询
    List<Resource> findResourceByRoleId(List<Integer> ids);
```

```xml
<!--    1.根据用户id查询关联的角色信息  List<Role> findUserRelationRoleById(int id);
                根据角色表和中间表进行两张表的关联查询就ok，因为已经有user_id 了
    -->
    <select id="findUserRelationRoleById" parameterType="int" resultType="role">
        SELECT * FROM roles r INNER JOIN user_role_relation ur ON r.id = ur.role_id WHERE ur.user_id = #{userId}
    </select>


<!--    2.根据角色ID，查询角色所拥有的顶级菜单(-1)
            根据角色ID，查询角色所拥有的顶级菜单(-1)  多对多
-->
    <select id="findParentMenuByRoleId" parameterType="java.util.List" resultType="Menu">
        SELECT
        DISTINCT m.*
        FROM
        roles r INNER JOIN role_menu_relation rm ON r.id = rm.role_id
        INNER JOIN menu m ON m.id =rm.menu_id
        WHERE
        m.parent_id = -1 AND r.id IN
        <foreach collection="list" item="item" open="(" separator="," close=")">
            #{item}
        </foreach>

    </select>


<!--    3.根据PID，父菜单信息，查询子菜单信息
            查询顶级菜单所关联的子级菜单
-->
    <select id="findSubMenuByPid" parameterType="int" resultType="menu">
        SELECT * FROM menu WHERE parent_id = #{pid}
    </select>


<!--    4.获取用户所拥有的资源权限信息
            根据角色ID集合，查询角色所拥有的资源信息	多对多
 -->
    <select id="findResourceByRoleId" parameterType="java.util.List" resultType="Resource">
        SELECT
        DISTINCT r.*
        FROM
        resource r INNER JOIN role_resource_relation rr ON r.id = rr.resource_id
        INNER JOIN roles ro ON ro.id = rr.role_id
        WHERE ro.id IN
        <foreach collection="list" item="item" open="(" separator="," close=")">
            #{item}
        </foreach>

    </select>
```



#### Service层：UserService

```java
/*
        获取用户权限，进行菜单动态展示
     */
    ResponseResult getUserPermissions(Integer userId);
```

```java
/*
        获取用户权限信息
     */
    @Override
    public ResponseResult getUserPermissions(Integer userId) {

        // 1.获取当前用户拥有的角色
        List<Role> roleList = userMapper.findUserRelationRoleById(userId);

        // 2.获取角色ID，保存到List集合中
        ArrayList<Integer> roleIds = new ArrayList<>();

        // 遍历保存角色Id
        for (Role role : roleList) {

            roleIds.add(role.getId());
        }

        // 3.根据角色ID查询父菜单
        List<Menu> parentMenu = userMapper.findParentMenuByRoleId(roleIds);


        // 4.查询封装父菜单所关联的子菜单
        // 因为现在查询出来的父菜单信息有很多，每个父菜单都对应一些子菜单
        // 所以要分别查询每个父菜单所关联的子菜单
        for (Menu menu : parentMenu) {
            List<Menu> subMenu = userMapper.findSubMenuByPid(menu.getId());
            // 封装父菜单里面的子菜单
            menu.setSubMenuList(subMenu);
        }


        // 5.获取资源信息
        List<Resource> resourceList = userMapper.findResourceByRoleId(roleIds);


        // 6.封装数据并返回
        Map<String, Object> map = new HashMap<>();
        // 封装好的父菜单及关联的子菜单集合
        map.put("menuList",parentMenu);
        map.put("resourceList",resourceList);

        return new ResponseResult(true,200,"获取用户权限信息成功",map);
    }
```



#### Web层：UserController

```java
/*
        获取用户拥有的菜单权限，进行菜单动态展示
     */
    @RequestMapping("/getUserPermissions")
    public ResponseResult getUserPermissions(HttpServletRequest request){

        // 1.获取请求头中的token信息
        String header_token = request.getHeader("Authorization");

        // 2.获取之前在登录方法中存到session中的token信息
        String session_token = (String) request.getSession().getAttribute("access_token");

        // 3.判断token是否一致
        if (header_token.equalsIgnoreCase(session_token)){
            // 一样的话，表示登录的是同一个用户且在登录状态
            // 1.从session中获取到之前登录方法中存到session中的 用户id
            Integer user_id = (Integer) request.getSession().getAttribute("user_id");

            // 2.调用service，进行菜单信息查询
            return userService.getUserPermissions(user_id);
        } else {
            // 表示token不一致
            return new ResponseResult(false,400,"获取菜单信息失败",null);
        }
    }
```



#### Postman测试接口