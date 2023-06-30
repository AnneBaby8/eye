package cn.com.citydo.module.basic.service;

import cn.com.citydo.common.enums.RoleCodeEnum;
import cn.com.citydo.module.basic.dto.UserDTO;
import cn.com.citydo.module.basic.entity.Permission;
import cn.com.citydo.module.basic.entity.Role;
import cn.com.citydo.module.basic.entity.User;
import cn.com.citydo.module.basic.vo.UserVO;
import cn.com.citydo.module.core.dto.DeviceDepartmentDTO;
import cn.com.citydo.module.core.vo.GridUserVO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author blackcat
 * @since 2021-05-20
 */
public interface UserService extends IService<User> {
    List<Permission> getPermissions(Long userId);

    List<Role> getRoles(Long userId);

    Long saveOrUpdate(UserDTO entityDTO);

    /**
     * 根据id获取一条记录
     *
     * @param id
     * @return
     */
    User getUserById(Long id);

    /**
     * 根据部门获取网格员信息
     *
     * @param deviceDepartmentDTO 入参
     * @return
     */
    List<UserVO> getUserByDepartment(DeviceDepartmentDTO deviceDepartmentDTO);

    /**
     * 获取网格员列表
     * @return
     */
    List<User> getGridUserList();


     List<User> getUserByRoleCode(RoleCodeEnum roleCodeEnum);


    List<GridUserVO> selectGridUserListByParam(Map<String,Object> hashMap);

    /**
     * 查询网格员上报人员列表
     * @param hashMap
     * @return
     */
    List<User> selectGridMemberReportByParam(Map<String,Object> hashMap);

    Boolean deleteAccount();

}
