package cn.com.citydo.module.core.entity;

import cn.com.citydo.common.frame.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 流程文件表
 * </p>
 *
 * @author blackcat
 * @since 2021-07-29
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("workflow_file")
@ApiModel(value="WorkflowFile对象", description="流程文件表")
public class WorkflowFile extends BaseEntity {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "流程主表id")
    private Long workflowId;

    @ApiModelProperty(value = "核查图片")
    private String image;

    @ApiModelProperty(value = "核查时间")
    private String captureTime;


}
