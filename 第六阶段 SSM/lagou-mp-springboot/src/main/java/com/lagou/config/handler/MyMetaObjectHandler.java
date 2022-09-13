package com.lagou.config.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

/**
 * @BelongsProject lagou-mybatis-plus
 * @Author lengy
 * @CreateTime 2022/8/20 21:23
 * @Description
 */
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {

        Object version = getFieldValByName("version", metaObject);
        if (version == null) {
            // 该属性为空，可以进行填充
            setFieldValByName("version",1,metaObject);
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {

    }
}
