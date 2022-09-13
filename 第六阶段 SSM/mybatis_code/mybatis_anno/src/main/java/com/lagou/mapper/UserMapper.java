package com.lagou.mapper;

import com.lagou.domain.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author zs
 * @date 2022/6/7 22:51
 * @description
 */

@CacheNamespace // 配置了二级缓存
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

    /*
        根据id查询用户
     */
    /*
        根据传递过来的uid查询出关联的用户信息
     */
    @Select("select * from user where id = #{uid}")
    public User findById(Integer uid);



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

}
