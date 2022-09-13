package com.lagou.TestFastJSON;

import com.alibaba.fastjson.JSON;
import com.lagou.utils.DateUtils;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author 张舒
 * @date 2022/5/12 16:22
 * @description
 */
public class TestFastJSON {

    // Java对象转为JSON
    @Test
    public void javaBeanToJSON(){

        // 创建Person对象
        Person p = new Person("马云",40, DateUtils.getDateFormart());

        // 使用JSON对象的 toString方法将对象转为JSON数据
        String jsonString = JSON.toJSONString(p);

        System.out.println(jsonString); // {"age":40,"birthday":"2022-05-12 16:25:55","userName":"马云"}
    }

    // List集合转JSON
    @Test
    public void ListToJSON(){

        // 创建Person对象
        Person p1 = new Person("唐心",26,DateUtils.getDateFormart());
        Person p2 = new Person("孙笑川",30,DateUtils.getDateFormart());
        Person p3 = new Person("蔡徐坤",25,DateUtils.getDateFormart());

        List<Person> list = new ArrayList();

        Collections.addAll(list,p1,p2,p3);

        String jsonString = JSON.toJSONString(list);

        System.out.println(jsonString);
        // [{"age":26,"birthday":"2022-05-12 16:30:29","userName":"唐心"},{"age":30,"birthday":"2022-05-12 16:30:29","userName":"孙笑川"},{"age":25,"birthday":"2022-05-12 16:30:29","userName":"蔡徐坤"}]
    }

    // JSON转Java对象
    @Test
    public void JSONToJavaBean(){

        String json = "{\"age\":15,\"birthday\":\"2022-05-07 20:02:25\",\"userName\":\"大雄\"}";

        Person p = JSON.parseObject(json, Person.class);

        System.out.println(p);
    }

    // JSON转List集合
    @Test
    public void JSONToList(){

        String json = "[{\"age\":15,\"birthday\":\"2022-05-07 20:06:15\",\"userName\":\"大雄\"},{\"age\":15,\"birthday\":\"2022-05-07 20:06:16\",\"userName\":\"大雄\"},{\"age\":15,\"birthday\":\"2022-05-07 20:06:16\",\"userName\":\"大雄\"}]";

        List<Person> list = JSON.parseArray(json, Person.class);

        // [Person(userName=大雄, age=15, birthday=2022-05-07 20:06:15), Person(userName=大雄, age=15, birthday=2022-05-07 20:06:16), Person(userName=大雄, age=15, birthday=2022-05-07 20:06:16)]
        System.out.println(list);
    }
}
