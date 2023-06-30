package cn.com.citydo.module.core.service.impl;

import cn.com.citydo.common.BaseException;
import cn.com.citydo.common.Consts;
import cn.com.citydo.common.EyeStatus;
import cn.com.citydo.common.enums.*;
import cn.com.citydo.module.basic.entity.Department;
import cn.com.citydo.module.basic.entity.Role;
import cn.com.citydo.module.basic.entity.User;
import cn.com.citydo.module.basic.entity.UserDepartment;
import cn.com.citydo.module.basic.mapper.RoleMapper;
import cn.com.citydo.module.basic.service.DepartmentService;
import cn.com.citydo.module.basic.service.RoleService;
import cn.com.citydo.module.basic.service.UserDepartmentService;
import cn.com.citydo.module.basic.service.UserService;
import cn.com.citydo.module.basic.vo.UserPrincipal;
import cn.com.citydo.module.core.dto.HandleDTO;
import cn.com.citydo.module.core.dto.WorkflowCountQUERY;
import cn.com.citydo.module.core.dto.WorkflowQUERY;
import cn.com.citydo.module.core.entity.*;
import cn.com.citydo.module.core.inner.ProcessDO;
import cn.com.citydo.module.core.mapper.DeviceBindUserMapper;
import cn.com.citydo.module.core.mapper.DeviceGridMemberMapper;
import cn.com.citydo.module.core.mapper.DeviceMapper;
import cn.com.citydo.module.core.mapper.WorkflowMapper;
import cn.com.citydo.module.core.service.*;
import cn.com.citydo.module.core.vo.*;
import cn.com.citydo.module.screen.entity.EventWarning;
import cn.com.citydo.module.screen.entity.EventWarningReadedRecord;
import cn.com.citydo.module.screen.enums.WarnTypeEnum;
import cn.com.citydo.module.screen.service.EventWarningReadedRecordService;
import cn.com.citydo.utils.Msg;
import cn.com.citydo.utils.SecurityUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 流程主表 服务实现类
 * </p>
 *
 * @author blackcat
 * @since 2021-06-08
 */
@Service
@Slf4j
public class WorkflowServiceImpl extends ServiceImpl<WorkflowMapper, Workflow> implements WorkflowService {

    @Autowired
    private WorkflowStepService stepService;

    @Autowired
    private DeviceService deviceService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserDepartmentService userDepartmentService;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private UserService userService;

    @Autowired
    private DeviceGridMemberService deviceGridMemberService;

    @Autowired
    private WorkflowNewsService workflowNewsService;

    @Autowired
    private EventWarningReadedRecordService eventWarningReadedRecordService;

    @Autowired
    private WorkflowStepService workflowStepService;

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private DeviceBindUserService deviceBindUserService;

    @Autowired
    private DeviceGridMemberMapper deviceGridMemberMapper;

    @Autowired
    private DeviceMapper deviceMapper;

    @Value("${fileserver.url}")
    private String fileServerUrl;

    @Override
    public ProcessDO startProcess(EventWarning eventWarning) {
        ProcessDO process = new ProcessDO();
        String reason = "";
        Workflow workflow = new Workflow();
        workflow.setEventWarningId(eventWarning.getId());
        workflow.setEventType(eventWarning.getWarnType());
        workflow.setStreamId(eventWarning.getStreamId());
        //TODO  预警时间处理
        workflow.setWarnTime(DateUtil.date(eventWarning.getTimestamp() * 1000));
        //根据摄像头id 查设备
        List<Device> deviceList = deviceService.getByStreamId(eventWarning.getStreamId());
        if (CollectionUtils.isEmpty(deviceList)) {
            log.error("当前设备号【{}】  id【{}】没有入系统的设备表", eventWarning.getStreamId(), eventWarning.getId());
            reason = "当前设备号没有入系统的设备表";
            process.setReason(reason);
            return process;
        }
        Device device = deviceList.get(0);
        workflow.setAreaId(device.getAreaId());
        workflow.setStreetId(device.getStreetId());
        workflow.setSocialId(device.getSocialId());
        workflow.setGridId(device.getGridId());
        workflow.setDeviceName(device.getName());
        workflow.setImage(eventWarning.getImageBase64());
        workflow.setAddress(device.getAddress());
        workflow.setAreaName(departmentService.getDepartmentName(device.getAreaId(), device.getStreetId(), device.getSocialId()));

        List<DeviceGridMember> gridMembers = deviceGridMemberService.getByDeviceId(device.getId());
        if (CollectionUtils.isEmpty(gridMembers)) {
            log.info("当前设备号【{}】没有关联的网格员", eventWarning.getStreamId());
            reason = reason + "当前设备号"+eventWarning.getStreamId()+"没有关联的网格员";
        }

        //反查网格长
        List<User> gridLeads = userDepartmentService.getUserByRole(workflow.getSocialId(), RoleCodeEnum.GRID_LEAD);
        if (CollectionUtils.isEmpty(gridLeads)) {
            log.info("当前设备号【{}】没有关联的网格长", eventWarning.getStreamId());
            reason = reason + "当前设备号"+eventWarning.getStreamId()+"没有关联的网格长";
        } else {
            User user = gridLeads.get(0);
            workflow.setGridLeadUserId(user.getId());
            workflow.setGridLeadUserName(user.getUsername());
        }
        boolean save = this.save(workflow);
        if (!save) {
            throw new BaseException(EyeStatus.SAVE_ERROR);
        }
        stepService.createMachine(workflow.getId());
        //网格员绑定 多人
        for (DeviceGridMember gridMember : gridMembers) {
            User user = userService.getById(gridMember.getGridMemberId());
            if (user != null) {
                DeviceBindUser deviceBindUser = new DeviceBindUser();
                deviceBindUser.setWorkflowId(workflow.getId());
                deviceBindUser.setGridUserId(user.getId());
                deviceBindUser.setGridUserName(user.getUsername());
                deviceBindUserService.save(deviceBindUser);
            }
        }

        //TODO 多人消息发送
        List<Msg> list = new ArrayList<>();
        for (DeviceGridMember gridMember : gridMembers) {
            Msg msg = new Msg();
            msg.setWorkflowId(workflow.getId());
            BeanUtils.copyProperties(workflow, msg);
            msg.setNewsEnum(NewsEnum.WARN_GRID);
            msg.setUserId(gridMember.getGridMemberId());
            list.add(msg);
        }
        Msg msgLead = new Msg();
        msgLead.setWorkflowId(workflow.getId());
        BeanUtils.copyProperties(workflow, msgLead);
        msgLead.setNewsEnum(NewsEnum.WARN_GRID_LEAD);
        msgLead.setUserId(workflow.getGridLeadUserId());
        list.add(msgLead);
        workflowNewsService.saveMsg(list);
        process.setReason(reason);
        process.setProcess(Boolean.TRUE);
        return process;
    }

