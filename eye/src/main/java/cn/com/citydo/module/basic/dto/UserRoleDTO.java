package cn.com.citydo.module.basic.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @Author blackcat
 * @create 2021/5/25 14:22
 * @version: 1.0
 * @description:
 */
@Data
public class UserRoleDTO {
    @NotNull(message = "部门不能为空" )
    @ApiModelProperty(value = "部门")
    private Long  departmentId;

}
