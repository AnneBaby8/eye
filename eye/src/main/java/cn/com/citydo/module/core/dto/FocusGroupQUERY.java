package cn.com.citydo.module.core.dto;

import cn.com.citydo.common.PageDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @Author shaodl
 * @create 2021/6/23 06:42
 * @version: 1.0
 * @description:
 */
@Data
public class FocusGroupQUERY extends PageDTO {

    @ApiModelProperty(value = "所属区域")
    private Long zoneId;

    @ApiModelProperty(value = "人员类型")
    private Integer type;

    @ApiModelProperty(value = "姓名")
    private String name;

    @ApiModelProperty(value = "启始日期")
    private String fromDate;

    @ApiModelProperty(value = "截止日期")
    private String endDate;


}
