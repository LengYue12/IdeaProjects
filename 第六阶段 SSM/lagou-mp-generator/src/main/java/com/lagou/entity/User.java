package com.lagou.entity;
import lombok.Data;
import java.io.Serializable;

/**
* <p>
    * 
    * </p>
*
* @author lengyue
* @since 2022-08-20
*/
    @Data
    public class User implements Serializable {

    private static final long serialVersionUID = 1L;

            /**
            * 姓名
            */
    private String name;

            /**
            * 年龄
            */
    private Integer age;

            /**
            * 邮箱
            */
    private String email;

    private String userName;

    private Integer version;

    private Integer deleted;


}
