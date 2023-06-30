package cn.com.citydo.module.screen.controller;

import cn.com.citydo.common.ApiResponse;
import cn.com.citydo.module.basic.vo.DepartmentVO;
import cn.com.citydo.module.core.vo.OverviewWarnTypeVO;
import cn.com.citydo.module.screen.dto.OverviewDataDTO;
import cn.com.citydo.module.screen.service.DataOverviewService;
import cn.com.citydo.utils.BaseTreeNode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 数据概览
 * </p>
 *
 * @author wx227336
 * @Date 2021/6/7 10:12
 * @Version 1.0
 */
@Api(tags = "数据概览")
@RestController
@RequestMapping("api/basic/view")
public class DataOverviewController {

    @Autowired
    private DataOverviewService baseService;

    @ApiOperation(value = "数据概览")
    @PostMapping("data")
    public ApiResponse<DepartmentVO> data(@RequestBody @Validated OverviewDataDTO overviewDataDTO) {
        return ApiResponse.ofSuccess(baseService.data(overviewDataDTO));
    }

    @ApiOperation(value = "设备列表（分页查询）")
    @PostMapping("device/page")
    public ApiResponse<DepartmentVO> getOverviewDeviceData(@RequestBody @Validated OverviewDataDTO overviewDataDTO) {
        return ApiResponse.ofSuccess(baseService.getOverviewDeviceData(overviewDataDTO));
    }

    @ApiOperation(value = "获取预警类型",response = OverviewWarnTypeVO.class)
    @PostMapping("getWarnType")
    public ApiResponse<OverviewWarnTypeVO> getWarnType(@RequestBody @Validated OverviewDataDTO overviewDataDTO) {
        return ApiResponse.ofSuccess(baseService.getWarnType(overviewDataDTO));
    }

    @ApiOperation(value = "部门")
    @PostMapping("department")
    public ApiResponse<BaseTreeNode> department() {
        return ApiResponse.ofSuccess(baseService.selectDepartmentData());
    }
}
