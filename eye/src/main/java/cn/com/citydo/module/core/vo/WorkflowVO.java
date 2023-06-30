package cn.com.citydo.module.core.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @Author blackcat
 * @create 2021/6/8 15:55
 * @version: 1.0
 * @description:
 */
@Data
public class WorkflowVO {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "事件编号")
    private String eventNo;

    @ApiModelProperty(value = "事件类型")
    private String eventType;

    @ApiModelProperty(value = "事件名称")
    private String eventName;


    @ApiModelProperty(value = "所属区域，包括区、街道、社区")
    private String areaName;

    @ApiModelProperty(value = "设备点位")
    private String deviceName;

    @ApiModelProperty(value = "抓拍图片，图片路由")
    private String image;

    @ApiModelProperty(value = "网格员")
    private String gridName;

    @ApiModelProperty(value = "预警时间（格式：年-月-日 时:分:秒）")
    private Date warnTime;

    @ApiModelProperty(value = "状态（0-未开始，1-处理中，2-处理结束）")
    private Integer status;

    @ApiModelProperty(value = "是否处理事件")
    private Boolean iaHandle = Boolean.FALSE;

    @ApiModelProperty(value = "处理状态  0-未开始 1-处理中  2-结束")
    private Integer queryStatus;

    @ApiModelProperty(value = "提交操作")
    private String operation;

    @ApiModelProperty(value = "提交人")
    private String submitName;

    @ApiModelProperty(value = "提交时间")
    private Date submitTime;

    @ApiModelProperty(value = "办结人员id")
    private Long finishUserId;

    @ApiModelProperty(value = "办结人员姓名")
    private String finishUserName;

}