    @Override
    public IPage<WorkflowVO> getByQuery(WorkflowQUERY query) {
        IPage<Workflow> page = new Page<>(query.getPageNo(), query.getPageSize());
        // 获取查询条件里的角色信息
        Long roleId = query.getRoleId();
        // 获取当前登录用户信息
        UserPrincipal user = SecurityUtil.getCurrentUser();
        List<Role> roleList = getRole(roleId, user);
        Role role = roleList.get(0);

        QueryWrapper<Workflow> queryWrapper = new QueryWrapper();
        LambdaQueryWrapper<Workflow> lambda = queryWrapper.lambda();
        handleParam(query, lambda);

        if (QueryStatusEnum.FINISH.getCode().equals(query.getStatusType())) {
            lambda.in(Workflow::getStatus, Arrays.asList(ProcessStatusEnum.END.getCode(), ProcessStatusEnum.CANCEL.getCode()));
        }
        if (QueryStatusEnum.PROCESS.getCode().equals(query.getStatusType())) {
            lambda.notIn(Workflow::getStatus, Arrays.asList(ProcessStatusEnum.END.getCode(), ProcessStatusEnum.CANCEL.getCode(), ProcessStatusEnum.START.getCode()));
        }
        if (QueryStatusEnum.UNSTART.getCode().equals(query.getStatusType())) {
            lambda.eq(Workflow::getStatus, ProcessStatusEnum.START.getCode());
        }
        Long userId = user.getId();
        List<UserDepartment> userDepartmentList = userDepartmentService.getByUserId(userId);
        Long departmentId = null;
        if (!CollectionUtils.isEmpty(userDepartmentList)) {
            departmentId = userDepartmentList.get(0).getDepartmentId();
        }
        if( StringUtils.isNotEmpty(query.getSelfDisposalType()) ){
            lambda.eq(Workflow::getSelfDisposalType,query.getSelfDisposalType());
        }
        //TODO 以下根据当前用户角色设置查询数据范围
        if (RoleCodeEnum.STREET.getCode().equals(role.getCode())) {
            lambda.eq(Workflow::getStreetId, departmentId);
        } else if (RoleCodeEnum.AREA.getCode().equals(role.getCode())) {
            // 区网格中心
            lambda.eq(Workflow::getAreaId, departmentId);
        } else if (RoleCodeEnum.SOCIAL.getCode().equals(role.getCode())) {
            // 社区网络中心
            lambda.eq(Workflow::getSocialId, departmentId);

        } else if (RoleCodeEnum.ADMIN.getCode().equals(role.getCode())) {
            // 管理员
//            以下角色web端没有查看权限，在app端操作
//        } else if (RoleCodeEnum.GRID.getCode().equals(role.getCode())) {
//            //  网格员
//
//        } else if (RoleCodeEnum.GRID_LEAD.getCode().equals(role.getCode())) {
//            // 网格长
//
//        } else if (RoleCodeEnum.HANDLE.getCode().equals(role.getCode())) {
//            // 处置部门

        } else {
            return new Page<WorkflowVO>(query.getPageNo(), query.getPageSize());
        }

        if (RoleCodeEnum.STREET.getCode().equals(role.getCode()) ) {
            if( StringUtils.isNotEmpty(query.getIsHandlingEvents()) ){
                //可处理事件
                if(Consts.ZERO.equals(query.getIsHandlingEvents())){
                    lambda.in(Workflow::getStatus, Arrays.asList(ProcessStatusEnum.REPORT_STREET.getCode(), ProcessStatusEnum.HANDLE_TO_STREET.getCode()));
                }
                //不可处理事件
                if(Consts.ONE.equals(query.getIsHandlingEvents())){
                    lambda.notIn(Workflow::getStatus, Arrays.asList(ProcessStatusEnum.REPORT_STREET.getCode(), ProcessStatusEnum.HANDLE_TO_STREET.getCode()));
                }
            }
        }

        lambda.orderByDesc(Workflow::getWarnTime);
        log.info("============开始查询web事件列表============");
        IPage<Workflow> workflowIPage = this.page(page, queryWrapper);
        IPage<WorkflowVO> resultPage = pageConver(workflowIPage, role);
        return resultPage;
    }


    @Override
    public IPage<WorkflowStreetVO> streetPage(WorkflowQUERY query) {
        IPage<Workflow> page = new Page<>(query.getPageNo(), query.getPageSize());
        Long departmentId = null;
        Long roleId = query.getRoleId();
        UserPrincipal user = SecurityUtil.getCurrentUser();
        List<Role> roleList = getRole(roleId, user);
        Role role = roleList.get(0);

        Long userId = user.getId();
        List<UserDepartment> userDepartmentList = userDepartmentService.getByUserId(userId);
        if (!CollectionUtils.isEmpty(userDepartmentList)) {
            departmentId = userDepartmentList.get(0).getDepartmentId();
        }
        QueryWrapper<Workflow> queryWrapper = new QueryWrapper();
        LambdaQueryWrapper<Workflow> lambda = queryWrapper.lambda();
        handleParam(query, lambda);

        lambda.in(Workflow::getStatus, Arrays.asList(ProcessStatusEnum.REPORT_STREET.getCode(), ProcessStatusEnum.HANDLE_TO_STREET.getCode()));
        if (RoleCodeEnum.STREET.getCode().equals(role.getCode())) {
            lambda.eq(Workflow::getStreetId, departmentId);
        } else {
            return new Page<WorkflowStreetVO>(query.getPageNo(), query.getPageSize());
        }
        queryWrapper.lambda().orderByDesc(Workflow::getWarnTime);
        IPage<Workflow> workflowIPage = this.page(page, queryWrapper);
        IPage<WorkflowStreetVO> resultPage = new Page<>();
        List<WorkflowStreetVO> list = new ArrayList<>(10);
        BeanUtils.copyProperties(workflowIPage, resultPage);
        List<Workflow> records = workflowIPage.getRecords();
        for (Workflow workflow : records) {
            WorkflowStreetVO workflowVO = new WorkflowStreetVO();
            BeanUtils.copyProperties(workflow, workflowVO);
            List<WorkflowStep> stepList = stepService.getByWorkflowId(workflow.getId());
            if (!CollectionUtils.isEmpty(stepList)) {
                WorkflowStep step = stepList.get(stepList.size() - 1);
                workflowVO.setOperation(step.getOperation());
                workflowVO.setSubmitName(step.getName());
                workflowVO.setSubmitTime(step.getCreateTime());
            }
            list.add(workflowVO);
        }
        resultPage.setRecords(list);
        return resultPage;
    }


    /**
     * 处理查询参数
     */
    private void handleParam(WorkflowQUERY query, LambdaQueryWrapper<Workflow> lambda) {
        if (StringUtils.isNotBlank(query.getEventType())) {
            lambda.eq(Workflow::getEventType, WarnTypeEnum.findEnumByName(query.getEventType()).getKey());
        }
        if (StringUtils.isNotBlank(query.getStreamId())) {
            lambda.eq(Workflow::getStreamId, query.getStreamId());
        }
        if (StringUtils.isNotBlank(query.getDeviceName())) {
            lambda.like(Workflow::getDeviceName, query.getDeviceName());
        }
        query.setZoneId(departmentService.dataHandle(query.getZoneId()));
        if (!ObjectUtils.isEmpty(query.getZoneId())) {
            lambda.and(wrapper -> wrapper.eq(Workflow::getAreaId, query.getZoneId())
                    .or().eq(Workflow::getSocialId, query.getZoneId())
                    .or().eq(Workflow::getStreetId, query.getZoneId()));
        }

        if (!ObjectUtils.isEmpty(query.getWarnStartTime())) {
            lambda.ge(Workflow::getWarnTime, query.getWarnStartTime());
        }
        if (!ObjectUtils.isEmpty(query.getWarnEndTime())) {
            Date warnEndTime = query.getWarnEndTime();
            warnEndTime = DateUtils.addDays(warnEndTime, 1);
            warnEndTime = DateUtils.addSeconds(warnEndTime, -1);
            lambda.le(Workflow::getWarnTime, warnEndTime);
        }
    }

