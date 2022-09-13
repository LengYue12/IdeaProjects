import com.lagou.dao.DeptDao;
import com.lagou.domain.Dept;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @author zs
 * @date 2022/6/26 20:55
 * @description
 */
/*
    测试类
 */
public class DeptTest {

    @Test
    public void testMybatis() throws IOException {

        // 加载核心配置文件，获取输入流
        InputStream resourceAsStream = Resources.getResourceAsStream("SqlMapConfig.xml");

        // 获取SqlSessionFactory工厂对象，用来创建SqlSession会话对象
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);

        // 获取SqlSession会话对象
        SqlSession sqlSession = sqlSessionFactory.openSession();

        // 借助Mybatis的API通过sqlSession.getMapper 获取mapper接口代理对象
        DeptDao mapper = sqlSession.getMapper(DeptDao.class);

        // 执行方法
        List<Dept> all = mapper.findAll();

        // 遍历打印
        for (Dept dept : all) {
            System.out.println(dept);
        }

        // 释放资源
        sqlSession.close();
    }
}
