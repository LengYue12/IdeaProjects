package com.lagou.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @BelongsProject Spring-Cloud
 * @Author lengy
 * @CreateTime 2022/8/28 12:44
 * @Description
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseResult implements Serializable {
    private static final long serialVersionUID = -6687193235432100574L;
    private Boolean success;
    private Integer state;
    private String message;
    private Object content;
}
