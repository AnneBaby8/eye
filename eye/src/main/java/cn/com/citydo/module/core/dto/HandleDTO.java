package cn.com.citydo.module.core.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author blackcat
 * @create 2021/6/10 10:50
 * @version: 1.0
 * @description:
 */
@Data
public class HandleDTO {
    @ApiModelProperty(value = "角色id 可不传")
    private Long roleId;
    @ApiModelProperty(value = "流程id")
    private Long workflowId;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "自行处置类型 0-正常 1-核实后无情况 2-误报")
    private String selfType;

    @ApiModelProperty(value = "核实图片")
    private String image;
}
