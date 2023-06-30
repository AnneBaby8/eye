package cn.com.citydo.module.core.controller;


import cn.com.citydo.common.ApiResponse;
import cn.com.citydo.common.BaseException;
import cn.com.citydo.common.EyeStatus;
import cn.com.citydo.common.frame.BaseController;
import cn.com.citydo.common.valid.InsertGroup;
import cn.com.citydo.module.basic.entity.Department;
import cn.com.citydo.module.basic.entity.Role;
import cn.com.citydo.module.basic.entity.UserDepartment;
import cn.com.citydo.module.basic.service.DepartmentService;
import cn.com.citydo.module.basic.service.UserDepartmentService;
import cn.com.citydo.module.basic.vo.UserPrincipal;
import cn.com.citydo.module.core.dto.DeviceDTO;
import cn.com.citydo.module.core.dto.DeviceQUERY;
import cn.com.citydo.module.core.entity.Device;
import cn.com.citydo.module.core.service.DeviceService;
import cn.com.citydo.module.core.vo.DeviceVO;
import cn.com.citydo.utils.SecurityUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 设备表 前端控制器
 * </p>
 *
 * @author blackcat
 * @since 2021-06-01
 */
@Api(tags = "设备管理")
@Slf4j
@RestController
@RequestMapping("api/core/device")
public class DeviceController extends BaseController<Device, DeviceService, DeviceDTO, DeviceVO, DeviceQUERY> {

    @Autowired
    private DepartmentService departmentService;


    @Autowired
    private UserDepartmentService userDepartmentService;


    @PostMapping("page")
    @Override
    public ApiResponse<IPage<DeviceVO>> page(@RequestBody DeviceQUERY query) {
        IPage<Device> page = new Page<>(query.getPageNo(), query.getPageSize());
        QueryWrapper<Device> queryWrapper = new QueryWrapper<>();
        query.setSocialId(departmentService.dataHandle(query.getSocialId()));
        if(!ObjectUtils.isEmpty(query.getSocialId())) {
            queryWrapper.lambda().eq(Device::getSocialId, query.getSocialId())
                    .or().eq(Device::getStreetId, query.getSocialId())
                    .or().eq(Device::getGridId, query.getSocialId())
                    .or().eq(Device::getAreaId, query.getSocialId());
        }
        queryWrapper.orderByDesc("create_time");
        IPage<Device> iPage = baseService.page(page, queryWrapper);
        IPage<DeviceVO> resultPage = pageConver(iPage);
        return ApiResponse.ofSuccess(resultPage);
    }

    @Override
    protected IPage<DeviceVO> pageConver(IPage<Device> iPage) {
        List<DeviceVO> list = new ArrayList<>();
        List<Device> records = iPage.getRecords();
        for (Device record :records) {
            DeviceVO vo = new DeviceVO();
            BeanUtils.copyProperties(record, vo);
            //TODO 可能存在性能问题
            Department department = departmentService.getById(vo.getGridId());
            if(!ObjectUtils.isEmpty(department)){
                vo.setGridName(department.getName());
            }
            vo.setAreaName(departmentService.getDepartmentName(vo.getAreaId(),vo.getStreetId(),vo.getSocialId()));
            list.add(vo);
        }
        IPage<DeviceVO> resultPage = new Page<>();
        BeanUtils.copyProperties(iPage,resultPage);
        resultPage.setRecords(list);
        return resultPage;
    }
}

