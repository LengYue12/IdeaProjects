package com.lagou.pojo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @BelongsProject springbootdemo
 * @Author lengy
 * @CreateTime 2022/8/12 21:55
 * @Description
 */
@Component
public class Student {

    @Value("${person.id}")
    private String number;
    @Value("${person.name}")
    private String name;

    //@Value("${person.hobby}")
    private List<String> hobby;

    //@Value("${person.map}")
    private Map<String,String> map;

    public List<String> getHobby() {
        return hobby;
    }

    public void setHobby(List<String> hobby) {
        this.hobby = hobby;
    }

    public Map<String, String> getMap() {
        return map;
    }

    public void setMap(Map<String, String> map) {
        this.map = map;
    }

    @Override
    public String toString() {
        return "Student{" +
            "number=" + number +
            ", name=" + name +
            '}';
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
