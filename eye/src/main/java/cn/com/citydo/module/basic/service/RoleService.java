package cn.com.citydo.module.basic.service;

import cn.com.citydo.module.basic.entity.Role;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;
/**
 * <p>
 * 角色表 服务类
 * </p>
 *
 * @author blackcat
 * @since 2021-05-20
 */
public interface RoleService extends IService<Role> {
    List<Role> getAll();

    List<Role> getRoleDetailByOutUserId(Long outUserId);

}
