import com.lagou.domain.Article;
import com.lagou.mapper.ArticleMapper;
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
 * @date 2022/6/9 20:41
 * @description
 */
public class mybatisTest {

    /*
        一对多嵌套查询：测试  查询文章信息及关联的评论信息时，并实现延迟加载效果
     */
    @Test
    public void test() throws IOException {

        // 模版代码

        // 加载核心配置文件
        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");

        // 获取SqlSessionFactory工厂对象
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);

        // 获取SqlSession会话对象
        SqlSession sqlSession = sqlSessionFactory.openSession();

        // 获取接口代理对象
        ArticleMapper mapper = sqlSession.getMapper(ArticleMapper.class);

        // 使用代理对象调用方法
        List<Article> articleList = mapper.findAll();

        // 遍历文章信息，因为开启了全局延迟加载，所以只打印文章信息
        for (Article article : articleList) {
            System.out.println(article);

            // 要获取文章关联的评论信息了

            // 所以使用article 文章对象来调用 getCommentList 方法，就可以再发起查询SQL，获取到关联的评论信息了
            System.out.println(article.getCommentList());
        }
    }

}
