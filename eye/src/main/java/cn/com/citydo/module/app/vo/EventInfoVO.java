package cn.com.citydo.module.app.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class EventInfoVO {

    /** 事件类型：通道占用、帮扶人群 */
    @ApiModelProperty(value = "eventType")
    private Integer eventType;
    /** 事件主体：人员姓名/地址 */
    @ApiModelProperty(value = "subject")
    private String subject;
    /** 发生日期 */
    @ApiModelProperty(value = "occurrenceDate")
    private String occurrenceDate;
    /** 事件预警方式：预警、督办、提醒... */
    @ApiModelProperty(value = "eventMode")
    private String eventMode;

}
