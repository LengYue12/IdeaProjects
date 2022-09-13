package com.lagou;

import com.lagou.pojo.Product;
import com.lagou.resposisory.ProductRepository;
import com.lagou.resultMapper.ESSearchResultMapper;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.query.FetchSourceFilter;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EsclientApplicationTests2 {

    @Autowired
    private ElasticsearchTemplate template;


    @Autowired
    private ProductRepository productRepository;


    /**
     * 需求：
     * 查询title中包含小米手机的商品，以价格升序排序，分页查询：每页展示2条，查询第1页。
     * 对查询结果进行聚合分析：获取品牌及个数
     */
    @Test
    public void nativeQuery(){
        // 1.构建一个原生查询器
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        // 2.source过滤
        // 2.1 参数
        // 如果不想执行source过滤，可以注释该行
        queryBuilder.withSourceFilter(new FetchSourceFilter(new String[0],new String[0]));
        // 3.查询条件
        queryBuilder.withQuery(QueryBuilders.matchQuery("title","小米手机"));
        // 4.设置分页并排序规则
        queryBuilder.withPageable(PageRequest.of(0,10, Sort.by(Sort.Direction.DESC, "price")));
        // 5.高亮
        // 指定高亮的字段
        HighlightBuilder.Field field = new HighlightBuilder.Field("title");
        // 高亮的效果
        field.preTags("<font style='color:red'>");
        field.postTags("</font>");
        queryBuilder.withHighlightFields(field);
        // 6.聚合
        queryBuilder.addAggregation(AggregationBuilders.terms("brandAgg").field("brand"));
        // 7.查询
        AggregatedPage<Product> products = template.queryForPage(queryBuilder.build(), Product.class,new ESSearchResultMapper());
        // 获取结果
        long totalElements = products.getTotalElements();
        // 获取页码
        int totalPages = products.getTotalPages();
        // 获取本页的数据集合
        List<Product> content = products.getContent();
        System.out.println(totalElements + "" + totalPages);
        content.stream().forEach(product -> System.out.println(product));
//
//        // 获得聚合的结果
//        Aggregations aggregations = products.getAggregations();
//        Terms terms = aggregations.get("brandAgg");
//        // 获取桶并且遍历桶中的内容
//        terms.getBuckets().forEach(b ->{
//            System.out.println("品牌：" + b.getKeyAsString());
//            System.out.println("个数: "+ b.getDocCount());
//        });

    }



    @Test
    public void findByPrice(){
        List<Product> byPriceBetween = productRepository.findByPriceBetween(2000.00, 4000.00);
        System.out.println(byPriceBetween.size());
    }


    @Test
    public void findAll(){
        Iterable<Product> all = productRepository.findAll();
        all.forEach(System.out::println);
    }

    @Test
    public void findById2(){
        Optional<Product> optional = productRepository.findById(30L);
        // 取出数据
        // orELse方法的作用：如果optional中封装的实体对象为空也就是没有从索引库中查询出匹配的文档，返回orElse方法的参数
        Product defaultResult = new Product();
        defaultResult.setTitle("default data");
        Product product = optional.orElse(defaultResult);
        System.out.println("product = " + product);
    }

    @Test
    public void findById(){
        Optional<Product> optional = productRepository.findById(3L);
        // 取出数据
        // orELse方法的作用：如果optional中封装的实体对象为空也就是没有从索引库中查询出匹配的文档，返回orElse方法的参数
        Product product = optional.orElse(null);
        System.out.println("product = " + product);
    }

    @Test
    public void insertDocument(){
        Product product = new Product(6L,"小米手机","手机","小米",3299.69,"http://image.chuizi.com/1.jpg");
        // 新增
        productRepository.save(product);
        System.out.println("save success");
    }


    @Test
    public void insertDocuments(){
        Product product1 = new Product(2L,"坚果手机","手机","phone",3299.69,"http://image.chuizi.com/1.jpg");
        Product product2 = new Product(3L,"华为手机","手机","phone",3299.69,"http://image.chuizi.com/1.jpg");
        Product product3 = new Product(4L,"苹果手机","手机","phone",3299.69,"http://image.chuizi.com/1.jpg");
        Product product4 = new Product(5L,"索引手机","手机","phone",3299.69,"http://image.chuizi.com/1.jpg");
        // 新增
        List<Product> list = new ArrayList<>();
        list.add(product1);
        list.add(product2);
        list.add(product3);
        list.add(product4);
        productRepository.saveAll(list);
        System.out.println("save success");
    }



    @Test
    public void check(){
        System.out.println("----------------" + template);
    }

    @Test
    public void createIndex(){
        // 创建索引的方法
        template.createIndex(Product.class);
    }

    @Test
    public void createType(){
        // 创建类型映射
        template.putMapping(Product.class);
    }
}
