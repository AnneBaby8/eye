package cn.com.citydo.module.app.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class MessageInfoVO {

    /** 消息ID */
    @ApiModelProperty(value = "id")
    private String id;
    /** 消息标题 */
    @ApiModelProperty(value = "title")
    private String title;
    /** 消息地点 */
    @ApiModelProperty(value = "address")
    private String address;
    /** 事件内容 */
    @ApiModelProperty(value = "eventContent")
    private String eventContent;
    /** 发生日期 */
    @ApiModelProperty(value = "occurrenceDate")
    private String occurrenceDate;

}
