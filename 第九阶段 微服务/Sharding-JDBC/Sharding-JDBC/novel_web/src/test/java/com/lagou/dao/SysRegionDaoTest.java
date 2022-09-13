package com.lagou.dao;

import com.lagou.pojo.SysRegion;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @BelongsProject Sharding-JDBC
 * @Author lengy
 * @CreateTime 2022/9/10 18:14
 * @Description
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SysRegionDaoTest {

    @Autowired
    private SysRegionDao sysRegionDao;

    /**
     * 测试插入, 观察更新操作时,是否所有公共表都进行了数据维护
     */
    @Test
    public void testInsert(){
        for (int i = 0; i < 10; i++) {
            sysRegionDao.insertRegion(new SysRegion("新疆"+i,"11",20));
        }
    }
}
