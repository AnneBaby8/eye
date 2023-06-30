package cn.com.citydo.module.basic.service.impl;

import cn.com.citydo.common.BaseException;
import cn.com.citydo.module.basic.entity.Role;
import cn.com.citydo.module.basic.mapper.RoleMapper;
import cn.com.citydo.module.basic.service.RoleService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 角色表 服务实现类
 * </p>
 *
 * @author blackcat
 * @since 2021-05-20
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Override
    public List<Role> getAll() {
        QueryWrapper<Role> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(Role::getDel,true);
        List<Role> roleList = this.baseMapper.selectList(queryWrapper);
        return roleList;
    }


    @Override
    public List<Role> getRoleDetailByOutUserId(Long outUserId) {
        if( outUserId == null){
            throw new BaseException(500, "用户id必须输入");
        }
        return baseMapper.getRoleDetailByOutUserId(outUserId);
    }
}
