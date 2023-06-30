package cn.com.citydo.module.basic.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * <p>
 * 创建角色入参
 * </p>
 *
 * @author blackcat
 * @since 2020-07-21
 */
@Data
public class RoleDTO {

    private Long id;

    /**
     * 角色名称
     */
    @NotBlank(message = "角色名称不能为空")
    @ApiModelProperty(value = "角色名称")
    private String name;

    /**
     * 角色描述
     */
    @NotBlank(message = "角色描述不能为空")
    @ApiModelProperty(value = "角色描述")
    private String description;
}
