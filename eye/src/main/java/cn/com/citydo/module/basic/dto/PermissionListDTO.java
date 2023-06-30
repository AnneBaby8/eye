package cn.com.citydo.module.basic.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class PermissionListDTO {

    @NotNull(message = "角色不能为空" )
    @ApiModelProperty(value = "角色")
    private Long roleId;
}
