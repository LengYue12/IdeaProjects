package com.lagou;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lagou.mapper.UserMapper;
import com.lagou.pojo.User;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
class LagouMpSpringbootApplicationTests {

    @Autowired
    private UserMapper userMapper;


    @Test
    void testFindAll(){

        List<User> all = userMapper.findAll();
        all.forEach(System.out::println);
    }


    @Test
    void test() {

        List<User> users = userMapper.selectList(null);
        users.forEach(System.out::println);
    }


    /**
     * 测试添加
     */
    @Test
    void testInsert(){

        User user = new User();
        user.setName("曹操");
        user.setAge(40);
        user.setEmail("test2@ll.com");

        // 返回值是受影响的行数
        int insert = userMapper.insert(user);

        System.out.println("id " + user.getId());
        System.out.println("insert = " + insert);

    }


    /**
     * 测试根据ID进行修改
     */
    @Test
    void testUpdateById(){

        User user = new User();
        user.setId(6L);
        user.setAge(30);

        int i = userMapper.updateById(user);
        System.out.println("i = " + i);
    }

    /**
     * 测试根据条件进行修改
     */
    @Test
    void testUpdate(){

        // 1.更新的字段
        User user = new User();
        user.setAge(35);

        // 2.更新的条件
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name","蔡徐坤");

        int i = userMapper.update(user,queryWrapper);
        System.out.println("i = " + i);
    }


    /**
     * 测试根据条件进行修改
     */
    @Test
    void testUpdate2(){

        UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
        // id为6的对象修改age 为40
        updateWrapper.eq("id",6).set("age",40);

        int i = userMapper.update(null, updateWrapper);
        System.out.println("i = " + i);
    }

    /**
     * 根据ID进行删除
     */
    @Test
    public void testDeleteById(){
        int i = userMapper.deleteById(2);
        System.out.println("i = " + i);
    }


    /**
     * 根据columnMap进行删除
     */
    @Test
    public void testDeleteByMap(){

        HashMap<String, Object> map = new HashMap<>();
        map.put("name","蔡徐坤");
        map.put("age",18);


        // 将columnMap中的元素设置为删除的条件，多个条件是and关系
        int i = userMapper.deleteByMap(map);
        System.out.println("i = " + i);
    }


    /**
     * 调用delete进行删除
     */
    @Test
    public void testDelete(){

        User user = new User();
        user.setName("蔡徐坤2");
        user.setAge(18);

        QueryWrapper<User> queryWrapper = new QueryWrapper<>(user);

        //queryWrapper.eq("name","蔡徐坤1").eq("age",18);

        int i = userMapper.delete(queryWrapper);
        System.out.println("i = " + i);
    }


    /**
     * 调用deleteBatchIds进行批量删除
     */
    @Test
    public void testDeleteBatchIds(){

        int i = userMapper.deleteBatchIds(Arrays.asList(10l, 11l));
        System.out.println("i = " + i);
    }


    /**
     * 根据Id进行查询
     */
    @Test
    void testSelectById(){

        User user = userMapper.selectById(2);
        System.out.println("user = " + user);

    }

    /**
     * 根据Id进行批量查询
     */
    @Test
    void testSelectBatchIds(){

        List<User> users = userMapper.selectBatchIds(Arrays.asList(1l, 2l));
        for (User user : users) {
            System.out.println(user);
        }
    }

    /**
     * 测试selectOne
     */
    @Test
    void testSelectOne(){

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name","Tom");

        // 根据条件查询一条记录，如果查询结果超过一条，会报错
        User user = userMapper.selectOne(queryWrapper);
        System.out.println(user);
    }


    /**
     * 根据wrapper条件查询总记录数
     */
    @Test
    void testSelectCount(){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.gt("age",18);  // 查询年龄大于18的

        Integer count = userMapper.selectCount(queryWrapper);
        List<User> users = userMapper.selectList(queryWrapper);
        System.out.println("count = " + count);

        for (User user : users) {
            System.out.println("user = " + user);
        }
    }


    /**
     * 分页查询
     */
    @Test
    public void testSelectPage(){

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.gt("age",18);  // 查询年龄大于18的

        // 第一个参数：当前页  第二个参数：每页显示条数
        Page<User> objectPage = new Page<>(2, 2);

        IPage<User> userIPage = userMapper.selectPage(objectPage, queryWrapper);

        System.out.println("总条数：" + userIPage.getTotal());
        System.out.println("总页数：" + userIPage.getPages());


        System.out.println("分页数据：" + userIPage.getRecords());
    }


    /**
     * 测试自定义方法 findById
     */
    @Test
    void findById(){
        User byId = userMapper.findById((long) 1);

        System.out.println("byId = " + byId);
    }


    /**
     * 测试条件构建器
     */
    @Test
    void testAllEq(){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();

        // 构建map
        HashMap<String, Object> map = new HashMap<>();

        map.put("name","jack");
        map.put("age",null);

        // where name = ? and age is NUll
//        queryWrapper.allEq(map);

        // where name = ?
//        queryWrapper.allEq(map,false);

        // select id,name,age,email AS mail,user_name from tb_user
//        queryWrapper.allEq(false,map,true);

        //  只根据name进行查询
        queryWrapper.allEq((k,v) -> k.equals("name"),map);

        List<User> users = userMapper.selectList(queryWrapper);

        users.forEach(System.out::println);
    }


    /**
     * 基本比较操作
     */
    @Test
    void testWrapper(){

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();

        // where email = ? and age >= ? and name in(?,?)
        queryWrapper.eq("email","test5@baomidou.com")
            .ge("age",20)
            .in("name","蔡徐坤","蔡徐坤12");

        List<User> users = userMapper.selectList(queryWrapper);
        users.forEach(System.out::println);
    }


    /**
     * 模糊查询
     */
    @Test
    void testWrapperLike(){

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();


        queryWrapper.like("name","蔡");

        List<User> users = userMapper.selectList(queryWrapper);
        users.forEach(System.out::println);
    }


    /**
     * 排序查询、逻辑查询、select
     */
    @Test
    void testWrapper2(){

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();

//        queryWrapper.orderByDesc("age");

        queryWrapper.eq("name","jack").or().eq("age",28).select("name");

        List<User> users = userMapper.selectList(queryWrapper);
        users.forEach(System.out::println);
    }




}
