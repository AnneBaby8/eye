package cn.com.citydo.module.basic.mapper;

import cn.com.citydo.module.basic.entity.RolePermission;
import cn.com.citydo.module.basic.vo.RolePermissionVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 角色权限关系表 Mapper 接口
 * </p>
 *
 * @author blackcat
 * @since 2021-05-20
 */
public interface RolePermissionMapper extends BaseMapper<RolePermission> {

    /**
     * 查询已有的权限
     * @param hashMap 入参
     * @return
     */
    List<RolePermissionVO> selectHasPermissionByParam(Map<String,Object> hashMap);

}
