package cn.com.citydo.module.core.service.impl;

import cn.com.citydo.common.enums.DepartmentGradeEnum;
import cn.com.citydo.module.basic.entity.Department;
import cn.com.citydo.module.basic.service.DepartmentService;
import cn.com.citydo.module.basic.vo.DepartmentVO;
import cn.com.citydo.module.core.dto.DeviceDepartmentDTO;
import cn.com.citydo.module.core.entity.Device;
import cn.com.citydo.module.core.mapper.DeviceMapper;
import cn.com.citydo.module.core.service.DeviceService;
import cn.com.citydo.module.core.vo.DeviceBindVO;
import cn.com.citydo.module.core.vo.OverviewDeviceVO;
import cn.com.citydo.module.core.vo.OverviewWarnTypeVO;
import cn.com.citydo.module.screen.dto.OverviewDataDTO;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 设备表 服务实现类
 * </p>
 *
 * @author blackcat
 * @since 2021-06-01
 */
@Slf4j
@Service
public class DeviceServiceImpl extends ServiceImpl<DeviceMapper, Device> implements DeviceService {

    @Autowired
    private DepartmentService departmentService;

    /**
     * 根据部门获取所有设备
     *
     * @param deviceDepartmentDTO
     * @return
     */
    @Override
    public List<Device> getDeviceByDepartment(DeviceDepartmentDTO deviceDepartmentDTO) {
        log.info("查询根据部门获取所有设备，入参为：[{}]", JSONObject.toJSONString(deviceDepartmentDTO));
        List<Device> list = new ArrayList<>();
        QueryWrapper<Device> objectQueryWrapper = new QueryWrapper<>();
        objectQueryWrapper.orderByDesc("id");
        Long departmentId = deviceDepartmentDTO.getDepartmentId();
        departmentId = departmentService.dataHandle(departmentId);
        if (departmentId != null) {
            Map<String, Department> parentDepartments = departmentService.getParentDepartments(departmentId);
            if (parentDepartments.containsKey(DepartmentGradeEnum.GRID.getCode())) {
                objectQueryWrapper.lambda().eq(Device::getGridId, parentDepartments.get(DepartmentGradeEnum.GRID.getCode()).getId());
            } else if (parentDepartments.containsKey(DepartmentGradeEnum.SOCIAL.getCode())) {
                objectQueryWrapper.lambda().eq(Device::getSocialId, parentDepartments.get(DepartmentGradeEnum.SOCIAL.getCode()).getId());
            } else if (parentDepartments.containsKey(DepartmentGradeEnum.STREET.getCode())) {
                objectQueryWrapper.lambda().eq(Device::getStreetId, parentDepartments.get(DepartmentGradeEnum.STREET.getCode()).getId());
            } else if (parentDepartments.containsKey(DepartmentGradeEnum.AREA.getCode())) {
                objectQueryWrapper.lambda().eq(Device::getAreaId, parentDepartments.get(DepartmentGradeEnum.AREA.getCode()).getId());
            }
        }
        list = this.list(objectQueryWrapper);
        return list;
    }

    /**
     * 根据id获取设备地址信息
     *
     * @param id
     * @return
     */
    @Override
    public DeviceBindVO getAddressInfoById(Long id) {
        DeviceBindVO deviceBindVO = new DeviceBindVO();

        Device device = baseMapper.selectById(id);
        if (device != null) {
            DepartmentVO vo = departmentService.getPartDepartmentName(device.getAreaId(), device.getStreetId(), device.getSocialId());
            if (vo != null) {
                //地址信息
                deviceBindVO.setAreaId(device.getAreaId());
                deviceBindVO.setStreetId(device.getStreetId());
                deviceBindVO.setSocialId(device.getSocialId());
                deviceBindVO.setAreaName(vo.getAreaName());
                deviceBindVO.setStreetName(vo.getStreetName());
                deviceBindVO.setSocialName(vo.getSocialName());
                //设备名称
                deviceBindVO.setDeviceName(device.getName());
                return deviceBindVO;
            }
        }
        return deviceBindVO;
    }

    /**
     * 数据概览-设备列表
     *
     * @param overviewDataDTO
     * @return
     */
    @Override
    public IPage<OverviewDeviceVO> selectOverviewDeviceData(OverviewDataDTO overviewDataDTO) {
        log.info("数据概览-设备列表，入参为：[{}]=================", JSONObject.toJSONString(overviewDataDTO));
        if (overviewDataDTO == null) {
            return null;
        }
        //分页数据
        Page<OverviewDeviceVO> page = new Page<OverviewDeviceVO>(overviewDataDTO.getPageNo(), overviewDataDTO.getPageSize());
        IPage<OverviewDeviceVO> pageList = baseMapper.selectOverviewDeviceData(page, overviewDataDTO);

        List<OverviewDeviceVO> records = pageList.getRecords();

        if (!CollectionUtils.isEmpty(records)) {
            for (OverviewDeviceVO overviewDeviceVO : records) {
                DepartmentVO vo = departmentService.getPartDepartmentName(overviewDeviceVO.getAreaId(), overviewDeviceVO.getStreetId(), overviewDeviceVO.getSocialId());
                if (vo != null) {
                    //地址信息
                    overviewDeviceVO.setAreaName(vo.getAreaName());
                    overviewDeviceVO.setStreetName(vo.getStreetName());
                    overviewDeviceVO.setSocialName(vo.getSocialName());
                }
            }
        }
        return pageList;
    }

    @Override
    public List<OverviewWarnTypeVO> selectWarnTypeDataList(OverviewDataDTO overviewDataDTO) {
        return  baseMapper.selectWarnTypeDataList(overviewDataDTO);
    }

    @Override
    public List<Device> getByStreamId(String streamId) {
        return this.list(new QueryWrapper<Device>().lambda().eq(Device::getStreamId, streamId));
    }

}
