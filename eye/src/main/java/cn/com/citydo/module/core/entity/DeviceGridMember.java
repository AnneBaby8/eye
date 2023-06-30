package cn.com.citydo.module.core.entity;

import cn.com.citydo.common.frame.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 设备关联网格员
 * </p>d
 *
 * @author blackcat
 * @since 2021-06-01
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("device_grid_member")
@ApiModel(value="DeviceGridMember对象", description="设备关联网格员")
public class DeviceGridMember extends BaseEntity {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "设备id")
    private Long deviceId;

    @ApiModelProperty(value = "网格员id")
    private Long gridMemberId;


}
