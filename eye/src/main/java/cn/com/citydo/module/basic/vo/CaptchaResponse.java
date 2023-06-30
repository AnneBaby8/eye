package cn.com.citydo.module.basic.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author blackcat
 * @create 2020/11/23 17:07
 * @version: 1.0
 * @description:
 */
@Data
public class CaptchaResponse {

    @ApiModelProperty(value = "uuid")
    private String uuid;
    @ApiModelProperty(value = "验证码的base64 图片")
    private String captcha;
}
