package cn.com.citydo.module.core.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author blackcat
 * @create 2021/6/9 15:18
 * @version: 1.0
 * @description:
 */
@Data
public class WorkflowCount {

    private Long count = 0L;

    private Long processCount = 0L;

    private Long finishCunt = 0L;

    @ApiModelProperty(value = "用户id")
    private Long userId;

    @ApiModelProperty(value = "用户名称")
    private String userName;

    @ApiModelProperty(value = "网格名称")
    private String gridName;

    @ApiModelProperty(value = "社区名称")
    private String socialName;

    @ApiModelProperty(value = "部门名称")
    private String departmentName;
}
