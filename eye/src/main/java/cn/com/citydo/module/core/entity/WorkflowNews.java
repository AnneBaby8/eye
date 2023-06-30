package cn.com.citydo.module.core.entity;

import cn.com.citydo.common.frame.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 流程消息表
 * </p>
 *
 * @author blackcat
 * @since 2021-06-08
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("workflow_news")
@ApiModel(value = "WorkflowNews对象", description = "流程消息表")
public class WorkflowNews extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "业务唯一标识")
    private String bussinessKey;

    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "内容")
    private String content;

    @ApiModelProperty(value = "接收人")
    private Long userId;

    @ApiModelProperty(value = "0-未读 1-已读")
    private Integer isRead;

    @ApiModelProperty(value = "消息类型")
    private String newType;

    @ApiModelProperty(value = "社区id")
    private Long socialId;
}
