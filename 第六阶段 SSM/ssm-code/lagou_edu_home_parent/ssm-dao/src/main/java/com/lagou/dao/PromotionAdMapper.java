package com.lagou.dao;

import com.lagou.entity.PromotionAd;

import java.util.List;

/**
 * @author zs
 * @date 2022/6/30 20:25
 * @description
 */
public interface PromotionAdMapper {

    /*
        分页查询广告信息
     */
    List<PromotionAd> findAllPromotionAdByPage();


    /*
        添加广告
     */
    void savePromotionAd(PromotionAd promotionAd);

    /*
        根据ID查询广告信息
     */
    PromotionAd findPromotionAdById(int id);

    /*
        修改广告
     */
    void updatePromotionAd(PromotionAd promotionAd);


    /*
        广告动态上下线
     */
    void updatePromotionAdStatus(PromotionAd promotionAd);

}
