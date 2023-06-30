package cn.com.citydo.module.core.controller;

import cn.com.citydo.common.ApiResponse;
import cn.com.citydo.common.BaseException;
import cn.com.citydo.common.enums.*;
import cn.com.citydo.module.basic.entity.Role;
import cn.com.citydo.module.basic.service.RoleService;
import cn.com.citydo.module.basic.service.UserService;
import cn.com.citydo.module.basic.vo.UserPrincipal;
import cn.com.citydo.module.core.dto.HandleDTO;
import cn.com.citydo.module.core.dto.WorkflowCountQUERY;
import cn.com.citydo.module.core.entity.Workflow;
import cn.com.citydo.module.core.entity.WorkflowFile;
import cn.com.citydo.module.core.entity.WorkflowStep;
import cn.com.citydo.module.core.service.*;
import cn.com.citydo.module.core.vo.*;
import cn.com.citydo.module.screen.entity.EventWarningDetail;
import cn.com.citydo.module.screen.entity.EventWarningReadedRecord;
import cn.com.citydo.module.screen.service.EventWarningDetailService;
import cn.com.citydo.module.screen.service.EventWarningReadedRecordService;
import cn.com.citydo.thirdparty.huawei.service.HuaweiApiService;
import cn.com.citydo.utils.FileOperateUtil;
import cn.com.citydo.utils.SecurityUtil;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author blackcat
 * @create 2021/6/9 14:24
 * @version: 1.0
 * @description:
 */
@Api(tags = "app-事件模块")
@RestController
@RequestMapping("api/app/workflow")
public class AppWorkflowController {

    @Value("${fileserver.saveBasePath}")
    private String saveBasePath;

    @Value("${fileserver.uploadPath}")
    private String uploadPath;


    @Value("${fileserver.url}")
    private String prePath;

    @Value("${fileserver.appUrl}")
    private String appPath;

    @Autowired
    private WorkflowService workflowService;

    @Autowired
    private WorkflowStepService workflowStepService;

    @Autowired
    private EventWarningDetailService eventWarningDetailService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private EventWarningReadedRecordService eventWarningReadedRecordService;

    @Autowired
    private WorkflowFileService workflowFileService;

    @Autowired
    private HuaweiApiService huaweiApiService;

    @Autowired
    private WorkflowNewsService workflowNewsService;

    @Autowired
    private DeviceBindUserService deviceBindUserService;

    @Autowired
    private UserService userService;

    @ApiOperation(value = "获取事件的数量-逻辑待优化")
    @PostMapping("count")
    public ApiResponse<WorkflowCount> count(@RequestBody WorkflowCountQUERY query) {
        WorkflowCount workflowCount = workflowService.count(query);
        return ApiResponse.ofSuccess(workflowCount);
    }

    @ApiOperation(value = "分页查询")
    @PostMapping("page")
    public ApiResponse<IPage<AppWorkflowVO>> page(@RequestBody WorkflowCountQUERY query) {
        return ApiResponse.ofSuccess( workflowService.pageByQuery(query));
    }

    @ApiOperation(value = "获取当前流程的处置部门")
    @GetMapping("getHandle")
    public ApiResponse<List<HandleVO>> getHandle(Long workflowId) {
        List<HandleVO> list = workflowService.getHandle(workflowId);
        return ApiResponse.ofSuccess(list);
    }


    @ApiOperation(value = "网格员自行处置")
    @PostMapping("gridHandle")
    public ApiResponse<Long> gridHandle(@RequestBody HandleDTO handle) {
        return ApiResponse.ofSuccess(workflowService.handle(handle, RoleCodeEnum.GRID));
    }


    @ApiOperation(value = "网格员上报处置部门、街道")
    @GetMapping("report")
    public ApiResponse<Long> report(@RequestParam Long workflowId,@RequestParam Long departmentId,@RequestParam String remark) {
        return ApiResponse.ofSuccess(workflowService.report(workflowId, departmentId, remark));
    }