    @Override
    public WorkflowCount count(WorkflowCountQUERY query) {
        WorkflowCount workflowCount = new WorkflowCount();
        Long roleId = query.getRoleId();
        UserPrincipal user = SecurityUtil.getCurrentUser();
        List<Role> roleList = getRole(roleId, user);
        Role role = roleList.get(0);

        QueryWrapper<Workflow> queryWrapper = new QueryWrapper();
        LambdaQueryWrapper<Workflow> lambda = queryWrapper.lambda();
        if (StringUtils.isNotBlank(query.getEventType())) {
            lambda.eq(Workflow::getEventType, query.getEventType());
        }

        if (!ObjectUtils.isEmpty(query.getWarnStartTime()) && !ObjectUtils.isEmpty(query.getWarnEndTime()) && query.getWarnStartTime().equals(query.getWarnEndTime())) {
            Date warnEndTime = query.getWarnEndTime();
            warnEndTime = DateUtils.addDays(warnEndTime, 1);
            warnEndTime = DateUtils.addSeconds(warnEndTime, -1);
            query.setWarnEndTime(warnEndTime);
        }

        if (!ObjectUtils.isEmpty(query.getWarnStartTime())) {
            lambda.ge(Workflow::getWarnTime, query.getWarnStartTime());
        }
        if (!ObjectUtils.isEmpty(query.getWarnEndTime())) {
            lambda.le(Workflow::getWarnTime, query.getWarnEndTime());
        }
        if( StringUtils.isNotEmpty(query.getSelfDisposalType()) ){
            lambda.eq(Workflow::getSelfDisposalType,query.getSelfDisposalType());
        }
        Long userId = user.getId();
        List<UserDepartment> userDepartmentList = userDepartmentService.getByUserId(userId);
        Long departmentId = null;
        if (!CollectionUtils.isEmpty(userDepartmentList)) {
            departmentId = userDepartmentList.get(0).getDepartmentId();
        }
        //TODO 待完善 有问题
        if (RoleCodeEnum.GRID.getCode().equals(role.getCode())) {
            List<DeviceBindUser> userList = deviceBindUserService.getByUserId(userId);
            if(CollectionUtils.isEmpty(userList)){
                lambda.eq(Workflow::getAssignGridId, userId);
            }else {
                lambda.and(wrapper -> {
                    wrapper.or(itemWrapper -> {
                        itemWrapper.in(Workflow::getId, userList.stream().map(DeviceBindUser::getWorkflowId).collect(Collectors.toList()));
                        // itemWrapper.eq(Workflow::getGridUserId, userId).isNull(Workflow::getAssignGridId);
                    });
                    wrapper.or(itemWrapper -> {
                        itemWrapper.eq(Workflow::getAssignGridId, userId);
                    });
                });
            }

            //lambda.eq(Workflow::getGridId, query.getGridId());
        } else if (RoleCodeEnum.GRID_LEAD.getCode().equals(role.getCode())) {
            //TODO 指定网格员
            lambda.eq(Workflow::getSocialId, departmentId);
            if (query.getGridId() != null) {
                lambda.and(wrapper -> {
                    wrapper.or(itemWrapper -> {
                        itemWrapper.eq(Workflow::getGridUserId, query.getGridId());
                      //itemWrapper.eq(Workflow::getGridUserId, query.getGridId()).isNull(Workflow::getAssignGridId);
                    });
                    wrapper.or(itemWrapper -> {
                        itemWrapper.eq(Workflow::getAssignGridId, query.getGridId());
                    });
                });
            }
        } else if (RoleCodeEnum.HANDLE.getCode().equals(role.getCode())) {
            //指定社区
            lambda.eq(Workflow::getStreetId, departmentId).eq(Workflow::getHandleId, userId);
            if (query.getSocialId() != null) {
                lambda.eq(Workflow::getSocialId, query.getSocialId());
            }
        }
        List<Workflow> list = this.list(queryWrapper);
        if (!CollectionUtils.isEmpty(list)) {
           /* long finish = list.stream().filter(Workflow -> ProcessStatusEnum.END.getCode().equals(Workflow.getStatus())).count();
            finish = finish + list.stream().filter(Workflow -> ProcessStatusEnum.CANCEL.getCode().equals(Workflow.getStatus())).count();
            long count = list.stream().filter(Workflow -> ProcessStatusEnum.START.getCode().equals(Workflow.getStatus())).count();
            long process = list.size() - finish - count;
            workflowCount.setFinishCunt(finish);
            workflowCount.setProcessCount(process);
            workflowCount.setCount(count);*/

             Long count = 0L;
             Long processCount = 0L;
             Long finishCunt = 0L;
            for (Workflow workflow : list) {
                WorkflowStep workflowStep = new WorkflowStep();
                if(ProcessStatusEnum.END.getCode().equals(workflow.getStatus()) || ProcessStatusEnum.CANCEL.getCode().equals(workflow.getStatus())){
                    if( StringUtils.isEmpty(query.getRoleType()) ){
                        finishCunt = finishCunt+1;
                    }else{
                        if(ProcessStatusEnum.END.getCode().equals(workflow.getStatus())){
                            workflowStep = workflowStepService.selectNewDataByWorkflowId(workflow.getId(), OperationEnum.SELF_HANDLE.getCode());
                        }
                        if(ProcessStatusEnum.CANCEL.getCode().equals(workflow.getStatus())){
                            workflowStep = workflowStepService.selectNewDataByWorkflowId(workflow.getId(), OperationEnum.CANCEL.getCode());
                        }
                        if( workflowStep != null ){
                            Map<String, Object> hashMap = new HashMap<>();
                            hashMap.put("userId",workflowStep.getUserId());
                            hashMap.put("code", query.getRoleType());
                            log.info("map参数为：[{}],[{}]", hashMap.get("userId"),hashMap.get("code"));
                            Role roleById = roleMapper.selectDataByParam(hashMap);
                            if( roleById != null ){
                                finishCunt = finishCunt+1;
                            }
                        }
                    }
                }
                if(ProcessStatusEnum.START.getCode().equals(workflow.getStatus())){
                    count = count+1;
                }
                processCount = list.size() - finishCunt - count;
                workflowCount.setFinishCunt(finishCunt);
                workflowCount.setProcessCount(processCount);
                workflowCount.setCount(count);
            }

        }
        //获取当前登陆人的信息
        if (user != null) {
            workflowCount.setUserId(user.getId());
            workflowCount.setUserName(user.getNickname());
            List<UserDepartment> byUserId = userDepartmentService.getByUserId(user.getId());
            if (!CollectionUtils.isEmpty(byUserId)) {
                UserDepartment userDepartment = byUserId.get(0);
                //网格长+网格员
                if (RoleCodeEnum.GRID.getCode().equals(role.getCode()) || RoleCodeEnum.GRID_LEAD.getCode().equals(role.getCode())) {
                    WorkflowCount infoByDepartmentId = departmentService.getInfoByDepartmentId(userDepartment.getDepartmentId());
                    if (infoByDepartmentId != null) {
                        workflowCount.setSocialName(infoByDepartmentId.getSocialName());
                        workflowCount.setGridName(infoByDepartmentId.getGridName());
                    }
                } else if (RoleCodeEnum.HANDLE.getCode().equals(role.getCode())) {
                    //todo 处置部门
                }
            }

        }
        return workflowCount;
    }

