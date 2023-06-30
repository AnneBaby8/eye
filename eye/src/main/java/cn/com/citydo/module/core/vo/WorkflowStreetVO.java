package cn.com.citydo.module.core.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @Author blackcat
 * @create 2021/6/16 16:36
 * @version: 1.0
 * @description:
 */
@Data
public class WorkflowStreetVO {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "事件编号-暂未生成")
    private String eventNo;

    @ApiModelProperty(value = "所属区域")
    private String areaName;

    @ApiModelProperty(value = "设备名称")
    private String deviceName;

    @ApiModelProperty(value = "抓拍图片-base64")
    private String image;

    @ApiModelProperty(value = "提交操作")
    private String operation;

    @ApiModelProperty(value = "提交人")
    private String submitName;

    @ApiModelProperty(value = "提交时间")
    private Date submitTime;

}
