package com.lagou.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lagou.mapper.OrderMapper;
import com.lagou.pojo.OrderVO;
import com.lagou.pojo.Orders;
import com.lagou.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @BelongsProject Spring-Cloud
 * @Author lengy
 * @CreateTime 2022/8/28 14:12
 * @Description
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Override
    public Orders findById(Integer oid) {
        return orderMapper.selectById(oid);
    }

    @Override
    public IPage<Orders> selectPage(OrderVO orderVO) {

        QueryWrapper<Orders> wrapper = new QueryWrapper<>();
        wrapper.between("order_time",orderVO.getStartTime(),orderVO.getEndTime());
        wrapper.eq("state",orderVO.getState());

        Page<Orders> page = new Page<>(orderVO.getCurrentPage(),orderVO.getPageSize());

        // 分页查询，根据条件查询数据
        return orderMapper.selectPage(page,wrapper);
    }
}
