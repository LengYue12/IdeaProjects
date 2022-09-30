package com.lagou.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @BelongsProject test-seata
 * @Author lengy
 * @CreateTime 2022/9/21 13:39
 * @Description
 */
@Data
@TableName("taccount")
public class Account {
    @TableId
    private int id;
    private String name;
    private int score;
}
