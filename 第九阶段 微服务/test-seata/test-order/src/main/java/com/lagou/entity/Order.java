package com.lagou.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @BelongsProject test-seata
 * @Author lengy
 * @CreateTime 2022/9/21 13:49
 * @Description
 */
@Data
@TableName("torder")
public class Order {

    @TableId
    private String id;
    private int uid;
    private int pid;
}
