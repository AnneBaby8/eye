package cn.com.citydo.module.core.controller;

import cn.com.citydo.common.ApiResponse;
import cn.com.citydo.common.enums.DepartmentGradeEnum;
import cn.com.citydo.common.enums.NewsReadEnum;
import cn.com.citydo.module.basic.entity.Department;
import cn.com.citydo.module.basic.entity.User;
import cn.com.citydo.module.basic.service.DepartmentService;
import cn.com.citydo.module.basic.service.UserService;
import cn.com.citydo.module.basic.vo.UserPrincipal;
import cn.com.citydo.module.core.dto.WorkflowNewsDTO;
import cn.com.citydo.module.core.dto.WorkflowNewsQUERY;
import cn.com.citydo.module.core.entity.WorkflowNews;
import cn.com.citydo.module.core.service.WorkflowNewsService;
import cn.com.citydo.module.core.service.WorkflowService;
import cn.com.citydo.module.core.vo.WorkflowNewsVO;
import cn.com.citydo.utils.BaseTreeNode;
import cn.com.citydo.utils.SecurityUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author blackcat
 * @create 2021/6/10 11:28
 * @version: 1.0
 * @description:
 */
@Api(tags = "消息")
@RestController
@RequestMapping("api/core/news")
@Slf4j
public class WorkflowNewsController {

    @Autowired
    private WorkflowNewsService baseService;

    @Autowired
    private WorkflowService workflowService;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private UserService userService;

    @ApiOperation(value = "消息查询-暂无数据(发送逻辑未写)")
    @PostMapping("page")
    public ApiResponse<IPage<WorkflowNewsVO>> page(@RequestBody WorkflowNewsQUERY query) {
        log.info("开始查询消息列表，入参为：[{}]", JSONObject.toJSONString(query));
        IPage<WorkflowNews> page = new Page<>(query.getPageNo(), query.getPageSize());
        QueryWrapper<WorkflowNews> queryWrapper = getWorkflowNewsLambdaQueryWrapper(query);
        //首页消息
        if (!ObjectUtils.isEmpty(query.getIsRead()) && 0 == query.getIsRead()) {
            queryWrapper.lambda().eq(WorkflowNews::getIsRead, NewsReadEnum.UNREAD.getCode()).orderByDesc(WorkflowNews::getCreateTime);
        } else {
            //消息列表
            queryWrapper.lambda().orderByAsc(WorkflowNews::getIsRead).orderByDesc(WorkflowNews::getCreateTime);
        }
        IPage<WorkflowNews> data  = baseService.page(page, queryWrapper);
        List<WorkflowNews> records = data.getRecords();

        List<WorkflowNewsVO> list = new ArrayList<>();
        for (WorkflowNews record : records) {
            WorkflowNewsVO workflowNewsVO = new WorkflowNewsVO();
            BeanUtils.copyProperties(record, workflowNewsVO);
            if (StringUtils.isNotEmpty(record.getCreator())) {
                User userById = userService.getUserById(Long.valueOf(record.getCreator()));
                if (userById != null) {
                    workflowNewsVO.setCreatorName(userById.getNickname());
                }
            }else{
                workflowNewsVO.setCreatorName("预警平台");
            }
            list.add(workflowNewsVO);
        }
        IPage<WorkflowNewsVO> resultPage  = new Page();
        BeanUtils.copyProperties(page, resultPage);
        resultPage.setRecords(list);
        return ApiResponse.ofSuccess(resultPage);
    }

    private  QueryWrapper<WorkflowNews> getWorkflowNewsLambdaQueryWrapper(WorkflowNewsQUERY query) {
        QueryWrapper<WorkflowNews> queryWrapper = new QueryWrapper<>();
        LambdaQueryWrapper<WorkflowNews> lambda = queryWrapper.lambda();
        UserPrincipal user = SecurityUtil.getCurrentUser();

        if (Boolean.TRUE.equals(query.getSupervise())) {
            log.info("newTypeList");
            if (!CollectionUtils.isEmpty(query.getNewTypeList())) {
                lambda.in(WorkflowNews::getNewType, query.getNewTypeList());
            }
        } else {
            if (StringUtils.isNotEmpty(query.getNewType())) {
                log.info("newType");
                lambda.eq(WorkflowNews::getNewType, query.getNewType());
            }
        }

        if (!ObjectUtils.isEmpty(query.getStartTime())) {
            lambda.ge(WorkflowNews::getCreateTime, query.getStartTime());
        }
        if (!ObjectUtils.isEmpty(query.getEndTime())) {
            lambda.le(WorkflowNews::getCreateTime, query.getEndTime());
        }
        if (query.getSocialId() != null) {
            lambda.eq(WorkflowNews::getSocialId, query.getSocialId());
        }
        queryWrapper.lambda().eq(WorkflowNews::getUserId, user.getId());
        return queryWrapper;
    }

    @ApiOperation(value = "消息详情")
    @PostMapping("detail")
    public ApiResponse<WorkflowNewsVO> detail(@RequestBody WorkflowNewsDTO workflowNewsDTO) {
        return ApiResponse.ofSuccess(baseService.detail(workflowNewsDTO));
    }

    @ApiOperation(value = "设置已读/未读")
    @PostMapping("setReadStatus")
    public ApiResponse<Boolean> setReadStatus(@RequestBody WorkflowNewsDTO workflowNewsDTO) {
        return ApiResponse.ofSuccess(baseService.setReadStatus(workflowNewsDTO));
    }

    @ApiOperation(value = "处置部门-获取当前用户的社区")
    @PostMapping("getSocialData")
    public ApiResponse<List<BaseTreeNode>> getSocialData() {
        return ApiResponse.ofSuccess(departmentService.getAppDepartmentDataList());
    }

    @ApiOperation(value = "处置部门-获取当前用户的街道")
    @PostMapping("getStreetData")
    public ApiResponse<Department> getStreetData() {
        return ApiResponse.ofSuccess(departmentService.getDepartmentDataByGrade(DepartmentGradeEnum.STREET.getCode()));
    }
}