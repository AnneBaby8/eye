package cn.com.citydo.module.core.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @Author blackcat
 * @create 2021/6/10 20:31
 * @version: 1.0
 * @description:
 */
@Data
public class WarningPolicyVO {

    @ApiModelProperty(value = "预警配置id")
    private Long configId;

    @ApiModelProperty(value = "0-实时预警,1-未发现预警,2-出入预警,3-拦截预警")
    private String policyType;

    @ApiModelProperty(value = "生效开始时间")
    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
    private Date effectStartTime;

    @ApiModelProperty(value = "生效结束时间")
    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
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
