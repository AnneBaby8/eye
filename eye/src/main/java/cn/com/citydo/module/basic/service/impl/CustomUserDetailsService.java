package cn.com.citydo.module.basic.service.impl;


import cn.com.citydo.module.basic.entity.*;
import cn.com.citydo.module.basic.service.*;
import cn.com.citydo.module.basic.vo.UserPrincipal;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 自定义UserDetails查询
 * </p>
 * @author blackcat
 * @since 2020-07-21
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private RolePermissionService rolePermissionService;


    @Override
    public UserDetails loadUserByUsername(String usernameOrEmailOrPhone) throws UsernameNotFoundException {
        QueryWrapper<User> queryWrapper = new QueryWrapper();
        queryWrapper.lambda().eq(User::getOutUserId, usernameOrEmailOrPhone).or()
                .eq(User::getPhone, usernameOrEmailOrPhone).or()
                .eq(User::getUsername, usernameOrEmailOrPhone);
        User user = userService.getOne(queryWrapper);
        if (user == null) {
            throw new UsernameNotFoundException("未找到用户信息 : " + usernameOrEmailOrPhone);
        }
        //关联表信息
        QueryWrapper<UserRole> roleWrapper = new QueryWrapper();
        roleWrapper.lambda().eq(UserRole::getUserId, user.getId());
        List<UserRole> userRoles = userRoleService.list(roleWrapper);

        if(CollectionUtils.isEmpty(userRoles)){
            return UserPrincipal.create(user, new ArrayList<>(), new ArrayList<>());
        }

        List<Long> roleIds = userRoles.stream()
                .map(UserRole::getRoleId)
                .collect(Collectors.toList());
        //角色信息
        List<Role> roles = roleService.listByIds(roleIds);

        QueryWrapper<RolePermission> permissionsWrapper = new QueryWrapper();
        permissionsWrapper.lambda().in(RolePermission::getRoleId, roleIds);
        List<RolePermission> rolePermissions = rolePermissionService.list(permissionsWrapper);
        List<Long> permissionIds = rolePermissions.stream()
                .map(RolePermission::getPermissionId)
                .collect(Collectors.toList());

        List<Permission> permissions = new ArrayList<>(10);
        if(!CollectionUtils.isEmpty(permissionIds)){
            //权限信息
            permissions  = permissionService.listByIds(permissionIds);
        }

        return UserPrincipal.create(user, roles, permissions);
    }
}
