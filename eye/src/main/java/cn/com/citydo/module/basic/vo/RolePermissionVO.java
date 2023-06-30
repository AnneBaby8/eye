package cn.com.citydo.module.basic.vo;

import cn.com.citydo.module.basic.entity.RolePermission;
import lombok.Data;

/**
 * <p>
 *
 * </p>
 *
 * @author wx227336
 * @Date 2021/6/1 10:04
 * @Version 1.0
 */
@Data
public class RolePermissionVO extends RolePermission {
    /**
     * 是否为已有权限 0-否 1-是
     * StatusEnum
     */
    private String falg;
}
