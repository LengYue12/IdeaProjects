package com.lagou.pojo;

/**
 * @BelongsProject springbootdemo
 * @Author lengy
 * @CreateTime 2022/8/12 21:23
 * @Description 宠物类
 */
public class Pet {

    private String type;    // 品种
    private String name;    // 名称

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Pet{" +
            "type='" + type + '\'' +
            ", name='" + name + '\'' +
            '}';
    }
}
