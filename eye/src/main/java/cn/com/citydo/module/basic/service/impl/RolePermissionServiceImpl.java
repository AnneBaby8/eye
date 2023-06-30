package cn.com.citydo.module.basic.service.impl;

import cn.com.citydo.module.basic.dto.PermissionListDTO;
import cn.com.citydo.module.basic.dto.RoleGrantDTO;
import cn.com.citydo.module.basic.entity.Permission;
import cn.com.citydo.module.basic.entity.RolePermission;
import cn.com.citydo.module.basic.mapper.RolePermissionMapper;
import cn.com.citydo.module.basic.service.PermissionService;
import cn.com.citydo.module.basic.service.RolePermissionService;
import cn.com.citydo.module.basic.vo.PermissionVO;
import cn.com.citydo.module.basic.vo.UserPrincipal;
import cn.com.citydo.utils.SecurityUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 角色权限关系表 服务实现类
 * </p>
 *
 * @author blackcat
 * @since 2021-05-20
 */
@Slf4j
@Service
public class RolePermissionServiceImpl extends ServiceImpl<RolePermissionMapper, RolePermission> implements RolePermissionService {

    @Autowired
    private PermissionService permissionService;

    /**
     * 给角色授权
     * @param roleGrantDTO
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean grant(RoleGrantDTO roleGrantDTO) {
        //获取当前登录人
        UserPrincipal user = SecurityUtil.getCurrentUser();

        Long roleId = roleGrantDTO.getRoleId();
        List<Long> permissionList = roleGrantDTO.getPermissionId();

        //判断该角色是否已经初始化,若初始化，则逻辑删除
        List<RolePermission> list = this.selectDataListByRoleId(roleId);
        if(!CollectionUtils.isEmpty(list)){
            UpdateWrapper<RolePermission> updateWrapper = new UpdateWrapper<>();
            updateWrapper.lambda().eq(RolePermission::getRoleId,roleGrantDTO.getRoleId()).set(RolePermission::getDel, true);
            this.update(updateWrapper);
        }
        List<RolePermission> entityList = new ArrayList<>(10);
        for (Long permissionId : permissionList) {
            RolePermission rolePermission = new RolePermission();
            rolePermission.setPermissionId(permissionId);
            rolePermission.setRoleId(roleId);
            rolePermission.setCreator(user.getId().toString());
            rolePermission.setUpdator(user.getId().toString());
            entityList.add(rolePermission);
        }
        return this.saveBatch(entityList);
    }

    /**
     * 根据角色id获取所有权限
     * @param roleId 角色id
     * @return
     */
    @Override
    public List<RolePermission> selectDataListByRoleId(Long roleId) {
        log.info("根据角色id获取所有权限，入参为：",roleId);
        if(roleId == null ){
            return new ArrayList<>();
        }
        QueryWrapper<RolePermission> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(RolePermission::getRoleId,roleId);
        List<RolePermission> list = this.list(queryWrapper);
        return list;
    }
    /**
     * 获取权限
     * @param permissionListDTO 入参
     * @return
     */
    @Override
    public List<PermissionVO> getPermisssionByRoleId(PermissionListDTO permissionListDTO) {
        List<PermissionVO> list = new ArrayList<>();
        //查询所有权限
        List<Permission> permissionList = permissionService.getAll();
        if(CollectionUtils.isEmpty(permissionList)){
            return list;
        }

        //查询某角色已经设置的权限
        QueryWrapper<RolePermission> rolePermissionWrapper = new QueryWrapper<>();
        rolePermissionWrapper.lambda().eq(RolePermission::getRoleId,permissionListDTO.getRoleId());
        List<RolePermission> rolePermissionList = this.list(rolePermissionWrapper);
        List<Long> permissionIdList = new ArrayList<>();
        if(!CollectionUtils.isEmpty(rolePermissionList)){
            permissionIdList = rolePermissionList.stream().map(RolePermission::getPermissionId).collect(Collectors.toList());
        }

        for (Permission permission : permissionList) {
            PermissionVO permissionVO = new PermissionVO();
            BeanUtils.copyProperties(permission,permissionVO);
            permissionVO.setPId(permission.getParentId());
            if(!CollectionUtils.isEmpty(permissionIdList)){
                if(permissionIdList.contains(permission.getId())){
                    permissionVO.setIsSelect(Boolean.TRUE);;
                }
            }
            list.add(permissionVO);
        }
        return list;
    }

    @Override
    public List<RolePermission> selectDataListByRoleIdList(List<Long> roleIdList) {
        QueryWrapper<RolePermission> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().in(RolePermission::getRoleId,roleIdList);
        List<RolePermission> rolePermissions = baseMapper.selectList(queryWrapper);
        return rolePermissions;
    }


}
