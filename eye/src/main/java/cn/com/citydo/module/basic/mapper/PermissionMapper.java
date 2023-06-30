package cn.com.citydo.module.basic.mapper;

import cn.com.citydo.module.basic.entity.Permission;
import cn.com.citydo.module.basic.vo.PermissionVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 权限表 Mapper 接口
 * </p>
 *
 * @author blackcat
 * @since 2021-05-20
 */
public interface PermissionMapper extends BaseMapper<Permission> {
    /**
     * 查询子权限
     * @param parentId 入参
     * @return
     */
    List<PermissionVO> selectChildrenByParentId(Long parentId);
}
