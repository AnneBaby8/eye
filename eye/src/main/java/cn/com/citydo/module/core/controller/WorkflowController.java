package cn.com.citydo.module.core.controller;


import cn.com.citydo.common.ApiResponse;
import cn.com.citydo.common.BaseException;
import cn.com.citydo.common.EyeStatus;
import cn.com.citydo.common.enums.NewsEnum;
import cn.com.citydo.common.enums.ProcessStatusEnum;
import cn.com.citydo.common.enums.RoleCodeEnum;
import cn.com.citydo.module.basic.entity.Role;
import cn.com.citydo.module.basic.vo.UserPrincipal;
import cn.com.citydo.module.core.dto.HandleDTO;
import cn.com.citydo.module.core.dto.WorkflowQUERY;
import cn.com.citydo.module.core.entity.Workflow;
import cn.com.citydo.module.core.entity.WorkflowFile;
import cn.com.citydo.module.core.entity.WorkflowStep;
import cn.com.citydo.module.core.service.DeviceBindUserService;
import cn.com.citydo.module.core.service.WorkflowFileService;
import cn.com.citydo.module.core.service.WorkflowService;
import cn.com.citydo.module.core.service.WorkflowStepService;
import cn.com.citydo.module.core.vo.HandleVO;
import cn.com.citydo.module.core.vo.WorkflowDetailVO;
import cn.com.citydo.module.core.vo.WorkflowLogVO;
import cn.com.citydo.module.core.vo.WorkflowVO;
import cn.com.citydo.module.screen.entity.EventWarningDetail;
import cn.com.citydo.module.screen.enums.WarnTypeEnum;
import cn.com.citydo.module.screen.service.EventWarningDetailService;
import cn.com.citydo.utils.SecurityUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 流程主表 前端控制器
 * </p>
 *
 * @author blackcat
 * @since 2021-06-08
 */
@Api(tags = "web-事件模块")
@RestController
@RequestMapping("api/core/workflow")
@Slf4j
public class WorkflowController {


    @Autowired
    private WorkflowService workflowService;
    @Autowired
    private EventWarningDetailService eventWarningDetailService;

    @Autowired
    private WorkflowStepService workflowStepService;

    @Autowired
    private WorkflowFileService workflowFileService;

    @Autowired
    private DeviceBindUserService deviceBindUserService;

    @Value("${fileserver.url}")
    private String fileServerUrl;

    @ApiOperation(value = "事件列表的接口")
    @PostMapping("page")
    public ApiResponse<IPage<WorkflowVO>> page(@RequestBody WorkflowQUERY query) {
        IPage<WorkflowVO> iPage = workflowService.getByQuery(query);
        return ApiResponse.ofSuccess(iPage);
    }


//    @ApiOperation(value = "街道待处理列表的接口")
//    @PostMapping("streetPage")
//    public ApiResponse<IPage<WorkflowStreetVO>> streetPage(@RequestBody WorkflowQUERY query) {
//        IPage<WorkflowStreetVO> iPage = workflowService.streetPage(query);
//        return ApiResponse.ofSuccess(iPage);
//    }

    @ApiOperation(value = "获取当前流程的处置部门")
    @GetMapping("getHandle")
    public ApiResponse<List<HandleVO>> getHandle(Long workflowId) {
        List<HandleVO> list = workflowService.getHandle(workflowId);
        return ApiResponse.ofSuccess(list);
    }

    @ApiOperation(value = "社区督办")
    @GetMapping("supervise")
    public ApiResponse<Long> supervise(@RequestParam Long workflowId, String remark) {
        UserPrincipal user = SecurityUtil.getCurrentUser();
        List<Role> roles = user.getRoles();
        if (CollectionUtils.isEmpty(roles) || !roles.stream().map(Role::getCode).collect(Collectors.toList()).contains(RoleCodeEnum.SOCIAL.getCode())) {
            throw new BaseException(500, "没有督办权限");
        }
        return ApiResponse.ofSuccess(workflowService.supervise(workflowId, NewsEnum.SUPRISE_SOCIAL, remark));
    }


    @ApiOperation(value = "街道网格中心自行处置")
    @PostMapping("handle")
    public ApiResponse<Long> handle(@RequestBody HandleDTO handle) {
        return ApiResponse.ofSuccess(workflowService.handle(handle, RoleCodeEnum.STREET));
    }

    @ApiOperation(value = "街道网格中心作废")
    @GetMapping("cancel")
    public ApiResponse<Long> cancel(@RequestParam Long workflowId, String remark) {
        return ApiResponse.ofSuccess(workflowService.cancel(workflowId, remark));
    }

