package cn.com.citydo.module.basic.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @Author blackcat
 * @create 2021/5/25 14:22
 * @version: 1.0
 * @description:
 */
@Data
public class UserVO {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "账号")
    private String username;

    @ApiModelProperty(value = "姓名")
    private String nickname;

    @ApiModelProperty(value = "角色名称")
    private List<String> roleNames;

    @ApiModelProperty(value = "部门名称")
    private List<String> departmentNames;

    @ApiModelProperty(value = "部门名称")
    private String departmentName;
}
