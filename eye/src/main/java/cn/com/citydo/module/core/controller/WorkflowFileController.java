package cn.com.citydo.module.core.controller;


import cn.com.citydo.common.ApiResponse;
import cn.com.citydo.common.valid.InsertGroup;
import cn.com.citydo.module.basic.vo.UserPrincipal;
import cn.com.citydo.module.core.dto.WarningConfigDTO;
import cn.com.citydo.module.core.dto.WorkflowFileDTO;
import cn.com.citydo.module.core.entity.WarningConfig;
import cn.com.citydo.module.core.entity.WorkflowFile;
import cn.com.citydo.module.core.service.WorkflowFileService;
import cn.com.citydo.utils.SecurityUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import cn.com.citydo.common.frame.BaseController;

/**
 * <p>
 * 流程文件表 前端控制器
 * </p>
 *
 * @author blackcat
 * @since 2021-07-29
 */
@RestController
@RequestMapping("/core/workflowFile")
public class WorkflowFileController {


    @Autowired
    private WorkflowFileService baseService;

    @ApiOperation(value = "核查图片新增新增")
    @PostMapping("create")
    public ApiResponse<Boolean> create(@Validated(InsertGroup.class) @RequestBody WorkflowFileDTO entityDTO) {
        WorkflowFile entity = toEntityConver(entityDTO);
        UserPrincipal user = SecurityUtil.getCurrentUser();
        entity.setCreator(user.getId().toString());
        entity.setUpdator(user.getId().toString());

        return ApiResponse.ofSuccess(baseService.save(entity));
    }

    private WorkflowFile toEntityConver(WorkflowFileDTO entityDTO) {
        WorkflowFile entity = new WorkflowFile();
        BeanUtils.copyProperties(entityDTO,entity);
        return entity;
    }
}

