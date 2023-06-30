package cn.com.citydo.module.core.dto;

import cn.com.citydo.common.PageDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @Author blackcat
 * @create 2021/6/9 15:19
 * @version: 1.0
 * @description:
 */
@Data
public class WorkflowCountQUERY extends PageDTO {

    private Long roleId;

    @ApiModelProperty(value = "事件类型 0-占道经营 1-垃圾检测 2-救助人群 3-重点上访人员 4-空巢老人")
    private String eventType;

    @ApiModelProperty(value = "预警结束时间")
    private Date warnEndTime;

    @ApiModelProperty(value = "预警开始时间")
    private Date warnStartTime;

    @ApiModelProperty(value = "网格员id")
    private Long gridId;

    @ApiModelProperty(value = "社区id")
    private Long socialId;

    @ApiModelProperty(value = "状态  0-未处理   1-处理中  2-完结")
    private Integer status;

    @ApiModelProperty(value = "自行处置类型 0-正常 1-核实后无情况 2-误报  SelfDisposalTypeEnum")
    private String selfDisposalType;

    @ApiModelProperty(value = "角色类型 grid-网格员、handle-处置部门、street-街道网格中心  RoleCodeEnum")
    private String roleType;

}
