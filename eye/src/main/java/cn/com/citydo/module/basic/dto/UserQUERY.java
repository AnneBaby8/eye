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
public class UserQUERY extends PageDTO {

    @ApiModelProperty(value = "账号")
    private String username;

    @ApiModelProperty(value = "姓名")
    private String nickname;

    @ApiModelProperty(value = "角色id")
    private Long roleId;
}
