package com.lagou.common.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * (Products)实体类
 *
 * @author LengYue
 * @since 2022-08-21 20:45:46
 */
@Data
@TableName("products")
public class Products implements Serializable {
    private static final long serialVersionUID = -31762864202844171L;

    @TableField
    private Integer id;
    
    private String name;
    
    private String price;
    
    private String flag;
    
    private String goodsDesc;
    
    private String images;
    
    private Integer goodsStock;
    
    private String goodsType;

}

