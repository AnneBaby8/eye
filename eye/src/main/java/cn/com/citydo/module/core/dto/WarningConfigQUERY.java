package cn.com.citydo.module.core.dto;

import cn.com.citydo.common.PageDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author blackcat
 * @create 2021/6/1 13:51
 * @version: 1.0
 * @description:
 */
@Data
public class WarningConfigQUERY extends PageDTO {


    @ApiModelProperty(value = "名称")
    private String name;
}
