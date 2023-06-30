package cn.com.citydo.module.core.entity;

import cn.com.citydo.common.frame.BaseEntity;
import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 设备表
 * </p>
 *
 * @author blackcat
 * @since 2021-06-01
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("device")
@ApiModel(value="Device对象", description="设备表")
public class Device extends BaseEntity {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "华为摄像头唯一编码")
    private String streamId;

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

    @ApiModelProperty(value = "接口调用平台")
    private String  platform;

    @ApiModelProperty(value = "预警推送平台")
    private String warnPlatform;

}
