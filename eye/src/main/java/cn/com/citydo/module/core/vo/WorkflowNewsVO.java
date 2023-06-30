package cn.com.citydo.module.core.vo;

import cn.com.citydo.common.enums.NewsReadEnum;
import cn.com.citydo.common.enums.NewsTypeEnum;
import cn.com.citydo.module.core.entity.WorkflowNews;
import cn.com.citydo.module.screen.entity.EventWarningDetail;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 流程消息表
 * </p>
 *
 * @author blackcat
 * @since 2021-06-08
 */
@Data
public class WorkflowNewsVO extends WorkflowNews {

    @ApiModelProperty(value = "所属区域名称")
    private String areaName;

    @ApiModelProperty(value = "事件类型")
    private String eventType;

    @ApiModelProperty(value = "设备地址")
    private String address;

    @ApiModelProperty(value = "消息类型名称")
    private String newTypeName;

    @ApiModelProperty(value = "消息发送人")
    private String creatorName;

    @ApiModelProperty(value = "未读/已读")
    private String isReadName;

    @ApiModelProperty(value = "人员信息")
    private EventWarningDetail eventWarningDetail;

    public String getNewTypeName() {
        return NewsTypeEnum.getNameByCode(this.getNewType());
    }

    public String getIsReadName() {
        return NewsReadEnum.getNameByCode(super.getIsRead());
    }
}
