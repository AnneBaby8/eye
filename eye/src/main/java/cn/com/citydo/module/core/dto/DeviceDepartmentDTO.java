package cn.com.citydo.module.core.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 *
 * </p>
 *
 * @author wx227336
 * @Date 2021/6/3 11:20
 * @Version 1.0
 */
@Data
public class DeviceDepartmentDTO {
   // @NotNull(message = "区不能为空")
    @ApiModelProperty(value = "区id")
    private Long areaId;

   // @NotNull(message = "街道不能为空")
    @ApiModelProperty(value = "街道id")
    private Long streetId;

    //@NotNull(message = "社区不能为空")
    @ApiModelProperty(value = "社区id")
    private Long socialId;

    @ApiModelProperty(value = "网格id")
    private Long gridId;

    @ApiModelProperty(value = "部门id")
    private Long departmentId;
}
