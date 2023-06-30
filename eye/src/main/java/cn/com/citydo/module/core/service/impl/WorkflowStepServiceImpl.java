package cn.com.citydo.module.core.service.impl;

import cn.com.citydo.common.BaseException;
import cn.com.citydo.common.EyeStatus;
import cn.com.citydo.common.enums.OperationEnum;
import cn.com.citydo.module.core.entity.WorkflowStep;
import cn.com.citydo.module.core.mapper.WorkflowStepMapper;
import cn.com.citydo.module.core.service.WorkflowStepService;
import cn.com.citydo.module.core.vo.WorkflowStepVO;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 流程步骤表 服务实现类
 * </p>
 *
 * @author blackcat
 * @since 2021-06-08
 */
@Service
@Slf4j
public class WorkflowStepServiceImpl extends ServiceImpl<WorkflowStepMapper, WorkflowStep> implements WorkflowStepService {

    @Override
    public void createMachine(Long workflowId) {
        WorkflowStep workflowStep = new WorkflowStep();
        workflowStep.setWorkflowId(workflowId);
        workflowStep.setName(OperationEnum.MACHINE.getCode());
        workflowStep.setOperation(OperationEnum.MACHINE.getMessage());
        workflowStep.setRoleName(OperationEnum.MACHINE.getCode());
        boolean save = this.save(workflowStep);
        if (!save) {
            throw new BaseException(EyeStatus.SAVE_ERROR);
        }
    }

    @Override
    public List<WorkflowStep> getByWorkflowId(Long workflowId) {
        return this.list(new QueryWrapper<WorkflowStep>().lambda()
                .eq(WorkflowStep::getWorkflowId, workflowId).orderByAsc(WorkflowStep::getCreateTime));
    }

    @Override
    public List<WorkflowStepVO> log(Long workflowId) {
        List<WorkflowStepVO> list = new ArrayList<>();
        List<WorkflowStep> byWorkflowId = this.getByWorkflowId(workflowId);
        if( !CollectionUtils.isEmpty(byWorkflowId) ){
            for (WorkflowStep workflowStep : byWorkflowId) {
                WorkflowStepVO workflowStepVO = new WorkflowStepVO();
                BeanUtils.copyProperties(workflowStep,workflowStepVO);
                Date updateTime = workflowStepVO.getUpdateTime();
                try {
                    if( updateTime != null ){
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                        String str = simpleDateFormat.format(updateTime);
                        workflowStepVO.setFormatUpdateTime(str);
                    }
                }catch (Exception e){
                    log.info("日期转化错误，数据为：[{}]", JSONObject.toJSONString(workflowStepVO));
                }finally {
                    list.add(workflowStepVO);
                }

            }
        }
        return list;
    }

    @Override
    public List<WorkflowStep> getReportTime(Long workflowId, String code) {
        if( workflowId ==null || StringUtils.isEmpty(code)){
            return null;
        }
        QueryWrapper<WorkflowStep> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().orderByDesc(WorkflowStep::getCreateTime).eq(WorkflowStep::getWorkflowId,workflowId)
                .eq(WorkflowStep::getOperation,code);

        return  baseMapper.selectList(queryWrapper);
    }

    @Override
    public WorkflowStep selectNewDataByWorkflowId(Long workflowId, String operation) {
        QueryWrapper<WorkflowStep> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().orderByDesc(WorkflowStep::getId).eq(WorkflowStep::getWorkflowId,workflowId);
        List<WorkflowStep> workflowSteps = baseMapper.selectList(queryWrapper);
        if( !CollectionUtils.isEmpty(workflowSteps) ){
            return workflowSteps.get(0);
        }
        return null;
    }
}
