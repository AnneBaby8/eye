package cn.com.citydo.module.basic.service;

import cn.com.citydo.module.basic.dto.PermissionListDTO;
import cn.com.citydo.module.basic.dto.RoleGrantDTO;
import cn.com.citydo.module.basic.entity.RolePermission;
import cn.com.citydo.module.basic.vo.PermissionVO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
/**
 * <p>
 * 角色权限关系表 服务类
 * </p>
 *
 * @author blackcat
 * @since 2021-05-20
 */
public interface RolePermissionService extends IService<RolePermission> {
    /**
     * 根据角色id获取所有权限
     * @param roleId 角色id
     * @return
     */
    List<RolePermission> selectDataListByRoleId(Long roleId);

    /**
     * 授权
     * @param roleGrantDTO 入参
     * @return
     */
    boolean grant(RoleGrantDTO roleGrantDTO);
    /**
     * 获取权限
     * @param permissionListDTO 入参
     * @return
     */
    List<PermissionVO>  getPermisssionByRoleId(PermissionListDTO permissionListDTO);
    /**
     * 根据角色id获取所有权限
     * @param roleIdList 角色id
     * @return
     */
    List<RolePermission> selectDataListByRoleIdList(List<Long> roleIdList);

}
