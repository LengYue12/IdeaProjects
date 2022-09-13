package com.lagou.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lagou.dao.PromotionAdMapper;
import com.lagou.entity.PromotionAd;
import com.lagou.entity.PromotionAdPageVO;
import com.lagou.service.PromotionAdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author zs
 * @date 2022/6/30 20:50
 * @description
 */
@Service
public class PromotionAdServiceImpl implements PromotionAdService {

    @Autowired
    private PromotionAdMapper promotionAdMapper;

    @Override
    public PageInfo<PromotionAd> findAllPromotionAdByPage(PromotionAdPageVO promotionAdPageVO) {

        // 分页查询     在调用mapper方法之前进行     传入当前页 和 每页显示的条数
        PageHelper.startPage(promotionAdPageVO.getCurrentPage(), promotionAdPageVO.getPageSize());

        // 这是查询所有操作，分页查询了.list集合就已经是经过分页查询所查询出来的数据封装了
        List<PromotionAd> list = promotionAdMapper.findAllPromotionAdByPage();

        PageInfo<PromotionAd> pageInfo = new PageInfo<>(list);

        return pageInfo;
    }

    /*
        新建广告
     */
    @Override
    public void savePromotionAd(PromotionAd promotionAd) {

        // 封装数据
        Date date = new Date();
        promotionAd.setCreateTime(date);
        promotionAd.setUpdateTime(date);

        // 调用mapper
        promotionAdMapper.savePromotionAd(promotionAd);
    }


    /*
        根据ID查询广告信息
     */
    @Override
    public PromotionAd findPromotionAdById(int id) {
        return promotionAdMapper.findPromotionAdById(id);
    }


    /*
        修改广告
     */
    @Override
    public void updatePromotionAd(PromotionAd promotionAd) {

        // 封装数据
        promotionAd.setUpdateTime(new Date());

        // 调用mapper
        promotionAdMapper.updatePromotionAd(promotionAd);
    }


    /*
        广告动态上下线
     */
    @Override
    public void updatePromotionAdStatus(int id, int status) {

        // 封装数据
        PromotionAd promotionAd = new PromotionAd();
        promotionAd.setUpdateTime(new Date());
        promotionAd.setId(id);
        promotionAd.setStatus(status);

        // 调用mapper
        promotionAdMapper.updatePromotionAdStatus(promotionAd);
    }
}
