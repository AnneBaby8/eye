package cn.com.citydo.module.basic.conver;

import cn.com.citydo.module.basic.dto.DepartmentDTO;
import cn.com.citydo.module.basic.entity.Department;
import org.springframework.beans.BeanUtils;
import org.springframework.core.convert.converter.Converter;

/**
 * @Author blackcat
 * @create 2021/5/20 16:48
 * @version: 1.0
 * @description:
 */
public class DepartmentDTOConver implements Converter<DepartmentDTO, Department> {

    @Override
    public Department convert(DepartmentDTO departmentDTO) {
        Department department = new Department();
        BeanUtils.copyProperties(departmentDTO, department);
        return department;
    }
}
