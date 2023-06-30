package cn.com.citydo.module.core.controller;


import cn.com.citydo.common.ApiResponse;
import cn.com.citydo.module.basic.entity.User;
import cn.com.citydo.module.basic.service.UserService;
import cn.com.citydo.module.core.dto.CancleBindDTO;
import cn.com.citydo.module.core.dto.DeviceBindDTO;
import cn.com.citydo.module.core.dto.DeviceBindQUERY;
import cn.com.citydo.module.core.dto.DeviceDepartmentDTO;
import cn.com.citydo.module.core.entity.Device;
import cn.com.citydo.module.core.service.DeviceGridMemberService;
import cn.com.citydo.module.core.service.DeviceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 设备关联网格员 前端控制器
 * </p>
 *
 * @author blackcat
 * @since 2021-06-01
 */
@Api(tags = "网格绑定")
@RestController
@RequestMapping("api/core/deviceGridMember")
public class DeviceGridMemberController{
    @Autowired
    private DeviceGridMemberService baseService;

    @Autowired
    private DeviceService deviceService;

    @Autowired
    private UserService userService;

    //TODO   接口修改 参数类型
    @ApiOperation(value = "设备绑定")
    @PostMapping("bind")
    public ApiResponse<Boolean> bind(@RequestBody @Validated DeviceBindDTO deviceBindDTO){
        return ApiResponse.ofSuccess(baseService.bind(deviceBindDTO));
    }

    /**
     * 根据部门获取所有设备
     * @param deviceDepartmentDTO
     * @return
     */
    @ApiOperation(value = "根据部门获取所有设备")
    @PostMapping("getDeviceByDepartment")
    public ApiResponse<Device> getDeviceByDepartment(@RequestBody DeviceDepartmentDTO deviceDepartmentDTO) {
        return ApiResponse.ofSuccess(deviceService.getDeviceByDepartment(deviceDepartmentDTO));
    }

    @ApiOperation(value = "根据部门获取网格员信息")
    @PostMapping(value = "getUserByDepartment")
    public ApiResponse<List<User>> getUserByDepartment(@RequestBody @Validated DeviceDepartmentDTO deviceDepartmentDTO){
        return ApiResponse.ofSuccess(userService.getUserByDepartment(deviceDepartmentDTO));
    }

    @ApiOperation(value = "绑定记录")
    @PostMapping(value = "record")
    public ApiResponse record(@RequestBody @Validated DeviceBindQUERY deviceBindQUERY){
        return ApiResponse.ofSuccess(baseService.record(deviceBindQUERY));
    }

    @ApiOperation(value = "解绑")
    @PostMapping(value = "cancle")
    public ApiResponse cancle(@RequestBody @Validated CancleBindDTO cancleBindDTO){
        return ApiResponse.ofSuccess(baseService.cancle(cancleBindDTO));
    }
}

