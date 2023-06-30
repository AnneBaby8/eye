package cn.com.citydo.module.core.controller;


import cn.com.citydo.common.ApiResponse;
import cn.com.citydo.module.core.dto.WarningPolicyDTO;
import cn.com.citydo.module.core.entity.WarningPolicy;
import cn.com.citydo.module.core.service.WarningPolicyService;
import cn.com.citydo.module.core.vo.WarningPolicyVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * <p>
 * 预警配置表 前端控制器
 * </p>
 *
 * @author blackcat
 * @since 2021-06-01
 */
@Api(tags = "预警策略")
@RestController
@RequestMapping("api/core/warningPolicy")
public class WarningPolicyController {

    @Autowired
    private WarningPolicyService warningPolicyService;

    @ApiOperation(value = "预警策略设置")
    @PostMapping("set")
    public ApiResponse<Boolean> set(@RequestBody WarningPolicyDTO warningPolicyDTO) {
        return ApiResponse.ofSuccess(warningPolicyService.set(warningPolicyDTO));
    }


    @ApiOperation(value = "获取预警策略设置")
    @GetMapping("get")
    public ApiResponse<List<WarningPolicyVO>> get(Long configId) {
        List<WarningPolicyVO> list = new ArrayList<>();
        List<WarningPolicy> policyList = warningPolicyService.getByConfigId(configId);
        for (WarningPolicy warningPolicy : policyList) {
            WarningPolicyVO vo = new WarningPolicyVO();
            BeanUtils.copyProperties(warningPolicy, vo);
            list.add(vo);
        }
        return ApiResponse.ofSuccess(list);
    }
}

