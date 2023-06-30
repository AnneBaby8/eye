package cn.com.citydo.utils;

import cn.com.citydo.common.enums.NewsEnum;
import cn.hutool.core.date.DateUtil;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.util.ObjectUtils;

import java.util.Date;

/**
 * @Author blackcat
 * @create 2021/6/17 10:05
 * @version: 1.0
 * @description:
 */
@Data
public class Msg {

    @ApiModelProperty(value = "流程id")
    private Long workflowId;

    @ApiModelProperty(value = "所属区域名称")
    private String areaName;

    @ApiModelProperty(value = "消息类型")
    private NewsEnum newsEnum;

    @ApiModelProperty(value = "事件类型")
    private String eventType;

    @ApiModelProperty(value = "事件人员")
    private String eventPeople;

    @ApiModelProperty(value = "事件内容")
    private String eventContent;

    @ApiModelProperty(value = "接收人id")
    private Long userId;

    @ApiModelProperty(value = "设备地址")
    private String address;

    @ApiModelProperty(value = "网格员名称")
    private String gridUserName;

    @ApiModelProperty(value = "预警时间")
    private Date warnTime;

    @ApiModelProperty(value = "指派网格员名称")
    private String assignGridName;

    @ApiModelProperty(value = "提交人")
    private String submitName;

    @ApiModelProperty(value = "提交部门")
    private String submitDepartment;

    @ApiModelProperty(value = "督办部门")
    private String supriseDepartment;

    @ApiModelProperty(value = "说明")
    private String remark;

    @ApiModelProperty(value = "事件预警ID")
    private Long eventWarningId;

    public String getEventPeople() {
        return eventPeople == null ? "" : eventPeople;
    }

    public String getEventContent() {
        return eventContent == null ? "" : eventContent;
    }

    public String getSubmitName() {
        return submitName == null ? "" : submitName;
    }

    public String getSubmitDepartment() {
        return submitDepartment == null ? "" : submitDepartment;
    }

    public String getWarnTime() {
        if (ObjectUtils.isEmpty(warnTime)) {
            return "";
        }
        return DateUtil.format(warnTime, "yyyy-MM-dd HH:mm:ss");
    }
}
