package cn.com.citydo.module.basic.mapper;

import cn.com.citydo.module.basic.entity.User;
import cn.com.citydo.module.basic.vo.UserVO;
import cn.com.citydo.module.core.vo.GridUserVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author blackcat
 * @since 2021-05-20
 */
public interface UserMapper extends BaseMapper<User> {
    List<UserVO> selectDataListByDepartment(Map<String,Object> hashMap);

    List<User> selectGridUserList(String code);

    List<GridUserVO> selectGridUserListByParam(Map<String,Object> hashMap);

    List<User> selectGridMemberReportByParam(Map<String, Object> hashMap);
}
