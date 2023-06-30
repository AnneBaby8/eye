package cn.com.citydo.module.core.service;

import cn.com.citydo.module.core.entity.WorkflowStep;
import cn.com.citydo.module.core.vo.WorkflowStepVO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 流程步骤表 服务类
 * </p>
 *
 * @author blackcat
 * @since 2021-06-08
 */
public interface WorkflowStepService extends IService<WorkflowStep> {

   void createMachine(Long workflowId);

   List<WorkflowStep> getByWorkflowId(Long workflowId);

    List<WorkflowStepVO> log(Long workflowId);

    List<WorkflowStep> getReportTime(Long workflowId, String code);

    WorkflowStep selectNewDataByWorkflowId(Long workflowId,String operation);
}
