package cn.com.citydo.module.core.service;

import cn.com.citydo.module.core.dto.DeviceDepartmentDTO;
import cn.com.citydo.module.core.entity.Device;
import cn.com.citydo.module.core.vo.DeviceBindVO;
import cn.com.citydo.module.core.vo.OverviewDeviceVO;
import cn.com.citydo.module.core.vo.OverviewWarnTypeVO;
import cn.com.citydo.module.screen.dto.OverviewDataDTO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 设备表 服务类
 * </p>
 *
 * @author blackcat
 * @since 2021-06-01
 */
public interface DeviceService extends IService<Device> {
    /**
     * 根据部门获取设备
     * @param deviceDepartmentDTO
     * @return
     */
    List<Device> getDeviceByDepartment(DeviceDepartmentDTO deviceDepartmentDTO);

    /**
     * 根据id获取设备地址信息
     * @param id
     * @return
     */
    DeviceBindVO getAddressInfoById(Long id);

    List<Device> getByStreamId(String streamId);

    IPage<OverviewDeviceVO> selectOverviewDeviceData(OverviewDataDTO overviewDataDTO);

    List<OverviewWarnTypeVO> selectWarnTypeDataList(OverviewDataDTO overviewDataDTO);

}
