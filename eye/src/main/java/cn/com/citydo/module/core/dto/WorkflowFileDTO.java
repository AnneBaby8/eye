package cn.com.citydo.module.core.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @Author blackcat
 * @create 2021/7/29 13:48
 * @version: 1.0
 * @description:
 */
@Data
public class WorkflowFileDTO {

    @ApiModelProperty(value = "流程主表id")
    @NotNull
    private Long workflowId;

    @ApiModelProperty(value = "核查图片")
    private String image;

    @ApiModelProperty(value = "核查时间")
    private String captureTime;
}
