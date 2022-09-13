package com.lagou.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * @BelongsProject ElasticSearchDemo
 * @Author lengy
 * @CreateTime 2022/9/3 18:51
 * @Description
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(indexName = "lagou",type = "product",shards = 3,replicas = 1)
public class Product {
    @Id // 作为Document的id
    private Long id;
    @Field(type = FieldType.Text,analyzer = "ik_max_word")
    private String title;   // 标题
    @Field(type = FieldType.Keyword)
    private String category;    // 分类
    @Field(type = FieldType.Keyword)
    private String brand;   // 品牌
    @Field(type = FieldType.Double)
    private Double price;   // 价格
    @Field(type = FieldType.Keyword,index = false)
    private String images;  // 图片地址
}
