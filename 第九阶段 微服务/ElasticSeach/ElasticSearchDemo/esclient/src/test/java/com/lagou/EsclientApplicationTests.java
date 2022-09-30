package com.lagou;

import com.google.gson.Gson;
import com.lagou.pojo.Product;
import org.apache.http.HttpHost;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EsclientApplicationTests {

    private RestHighLevelClient restHighLevelClient;
    private Gson gson = new Gson();

    /**
     * 初始化客户端
     */
    @Before
    public void init(){
        restHighLevelClient = new RestHighLevelClient(RestClient.builder(
            new HttpHost("127.0.0.1",9201,"http"),
            new HttpHost("127.0.0.1",9202,"http"),
            new HttpHost("127.0.0.1",9203,"http")));
    }

    @Test
    public void matchQuery() throws IOException {
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        // 设置查询类型和查询条件
        searchSourceBuilder.query(QueryBuilders.matchQuery("title","手机"));
        // 调用基础查询方法
        baseQuery(searchSourceBuilder);

    }

    @Test
    public void sourceFilter() throws IOException {
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        // 指定查询条件和查询类型
        searchSourceBuilder.query(QueryBuilders.rangeQuery("price").gte(3000).lte(4300));
        // source过滤，只保留id、title、price
        searchSourceBuilder.fetchSource(new String[]{"id","title","price"},null);
        baseQuery(searchSourceBuilder);
    }

    /**
     * 查询3600-4300 的手机
     */
    @Test
    public void rangeQuery() throws IOException {
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        // 指定查询条件和查询类型
        searchSourceBuilder.query(QueryBuilders.rangeQuery("price").gte(3000).lte(4300));
        baseQuery(searchSourceBuilder);
    }

    public void baseQuery(SearchSourceBuilder searchSourceBuilder) throws IOException {
        // 创建搜索的请求对象
        SearchRequest request = new SearchRequest();

        request.source(searchSourceBuilder);
        // 执行查询
        SearchResponse search = restHighLevelClient.search(request, RequestOptions.DEFAULT);
        // 获得查询结果
        SearchHits hits = search.getHits();
        // 获得文档数据
        SearchHit[] hits1 = hits.getHits();
        for (SearchHit documentFields : hits1) {
            String sourceAsString = documentFields.getSourceAsString();
            // 将JSON反序列化为Product格式
            Product product = gson.fromJson(sourceAsString, Product.class);
            System.out.println("product = " + product);
        }
    }


    @Test
    public void sortAndPage() throws IOException {
        // 创建搜索的请求对象
        SearchRequest request = new SearchRequest();
        // 查询构建工具
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        // 添加查询条件，执行查询类型
        searchSourceBuilder.query(QueryBuilders.matchAllQuery());
        // 执行排序 价格升序排序
        searchSourceBuilder.sort("price", SortOrder.ASC);

        // 分页信息
        // 当前页
        int pageNum = 1;
        // 每页大小
        int pageSize = 3;
        // 起始位置
        int from = (pageNum-1) * pageSize;

        // 设置分页
        // 从哪开始查询
        searchSourceBuilder.from(from);
        // 查询多少
        searchSourceBuilder.size(pageSize);

        baseQuery(searchSourceBuilder);
    }


    @Test
    public void matchAll() throws IOException {
        // 创建搜索的请求对象
        SearchRequest request = new SearchRequest();
        // 查询构建工具
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        // 添加查询条件，执行查询类型
        searchSourceBuilder.query(QueryBuilders.matchAllQuery());
        request.source(searchSourceBuilder);
        // 执行查询
        SearchResponse search = restHighLevelClient.search(request, RequestOptions.DEFAULT);
        // 获得查询结果
        SearchHits hits = search.getHits();
        // 获得文档数据
        SearchHit[] hits1 = hits.getHits();
        for (SearchHit documentFields : hits1) {
            String sourceAsString = documentFields.getSourceAsString();
            // 将JSON反序列化为Product格式
            Product product = gson.fromJson(sourceAsString, Product.class);
            System.out.println("product = " + product);
        }
    }

    /**
     * 插入文档
     */
    @Test
    public void testInsert() throws IOException {
        // 1.文档数据
        Product product = new Product();
        product.setBrand("华为");
        product.setCategory("手机");
        product.setId(1L);
        product.setImages("http://image.huawei.com/1.jpg");
        product.setPrice(5999.99);
        product.setTitle("华为P30");
        // 2.将文档数据转换为JSON
        String source = gson.toJson(product);
        // 3.创建索引请求对象，访问哪个索引库，哪个type，指定文档id
        IndexRequest indexRequest = new IndexRequest("lagou","product",product.getId().toString());
        indexRequest.source(source, XContentType.JSON);
        // 4.发出请求
        IndexResponse indexResponse = restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);
        System.out.println(indexResponse);
    }

    @Test
    public void testQuery() throws IOException {
        // 初始化GetRequest对象
        GetRequest getRequest = new GetRequest("lagou","product","1");
        // 执行查询
        GetResponse getResponse = restHighLevelClient.get(getRequest, RequestOptions.DEFAULT);
        // 取出数据
        String sourceAsString = getResponse.getSourceAsString();
        Product product = gson.fromJson(sourceAsString, Product.class);
        System.out.println(product);

    }


    @Test
    public void testDelete() throws IOException {
        // 初始化DeleteRequest对象
        DeleteRequest deleteRequest = new DeleteRequest("lagou","product","1");
        // 执行删除
        DeleteResponse delete = restHighLevelClient.delete(deleteRequest, RequestOptions.DEFAULT);
        System.out.println(delete);
    }

    /**
     * 关闭资源
     */
    @After
    public void close() throws IOException {
        restHighLevelClient.close();
    }

}
