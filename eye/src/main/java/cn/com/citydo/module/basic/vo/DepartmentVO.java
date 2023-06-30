package cn.com.citydo.module.basic.vo;

import cn.com.citydo.module.basic.entity.Department;
import lombok.Data;

/**
 * @Author blackcat
 * @create 2021/5/20 16:41
 * @version: 1.0
 * @description:
 */
@Data
public class DepartmentVO extends Department {
    /**
     * 区名称
     */
    private String areaName;
    /**
     * 街道名称
     */
    private String streetName;
    /**
     * 社区名称
     */
    private String socialName;
}
