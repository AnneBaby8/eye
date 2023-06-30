package cn.com.citydo.module.core.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @Author blackcat
 * @create 2021/6/1 13:51
 * @version: 1.0
 * @description:
 */
@Data
public class WarningConfigDTO {


    private Long id;

    @ApiModelProperty(value = "名称")
    @NotNull
    private String name;

    @ApiModelProperty(value = "预警编号")
    private String warnNo;

    @ApiModelProperty(value = "描述")
    private String description;


    @ApiModelProperty(value = "状态，生效-0,暂停-1")
    private Integer status;


    @ApiModelProperty(value = "预警类型，0-通道占用 1-垃圾堆栈 2-帮扶人员 3-重点人员 4-空巢老人 5-人群密集 6-污水跑冒 7-机动车违停 8-非机动车违停")
    @NotNull
    private String warnType;
}
