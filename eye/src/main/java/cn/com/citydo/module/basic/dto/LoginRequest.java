package cn.com.citydo.module.basic.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * <p>
 * 登录请求参数
 * </p>
 *
 * @author blackcat
 * @since 2020-07-21
 */
@Data
public class LoginRequest {

    /**
     * 用户名或邮箱或手机号
     */
    @NotBlank(message = "用户名不能为空")
    @ApiModelProperty(value = "用户名或邮箱或手机号")
    private String usernameOrEmailOrPhone;

    /**
     * 密码
     */
    @NotBlank(message = "密码不能为空")
    @ApiModelProperty(value = "密码")
    private String password;

    /**
     * 记住我
     */
    @ApiModelProperty(value = "是否记住我 可忽略")
    private Boolean rememberMe = false;

    @ApiModelProperty(value = "验证码")
    private String captcha;

    @ApiModelProperty(value = "uuid")
    private String uuid;

}
