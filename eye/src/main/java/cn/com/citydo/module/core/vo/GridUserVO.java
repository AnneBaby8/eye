package cn.com.citydo.module.core.vo;

import cn.com.citydo.module.basic.entity.User;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 *
 * </p>
 *
 * @author wx227336
 * @Date 2021/7/6 13:22
 * @Version 1.0
 */
@Data
public class GridUserVO extends User {

    @ApiModelProperty(value = "网格id")
    private Long gridId;

    @ApiModelProperty(value = "重复绑定标志")
    private Boolean repeatBindFlag = Boolean.FALSE;
}
