package com.lagou.pojo;

import lombok.Data;

import java.io.Serializable;

/**
 * @BelongsProject Spring-Cloud
 * @Author lengy
 * @CreateTime 2022/8/28 12:44
 * @Description Product视图对象
 */
@Data
public class ProductVO implements Serializable {

    private static final long serialVersionUID = -7989111557943343996L;

    // 当前页
    private Integer currentPage;

    // 每页显示条数
    private Integer pageSize;
    // 商品名称
    private String name;
    // 商品分类
    private Integer goodsType;

    // 价格范围
    private double minPrice;

    private double maxPrice;

    // 库存范围
    private Integer minGoodsStock;

    private Integer maxGoodsStock;

    // 上架状态
    private Integer status;
}
