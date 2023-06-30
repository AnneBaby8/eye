package cn.com.citydo.module.core.vo;

import cn.com.citydo.module.core.entity.Device;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author blackcat
 * @create 2021/6/1 9:11
 * @version: 1.0
 * @description:
 */
@Data
public class DeviceVO extends Device {

    @ApiModelProperty(value = "华为摄像头唯一编码")
    private String streamId;

    @ApiModelProperty(value = "归属网格名称")
    private String gridName;

    @ApiModelProperty(value = "区域名称")
    private String areaName;

    @ApiModelProperty(value = "归属网格id")
    private Long gridId;

    @ApiModelProperty(value = "街道id")
    private Long streetId;

    @ApiModelProperty(value = "社区id")
    private Long socialId;

    @ApiModelProperty(value = "区id")
    private Long areaId;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "地址")
    private String address;

    @ApiModelProperty(value = "经度")
    private String longitude;

    @ApiModelProperty(value = "纬度")
    private String latitude;

    @ApiModelProperty(value = "状态，不在线-0,在线-1")
    private Integer status;

    @ApiModelProperty(value = "算法类型")
    private String algorithm;

    @ApiModelProperty(value = "卡口方向 1-入口,2-出口,0-非卡口置空")
    private Integer direction;
}
