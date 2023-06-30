package cn.com.citydo.module.core.dto;

import cn.com.citydo.common.PageDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @Author blackcat
 * @create 2021/6/10 11:30
 * @version: 1.0
 * @description:
 */
@Data
public class WorkflowNewsQUERY extends PageDTO {

    @ApiModelProperty(value = "消息类型")
    private String newType;

    @ApiModelProperty(value = "结束时间")
    private Date endTime;

    @ApiModelProperty(value = "开始时间")
    private Date startTime;

    @ApiModelProperty(value = "0-未读 1-已读")
    private Integer isRead;

    @ApiModelProperty(value = "社区id")
    private Long socialId;

    @ApiModelProperty(value = "是否为督办 是-true")
    private Boolean isSupervise;

    @ApiModelProperty(value = "消息类型-督办专用")
    private List<String> newTypeList;

    public Boolean getSupervise() {
        return isSupervise;
    }

    public void setSupervise(Boolean supervise) {
        isSupervise = supervise;
    }
}
