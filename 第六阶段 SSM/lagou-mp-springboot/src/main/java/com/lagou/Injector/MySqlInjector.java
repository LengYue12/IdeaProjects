package com.lagou.Injector;

import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.injector.DefaultSqlInjector;

import java.util.List;

/**
 * @BelongsProject lagou-mybatis-plus
 * @Author lengy
 * @CreateTime 2022/8/20 20:52
 * @Description 自定义sql注入器
 */
public class MySqlInjector extends DefaultSqlInjector {


    @Override
    public List<AbstractMethod> getMethodList() {

        List<AbstractMethod> methodList = super.getMethodList();


        // 扩充自定义方法
        //List<AbstractMethod> list = new ArrayList<>();
        methodList.add(new FindAll());

        return methodList;
    }
}
