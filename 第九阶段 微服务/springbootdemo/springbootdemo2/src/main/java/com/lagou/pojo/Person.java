package com.lagou.pojo;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @BelongsProject springbootdemo
 * @Author lengy
 * @CreateTime 2022/8/12 21:25
 * @Description 人类
 */
@Component
// 将配置文件中所有以person开头的配置信息注入到当前类中
// 前提1：必须保证配置文件中person.xxx与当前person类的属性名一致
// 前提2：必须保证当前person类中的属性都具有set方法
@ConfigurationProperties(prefix = "person")
public class Person {
    private int id; //id
    private String name;    // 名称
    private List hobby;     // 爱好
    private String[] family;    // 家庭成员
    private Map map;
    private Pet pet;    // 宠物

    @Override
    public String toString() {
        return "Person{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", hobby=" + hobby +
            ", family=" + Arrays.toString(family) +
            ", map=" + map +
            ", pet=" + pet +
            '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List getHobby() {
        return hobby;
    }

    public void setHobby(List hobby) {
        this.hobby = hobby;
    }

    public String[] getFamily() {
        return family;
    }

    public void setFamily(String[] family) {
        this.family = family;
    }

    public Map getMap() {
        return map;
    }

    public void setMap(Map map) {
        this.map = map;
    }

    public Pet getPet() {
        return pet;
    }

    public void setPet(Pet pet) {
        this.pet = pet;
    }
}
