package cn.com.citydo.module.core.service;

import cn.com.citydo.module.core.entity.DeviceBindUser;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 设备关联网格员 服务类
 * </p>
 *
 * @author blackcat
 * @since 2021-06-01
 */
public interface DeviceBindUserService extends IService<DeviceBindUser> {

   List<DeviceBindUser> getByUserId(Long userId);

  String getUserName(Long workflowId);
}
