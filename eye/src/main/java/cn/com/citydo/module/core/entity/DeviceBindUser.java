package cn.com.citydo.module.core.entity;

import cn.com.citydo.common.frame.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 预警信息关联网格员数据
 * </p>
 *
 * @author blackcat
 * @since 2021-06-01
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("device_bind_user")
@ApiModel(value="DeviceBindUser对象", description="设备关联网格员")
public class DeviceBindUser extends BaseEntity {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "流程id")
    private Long workflowId;

    @ApiModelProperty(value = "网格员id")
    private Long gridUserId;

    @ApiModelProperty(value = "网格员名称")
    private String gridUserName;

}
