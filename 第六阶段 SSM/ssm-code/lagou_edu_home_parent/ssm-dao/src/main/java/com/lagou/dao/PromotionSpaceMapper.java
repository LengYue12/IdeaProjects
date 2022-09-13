package com.lagou.dao;

import com.lagou.entity.PromotionSpace;

import java.util.List;

/**
 * @author zs
 * @date 2022/6/30 18:29
 * @description
 */
public interface PromotionSpaceMapper {

    /*
        获取所有广告位
     */
    public List<PromotionSpace> findAllPromotionSpace();

    /*
        添加广告位
     */
    void savePromotionSpace(PromotionSpace promotionSpace);

    /*
        根据ID查询广告位信息
     */
    PromotionSpace findPromotionSpaceById(int id);

    /*
        修改广告位名称
     */
    void updatePromotionSpace(PromotionSpace promotionSpace);
}
