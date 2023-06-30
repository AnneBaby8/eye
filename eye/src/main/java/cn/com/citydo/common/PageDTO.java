package cn.com.citydo.common;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author blackcat
 * @create 2021/5/31 9:08
 * @version: 1.0
 * @description:
 */
@Data
public class PageDTO {

    @ApiModelProperty(value = "当前页")
    private Integer pageNo = 1;
    @ApiModelProperty(value = "每页数据条数")
    private Integer pageSize = 10;
}
