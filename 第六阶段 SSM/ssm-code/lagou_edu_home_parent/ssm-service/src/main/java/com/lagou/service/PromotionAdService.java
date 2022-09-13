package com.lagou.service;

import com.github.pagehelper.PageInfo;
import com.lagou.entity.PromotionAd;
import com.lagou.entity.PromotionAdPageVO;

/**
 * @author zs
 * @date 2022/6/30 20:46
 * @description
 */
public interface PromotionAdService {

    /*
            分页查询广告信息
         */
    PageInfo<PromotionAd> findAllPromotionAdByPage(PromotionAdPageVO promotionAdPageVO);


    /*
        新建广告
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
    void updatePromotionAdStatus(int id, int status);
}