    @Override
    public IPage<AppWorkflowVO> pageByQuery(WorkflowCountQUERY query) {
        IPage<AppWorkflowVO> resultPage= new Page<>(query.getPageNo(), query.getPageSize());
        IPage<Workflow> page = new Page<>(query.getPageNo(), query.getPageSize());
        Long roleId = query.getRoleId();
        UserPrincipal user = SecurityUtil.getCurrentUser();
        List<Role> roleList = getRole(roleId, user);
        Role role = roleList.get(0);

        QueryWrapper<Workflow> queryWrapper = new QueryWrapper();
        LambdaQueryWrapper<Workflow> lambda = queryWrapper.lambda();
        if (StringUtils.isNotBlank(query.getEventType())) {
            lambda.eq(Workflow::getEventType, query.getEventType());
        }

        if (!ObjectUtils.isEmpty(query.getWarnStartTime())) {
            lambda.ge(Workflow::getWarnTime, query.getWarnStartTime());
        }
        if (!ObjectUtils.isEmpty(query.getWarnEndTime())) {
            Date warnEndTime = query.getWarnEndTime();
            warnEndTime = DateUtils.addDays(warnEndTime, 1);
            warnEndTime = DateUtils.addSeconds(warnEndTime, -1);
            lambda.le(Workflow::getWarnTime, warnEndTime);
        }
        if (QueryStatusEnum.FINISH.getCode().equals(query.getStatus())) {
            lambda.in(Workflow::getStatus, Arrays.asList(ProcessStatusEnum.END.getCode(), ProcessStatusEnum.CANCEL.getCode()));
        }
        if (QueryStatusEnum.PROCESS.getCode().equals(query.getStatus())) {
            lambda.notIn(Workflow::getStatus, Arrays.asList(ProcessStatusEnum.END.getCode(), ProcessStatusEnum.CANCEL.getCode(), ProcessStatusEnum.START.getCode()));
        }
        if (QueryStatusEnum.UNSTART.getCode().equals(query.getStatus())) {
            lambda.eq(Workflow::getStatus, ProcessStatusEnum.START.getCode());
        }

        if( StringUtils.isNotEmpty(query.getSelfDisposalType()) ){
            lambda.eq(Workflow::getSelfDisposalType,query.getSelfDisposalType());
        }
        Long userId = user.getId();
        List<UserDepartment> userDepartmentList = userDepartmentService.getByUserId(userId);
        Long departmentId = null;
        if (!CollectionUtils.isEmpty(userDepartmentList)) {
            departmentId = userDepartmentList.get(0).getDepartmentId();
        }
        //TODO 待完善 有问题
        if (RoleCodeEnum.GRID.getCode().equals(role.getCode())) {
            List<DeviceBindUser> userList = deviceBindUserService.getByUserId(userId);
            if(CollectionUtils.isEmpty(userList)){
                lambda.eq(Workflow::getAssignGridId, userId);
            }else {
                lambda.and(wrapper -> {
                    wrapper.or(itemWrapper -> {
                        itemWrapper.in(Workflow::getId, userList.stream().map(DeviceBindUser::getWorkflowId).collect(Collectors.toList()));
                    });
                    wrapper.or(itemWrapper -> {
                        itemWrapper.eq(Workflow::getAssignGridId, userId);
                    });
                });
            }
        } else if (RoleCodeEnum.GRID_LEAD.getCode().equals(role.getCode())) {
            //TODO 指定网格长
            lambda.eq(Workflow::getSocialId, departmentId);
            if (query.getGridId() != null) {
                lambda.and(wrapper -> {
                    wrapper.or(itemWrapper -> {
                        itemWrapper.eq(Workflow::getGridUserId, query.getGridId());
                     // itemWrapper.eq(Workflow::getGridUserId, query.getGridId()).isNull(Workflow::getAssignGridId);
                    });
                    wrapper.or(itemWrapper -> {
                        itemWrapper.eq(Workflow::getAssignGridId, query.getGridId());
                    });
                });
            }
        } else if (RoleCodeEnum.HANDLE.getCode().equals(role.getCode())) {
            //TODO 指定社区
            lambda.eq(Workflow::getStreetId, departmentId).eq(Workflow::getHandleId, userId);
            if (query.getSocialId() != null) {
                //TODO 指定社区
                lambda.eq(Workflow::getSocialId, query.getSocialId());
            }
        }
        log.info("=====================开始进行查询事件列表=====================");

        String sql = "order by case when status = 0 and suprise_type is not null then -1 when status = 0 and suprise_type is null then 1\n" +
                "when status = 2 then 2 when status in(1,3,4,5) then 3 when status in(6,7) then 4 else 5 end asc,warn_time desc";
        queryWrapper.last(sql);
        //queryWrapper.lambda().orderByAsc(Workflow::getStatus).orderByDesc(Workflow::getWarnTime);
        IPage<Workflow> workflowIPage = this.page(page, queryWrapper);
        BeanUtils.copyProperties(workflowIPage,resultPage);
        List<AppWorkflowVO> list = new ArrayList<>(10);
        for (Workflow workflow : workflowIPage.getRecords()) {
            log.info("开始进行转化，数据为：[{}]", JSONObject.toJSONString(workflow));
            AppWorkflowVO workflowVO = new AppWorkflowVO();
            BeanUtils.copyProperties(workflow, workflowVO);

            //获取处置图片
            List<WorkflowStep> byWorkflowId = workflowStepService.getByWorkflowId(workflow.getId());
            if (!CollectionUtils.isEmpty(byWorkflowId)) {
                workflowVO.setCheckImage(byWorkflowId.get(0).getImage());
            }
            //处置员获取上报时间
            List<WorkflowStep> reportTime = workflowStepService.getReportTime(workflow.getId(), OperationEnum.REPORT_HANDLE.getCode());
            if (!CollectionUtils.isEmpty(reportTime)) {
                workflowVO.setReportTimeFormat(reportTime.get(0).getCreateTime());
            }

            if (StringUtils.isNotBlank(workflow.getAssignGridName())) {
                workflowVO.setGridName(workflow.getAssignGridName());
            } else {
                workflowVO.setGridName(workflow.getGridUserName());
            }
            // 构造查看人员信息
            QueryWrapper<EventWarningReadedRecord> readQueryWrapper = new QueryWrapper();
            readQueryWrapper.orderByDesc("read_Time");
            readQueryWrapper.lambda().eq(EventWarningReadedRecord::getWorkflowId, workflow.getId());
            List<EventWarningReadedRecord> readedRecords = eventWarningReadedRecordService.list(readQueryWrapper);
            Set<String> readerNameList = new HashSet<>();
            if (!CollectionUtils.isEmpty(readedRecords)) {
                for (EventWarningReadedRecord record : readedRecords) {
                    readerNameList.add(record.getReaderName());
                    workflowVO.setIsReaded(0);
                }
            }
            String readers = StringUtils.join(readerNameList, ",");
            workflowVO.setReaders(readers);

            //退回标志
            if (ProcessStatusEnum.BACK_GRID.getCode().equals(workflow.getStatus())) {
                workflowVO.setBackFlag(Boolean.TRUE);
            }
            // 转换流程状态信息
            Integer i = workflow.getStatus().intValue();
            if (i != null) {
                if (workflow.getStatus().intValue() == ProcessStatusEnum.START.getCode().intValue()) {
                    // 未开始
                    workflowVO.setQueryStatus(QueryStatusEnum.UNSTART.getCode());
                } else if (workflow.getStatus().intValue() == ProcessStatusEnum.CANCEL.getCode().intValue() || workflow.getStatus().intValue() == ProcessStatusEnum.END.getCode().intValue()) {
                    // 结束
                    workflowVO.setQueryStatus(QueryStatusEnum.FINISH.getCode());
                    workflowVO = this.initFinishUserInfo(workflowVO,workflow.getStatus().intValue());

                    if( StringUtils.isNotEmpty(query.getRoleType()) ){
                        Map<String, Object> hashMap = new HashMap<>();
                        hashMap.put("userId",workflowVO.getFinishUserId());
                        hashMap.put("code", query.getRoleType());
                        log.info("map参数为：[{}],[{}]", hashMap.get("userId"),hashMap.get("code"));
                        Role roleById = roleMapper.selectDataByParam(hashMap);
                        if(roleById == null){
                            continue;
                        }
                    }
                } else {
                    // 处理中
                    workflowVO.setQueryStatus(QueryStatusEnum.PROCESS.getCode());
                }
            }
            list.add(workflowVO);
        }

        resultPage.setRecords(list);
        return resultPage;
    }

