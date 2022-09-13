package com.lagou.dao.impl;

import com.lagou.dao.EmpDAO;
import com.lagou.entity.Emp;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.regex.Pattern;

/**
 * @BelongsProject mongo-com.lagou.test
 * @Author lengy
 * @CreateTime 2022/8/1 22:28
 * @Description 实现类
 */
@Repository("empDao")
public class EmpDAOImpl implements EmpDAO {

    @Autowired
    private MongoTemplate mongoTemplate;


    @Override
    public void save(Emp emp) {
        mongoTemplate.insert(emp);
    }

    @Override
    public void delete(String id) {
        Query query = new Query(Criteria.where("_id").is(id));

        // 要删除的记录和要操作的集合
        DeleteResult remove = mongoTemplate.remove(query, Emp.class);
        long i = remove.getDeletedCount();
        if (i > 0) {
            System.out.println("删除成功");
        }
    }

    @Override
    public void update(Emp emp) {

        // 查询被修改的对象
        Query query = new Query(Criteria.where("_id").is(emp.getId()));
        // 设置全新的数据对象
        Update update = new Update();
        update.set("name",emp.getName());
        update.set("age",emp.getAge());
        update.set("sex",emp.getSex());
        update.set("job",emp.getJob());
        update.set("salary",emp.getSalary());
        UpdateResult upsert = mongoTemplate.upsert(query, update, Emp.class);
        long i = upsert.getModifiedCount();
        if (i > 0) {
            System.out.println("修改成功");
        }
    }

    // 精准查询
    @Override
    public Emp findById(String id) {
        return mongoTemplate.findById(id,Emp.class);
    }

    // 模糊查询以 【^】开始 以【$】结束，【.*】相当于MySQL中的%
    // 分页+模糊
    @Override
    public List<Emp> findListPage(Integer pageIndex, Integer pageSize, String name) {
        Query query = new Query();

        if (!StringUtils.isEmpty(name)) {
            // 使用正则拼装模糊查询的条件                    模糊的关键字
            String regx = String.format("%s%s%s", "^.*", name, ".*$");

            // 构建正则条件对象，Pattern.CASE_INSENSITIVE忽略正则中的大小写
            Pattern pattern = Pattern.compile(regx, Pattern.CASE_INSENSITIVE);

            // 拼条件
            query.addCriteria(Criteria.where("name").regex(pattern));
        }

        // 获取总数量
        long count = mongoTemplate.count(query, Emp.class);
        System.out.println("总数量：" + count);

        // 分页查询
        List<Emp> emps = mongoTemplate.find(query.skip((pageIndex-1)*pageSize).limit(pageSize), Emp.class);

        return emps;
    }
}
