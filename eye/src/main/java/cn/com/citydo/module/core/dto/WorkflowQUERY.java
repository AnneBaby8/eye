package cn.com.citydo.module.core.dto;

import cn.com.citydo.common.PageDTO;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @Author blackcat
 * @create 2021/6/8 15:35
 * @version: 1.0
 * @description:
 */
@Data
public class WorkflowQUERY extends PageDTO {
    @ApiModelProperty(value = "角色id 可忽略")
    private Long roleId;

    @ApiModelProperty(value = "事件类型 0-占道经营 1-垃圾检测 2-救助人群 3-重点上访人员 4-空巢老人")
    private String eventType;

    @ApiModelProperty(value = "所属区域")
    private Long zoneId;

    @ApiModelProperty(value = "华为摄像头唯一编码")
    private String streamId;

    @ApiModelProperty(value = "设备名称")
    private String deviceName;

//    @ApiModelProperty(value = "街道id")
//    private Long streetId;
//
//    @ApiModelProperty(value = "社区id")
//    private Long socialId;
//
//    @ApiModelProperty(value = "区id")
//    private Long areaId;

    @ApiModelProperty(value = "状态   0-未处理   1-处理中  2-完结")
    private Integer statusType;

    @ApiModelProperty(value = "预警结束时间")
    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
    private Date warnEndTime;

    @ApiModelProperty(value = "预警开始时间")
    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
    private Date warnStartTime;

    @ApiModelProperty(value = "是否处理事件 0-可处理 1-不可处理 空-全部")
    private String isHandlingEvents;

    @ApiModelProperty(value = "自行处置类型 0-正常 1-核实后无情况 2-误报  SelfDisposalTypeEnum")
    private String selfDisposalType;

}