    @Override
    public AppWorkflowVO initFinishUserInfo(AppWorkflowVO vo, Integer status){
        WorkflowStep workflowStep = null;
        if(status == ProcessStatusEnum.CANCEL.getCode().intValue()){
            workflowStep = workflowStepService.selectNewDataByWorkflowId(vo.getId(), OperationEnum.CANCEL.getCode());
        }
        if(status == ProcessStatusEnum.END.getCode().intValue()){
            workflowStep = workflowStepService.selectNewDataByWorkflowId(vo.getId(), OperationEnum.SELF_HANDLE.getCode());
        }
        if( workflowStep != null ){
            vo.setFinishUserId(workflowStep.getUserId());
            vo.setFinishUserName(workflowStep.getName());
        }
        return vo;
    }

    @Override
    @Transactional
    public void initGridUserInfo() {
        log.info("-----initGridUserInfo 初始化数据【开始】-------");
        List<Workflow> list = this.list();
        log.info("initGridUserInfo size{}",list.size());
        List<DeviceBindUser> bindUserList = new ArrayList<>(list.size());
        for (Workflow workflow : list) {
            DeviceBindUser deviceBindUser = new DeviceBindUser();
            deviceBindUser.setWorkflowId(workflow.getId());
            deviceBindUser.setGridUserId(workflow.getGridUserId());
            deviceBindUser.setGridUserName(workflow.getGridUserName());
            UserPrincipal currentUser = SecurityUtil.getCurrentUser();
            deviceBindUser.setCreator(currentUser.getId().toString());
            deviceBindUser.setUpdator(currentUser.getId().toString());
            bindUserList.add(deviceBindUser);

        }
        boolean saveBatch = deviceBindUserService.saveBatch(bindUserList);
        if(!saveBatch){
            throw new RuntimeException("initGridUserInfo error");
        }
        log.info("-----initGridUserInfo 初始化数据【结束】-------");
    }



    public WorkflowVO initFinishUserInfo(WorkflowVO vo, Integer status){
        WorkflowStep workflowStep = null;
        if(status == ProcessStatusEnum.CANCEL.getCode().intValue()){
            workflowStep = workflowStepService.selectNewDataByWorkflowId(vo.getId(), OperationEnum.CANCEL.getCode());
        }
        if(status == ProcessStatusEnum.END.getCode().intValue()){
            workflowStep = workflowStepService.selectNewDataByWorkflowId(vo.getId(), OperationEnum.SELF_HANDLE.getCode());
        }
        if( workflowStep != null ){
            vo.setFinishUserId(workflowStep.getUserId());
            vo.setFinishUserName(workflowStep.getName());
        }
        return vo;
    }
    @Transactional
    @Override
    public Long handle(HandleDTO handle, RoleCodeEnum roleCodeEnum) {
        log.info("网格员自行处置，类型为：[{}]", JSONObject.toJSONString(RoleCodeEnum.getNameByCode(roleCodeEnum.getCode())));
        Long roleId = handle.getRoleId();
        UserPrincipal user = SecurityUtil.getCurrentUser();
        log.info("当前登录人是：[{}]", JSONObject.toJSONString(user));
        List<Role> roleList = getRole(roleId, user);
        log.info("查询的角色：[{}]", JSONObject.toJSONString(roleList));
        boolean flag = false;
        for (Role role : roleList) {
            if (roleCodeEnum.getCode().equals(role.getCode())) {
                flag = true;
            }
        }
        if (!flag) {
            throw new BaseException(EyeStatus.ROLE_ERROR);
        }
        Long workflowId = handle.getWorkflowId();
        Workflow workflow = this.getById(workflowId);
        if (ObjectUtils.isEmpty(workflow)) {
            throw new BaseException(EyeStatus.ID_ERROR);
        }

        boolean flag1 = false;
        String roleName = "";
        String roleCode = "";
        for (Role role : roleList) {
            if (RoleCodeEnum.STREET.getCode().equals(role.getCode())) {
                if (Arrays.asList(ProcessStatusEnum.REPORT_STREET.getCode(), ProcessStatusEnum.HANDLE_TO_STREET.getCode()).contains(workflow.getStatus())) {
                    log.info("id:[{}],status:[{}]", workflow.getId(), workflow.getStatus());
                    //throw new BaseException(500, "当前状态不支持此操作");
                    roleName = role.getName();
                    roleCode = role.getCode();
                    flag1 = true;
                    break;
                }
            }
            if (RoleCodeEnum.GRID.getCode().equals(role.getCode())) {
                if (Arrays.asList(ProcessStatusEnum.START.getCode(), ProcessStatusEnum.BACK_GRID.getCode()).contains(workflow.getStatus())) {
                    log.info("id:[{}],status:[{}]", workflow.getId(), workflow.getStatus());
                    //throw new BaseException(500, "当前状态不支持此操作");
                    roleName = role.getName();
                    roleCode = role.getCode();
                    flag1 = true;
                    break;
                }
            }

            if (RoleCodeEnum.HANDLE.getCode().equals(role.getCode())) {
                if (Arrays.asList(ProcessStatusEnum.REPORT_HANDLE.getCode(), ProcessStatusEnum.STREET_TO_HANDLE.getCode()).contains(workflow.getStatus())) {
                    log.info("id:[{}],status:[{}]", workflow.getId(), workflow.getStatus());
                    //throw new BaseException(500, "当前状态不支持此操作");
                    roleName = role.getName();
                    roleCode = role.getCode();
                    flag1 = true;
                    break;
                }
            }
        }

        if (!flag1) {
            throw new BaseException(500, "当前状态不支持此操作");
        }
        //更新原来的状态
        Integer version = workflow.getVersion();
        updateByVersion(workflowId, version, ProcessStatusEnum.END,handle.getSelfType());
        //添加一条操作记录
        WorkflowStep entity = new WorkflowStep();
        entity.setUserId(user.getId());
        entity.setName(user.getUsername());
        entity.setRoleName(roleName);
        entity.setRoleCode(roleCode);
        BeanUtils.copyProperties(handle, entity);
        entity.setOperation(OperationEnum.SELF_HANDLE.getCode());
        stepService.save(entity);
        return workflowId;
    }

