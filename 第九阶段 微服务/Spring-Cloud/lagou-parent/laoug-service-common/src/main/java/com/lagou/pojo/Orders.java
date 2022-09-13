package com.lagou.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * (Orders)实体类
 *
 * @author LengYue
 * @since 2022-08-28 14:09:44
 */
@Data
@TableName("orders")
public class Orders implements Serializable {
    private static final long serialVersionUID = -89390384665113652L;

    @TableId(type = IdType.AUTO)
    private Integer oid;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date orderTime;
    
    private String total;
    
    private Integer state;
}

