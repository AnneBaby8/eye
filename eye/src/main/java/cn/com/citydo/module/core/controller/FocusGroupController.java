package cn.com.citydo.module.core.controller;

import cn.com.citydo.common.ApiResponse;
import cn.com.citydo.module.core.dto.FocusGroupQUERY;
import cn.com.citydo.module.core.service.FocusGroupService;
import cn.com.citydo.module.core.vo.FocusGroupVO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 【事件管理模块】重点人群
 * </p>
 *
 * @author shaodl
 * @since 2021-06-01
 */
@Api(tags = "【事件管理】重点人群")
@Slf4j
@RestController
@RequestMapping("api/core/focusGroup")
public class FocusGroupController {

    @Autowired
    private FocusGroupService focusGroupService;


//    @ApiOperation(value = "重点人群列表查询")
//    @PostMapping("page")
//    public ApiResponse<IPage<FocusGroupVO>> page(@RequestBody FocusGroupQUERY query) {
//        IPage<FocusGroupVO> iPage = focusGroupService.getByQuery(query);
//        return ApiResponse.ofSuccess(iPage);
//    }

    @ApiOperation(value = "重点人群列表查询")
    @PostMapping("page")
    public ApiResponse<IPage<FocusGroupVO>> pageFocusGroup(@RequestBody FocusGroupQUERY query) {
        log.info("开始查询重点人群列表数据...");
        IPage<FocusGroupVO> resultPage = focusGroupService.getByQuery(query);
        log.info("完成查询重点人员列表数据...");
        return ApiResponse.ofSuccess(resultPage);
    }



}