    @Override
    public List<HandleVO> getHandle(Long workflowId) {
        List<HandleVO> list = new ArrayList<>(10);
        Workflow workflow = this.getById(workflowId);
        if (ObjectUtils.isEmpty(workflow)) {
            throw new BaseException(EyeStatus.ID_ERROR);
        }
        Department department = departmentService.getById(workflow.getStreetId());
        if (ProcessStatusEnum.BACK_GRID.getCode().equals(workflow.getStatus())) {
            if (department != null) {
                HandleVO handleVO = new HandleVO();
                handleVO.setDepartmentId(department.getId());
                handleVO.setName(department.getName());
                list.add(handleVO);
            }
        } else if (ProcessStatusEnum.START.getCode().equals(workflow.getStatus()) || ProcessStatusEnum.REPORT_STREET.getCode().equals(workflow.getStatus())
                || ProcessStatusEnum.HANDLE_TO_STREET.getCode().equals(workflow.getStatus())) {
            getHandleDepartment(list, workflow.getStreetId());
        }
        return list;
    }

    private void getHandleDepartment(List<HandleVO> list, Long streetId) {
        //得到街道关联的用户
        List<UserDepartment> userDepartments = userDepartmentService.getByDepartmentId(streetId);
        if (!CollectionUtils.isEmpty(userDepartments)) {
            List<Long> userIdList = userDepartments.stream()
                    .map(UserDepartment::getUserId)
                    .collect(Collectors.toList());
            List<Long> idList = new ArrayList<>();
            for (Long userId : userIdList) {
                List<Role> roles = userService.getRoles(userId);
                if (!CollectionUtils.isEmpty(roles) && roles.stream().map(Role::getCode).collect(Collectors.toList()).contains(RoleCodeEnum.HANDLE.getCode())) {
                    idList.add(userId);
                }
            }
            if (idList.isEmpty()) {
                return;
            }
            List<User> users = userService.listByIds(idList);
            for (User user : users) {
                HandleVO handleVO = new HandleVO();
                handleVO.setDepartmentId(user.getId());
                handleVO.setName(user.getUsername());
                list.add(handleVO);
            }
        }
    }

    @Transactional
    @Override
    public Long report(Long workflowId, Long departmentId, String remark) {
        log.info("开始进行上报，入参为：[{}],[{}],[{}]",workflowId,departmentId,remark);
        UserPrincipal user = SecurityUtil.getCurrentUser();
        if(user == null){
            throw new BaseException(500, "当前登录人为空");
        }
        Workflow workflow = this.getById(workflowId);
        if (ObjectUtils.isEmpty(workflow)) {
            throw new BaseException(EyeStatus.ID_ERROR);
        }
        Integer version = workflow.getVersion();
        //更新原来的状态
        String operation = "";
        if (ProcessStatusEnum.START.getCode().equals(workflow.getStatus())) {
            log.info("网格员上报");
            UpdateWrapper<Workflow> updateWrapper = new UpdateWrapper();
            updateWrapper.lambda().set(Workflow::getVersion, version + 1).set(Workflow::getStatus, ProcessStatusEnum.REPORT_HANDLE.getCode())
                    .set(Workflow::getReportUserId,user.getId())
                    .set(Workflow::getHandleId, departmentId).eq(Workflow::getId, workflowId).eq(Workflow::getVersion, version);
            boolean update = this.update(updateWrapper);
            if (!update) {
                throw new BaseException(EyeStatus.UPDATE_FLOW_ERROR);
            }
            operation = OperationEnum.REPORT_HANDLE.getCode();
            Msg msg = new Msg();
            msg.setWorkflowId(workflow.getId());
            BeanUtils.copyProperties(workflow, msg);
            msg.setNewsEnum(NewsEnum.REPORT_GRID);
            msg.setUserId(departmentId);
            msg.setRemark(remark);
            msg.setSubmitName(SecurityUtil.getCurrentUsername());
            workflowNewsService.saveMsg(msg);
        } else if (ProcessStatusEnum.BACK_GRID.getCode().equals(workflow.getStatus())) {
            log.info("处置部门退回给网格员");
            updateByVersion(workflowId, version, ProcessStatusEnum.REPORT_STREET,null);
            UpdateWrapper<Workflow> updateWrapper = new UpdateWrapper();
            updateWrapper.lambda().set(Workflow::getReportUserId,user.getId())
                    .eq(Workflow::getId, workflowId);
            boolean update = this.update(updateWrapper);
            operation = OperationEnum.REPORT_STREET.getCode();
        } else {
            throw new BaseException(500, "不支持此操作");
        }
        WorkflowStep entity = getWorkflowStep(workflowId);
        entity.setOperation(operation);
        entity.setRemark(remark);
        stepService.save(entity);
        return workflowId;
    }

    @Override
    public Long assignGrid(Long workflowId, Long gridUserId, String remark) {
        User gridUser = userService.getUserById(gridUserId);
        if (gridUser == null) {
            throw new BaseException(EyeStatus.GRID_ID_ERROR);
        }
        UserPrincipal user = SecurityUtil.getCurrentUser();
        List<Role> roleList = getRole(null, user);
        Workflow workflow = this.getById(workflowId);
        if (ObjectUtils.isEmpty(workflow)) {
            throw new BaseException(EyeStatus.ID_ERROR);
        }
        if (!ProcessStatusEnum.START.getCode().equals(workflow.getStatus())) {
            throw new BaseException(500, "不支持此操作");
        }
        Integer version = workflow.getVersion();
        UpdateWrapper<Workflow> updateWrapper = new UpdateWrapper();
        updateWrapper.lambda().set(Workflow::getVersion, version + 1)
                .set(Workflow::getAssignGridId, gridUserId).set(Workflow::getAssignGridName, gridUser.getUsername()).eq(Workflow::getId, workflowId).eq(Workflow::getVersion, version);
        boolean update = this.update(updateWrapper);
        if (!update) {
            throw new BaseException(EyeStatus.UPDATE_FLOW_ERROR);
        }
        WorkflowStep entity = new WorkflowStep();
        entity.setWorkflowId(workflowId);
        entity.setUserId(user.getId());
        entity.setName(user.getUsername());
        entity.setRoleName(roleList.get(0).getName());
        entity.setRoleCode(roleList.get(0).getCode());
        entity.setOperation(OperationEnum.ASSIGN_GRID.getCode());
        stepService.save(entity);


        Msg msg = new Msg();
        msg.setWorkflowId(workflow.getId());
        BeanUtils.copyProperties(workflow, msg);
        msg.setNewsEnum(NewsEnum.ASSIGN);
        msg.setUserId(gridUserId);
        msg.setAssignGridName(gridUser.getUsername());
        msg.setRemark(remark);
        workflowNewsService.saveMsg(msg);
        return workflowId;
    }

