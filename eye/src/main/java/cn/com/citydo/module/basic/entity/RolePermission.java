package cn.com.citydo.module.basic.entity;

import cn.com.citydo.common.frame.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 角色权限关系表
 * </p>
 *
 * @author blackcat
 * @since 2021-05-20
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sec_role_permission")
@ApiModel(value="RolePermission对象", description="角色权限关系表")
public class RolePermission extends BaseEntity {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "角色主键")
    private Long roleId;

    @ApiModelProperty(value = "权限主键")
    private Long permissionId;


}
