package cn.com.citydo.module.core.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @Author blackcat
 * @create 2021/6/1 9:10
 * @version: 1.0
 * @description:
 */
@Data
public class DeviceDTO {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "华为摄像头唯一编码")
    @NotNull
    private String streamId;

    @ApiModelProperty(value = "区id")
    private Long areaId;

    @ApiModelProperty(value = "街道id")
    private Long streetId;

    @ApiModelProperty(value = "社区id")
    private Long socialId;

    @ApiModelProperty(value = "归属网格id")
    private Long gridId;

    @ApiModelProperty(value = "名称")
    @NotNull
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
