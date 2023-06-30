package cn.com.citydo.module.core.dto;

import cn.com.citydo.common.PageDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author blackcat
 * @create 2021/6/1 9:11
 * @version: 1.0
 * @description:
 */
@Data
public class DeviceQUERY extends PageDTO {

    @ApiModelProperty(value = "社区id")
    private Long socialId;
}