    @ApiOperation(value = "网格员列表")
    @GetMapping("grid/list")
    public ApiResponse<List<GridUserVO>> gridList(@RequestParam(required = false) String bussinessKey) {
        return ApiResponse.ofSuccess(workflowService.gridList(bussinessKey));
    }

    @ApiOperation(value = "网格长指派网格员")
    @GetMapping("assignGrid")
    public ApiResponse<Long> assignGrid(@RequestParam(value = "workflowId", required = true) Long workflowId, @RequestParam(value = "gridUserId", required = false) Long gridUserId, @RequestParam(value = "reamrk", required = false) String reamrk) {
        return ApiResponse.ofSuccess(workflowService.assignGrid(workflowId, gridUserId, reamrk));
    }

    @ApiOperation(value = "网格长督办")
    @GetMapping("supervise")
    public ApiResponse<Long> supervise(@RequestParam Long workflowId, String remark) {
        return ApiResponse.ofSuccess(workflowService.supervise(workflowId, NewsEnum.SUPRISE_GRID_LEAD, remark));
    }


    @ApiOperation(value = "处置部门自行处置")
    @PostMapping("handle")
    public ApiResponse<Long> handle(@RequestBody HandleDTO handle) {
        return ApiResponse.ofSuccess(workflowService.handle(handle, RoleCodeEnum.HANDLE));
    }

    @ApiOperation(value = "处置部门回退")
    @GetMapping("back")
    public ApiResponse<Long> back(@RequestParam Long workflowId, String remark) {
        return ApiResponse.ofSuccess(workflowService.back(workflowId, remark));
    }

    @ApiOperation(value = "详情接口")
    @GetMapping("getDetail")
    public ApiResponse<AppWorkflowVO> getDetail(@RequestParam(value = "workflowId")Long workflowId) {
        if(workflowId == null){
            throw new BaseException(500, "事件Id");
        }
        UserPrincipal user = SecurityUtil.getCurrentUser();
        if(user == null){
            throw new BaseException(500, "当前登录人为空");
        }
        List<Role> roleList = user.getRoles();
        if (CollectionUtils.isEmpty(roleList)) {
            throw new BaseException(500, "没有权限");
        }

        AppWorkflowVO vo = new AppWorkflowVO();
        Workflow workflow = workflowService.getById(workflowId);

        if (workflow != null) {
            for (Role role : roleList) {
                if( RoleCodeEnum.GRID.getCode().equals(role.getCode()) ){
                    EventWarningReadedRecord readedRecord = new EventWarningReadedRecord();
                    readedRecord.setReaderId(user.getId());
                    readedRecord.setEventWarningId(workflow.getEventWarningId());
                    readedRecord.setReaderName(user.getNickname());
                    readedRecord.setWorkflowId(workflow.getId());
                    eventWarningReadedRecordService.create(readedRecord);
                }
            }

            BeanUtils.copyProperties(workflow, vo);
            vo.setGridName(deviceBindUserService.getUserName(workflowId));
            //退回，则展示退回人员信息
            Long reportUserId = workflow.getReportUserId();
            if( reportUserId != null ){
                vo.setBackUser(userService.getUserById(reportUserId));
            }
            //获取处置图片
            List<WorkflowStep> byWorkflowIdList = workflowStepService.getByWorkflowId(workflow.getId());
            if (!CollectionUtils.isEmpty(byWorkflowIdList)) {
                int size = byWorkflowIdList.size();
                vo.setCheckImage(byWorkflowIdList.get(size-1).getImage());
            }
            List<WorkflowFile> files = workflowFileService.getFiles(workflow.getId());
            if (!CollectionUtils.isEmpty(files)) {
                List<String> captureList = new ArrayList<>(10);
                for (WorkflowFile workflowFile : files) {
                    captureList.add(appPath + workflowFile.getImage());
                }
                vo.setCaptureList(captureList);
            }
            List<EventWarningDetail> details = eventWarningDetailService.queryByEventWarningId(workflow.getEventWarningId());
            if (details.size() > 0) {
                vo.setEventPeopleName(details.get(0).getUsername());
                vo.setCaptureTime(DateUtil.format(vo.getWarnTime(), "yyyy-MM-dd HH:mm:ss"));
                //查询人员信息
                EventWarningDetail detail = details.get(0);
                detail.setPicture(StringUtils.isNotEmpty(detail.getPicture() )?detail.getPicture():"");
                vo.setEventWarningDetail(detail);
            }
            // 转换流程状态信息
            Integer status = workflow.getStatus().intValue();
            if( status != null ){
                //处置员-状态
                //网格长/网格员状态
                if (status == ProcessStatusEnum.START.getCode().intValue()) {
                    // 未开始
                    vo.setQueryStatus(QueryStatusEnum.UNSTART.getCode());
                } else if (status == ProcessStatusEnum.CANCEL.getCode().intValue() || status == ProcessStatusEnum.END.getCode().intValue()) {
                    // 结束
                    vo.setQueryStatus(QueryStatusEnum.FINISH.getCode());
                    vo = workflowService.initFinishUserInfo(vo, status);

                } else {
                    // 处理中
                    vo.setQueryStatus(QueryStatusEnum.PROCESS.getCode());
                  /*  if(RoleCodeEnum.HANDLE.getCode().equals(role.getCode()) && status == ProcessStatusEnum.BACK_GRID.getCode().intValue() ){
                        vo.setQueryStatus(QueryStatusEnum.FINISH.getCode());
                    }*/
                }
            }
        }
        return ApiResponse.ofSuccess(vo);
    }

