package cn.com.citydo.module.basic.service.impl;

import cn.com.citydo.common.BaseException;
import cn.com.citydo.common.EyeStatus;
import cn.com.citydo.common.enums.DepartmentGradeEnum;
import cn.com.citydo.common.enums.RoleCodeEnum;
import cn.com.citydo.module.basic.dto.UserDTO;
import cn.com.citydo.module.basic.entity.*;
import cn.com.citydo.module.basic.mapper.UserMapper;
import cn.com.citydo.module.basic.service.*;
import cn.com.citydo.module.basic.vo.UserPrincipal;
import cn.com.citydo.module.basic.vo.UserVO;
import cn.com.citydo.module.core.dto.DeviceDepartmentDTO;
import cn.com.citydo.module.core.vo.GridUserVO;
import cn.com.citydo.utils.ConvertUtil;
import cn.com.citydo.utils.SecurityUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author blackcat
 * @since 2021-05-20
 */
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {


    @Autowired
    private RoleService roleService;

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private RolePermissionService rolePermissionService;

    @Autowired
    private DepartmentService departmentService;

    @Override
    public List<Permission> getPermissions(Long userId) {
        //关联表信息
        List<UserRole> userRoles = userRoleService.getByUserId(userId);
        if (CollectionUtils.isEmpty(userRoles)) {
            return new ArrayList<>();
        }

        List<Long> roleIds = userRoles.stream()
                .map(UserRole::getRoleId)
                .collect(Collectors.toList());
        //角色信息
        List<Role> roles = roleService.listByIds(roleIds);

        QueryWrapper<RolePermission> permissionsWrapper = new QueryWrapper();
        permissionsWrapper.lambda().in(RolePermission::getRoleId, roleIds);
        List<RolePermission> rolePermissions = rolePermissionService.list(permissionsWrapper);

        if (CollectionUtils.isEmpty(rolePermissions)) {
            return new ArrayList<>();
        }
        List<Long> permissionIds = rolePermissions.stream()
                .map(RolePermission::getPermissionId)
                .collect(Collectors.toList());

        //权限信息
        List<Permission> permissions = permissionService.listByIds(permissionIds);
        return permissions;
    }

    @Override
    public List<Role> getRoles(Long userId) {
        //关联表信息
        List<UserRole> userRoles = userRoleService.getByUserId(userId);
        if (CollectionUtils.isEmpty(userRoles)) {
            return new ArrayList<>();
        }
        List<Long> roleIds = userRoles.stream()
                .map(UserRole::getRoleId)
                .collect(Collectors.toList());
        //角色信息
        List<Role> roles = roleService.listByIds(roleIds);
        return roles;
    }

    @Autowired
    private BCryptPasswordEncoder encrypt;

    @Autowired
    private UserDepartmentService userDepartmentService;

    @Transactional
    @Override
    public Long saveOrUpdate(UserDTO entityDTO) {
        Long id = entityDTO.getId();
        UserPrincipal currentUser = SecurityUtil.getCurrentUser();
        if (!ObjectUtils.isEmpty(id)) {
            List<UserRole> userRoles = userRoleService.getByUserId(id);
            if (!CollectionUtils.isEmpty(userRoles)) {
                List<Long> roleIds = userRoles.stream()
                        .map(UserRole::getId)
                        .collect(Collectors.toList());
                userRoleService.removeByIds(roleIds);
            }

            List<UserDepartment> userDepartments = userDepartmentService.getByUserId(id);
            if (!CollectionUtils.isEmpty(userDepartments)) {
                List<Long> ids = userDepartments.stream()
                        .map(UserDepartment::getId)
                        .collect(Collectors.toList());
                userDepartmentService.removeByIds(ids);
            }
            User user = new User();
            user.setId(id);
            user.setUsername(entityDTO.getUsername());
            user.setNickname(entityDTO.getNickname());
            user.setUpdator(currentUser.getId().toString());
            this.updateById(user);
        } else {
            List<User> list = this.list(new QueryWrapper<User>().lambda().eq(User::getUsername, entityDTO.getUsername()));
            if (!CollectionUtils.isEmpty(list)) {
                throw new BaseException(EyeStatus.USERNAME_EXIST);
            }
            User user = new User();
            user.setPassword(encrypt.encode("Aa123456"));
            user.setUsername(entityDTO.getUsername());
            user.setNickname(entityDTO.getNickname());
            user.setCreator(currentUser.getId().toString());
            this.save(user);
            id = user.getId();
        }
        List<UserRole> userRoles = new ArrayList<>();
        List<Long> roleList = entityDTO.getRoleList();
        for (Long roleId : roleList) {
            UserRole userRole = new UserRole();
            userRole.setUserId(id);
            userRole.setRoleId(roleId);
            userRoles.add(userRole);
        }
        userRoleService.saveBatch(userRoles);

        Long departmentId = entityDTO.getDepartmentId();
        if (!ObjectUtils.isEmpty(departmentId)) {
            UserDepartment entity = new UserDepartment();
            entity.setUserId(id);
            entity.setDepartmentId(departmentId);
            userDepartmentService.save(entity);
        }
        return id;
    }

    /**
     * 根据id获取一条记录
     *
     * @param id
     * @return
     */
    @Override
    public User getUserById(Long id) {
        return baseMapper.selectById(id);
    }

    /**
     * 根据部门获取网格员信息
     *
     * @param deviceDepartmentDTO 入参
     * @return
     */
    @Override
    public List<UserVO> getUserByDepartment(DeviceDepartmentDTO deviceDepartmentDTO) {
        log.info("根据部门查询网格员信息，入参为：[{}]", JSONObject.toJSONString(deviceDepartmentDTO));
        List<UserVO> userInfoList = new ArrayList<>();
        Map<String, Object> map = ConvertUtil.objectToMap(deviceDepartmentDTO);
        map.put("code", RoleCodeEnum.GRID.getCode());

        Long departmentId = deviceDepartmentDTO.getDepartmentId();
        departmentId = departmentService.dataHandle(departmentId);
        if (departmentId != null) {
            Map<String, Department> parentDepartments = departmentService.getParentDepartments(departmentId);
            if (parentDepartments.containsKey(DepartmentGradeEnum.GRID.getCode())) {
                map.put("flag", "1");
                map.put("gridId", parentDepartments.get(DepartmentGradeEnum.GRID.getCode()).getId());

            } else if (parentDepartments.containsKey(DepartmentGradeEnum.SOCIAL.getCode())) {
                map.put("flag", "2");
                map.put("socialId", parentDepartments.get(DepartmentGradeEnum.SOCIAL.getCode()).getId());

            } else if (parentDepartments.containsKey(DepartmentGradeEnum.STREET.getCode())) {
                map.put("flag", "3");
                map.put("streetId", parentDepartments.get(DepartmentGradeEnum.STREET.getCode()).getId());

            } else if (parentDepartments.containsKey(DepartmentGradeEnum.AREA.getCode())) {
                map.put("flag", "4");
                map.put("areaId", parentDepartments.get(DepartmentGradeEnum.AREA.getCode()).getId());

            }
        }
        return baseMapper.selectDataListByDepartment(map);
    }

    @Override
    public List<User> getGridUserList() {
        return baseMapper.selectGridUserList(RoleCodeEnum.GRID.getCode());
    }

    @Override
    public List<User> getUserByRoleCode(RoleCodeEnum roleCodeEnum) {
        return baseMapper.selectGridUserList(roleCodeEnum.getCode());
    }

    @Override
    public List<GridUserVO> selectGridUserListByParam(Map<String, Object> hashMap) {
        return baseMapper.selectGridUserListByParam(hashMap);
    }

    @Override
    public List<User> selectGridMemberReportByParam(Map<String, Object> hashMap) {
        return baseMapper.selectGridMemberReportByParam(hashMap);
    }

    @Override
    public Boolean deleteAccount() {
        log.info("处理基础数据-删除原有的处置部门账户");
        //1.根据“处置部门”角色查询用户
        Role role = roleService.getOne(new QueryWrapper<Role>().lambda().eq(Role::getName, "处置部门"));
        Long roleId = role.getId();

        List<UserRole> userRoleList = userRoleService.getByRoleId(roleId);
        if( CollectionUtils.isEmpty(userRoleList) ){
            log.info("用户为空");
            return Boolean.TRUE;
        }

        for (UserRole userRole : userRoleList) {
            Long userId = userRole.getUserId();
            if( userId != null ){
                //2.根据用户id，删除用户角色表
                userRoleService.deleteByUserId(userId);
                //3.根据用户id，删除用户部门表
                userDepartmentService.deleteByUserId(userId);
                //4.删除用户信息
                baseMapper.deleteById(userId);
            }
        }
        return Boolean.TRUE;
    }
}
