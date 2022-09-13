package com.lagou.pojo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.extension.activerecord.Model;
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
//@TableName("tb_user")
public class User extends Model<User> {

//    @TableId(type = IdType.AUTO)
    private Long id;

    @TableField(select = true) // 查询时，不返回该字段的值
    private String name;
    private Integer age;

    @TableField(value = "email")    // 解决字段名不一致问题
    private String email;

    @TableField(exist = false)  // 该字段在数据库表中不存在
    private String address;

    private String userName;

    @Version
    @TableField(fill = FieldFill.INSERT)    // 指定该属性的生成策略，表示在插入数据时需要对该属性进行填充
    private Integer version;


    @TableLogic     //  逻辑删除字段
    private Integer deleted;

}
