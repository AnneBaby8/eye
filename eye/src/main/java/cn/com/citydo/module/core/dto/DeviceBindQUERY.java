package cn.com.citydo.module.core.dto;

import cn.com.citydo.common.PageDTO;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * <p>
 *  网格绑定
 * </p>
 *
 * @author wx227336
 * @Date 2021/6/3 14:37
 * @Version 1.0
 */
@Data
public class DeviceBindQUERY extends PageDTO {

    @ApiModelProperty(value = "区id")
    private Long areaId;

    @ApiModelProperty(value = "街道id")
    private Long streetId;

    @ApiModelProperty(value = "社区id")
    private Long socialId;

    @ApiModelProperty(value = "社区id")
    private Long gridId;

    @ApiModelProperty(value = "绑定时间-开始时间")
    private String startTime;

    @ApiModelProperty(value = "绑定时间-结束时间")
    private String endTime;

    @ApiModelProperty(value = "网格员")
    private String gridMemberName ;

    @ApiModelProperty(value = "部门id")
    private Long departmentId;
}