    @Transactional
    @Override
    public Long supervise(Long workflowId, NewsEnum newsEnum, String remark) {
        Workflow workflow = this.getById(workflowId);
        if (ObjectUtils.isEmpty(workflow)) {
            throw new BaseException(EyeStatus.ID_ERROR);
        }
        if (!ProcessStatusEnum.START.getCode().equals(workflow.getStatus())) {
            throw new BaseException(500, "不支持此操作");
        }
        WorkflowStep entity = getWorkflowStep(workflowId);
        entity.setOperation(OperationEnum.SUPERVISE.getCode());
        stepService.save(entity);

        List<Msg> list = new ArrayList<>();
        //发送消息
        Msg msg = new Msg();
        msg.setWorkflowId(workflow.getId());
        BeanUtils.copyProperties(workflow, msg);
        msg.setNewsEnum(newsEnum);
        msg.setUserId(workflow.getAssignGridId() == null ? workflow.getGridUserId() : workflow.getAssignGridId());
        String supriseDepartment = "";
        if (NewsEnum.SUPRISE_SOCIAL.equals(newsEnum)) {
            Department department = departmentService.getById(workflow.getSocialId());
            if (department != null) {
                supriseDepartment = department.getName();
            }
        } else {
            supriseDepartment = SecurityUtil.getCurrentUsername();
        }
        msg.setSupriseDepartment(supriseDepartment);
        msg.setRemark(remark);

        List<DeviceBindUser> userList = deviceBindUserService.list(new QueryWrapper<DeviceBindUser>().lambda().eq(DeviceBindUser::getWorkflowId, workflowId));
        for (DeviceBindUser user:userList) {
            Msg message = new Msg();
            BeanUtils.copyProperties(msg,message);
            message.setUserId(user.getGridUserId());
            list.add(message);
        }
        workflowNewsService.saveMsg(list);
        //修改事件督办状态
        //网格长督办
        if(NewsEnum.SUPRISE_GRID_LEAD.getCode().equals(newsEnum.getCode())){
            workflow.setSupriseType(SupriseTypeEnum.SUPRISE_GRID_LEAD.getCode());
        }
        if(NewsEnum.SUPRISE_SOCIAL.getCode().equals(newsEnum.getCode())){
            workflow.setSupriseType(SupriseTypeEnum.SUPRISE_SOCIAL.getCode());
        }
        baseMapper.updateById(workflow);
        return workflowId;
    }

    private WorkflowStep getWorkflowStep(Long workflowId) {
        UserPrincipal user = SecurityUtil.getCurrentUser();
        List<Role> roleList = getRole(null, user);
        Role role = roleList.get(0);
        WorkflowStep entity = new WorkflowStep();
        entity.setWorkflowId(workflowId);
        entity.setUserId(user.getId());
        entity.setName(user.getUsername());
        entity.setRoleName(role.getName());
        entity.setRoleCode(role.getCode());
        return entity;
    }

    @Transactional
    @Override
    public Long back(Long workflowId, String remark) {
        Workflow workflow = this.getById(workflowId);
        if (ObjectUtils.isEmpty(workflow)) {
            throw new BaseException(EyeStatus.ID_ERROR);
        }
        Integer version = workflow.getVersion();
        String operation = "";
        ProcessStatusEnum statusEnum = ProcessStatusEnum.BACK_GRID;
        if (ProcessStatusEnum.REPORT_HANDLE.getCode().equals(workflow.getStatus())) {
            operation = OperationEnum.BACK_GRID.getCode();
            Msg msg = new Msg();
            msg.setWorkflowId(workflow.getId());
            BeanUtils.copyProperties(workflow, msg);
            msg.setNewsEnum(NewsEnum.BACK);
            msg.setUserId(workflow.getAssignGridId() == null ? workflow.getReportUserId() : workflow.getAssignGridId());
            msg.setRemark(remark);
            workflowNewsService.saveMsg(msg);
        } else if (ProcessStatusEnum.STREET_TO_HANDLE.getCode().equals(workflow.getStatus())) {
            statusEnum = ProcessStatusEnum.HANDLE_TO_STREET;
            operation = OperationEnum.HANDLE_TO_STREET.getCode();
        } else {
            throw new BaseException(500, "不支持此操作");
        }
        updateByVersion(workflowId, version, statusEnum,null);
        WorkflowStep entity = getWorkflowStep(workflowId);
        entity.setOperation(operation);
        entity.setRemark(remark);
        stepService.save(entity);
        return workflowId;
    }

    @Override
    public Long streetTransfer(Long workflowId, String remark, Long departmentId) {
        UserPrincipal user = SecurityUtil.getCurrentUser();
        if(user == null){
            throw new BaseException(500, "当前登录人为空");
        }
        Workflow workflow = this.getById(workflowId);
        if (ObjectUtils.isEmpty(workflow)) {
            throw new BaseException(EyeStatus.ID_ERROR);
        }
        Integer version = workflow.getVersion();
        if (ProcessStatusEnum.REPORT_STREET.getCode().equals(workflow.getStatus()) || ProcessStatusEnum.HANDLE_TO_STREET.getCode().equals(workflow.getStatus())) {
            UpdateWrapper<Workflow> updateWrapper = new UpdateWrapper();
            updateWrapper.lambda().set(Workflow::getVersion, version + 1).set(Workflow::getStatus, ProcessStatusEnum.STREET_TO_HANDLE.getCode())
                    .set(Workflow::getReportUserId,user.getId())
                    .set(Workflow::getHandleId, departmentId).eq(Workflow::getId, workflowId).eq(Workflow::getVersion, version);
            boolean update = this.update(updateWrapper);
            if (!update) {
                throw new BaseException(EyeStatus.UPDATE_FLOW_ERROR);
            }
        } else {
            throw new BaseException(500, "不支持此操作");
        }
        WorkflowStep entity = getWorkflowStep(workflowId);
        entity.setOperation(OperationEnum.STREET_TO_HANDLE.getCode());
        entity.setRemark(remark);
        stepService.save(entity);

        Msg msg = new Msg();
        msg.setWorkflowId(workflow.getId());
        BeanUtils.copyProperties(workflow, msg);
        msg.setNewsEnum(NewsEnum.REPORT_STREET);
        msg.setUserId(departmentId);
        msg.setRemark(remark);
        msg.setSubmitName(SecurityUtil.getCurrentUsername());
        msg.setSubmitDepartment(SecurityUtil.getCurrentUsername());
        workflowNewsService.saveMsg(msg);
        return workflowId;
    }

    @Override
    public List<GridUserVO> gridList(String bussinessKey) {
        log.info("开始查询网格员信息");
        UserPrincipal currentUser = SecurityUtil.getCurrentUser();
        if (currentUser == null) {
            log.info("当前登录人为空，无法查询数据");
            return new ArrayList<>();
        }
        Long id = currentUser.getId();
        List<UserDepartment> byUserId = userDepartmentService.getByUserId(id);
        if (!CollectionUtils.isEmpty(byUserId)) {
            Long departmentId = byUserId.get(0).getDepartmentId();
            Map<String, Object> hashMap = new HashMap<>();
            hashMap.put("grid", RoleCodeEnum.GRID.getCode());
            hashMap.put("departmentId", departmentId);
            List<GridUserVO> users = userService.selectGridUserListByParam(hashMap);
            if( !CollectionUtils.isEmpty(users) ){
                if( StringUtils.isNotEmpty(bussinessKey) ){
                    Workflow workflow = baseMapper.selectById(bussinessKey);
                    if( workflow != null ){
                        List<Device> deviceList = deviceService.getByStreamId(workflow.getStreamId());
                        if (!CollectionUtils.isEmpty(deviceList)) {
                            for (GridUserVO user : users) {
                                List<DeviceGridMember> byGridMemberIdList = deviceGridMemberService.getByGridMemberId(user.getId(),deviceList.get(0).getId());
                                if( !CollectionUtils.isEmpty(byGridMemberIdList) ){
                                    user.setRepeatBindFlag(Boolean.TRUE);
                                }
                            }
                        }
                    }
                }

            }
            return users;
        }
        return null;
    }

