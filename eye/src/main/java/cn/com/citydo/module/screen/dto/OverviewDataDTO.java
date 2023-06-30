package cn.com.citydo.module.screen.dto;

import cn.com.citydo.common.PageDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 *  数据概览查询入参
 * </p>
 *
 * @author wx227336
 * @Date 2021/6/7 10:13
 * @Version 1.0
 */
@Data
public class OverviewDataDTO extends PageDTO {

    @ApiModelProperty(value = "预警类型")
    private String warnType;

    @ApiModelProperty(value = "环比增长率类型 0-日 1-周  2-月 3-年")
    private String chainType;

    @ApiModelProperty(value = "部门id")
    private Long departmentId;

    @ApiModelProperty(value = "区id")
    private Long areaId;

    @ApiModelProperty(value = "街道id")
    private Long streetId;

    @ApiModelProperty(value = "社区id")
    private Long socialId;

    @ApiModelProperty(value = "网格id")
    private Long gridId;

    @ApiModelProperty(value = "自行处置类型 0-正常 1-核实后无情况 2-误报  SelfDisposalTypeEnum")
    private String selfDisposalType;
}
