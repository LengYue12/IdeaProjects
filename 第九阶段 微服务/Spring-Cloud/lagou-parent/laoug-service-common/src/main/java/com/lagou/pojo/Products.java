package com.lagou.pojo;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("products")
public class Products {

  @TableId(type = IdType.AUTO)
  private Long pid;
  private String name;
  private double price;
  private String pDesc;
  private Integer status;
  private Integer goodsStock;
  private Integer goodsType;
}
