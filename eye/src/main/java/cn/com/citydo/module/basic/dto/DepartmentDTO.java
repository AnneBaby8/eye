package cn.com.citydo.module.basic.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author blackcat
 * @create 2021/5/20 16:41
 * @version: 1.0
 * @description:
 */
@Data
@ApiModel(value="Department请求参数")
public class DepartmentDTO {

    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "部门名称")
    private String name;

    @ApiModelProperty(value = "上级部门id")
    private Long parentId;
}
