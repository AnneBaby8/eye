package cn.com.citydo.module.core.vo;

import cn.com.citydo.module.screen.enums.WarnTypeEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 *  数据概览-设备
 * </p>
 *
 * @author wx227336
 * @Date 2021/6/8 10:36
 * @Version 1.0
 */
@Data
public class OverviewDeviceVO {


    @ApiModelProperty(value = "id")
    private Long id;

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

    @ApiModelProperty(value = "设备id")
    private String streamId;

    @ApiModelProperty(value = "设备名称")
    private String streamName;

    @ApiModelProperty(value = "预警类型code")
    private String warnType;

    @ApiModelProperty(value = "发生事件总数")
    private Integer eventCount;

    @ApiModelProperty(value = "预警时间")
    private String warnTime;

    @ApiModelProperty(value = "抓拍图片")
    private String imageBase64;

    @ApiModelProperty(value = "预警类型名称")
    private String warnTypeName;

    public String getWarnTypeName() {
        return WarnTypeEnum.getValueByKey(this.getWarnType());
    }
}
