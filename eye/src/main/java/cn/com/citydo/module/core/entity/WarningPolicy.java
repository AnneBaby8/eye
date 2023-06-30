package cn.com.citydo.module.core.entity;

import cn.com.citydo.common.frame.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
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
@TableName("warning_policy")
@ApiModel(value="WarningPolicy对象", description="预警配置表")
public class WarningPolicy extends BaseEntity {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "预警配置id")
    private Long configId;

    @ApiModelProperty(value = "0-实时预警,1-未发现预警,2-出入预警,3-拦截预警")
    private String policyType;

    @ApiModelProperty(value = "生效开始时间")
    private Date effectStartTime;

    @ApiModelProperty(value = "生效结束时间")
    private Date effectEndTime;

    @ApiModelProperty(value = "预警时段开始时间")
    private String warnStartTime;

    @ApiModelProperty(value = "预警时段结束时间")
    private String warnEndTime;

    @ApiModelProperty(value = "预警等级提醒")
    private String remind;

    @ApiModelProperty(value = "预警等级预警")
    private String warning;


}
