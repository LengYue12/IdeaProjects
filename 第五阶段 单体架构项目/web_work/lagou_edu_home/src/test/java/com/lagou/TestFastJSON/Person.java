package com.lagou.TestFastJSON;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 张舒
 * @date 2022/5/12 16:21
 * @description
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Person {

    // 自定义输出的名称，并且进行输出的排序
    @JSONField(name = "USERNAME",ordinal = 1)
    private String userName;

    @JSONField(name = "AGE",ordinal = 2)
    private int age;

    @JSONField(serialize = false)
    private String birthday;
}
