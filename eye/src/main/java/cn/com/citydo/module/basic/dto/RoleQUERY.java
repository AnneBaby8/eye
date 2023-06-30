package cn.com.citydo.module.basic.dto;

import cn.com.citydo.common.PageDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author blackcat
 * @create 2021/5/31 9:13
 * @version: 1.0
 * @description:
 */
@Data
public class RoleQUERY extends PageDTO {

    @ApiModelProperty(value = "角色名")
    private String name;

    @ApiModelProperty(value = "描述")
    private String description;
}
