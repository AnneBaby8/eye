package cn.com.citydo.module.core.vo;

import cn.com.citydo.module.screen.entity.EventWarningDetail;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @Author blackcat
 * @create 2021/6/16 15:16
 * @version: 1.0
 * @description:
 */
@Data
public class WorkflowDetailVO {

    /** 事件ID */
    @ApiModelProperty(value = "事件ID")
    private Long id;
    /** 设备编号 */
    @ApiModelProperty(value = "设备编号")
    private String streamId;

    /** 是否启用"作废"按钮 */
    @ApiModelProperty(value = "是否启用作废按钮（0-显示，1-不显示）")
    private Integer isCancel = 1;
    /** 是否启用"转部门"按钮 */
    @ApiModelProperty(value = "是否启用转部门按钮（0-显示，1-不显示）")
    private Integer isChangeDept = 1;
    /** 是否启用"处置"按钮 */
    @ApiModelProperty(value = "是否启用处置按钮（0-显示，1-不显示）")
    private Integer isDeal = 1;

    /** 预警信息 */
    @ApiModelProperty(value = "预警时间")
    private Date warnTime;
    @ApiModelProperty(value = "事件类型（0-占道经营 1-垃圾检测 2-救助人群 3-重点上访人员 4-空巢老人）")
    private String eventType;
    @ApiModelProperty(value = "事件人员")
    private String eventPeople;
    @ApiModelProperty(value = "事件内容")
    private String eventContent;

    /** 位置信息 */
    @ApiModelProperty(value = "所属区域")
    private String areaName;
    @ApiModelProperty(value = "事件地址")
    private String address;

    /** 负责人信息 */
    @ApiModelProperty(value = "网格员")
    private String gridUserName;
    @ApiModelProperty(value = "网格长")
    private String gridLeadUserName;

    /** 现场抓拍 */
    @ApiModelProperty(value = "抓拍图片URL地址")
    private String image;

    /** 处置上传图片 */
    @ApiModelProperty(value = "处置上传图片地址")
    private String checkImage;

    @ApiModelProperty(value = "核实图片")
    private List<String> captureList;

    @ApiModelProperty(value = "人员信息")
    private EventWarningDetail eventWarningDetail;
}
