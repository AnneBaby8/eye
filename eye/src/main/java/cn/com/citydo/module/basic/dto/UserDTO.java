package cn.com.citydo.module.basic.dto;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @Author blackcat
 * @create 2021/5/25 14:22
 * @version: 1.0
 * @description:
 */
@Data
public class UserDTO {


    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "账号")
    private String username;

    @ApiModelProperty(value = "姓名")
    private String nickname;

    private List<Long> roleList;

    private Long  departmentId;
}
