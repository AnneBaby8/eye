package cn.com.citydo.module.basic.service.impl;

import cn.com.citydo.module.basic.entity.UserRole;
import cn.com.citydo.module.basic.mapper.UserRoleMapper;
import cn.com.citydo.module.basic.service.UserRoleService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 用户角色关系表 服务实现类
 * </p>
 *
 * @author blackcat
 * @since 2021-05-20
 */
@Service
@Slf4j
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements UserRoleService {

    @Override
    public List<UserRole> getByUserId(Long userId) {
        QueryWrapper<UserRole> roleWrapper = new QueryWrapper();
        roleWrapper.lambda().eq(UserRole::getUserId, userId);
        return this.list(roleWrapper);
    }

    @Override
    public List<UserRole> getByRoleId(Long roleId) {
        QueryWrapper<UserRole> roleWrapper = new QueryWrapper();
        roleWrapper.lambda().eq(UserRole::getRoleId, roleId);
        return this.list(roleWrapper);
    }

    @Override
    public boolean deleteByUserId(Long userId) {
        UpdateWrapper<UserRole> updateWrapper = new UpdateWrapper<>();
        updateWrapper.lambda().eq(UserRole::getUserId,userId).set(UserRole::getDel,Boolean.TRUE);
        return this.update(updateWrapper);
    }
}
