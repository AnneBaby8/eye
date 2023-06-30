package cn.com.citydo.module.core.entity;

import cn.com.citydo.common.frame.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * <p>
 * 流程主表
 * </p>
 *
 * @author blackcat
 * @since 2021-06-08
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("workflow")
@ApiModel(value="Workflow对象", description="流程主表")
public class Workflow extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "事件编号")
    private String eventNo;

    @ApiModelProperty(value = "事件预警ID")
    private Long eventWarningId;

    @ApiModelProperty(value = "所属区域名称")
    private String areaName;

    @ApiModelProperty(value = "事件类型")
    private String eventType;

    @ApiModelProperty(value = "华为摄像头唯一编码")
    private String streamId;

    @ApiModelProperty(value = "指派网格员id")
    private Long assignGridId;

    @ApiModelProperty(value = "指派网格员名称")
    private String assignGridName;

    @ApiModelProperty(value = "处置部门id")
    private Long handleId;

    @ApiModelProperty(value = "归属网格id")
    private Long gridId;

    @ApiModelProperty(value = "街道id")
    private Long streetId;

    @ApiModelProperty(value = "社区id")
    private Long socialId;

    @ApiModelProperty(value = "区id")
    private Long areaId;

    @ApiModelProperty(value = "版本")
    private Integer version;

    @ApiModelProperty(value = "状态  0-网格员  1-网格员上报处置部门 2-处置部门退回给网格员 3-网格员上报街道 4-街道转处置部门 5-处置部门转街道  6-作废 7-完结")
    private Integer status;

    @ApiModelProperty(value = "预警时间")
    private Date warnTime;

    @ApiModelProperty(value = "抓拍图片-base64")
    private String image;

    @ApiModelProperty(value = "设备名称")
    private String deviceName;

    @ApiModelProperty(value = "设备地址")
    private String address;

    @ApiModelProperty(value = "网格员id")
    private Long gridUserId;

    @ApiModelProperty(value = "网格员名称")
    private String gridUserName;

    @ApiModelProperty(value = "网格长id")
    private Long gridLeadUserId;

    @ApiModelProperty(value = "网格长名称")
    private String gridLeadUserName;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "上报人员")
    private Long reportUserId;

    @ApiModelProperty(value = "督办类型 1-网格长督办；2-社区网格中心督办  SupriseTypeEnum")
    private String supriseType;

    @ApiModelProperty(value = "自行处置类型 0-正常 1-核实后无情况 2-误报  SelfDisposalTypeEnum")
    private String selfDisposalType;
}

