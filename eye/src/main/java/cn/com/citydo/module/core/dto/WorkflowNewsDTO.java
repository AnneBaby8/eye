package cn.com.citydo.module.core.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @Author blackcat
 * @create 2021/6/10 11:30
 * @version: 1.0
 * @description:
 */
@Data
public class WorkflowNewsDTO {

    @NotNull(message = "id不能为空")
    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "0-未读 1-已读")
    private Integer isRead;
}
