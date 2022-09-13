// 编写程序员类
public class Coder {

    // 编写姓名成员变量
    private String name;
    // 编写工号成员变量
    private int id;
    // 编写工资成员变量
    private int salary;

    // 无参空构造方法
    public Coder() {
    }

    // 有参构造方法
    public Coder(String name, int id, int salary) {
        setName(name);
        setId(id);
        setSalary(salary);
    }

    // setter和getter方法
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        if (id < 0) {
            System.out.println("工号不合理哦！");
        } else {
            this.id = id;
        }
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    // 定义工作方法
    public void work(){
        System.out.println("工号为" + getId() + "，基本工资为" + getSalary() + "，的程序员" + getName() + "，正在努力的写着代码...");
    }
}
