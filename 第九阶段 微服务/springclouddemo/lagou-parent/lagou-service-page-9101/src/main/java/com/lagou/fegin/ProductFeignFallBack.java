package com.lagou.fegin;

import com.lagou.common.pojo.Products;
import org.springframework.stereotype.Component;

/**
 * @BelongsProject lagou-parent
 * @Author lengy
 * @CreateTime 2022/8/23 21:23
 * @Description 熔断器触发之后的回调逻辑
 * 自定义实现类 实现 Feign接口   回退逻辑
 */
@Component
public class ProductFeignFallBack implements ProductFeign {
    @Override
    public Products findById(Integer id) {
        return null;
    }

    // 熔断之后的回退逻辑
    @Override
    public String getPort() {
        return "-1";
    }
}
