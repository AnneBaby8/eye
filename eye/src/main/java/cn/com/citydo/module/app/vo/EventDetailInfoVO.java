package cn.com.citydo.module.app.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class EventDetailInfoVO {

    /** 归属社区 */
    @ApiModelProperty(value = "community")
    private String community;
    /** 事件地址 */
    @ApiModelProperty(value = "address")
    private String address;
    /** 事件类型 */
    @ApiModelProperty(value = "eventType")
    private int eventType;
    /** 事件人员 */
    @ApiModelProperty(value = "eventName")
    private String eventName;
    /** 事件内容 */
    @ApiModelProperty(value = "eventContent")
    private String eventContent;
    /** 预警日期 */
    @ApiModelProperty(value = "supervisorDate")
    private String supervisorDate;
    /** 督办部门 */
    @ApiModelProperty(value = "supervisorDept")
    private String supervisorDept;
    /** 督办说明 */
    @ApiModelProperty(value = "supervisorExplanation")
    private String supervisorExplanation;

}
