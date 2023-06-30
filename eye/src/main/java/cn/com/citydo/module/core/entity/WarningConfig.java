package cn.com.citydo.module.core.entity;

import cn.com.citydo.common.frame.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 预警配置表
 * </p>
 *
 * @author blackcat
 * @since 2021-06-01
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("warning_config")
@ApiModel(value="WarningConfig对象", description="预警配置表")
public class WarningConfig extends BaseEntity {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "预警编号")
    private String warnNo;

    @ApiModelProperty(value = "描述")
    private String description;


    @ApiModelProperty(value = "状态，生效-0,暂停-1")
    private Integer status;

    @ApiModelProperty(value = "预警类型，0-通道占用 1-垃圾堆栈 2-帮扶人员 3-重点人员 4-空巢老人 5-人群密集 6-污水跑冒 7-机动车违停 8-非机动车违停")
    private String warnType;
}
