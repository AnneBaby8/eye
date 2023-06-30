package cn.com.citydo;

import cn.com.citydo.module.basic.entity.Department;
import cn.com.citydo.module.basic.service.DepartmentService;
import cn.com.citydo.thirdparty.huawei.service.FfmpegService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;

@SpringBootTest
@RunWith(SpringRunner.class)
public class DepartmentTest {

    @Autowired
    private DepartmentService departmentService;

    @Test
    public void test() {
        Map<String, Department> map = departmentService.getParentDepartments(120110003016L);
        System.out.println("------------");
    }
}
