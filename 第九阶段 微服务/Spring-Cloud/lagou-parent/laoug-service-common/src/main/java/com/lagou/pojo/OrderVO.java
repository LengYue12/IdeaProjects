package com.lagou.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @BelongsProject Spring-Cloud
 * @Author lengy
 * @CreateTime 2022/8/28 14:20
 * @Description 视图对象
 */
@Data
public class OrderVO implements Serializable {
    private static final long serialVersionUID = -6071279465424156101L;

    // 当前页
    private Integer currentPage;

    // 每页显示条数
    private Integer pageSize;

    // 订单创建时间范围
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date startTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date endTime;


    // 订单状态（0未支付、1已支付、-1已失效、已删除2）
    private Integer state;
}
