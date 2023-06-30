package cn.com.citydo.module.basic.service.impl;

import cn.com.citydo.common.enums.RoleCodeEnum;
import cn.com.citydo.module.basic.entity.Role;
import cn.com.citydo.module.basic.entity.User;
import cn.com.citydo.module.basic.entity.UserDepartment;
import cn.com.citydo.module.basic.mapper.UserDepartmentMapper;
import cn.com.citydo.module.basic.service.UserDepartmentService;
import cn.com.citydo.module.basic.service.UserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 用户部门关系表 服务实现类
 * </p>
 *
 * @author blackcat
 * @since 2021-05-20
 */
@Service
public class UserDepartmentServiceImpl extends ServiceImpl<UserDepartmentMapper, UserDepartment> implements UserDepartmentService {

    @Autowired
    private UserService userService;

    @Override
    public List<UserDepartment> getByUserId(Long userId) {
        return this.list(new QueryWrapper<UserDepartment>().lambda().eq(UserDepartment::getUserId, userId).eq(UserDepartment::getDel,false ));
    }

    @Override
    public List<UserDepartment> getByUserIds(List<Long> userIds) {
        return this.list(new QueryWrapper<UserDepartment>().lambda().in(UserDepartment::getUserId, userIds));
    }

    @Override
    public List<UserDepartment> getByDepartmentId(Long departmentId) {
        return this.list(new QueryWrapper<UserDepartment>().lambda().eq(UserDepartment::getDepartmentId, departmentId));
    }

    @Override
    public List<User> getUserByRole(Long departmentId, RoleCodeEnum roleCodeEnum) {
        List<User> list = new ArrayList<>();
        List<UserDepartment> userDepartments = this.getByDepartmentId(departmentId);
        for (UserDepartment userDepartment : userDepartments) {
            Long userId = userDepartment.getUserId();
            List<Role> roles = userService.getRoles(userId);
            if (!CollectionUtils.isEmpty(roles) && roles.stream().map(Role::getCode).collect(Collectors.toList()).contains(roleCodeEnum.getCode())) {
                User user = userService.getById(userId);
                list.add(user);
            }
        }
        return list;
    }

    @Override
    public boolean deleteByUserId(Long userId) {
        UpdateWrapper<UserDepartment> updateWrapper = new UpdateWrapper<>();
        updateWrapper.lambda().eq(UserDepartment::getUserId,userId).set(UserDepartment::getDel,Boolean.TRUE);
        return this.update(updateWrapper);
    }

}
