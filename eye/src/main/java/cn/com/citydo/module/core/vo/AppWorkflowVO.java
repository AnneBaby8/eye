package cn.com.citydo.module.core.vo;

import cn.com.citydo.common.enums.QueryStatusEnum;
import cn.com.citydo.common.enums.SupriseTypeEnum;
import cn.com.citydo.module.basic.entity.User;
import cn.com.citydo.module.screen.entity.EventWarningDetail;
import cn.hutool.core.date.DateUtil;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.util.ObjectUtils;

import java.util.Date;
import java.util.List;

/**
 * @Author blackcat
 * @create 2021/6/10 9:08
 * @version: 1.0
 * @description:
 */
@Data
public class AppWorkflowVO {

    @ApiModelProperty(value = "id")
    private Long id;



    @ApiModelProperty(value = "状态  0-网格员  1-网格员上报处置部门 2-处置部门退回给网格员 3-网格员上报街道 4-街道转处置部门 5-处置部门转街道  6-作废 7-完结")
    private Integer status;

    @ApiModelProperty(value = "预警时间")
    private Date warnTime;

    @ApiModelProperty(value = "所属区域")
    private String areaName;

    @ApiModelProperty(value = "事件类型 0-占道经营 1-垃圾检测 2-救助人群 3-重点上访人员 4-空巢老人")
    private String eventType;

    @ApiModelProperty(value = "设备地址")
    private String address;

    @ApiModelProperty(value = "抓拍图片-base64")
    private String image;

    @ApiModelProperty(value = "网格员名称")
    private String gridName;

    @ApiModelProperty(value = "查看人员，拼接好的字符串")
    private String readers;

    @ApiModelProperty(value = "是否已读（0-已读，1-未读）")
    private Integer isReaded;

    @ApiModelProperty(value = "事件编号")
    private String eventNo;

    @ApiModelProperty(value = "设备名称")
    private String deviceName;

    @ApiModelProperty(value = "抓拍时间")
    private String captureTime;

    @ApiModelProperty(value = "预警人员姓名")
    private String EventPeopleName;

    @ApiModelProperty(value = "处置图片")
    private String checkImage;

    @ApiModelProperty(value = "状态  0-网格员  1-网格员上报处置部门 2-处置部门退回给网格员 3-网格员上报街道 4-街道转处置部门 5-处置部门转街道  6-作废 7-完结")
    private String statusName;

    @ApiModelProperty(value = "处理状态  0-未开始 1-处理中  2-结束")
    private Integer queryStatus;

    @ApiModelProperty(value = "处理状态  未开始/处理中/结束")
    private String queryStatusName;

    @ApiModelProperty(value = "退回标志 true/false")
    private Boolean backFlag = Boolean.FALSE;

    @ApiModelProperty(value = "抓拍时间")
    private Date reportTimeFormat;

    @ApiModelProperty(value = "华为摄像头唯一编码")
    private String streamId;

    @ApiModelProperty(value = "核实图片")
    private List<String> captureList;

    @ApiModelProperty(value = "人员信息")
    private EventWarningDetail eventWarningDetail;

    @ApiModelProperty(value = "督办类型 1-网格长督办；2-社区网格中心督办")
    private String supriseType;

    @ApiModelProperty(value = "督办类型 1-网格长督办；2-社区网格中心督办")
    private String supriseTypeName;

    @ApiModelProperty(value = "退回人员账号")
    private User backUser;

    @ApiModelProperty(value = "办结人员id")
    private Long finishUserId;

    @ApiModelProperty(value = "办结人员姓名")
    private String finishUserName;


    public String getQueryStatusName() {
        return QueryStatusEnum.getNameByCode(this.getQueryStatus());
    }

    public String getReportTime() {
        if (ObjectUtils.isEmpty(reportTimeFormat)) {
            return "";
        }
        return DateUtil.format(reportTimeFormat, "yyyy-MM-dd HH:mm:ss");
    }

    public String getSupriseTypeName() {
        return SupriseTypeEnum.getNameByCode(this.getSupriseType());
    }
}
