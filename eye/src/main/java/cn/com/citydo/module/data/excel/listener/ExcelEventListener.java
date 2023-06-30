package cn.com.citydo.module.data.excel.listener;

import cn.com.citydo.module.basic.entity.*;
import cn.com.citydo.module.basic.service.*;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.exception.ExcelDataConvertException;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author blackcat
 * @create 2021/5/31 14:05
 * @version: 1.0
 * @description:
 */
@Slf4j
@Data
@Service
public class ExcelEventListener extends AnalysisEventListener<AccountWriter> {

    /**
     * 每1000条数据,存储一次数据库。实际使用可以3000条
     * 然后清除list，方便内存回收
     */
    private static final int BATCH_COUNT = 1000;

    private final List<AccountWriter> list = new ArrayList<>();


    @Override
    public void invoke(AccountWriter data, AnalysisContext analysisContext) {
        if (data == null) {
            return;
        }
        log.info("解析到一条数据:{}", JSON.toJSONString(data));
        list.add(data);
        if (list.size() >= BATCH_COUNT) {
            saveOrUpdateData(list);
            list.clear();
        }
    }

    public ExcelEventListener(UserService userService, BCryptPasswordEncoder encrypt, UserRoleService userRoleService, UserDepartmentService userDepartmentService, RoleService roleService, DepartmentService departmentService) {
        this.userService = userService;
        this.encrypt = encrypt;
        this.userRoleService = userRoleService;
        this.userDepartmentService = userDepartmentService;
        this.roleService = roleService;
        this.departmentService = departmentService;
    }

    private UserService userService;


    private BCryptPasswordEncoder encrypt;


    private UserRoleService userRoleService;


    private UserDepartmentService userDepartmentService;


    private RoleService roleService;


    private DepartmentService departmentService;

    private void saveOrUpdateData(List<AccountWriter> list) {
        for (AccountWriter accountWriter : list) {
            String departmentCode = accountWriter.getDepartmentCode();
            if (departmentCode.endsWith("000")) {
                departmentCode = departmentCode.substring(0, departmentCode.length() - 3);
            }
            Long departmentId = Long.valueOf(departmentCode);
            Department department = departmentService.getById(departmentId);
            if (department == null) {
                log.error("[{}]", departmentId);
                continue;
            }
            User user = new User();
            user.setNickname(accountWriter.getNikename());
            user.setUsername(accountWriter.getUsername());
            user.setPassword(encrypt.encode("Aa123456"));
            userService.save(user);

            Long userId = user.getId();

            Role role = roleService.getOne(new QueryWrapper<Role>().lambda().eq(Role::getName, accountWriter.getRolename()));
            Long roleId = role.getId();

            UserRole userRole = new UserRole();
            userRole.setRoleId(roleId);
            userRole.setUserId(userId);
            userRoleService.save(userRole);

            UserDepartment userDepartment = new UserDepartment();
            userDepartment.setUserId(userId);

            userDepartment.setDepartmentId(departmentId);
            userDepartmentService.save(userDepartment);
        }

    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        saveOrUpdateData(list);
        log.info("所有人员数据存储完成-------------------->");
    }

    @Override
    public void onException(Exception exception, AnalysisContext context) {
        log.error("解析失败，但是继续解析下一行:{}", exception.getMessage());
        // 如果是某一个单元格的转换异常 能获取到具体行号
        // 如果要获取头的信息 配合invokeHeadMap使用
        if (exception instanceof ExcelDataConvertException) {
            ExcelDataConvertException excelDataConvertException = (ExcelDataConvertException) exception;
            log.error("第{}行，第{}列解析异常，数据为:{}", excelDataConvertException.getRowIndex(),
                    excelDataConvertException.getColumnIndex(),
                    excelDataConvertException.getCellData());
        }
    }

}
