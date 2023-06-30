package cn.com.citydo.module.core.service.impl;

import cn.com.citydo.common.BaseException;
import cn.com.citydo.common.EyeStatus;
import cn.com.citydo.module.basic.service.DepartmentService;
import cn.com.citydo.module.basic.service.UserService;
import cn.com.citydo.module.basic.vo.UserPrincipal;
import cn.com.citydo.module.core.dto.WorkflowNewsDTO;
import cn.com.citydo.module.core.entity.Workflow;
import cn.com.citydo.module.core.entity.WorkflowNews;
import cn.com.citydo.module.core.mapper.WorkflowNewsMapper;
import cn.com.citydo.module.core.service.WorkflowNewsService;
import cn.com.citydo.module.core.service.WorkflowService;
import cn.com.citydo.module.core.vo.WorkflowNewsVO;
import cn.com.citydo.module.screen.entity.EventWarningDetail;
import cn.com.citydo.module.screen.enums.WarnTypeEnum;
import cn.com.citydo.module.screen.service.EventWarningDetailService;
import cn.com.citydo.utils.Msg;
import cn.com.citydo.utils.SecurityUtil;
import cn.com.citydo.utils.SmsUtils;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * <p>
 * 流程消息表 服务实现类
 * </p>
 *
 * @author blackcat
 * @since 2021-06-08
 */
@Slf4j
@Service
public class WorkflowNewsServiceImpl extends ServiceImpl<WorkflowNewsMapper, WorkflowNews> implements WorkflowNewsService {

    @Autowired
    private WorkflowService workflowService;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private UserService userService;

    @Autowired
    private EventWarningDetailService eventWarningDetailService;
    /**
     * APP端-查看消息详情
     *
     * @param workflowNewsDTO
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public WorkflowNewsVO detail(WorkflowNewsDTO workflowNewsDTO) {
        log.info("开始查看消息详情，入参为：[{}]", JSONObject.toJSONString(workflowNewsDTO));
        WorkflowNewsVO workflowNewsVO = new WorkflowNewsVO();

        WorkflowNews workflowNews = this.getById(workflowNewsDTO.getId());
        if (workflowNews != null) {
            BeanUtils.copyProperties(workflowNews, workflowNewsVO);
//            Workflow workflow = workflowService.getById(Long.valueOf(workflowNews.getBussinessKey()));
//            if (workflow != null) {
//                BeanUtils.copyProperties(workflow, workflowNewsVO);
//            }
            //将该信息设置为已读
            workflowNews.setIsRead(1);
            baseMapper.updateById(workflowNews);
        }
        //查询人员信息
        String bussinessKey = workflowNews.getBussinessKey();
        if(StringUtils.isNotEmpty(bussinessKey)){
            Workflow workflow = workflowService.selectDataById(Long.valueOf(bussinessKey));
            if( workflow != null ){
                List<EventWarningDetail> details = eventWarningDetailService.queryByEventWarningId(workflow.getEventWarningId());
                if (details.size() > 0) {
                    EventWarningDetail detail = details.get(0);
                    detail.setPicture(StringUtils.isNotEmpty(detail.getPicture() )?detail.getPicture():"");
                    workflowNewsVO.setEventWarningDetail(detail);
                }
            }
        }
        return workflowNewsVO;
    }

    @Override
    public Boolean setReadStatus(WorkflowNewsDTO workflowNewsDTO) {
        WorkflowNews workflowNews = baseMapper.selectById(workflowNewsDTO.getId());
        if (workflowNews != null) {
            workflowNews.setIsRead(workflowNewsDTO.getIsRead());
            baseMapper.updateById(workflowNews);
        }
        return Boolean.TRUE;
    }

    @Override
    public void saveMsg(List<Msg> msgList) {
        for (Msg msg : msgList) {
            saveMsg(msg);
        }
    }


    @Autowired
    private EventWarningDetailService warningDetailService;

    @Override
    public void saveMsg(Msg msg) {
        if (WarnTypeEnum.HELP_GROUP.getKey().equals(msg.getEventType()) || WarnTypeEnum.EMPTY_NESTER.getKey().equals(msg.getEventType()) || WarnTypeEnum.FOCUS_VISIT.getKey().equals(msg.getEventType())) {
            List<EventWarningDetail> list = warningDetailService.list(new QueryWrapper<EventWarningDetail>().lambda().eq(EventWarningDetail::getEventWarningId, msg.getEventWarningId()));
            if (!CollectionUtils.isEmpty(list)) {
                msg.setEventPeople(list.get(0).getUsername());
            }
        }
        WorkflowNews workflowNews = SmsUtils.getMsg(msg);
        UserPrincipal currentUser = SecurityUtil.getCurrentUser();
        workflowNews.setCreator(currentUser != null ? currentUser.getId().toString() : "");
        workflowNews.setUpdator(currentUser != null ? currentUser.getId().toString() : "");
        boolean save = this.save(workflowNews);
        if (!save) {
            throw new BaseException(EyeStatus.SAVE_ERROR);
        }
    }

    @Override
    public List<WorkflowNews> selectDataListByNewType(Long businessKey, String newType) {
        QueryWrapper<WorkflowNews> queryWrapper = new QueryWrapper<>();
        if( businessKey != null ){
            queryWrapper.lambda().eq(WorkflowNews::getBussinessKey,businessKey);
        }
        if( StringUtils.isNotEmpty(newType) ){
            queryWrapper.lambda().eq(WorkflowNews::getNewType,newType);
        }
        List<WorkflowNews> workflowNews = baseMapper.selectList(queryWrapper);
        return workflowNews;
    }
}
