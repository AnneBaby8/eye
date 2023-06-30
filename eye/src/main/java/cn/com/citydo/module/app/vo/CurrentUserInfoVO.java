package cn.com.citydo.module.app.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CurrentUserInfoVO {

    /** 归属社区 */
    @ApiModelProperty(value = "community")
    private String community;
    /** 归属网格 */
    @ApiModelProperty(value = "gridding")
    private String gridding;
    /** 姓名 */
    @ApiModelProperty(value = "name")
    private String name;

}
