package cn.com.citydo.module.core.service.impl;

import cn.com.citydo.module.basic.vo.UserPrincipal;
import cn.com.citydo.module.core.entity.DeviceBindUser;
import cn.com.citydo.module.core.mapper.DeviceBindUserMapper;
import cn.com.citydo.module.core.service.DeviceBindUserService;
import cn.com.citydo.utils.SecurityUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.base.Joiner;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *
 * </p>
 *
 * @author wx227336
 * @Date 2022/1/26 13:13
 * @Version 1.0
 */
@Slf4j
@Service
public class DeviceBindUserServiceImpl extends ServiceImpl<DeviceBindUserMapper, DeviceBindUser> implements DeviceBindUserService {

    @Override
    public List<DeviceBindUser> getByUserId(Long userId) {
        return this.list(new QueryWrapper<DeviceBindUser>().lambda().eq(DeviceBindUser::getGridUserId,userId));
    }

    @Override
    public String getUserName(Long workflowId) {
        List<DeviceBindUser> userList = this.list(new QueryWrapper<DeviceBindUser>().lambda().eq(DeviceBindUser::getWorkflowId, workflowId));
        String userName="";
        if(CollectionUtils.isEmpty(userList)){
            return userName;
        }
        userName = Joiner.on(",").join(userList.stream().map(DeviceBindUser::getGridUserName).collect(Collectors.toList()));
        return userName;
    }
}
