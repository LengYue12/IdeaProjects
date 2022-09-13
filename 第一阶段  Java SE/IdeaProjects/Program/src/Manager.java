// 编写项目经理类
public class Manager {
    // 私有成员变量
    // 姓名
    private String name;
    // 工号
    private int id;
    // 工资
    private int salary;
    // 奖金
    private int bonus;

    // 无参空构造方法
    public Manager() {
    }
    // 有参构造方法
    public Manager(String name, int id, int salary, int bonus) {
        setName(name);
        setId(id);
        setSalary(salary);
        setBonus(bonus);
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
        this.id = id;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public int getBonus() {
        return bonus;
    }

    public void setBonus(int bonus) {
        this.bonus = bonus;
    }

    // 定义工作行为方法
    public void work(){
        System.out.println("工号为" + getId() + "，基本工资为" + getSalary() + "，奖金为" + getBonus() + "，的项目经理" + getName() + "正在努力地做着管理工作，分配任务，检查员工提交上来的代码...");
    }
}
