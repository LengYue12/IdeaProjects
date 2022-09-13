package com.lagou.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @BelongsProject lagou-mybatis-plus
 * @Author lengy
 * @CreateTime 2022/8/19 15:02
 * @Description User实体
 */
@Data   // getter  setter toString
@NoArgsConstructor  // 生成无参构造
@AllArgsConstructor // 生成全参构造
@TableName("user")
public class User {

    private Long id;
    private String name;
    private Integer age;
    private String email;
}
