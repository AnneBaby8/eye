package cn.com.citydo.module.basic.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author blackcat
 * @create 2021/6/16 10:09
 * @version: 1.0
 * @description:
 */
@Data
public class AppLoginRequest {

    @ApiModelProperty(value = "手机号")
    private String tel;

    @ApiModelProperty(value = "外部用户id")
    private String outUserId;

    @ApiModelProperty(value = "token")
    private String token;
}