    @ApiOperation(value = "街道网格中心转处置部门")
    @GetMapping("transfer")
    public ApiResponse<Long> transfer(@RequestParam Long workflowId, Long departmentId, @RequestParam String remark) {
        return ApiResponse.ofSuccess(workflowService.streetTransfer(workflowId, remark, departmentId));
    }

    @ApiOperation(value = "事件详情")
    @GetMapping("getDetail")
    public ApiResponse<WorkflowDetailVO> getDetail(@RequestParam(value = "workflowId", required = true) Long workflowId) {
        WorkflowDetailVO workflowDetail = new WorkflowDetailVO();
        Workflow workflow = workflowService.getById(workflowId);
        if (!ObjectUtils.isEmpty(workflow)) {
            BeanUtils.copyProperties(workflow, workflowDetail);
            // TODO 判断事件类型，涉及到人员事件需获取人员信息
            if (WarnTypeEnum.FOCUS_VISIT.getKey().equals(workflowDetail.getEventType()) || WarnTypeEnum.HELP_GROUP.getKey().equals(workflowDetail.getEventType()) || WarnTypeEnum.EMPTY_NESTER.getKey().equals(workflowDetail.getEventType())) {
                List<EventWarningDetail> details = eventWarningDetailService.queryByEventWarningId(workflow.getEventWarningId());
                if (details.size() > 0) {
                    workflowDetail.setEventPeople(details.get(0).getUsername());
                    EventWarningDetail detail = details.get(0);
                    detail.setPicture(StringUtils.isNotEmpty(detail.getPicture())?fileServerUrl+detail.getPicture():"");
                    workflowDetail.setEventWarningDetail(detail);

                }
            }

            UserPrincipal user = SecurityUtil.getCurrentUser();
            List<Role> roleList = user.getRoles();
            for (Role role : roleList) {
                // 处置操作
                if (RoleCodeEnum.STREET.getCode().equals(role.getCode())) {
                    if (Arrays.asList(ProcessStatusEnum.REPORT_STREET.getCode(), ProcessStatusEnum.HANDLE_TO_STREET.getCode()).contains(workflow.getStatus())) {
                        workflowDetail.setIsCancel(0);
                        workflowDetail.setIsChangeDept(0);
                        workflowDetail.setIsDeal(0);
                        break;
                    }
                }
            }

            workflowDetail.setImage(fileServerUrl + workflowDetail.getImage());

            //获取处置图片
            List<WorkflowStep> byWorkflowIdList = workflowStepService.getByWorkflowId(workflow.getId());
            if (!CollectionUtils.isEmpty(byWorkflowIdList)) {
                int size = byWorkflowIdList.size();
                String image = byWorkflowIdList.get(size - 1).getImage();
                if (StringUtils.isNotEmpty(image)) {
                    workflowDetail.setCheckImage(fileServerUrl + image);
                }
            }
            List<WorkflowFile> files = workflowFileService.getFiles(workflow.getId());
            if (!CollectionUtils.isEmpty(files)) {
                List<String> captureList = new ArrayList<>(10);
                for (WorkflowFile workflowFile : files) {
                    captureList.add(fileServerUrl + workflowFile.getImage());
                }
                workflowDetail.setCaptureList(captureList);
            }
            StringBuffer gridUserName = new StringBuffer();


            gridUserName.append(deviceBindUserService.getUserName(workflowId));
            if( StringUtils.isNotEmpty(workflow.getAssignGridName()) ){
                gridUserName.append(",").append(workflow.getAssignGridName());
            }
            workflowDetail.setGridUserName(gridUserName.toString());

        }
        return ApiResponse.ofSuccess(workflowDetail);
    }

    @ApiOperation(value = "流程日志")
    @GetMapping("log")
    public ApiResponse<WorkflowLogVO> log(@RequestParam(value = "workflowId", required = true) Long workflowId) {
        WorkflowLogVO log = new WorkflowLogVO();
        Workflow workflow = workflowService.getById(workflowId);
        if (ObjectUtils.isEmpty(workflow)) {
            throw new BaseException(EyeStatus.PARAM_ERROR);
        }
        List<WorkflowStep> list = workflowStepService.getByWorkflowId(workflowId);
        log.setLogList(list);
        if (ProcessStatusEnum.CANCEL.getCode().equals(workflow.getStatus()) || ProcessStatusEnum.END.getCode().equals(workflow.getStatus())) {
            log.setIsEnd(Boolean.TRUE);
        }
        return ApiResponse.ofSuccess(log);
    }

    @ApiOperation(value = "初始化设备网格员绑定信息")
    @GetMapping("initGridUserInfo")
    public ApiResponse initGridUserInfo(){
        workflowService.initGridUserInfo();
        return ApiResponse.ofSuccess();
    }


}

