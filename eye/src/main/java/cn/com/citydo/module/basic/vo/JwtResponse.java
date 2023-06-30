package cn.com.citydo.module.basic.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * JWT 响应返回
 * </p>
 * @author blackcat
 * @since 2020-07-21
 */
@Data
public class JwtResponse {
    /**
     * token 字段
     */
    @ApiModelProperty(value = "token")
    private String token;
    /**
     * token类型
     */
    @ApiModelProperty(value = "token类型   其他的接口在请求头添加Authorization, value=tokenType+token 中间有一个空格 ")
    private String tokenType = "Bearer";

    public JwtResponse(String token) {
        this.token = token;
    }
}
