package cn.com.citydo.module.basic.service;

import cn.com.citydo.common.enums.RoleCodeEnum;
import cn.com.citydo.module.basic.entity.User;
import cn.com.citydo.module.basic.entity.UserDepartment;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 用户部门关系表 服务类
 * </p>
 *
 * @author blackcat
 * @since 2021-05-20
 */
public interface UserDepartmentService extends IService<UserDepartment> {

    List<UserDepartment> getByUserId(Long userId);

    List<UserDepartment> getByUserIds(List<Long> userIds);

    List<UserDepartment> getByDepartmentId(Long departmentId);

    List<User> getUserByRole(Long departmentId, RoleCodeEnum roleCodeEnum);

    boolean deleteByUserId(Long userId);

}
