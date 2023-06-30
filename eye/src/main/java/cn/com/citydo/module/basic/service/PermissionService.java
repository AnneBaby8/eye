package cn.com.citydo.module.basic.service;

import cn.com.citydo.module.basic.entity.Permission;
import cn.com.citydo.module.basic.vo.PermissionVO;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;
/**
 * <p>
 * 权限表 服务类
 * </p>
 *
 * @author blackcat
 * @since 2021-05-20
 */
public interface PermissionService extends IService<Permission> {
    List<Permission> getAll();

    List<PermissionVO> getChildrenByParentId(Long parentId);
}
