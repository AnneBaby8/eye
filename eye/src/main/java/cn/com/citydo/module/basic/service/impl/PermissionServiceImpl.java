package cn.com.citydo.module.basic.service.impl;

import cn.com.citydo.module.basic.entity.Permission;
import cn.com.citydo.module.basic.mapper.PermissionMapper;
import cn.com.citydo.module.basic.service.PermissionService;
import cn.com.citydo.module.basic.vo.PermissionVO;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 权限表 服务实现类
 * </p>
 *
 * @author blackcat
 * @since 2021-05-20
 */
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements PermissionService {

    @Override
    public List<Permission> getAll() {
        List<Permission> list = this.list();
        return list;
    }

    @Override
    public List<PermissionVO> getChildrenByParentId(Long parentId) {
        List<PermissionVO> childrenByParentId = baseMapper.selectChildrenByParentId(parentId);
        return childrenByParentId;
    }
}
