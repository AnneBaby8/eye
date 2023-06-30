package cn.com.citydo.module.core.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * <p>
 *
 * </p>
 *
 * @author wx227336
 * @Date 2021/6/3 17:06
 * @Version 1.0
 */
@Data
public class DeviceBindVO {

    @ApiModelProperty(value = "区id")
    private Long areaId;

    @ApiModelProperty(value = "街道id")
    private Long streetId;

    @ApiModelProperty(value = "社区id")
    private Long socialId;

    @ApiModelProperty(value = "区名称")
    private String areaName;

    @ApiModelProperty(value = "街道名称")
    private String streetName;

    @ApiModelProperty(value = "社区名称")
    private String socialName;

    @ApiModelProperty(value = "网格员id")
    private Long gridMemberId;

    @ApiModelProperty(value = "网格员名称")
    private String gridMemberName;

    @ApiModelProperty(value = "绑定时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date bindTime;

    @ApiModelProperty(value = "设备Id")
    private Long deviceId;

    @ApiModelProperty(value = "设备名称")
    private String deviceName;


}