    @Override
    public Long cancel(Long workflowId, String remark) {
        Workflow workflow = this.getById(workflowId);
        if (ObjectUtils.isEmpty(workflow)) {
            throw new BaseException(EyeStatus.ID_ERROR);
        }
        Integer version = workflow.getVersion();
        if (!Arrays.asList(ProcessStatusEnum.REPORT_STREET.getCode(), ProcessStatusEnum.HANDLE_TO_STREET.getCode()).contains(workflow.getStatus())) {
            throw new BaseException(500, "当前事件状态不支持此操作，只有网格员上报街道和街道转处置部门状态的事件才允许执行作废操作！");
        }
        updateByVersion(workflowId, version, ProcessStatusEnum.CANCEL,null);
        WorkflowStep entity = getWorkflowStep(workflowId);
        entity.setOperation(OperationEnum.CANCEL.getCode());
        entity.setRemark(remark);
        stepService.save(entity);
        return workflowId;
    }

    @Override
    public void sendRemindMsg(EventWarning entity) {
        List<Device> deviceList = deviceService.getByStreamId(entity.getStreamId());
        if (CollectionUtils.isEmpty(deviceList)) {
            log.info("当前设备号【{}】没有入系统的设备表", entity.getStreamId());
            return;
        }
        //提醒消息
        Msg msg = new Msg();
        msg.setEventType(entity.getWarnType());
        msg.setWarnTime(DateUtil.date(entity.getTimestamp()));
        msg.setNewsEnum(NewsEnum.REMIND);
        Device device = deviceList.get(0);
        msg.setAddress(device.getAddress());
        msg.setAreaName(departmentService.getDepartmentName(device.getAreaId(), device.getStreetId(), device.getSocialId()));
        List<DeviceGridMember> gridMembers = deviceGridMemberService.getByDeviceId(device.getId());
        if (CollectionUtils.isEmpty(gridMembers)) {
            log.info("当前设备号【{}】没有关联的网格员", entity.getStreamId());
        } else {
            DeviceGridMember gridMember = gridMembers.get(0);
            User user = userService.getById(gridMember.getGridMemberId());
            if (user != null) {
                msg.setUserId(user.getId());
                msg.setGridUserName(user.getUsername());
            }
        }
        workflowNewsService.saveMsg(msg);
    }

    @Override
    public Workflow selectDataById(Long id) {
        return baseMapper.selectById(id);
    }

    private void updateByVersion(Long workflowId, Integer version, ProcessStatusEnum statusEnum,String selfDisposalType) {
        UpdateWrapper<Workflow> updateWrapper = new UpdateWrapper();
        updateWrapper.lambda().set(Workflow::getVersion, version + 1).set(Workflow::getStatus, statusEnum.getCode())
                .set(Workflow::getSelfDisposalType,selfDisposalType)
                .eq(Workflow::getId, workflowId).eq(Workflow::getVersion, version);
        boolean update = this.update(updateWrapper);
        if (!update) {
            throw new BaseException(EyeStatus.UPDATE_FLOW_ERROR);
        }
    }

    private List<Role> getRole(Long roleId, UserPrincipal user) {
        List<Role> result = new ArrayList<Role>();
        if (ObjectUtils.isEmpty(roleId)) {
            List<Role> roles = user.getRoles();
            if (CollectionUtils.isEmpty(roles)) {
                throw new BaseException(500, "没有权限");
            }
            return roles;
        } else {
            Role role = roleService.getById(roleId);
            if (role == null) {
                throw new BaseException(500, "错误的roleId");
            }
            result.add(role);
        }
        return result;
    }

    /**
     * 列表数据结果转换
     */
    private IPage<WorkflowVO> pageConver(IPage<Workflow> workflowIPage, Role role) {
        IPage<WorkflowVO> resultPage = new Page<>();
        List<WorkflowVO> list = new ArrayList<>(10);
        BeanUtils.copyProperties(workflowIPage, resultPage);
        List<Workflow> records = workflowIPage.getRecords();
        for (Workflow workflow : records) {
            WorkflowVO workflowVO = new WorkflowVO();
            BeanUtils.copyProperties(workflow, workflowVO);
           /* if (StringUtils.isNotBlank(workflow.getAssignGridName())) {
                workflowVO.setGridName(workflow.getAssignGridName());
            } else {
                workflowVO.setGridName(workflow.getGridUserName());
            }*/

            workflowVO.setEventNo(String.valueOf(workflow.getEventWarningId()));
            workflowVO.setGridName(workflow.getGridUserName());
            workflowVO.setImage(fileServerUrl + workflow.getImage());

            // 转换流程状态信息
            Integer i = workflow.getStatus().intValue();
            if (i != null) {
                if (workflow.getStatus().intValue() == ProcessStatusEnum.START.getCode().intValue()) {
                    // 未开始
                    workflowVO.setStatus(QueryStatusEnum.UNSTART.getCode());
                } else if (workflow.getStatus().intValue() == ProcessStatusEnum.CANCEL.getCode().intValue() || workflow.getStatus().intValue() == ProcessStatusEnum.END.getCode().intValue()) {
                    // 结束
                    workflowVO.setStatus(QueryStatusEnum.FINISH.getCode());
                    workflowVO =  this.initFinishUserInfo(workflowVO,workflow.getStatus().intValue());
                } else {
                    // 处理中
                    workflowVO.setStatus(QueryStatusEnum.PROCESS.getCode());
                   /* if(RoleCodeEnum.HANDLE.getCode().equals(role.getCode()) && workflow.getStatus().intValue() == ProcessStatusEnum.BACK_GRID.getCode().intValue() ){
                        workflowVO.setQueryStatus(QueryStatusEnum.FINISH.getCode());
                    }*/
                }
            }
            workflowVO.setEventName(WarnTypeEnum.getValueByKey(workflowVO.getEventType()));

            if (RoleCodeEnum.STREET.getCode().equals(role.getCode()) && Arrays.asList(ProcessStatusEnum.REPORT_STREET.getCode(), ProcessStatusEnum.HANDLE_TO_STREET.getCode()).contains(workflow.getStatus())) {
                workflowVO.setIaHandle(Boolean.TRUE);
            }

            List<WorkflowStep> stepList = stepService.getByWorkflowId(workflow.getId());
            if (!CollectionUtils.isEmpty(stepList)) {
                WorkflowStep step = stepList.get(stepList.size() - 1);
                workflowVO.setOperation(step.getOperation());
                workflowVO.setSubmitName(step.getName());
                workflowVO.setSubmitTime(step.getCreateTime());
            }
            list.add(workflowVO);
        }
        resultPage.setRecords(list);
        return resultPage;
    }
}
