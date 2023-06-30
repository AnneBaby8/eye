package cn.com.citydo.module.basic.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.groups.Default;
import java.util.List;

@Data
public class RoleGrantDTO {

    @NotNull(message = "角色不能为空",groups = Default.class)
    @ApiModelProperty(value = "角色")
    private Long roleId;

    @NotEmpty(message = "权限不能为空")
    @ApiModelProperty(value = "权限")
    private List<Long> permissionId;
}
