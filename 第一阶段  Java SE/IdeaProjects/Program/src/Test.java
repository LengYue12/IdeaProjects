// 测试类
public class Test {

    public static void main(String[] args) {

        // 创建Manager类的对象并赋值
        Manager manager = new Manager(/*"一龙" , 123, 15000, 6000*/);
        // 调用set方法
        manager.setName("一龙");
        manager.setId(123);
        manager.setSalary(15000);
        manager.setBonus(6000);
        // 调用work方法
        manager.work();

        System.out.println("------------------------------------------");
        // 创建Coder类的对象并赋值
        Coder coder = new Coder(/*"方便" , 135, 10000*/);
        // 调用set方法
        coder.setName("方便");
        coder.setId(135);
        coder.setSalary(10000);
        // 调用work方法
        coder.work();
    }
}
