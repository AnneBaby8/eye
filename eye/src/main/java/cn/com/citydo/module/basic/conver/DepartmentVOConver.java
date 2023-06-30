package cn.com.citydo.module.basic.conver;

import cn.com.citydo.module.basic.entity.Department;
import cn.com.citydo.module.basic.vo.DepartmentVO;
import org.springframework.beans.BeanUtils;
import org.springframework.core.convert.converter.Converter;

/**
 * @Author blackcat
 * @create 2021/5/20 16:50
 * @version: 1.0
 * @description:
 */
public class DepartmentVOConver implements Converter<Department, DepartmentVO> {
    @Override
    public DepartmentVO convert(Department department) {
        DepartmentVO departmentVO = new DepartmentVO();
        BeanUtils.copyProperties(department, departmentVO);
        return departmentVO;
    }
}
