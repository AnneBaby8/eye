package cn.com.citydo.module.core.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * <p>
 *  网格绑定
 * </p>
 *
 * @author wx227336
 * @Date 2021/6/3 14:37
 * @Version 1.0
 */
@Data
public class CancleBindDTO {
    @NotNull(message = "网格员不能为空")
    @ApiModelProperty(value = "网格员")
    private Long gridMemberId;

    @NotNull(message = "设备不能为空")
    @ApiModelProperty(value = "设备")
    private Long deviceId;
}
