package cn.com.citydo.module.basic.vo;

import cn.com.citydo.module.basic.entity.Role;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @Author blackcat
 * @create 2021/6/3 17:20
 * @version: 1.0
 * @description:
 */
@Data
public class UserDetailVO {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "账号")
    private String username;

    @ApiModelProperty(value = "姓名")
    private String nickname;

    private List<Long> roleList;

    private Long departmentId;
}
