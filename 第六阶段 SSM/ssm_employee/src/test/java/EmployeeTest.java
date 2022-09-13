import com.lagou.domain.Employee;
import com.lagou.service.EmployeeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;
import java.util.List;

/**
 * @author zs
 * @date 2022/6/26 22:26
 * @description
 */
// spring整合junit进行测试
@RunWith(SpringJUnit4ClassRunner.class) // 替换运行环境
@ContextConfiguration("classpath:applicationContext.xml")   // 加载Spring核心配置文件路径，并整合mybatis
public class EmployeeTest {

    @Autowired  // 根据类型完成自动注入
    private EmployeeService employeeService;

    @Test
    public void testEmployeeFind(){

        // service调用Dao，从而执行对Employee表的查询操作，实现了Spring整合mybatis
        List<Employee> allWithDept = employeeService.findAllWithDept();
        for (Employee employee : allWithDept) {
            System.out.println(employee);
        }
    }


    @Test
    public void testEmployeeSave(){

        // 调用Dao，测试添加
        Employee employee = new Employee();
        employee.setEmp_name("蔡徐坤");
        employee.setJob_name("练习生");
        employee.setJoin_date(new Date());
        employee.setTelephone("18694283266");
        employee.setDept_id(1);
        employeeService.save(employee);

    }

}