    @ApiOperation(value = "流程日志")
    @GetMapping("log")
    public ApiResponse<List<WorkflowStepVO>> log(@RequestParam(value = "workflowId") Long workflowId){
        return ApiResponse.ofSuccess(workflowStepService.log(workflowId));
    }

  /*  @ApiOperation(value = "文件上传")
    @PostMapping("upload")
    public ApiResponse<String> upload(MultipartFile file) {
        System.out.println("开始上传，文件路径："+uploadPath);
        String upload = FileOperateUtil.upload(file, uploadPath);
        if(StringUtils.isBlank(upload)){
            throw new BaseException(500,"文件上传失败");
        }
        return ApiResponse.ofSuccess(upload);
    }*/

    /**
     * 填报的表单项中存在文件类型表单项，需先上传文件，接口返回临时图片序号，提交表单数据的时候把临时图片序号提交到后台
     */
    @ApiOperation(value = "上传单个文件", notes = "上传单个文件", httpMethod="POST" ,consumes="multipart/form-data")
    @PostMapping(value = "upload")
    @ResponseBody
    public ApiResponse<String> singleFileUpLoad(@ApiParam(value = "文件" , required = true) MultipartFile file) {
        System.out.println("开始上传，文件路径："+ saveBasePath + uploadPath);
        String upload = FileOperateUtil.upload(file, saveBasePath + uploadPath,uploadPath);
        if(StringUtils.isBlank(upload)){
            throw new BaseException(500,"文件上传失败");
        }
        return ApiResponse.ofSuccess(upload);
    }

    @ApiOperation(value = "文件下载")
    @GetMapping("download")
    public void download(HttpServletRequest request,HttpServletResponse response, @RequestParam(value = "filePath") String filePath) throws IOException {
        System.out.println("开始下载文件");
        FileOperateUtil.downloadFiles(request, response, filePath);
    }

    @GetMapping("role/detail")
    @ApiOperation(value = "根据用户id获取角色信息")
    public ApiResponse<List<Role>> getRoleDetailByOutUserId(@RequestParam(value = "outUserId")  Long outUserId) {
        return ApiResponse.ofSuccess(roleService.getRoleDetailByOutUserId(outUserId));
    }

    @GetMapping("test")
    @ApiOperation(value = "告警信息")
    public ApiResponse<Object> test() {
        return ApiResponse.ofSuccess(huaweiApiService.getWarnDataList());
    }
}
