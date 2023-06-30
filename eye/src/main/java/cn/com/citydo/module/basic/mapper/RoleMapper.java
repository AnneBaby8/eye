package cn.com.citydo.module.basic.mapper;

import cn.com.citydo.module.basic.entity.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 角色表 Mapper 接口
 * </p>
 *
 * @author blackcat
 * @since 2021-05-20
 */
public interface RoleMapper extends BaseMapper<Role> {

    List<Role> getRoleDetailByOutUserId(Long outUserId);

    Role selectDataByParam(Map<String,Object> hashMap);
}
