package cn.com.citydo.module.core.service;

import cn.com.citydo.module.core.dto.CancleBindDTO;
import cn.com.citydo.module.core.dto.DeviceBindDTO;
import cn.com.citydo.module.core.dto.DeviceBindQUERY;
import cn.com.citydo.module.core.entity.DeviceGridMember;
import cn.com.citydo.module.core.vo.DeviceBindVO;
import com.baomidou.mybatisplus.core.metadata.IPage;
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
public interface DeviceGridMemberService extends IService<DeviceGridMember> {

    Boolean bind(DeviceBindDTO deviceBindDTO);

    IPage<DeviceBindVO> record(DeviceBindQUERY deviceBindQUERY);

    Boolean cancle(CancleBindDTO cancleBindDTO);

    List<DeviceGridMember> getByDeviceId(Long deviceId);

    List<DeviceGridMember> getByGridMemberId(Long gridMemberId, Long deviceId);
}
