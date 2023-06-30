package cn.com.citydo.module.basic.service;

import cn.com.citydo.module.basic.entity.UserRole;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 用户角色关系表 服务类
 * </p>
 *
 * @author blackcat
 * @since 2021-05-20
 */
public interface UserRoleService extends IService<UserRole> {

   List<UserRole> getByUserId(Long userId);

   List<UserRole> getByRoleId(Long roleId);

   boolean deleteByUserId(Long userId);
}
