package cn.com.citydo.module.core.entity;

import cn.com.citydo.common.frame.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 流程步骤表
 * </p>
 *
 * @author blackcat
 * @since 2021-06-08
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("workflow_step")
@ApiModel(value = "WorkflowStep对象", description = "流程步骤表")
public class WorkflowStep extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "流程主表id")
    private Long workflowId;

    @ApiModelProperty(value = "角色名称")
    private String roleName;

    @ApiModelProperty(value = "角色编码")
    private String roleCode;

    @ApiModelProperty(value = "用户id")
    private Long userId;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "操作")
    private String operation;

    @ApiModelProperty(value = "备注")
    private String remark;

    private String selfType;

    @ApiModelProperty(value = "核实图片")
    private String image;


}
