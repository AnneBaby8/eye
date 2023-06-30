package cn.com.citydo.module.core.service;

import cn.com.citydo.common.enums.NewsEnum;
import cn.com.citydo.common.enums.RoleCodeEnum;
import cn.com.citydo.module.core.dto.HandleDTO;
import cn.com.citydo.module.core.dto.WorkflowCountQUERY;
import cn.com.citydo.module.core.dto.WorkflowQUERY;
import cn.com.citydo.module.core.entity.Workflow;
import cn.com.citydo.module.core.inner.ProcessDO;
import cn.com.citydo.module.core.vo.*;
import cn.com.citydo.module.screen.entity.EventWarning;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 流程主表 服务类
 * </p>
 *
 * @author blackcat
 * @since 2021-06-08
 */
public interface WorkflowService extends IService<Workflow> {

    ProcessDO startProcess(EventWarning eventWarning);

    /**
     * 按条件查询事件列表
     *
     * @param query
     * @return
     */
    IPage<WorkflowVO> getByQuery(WorkflowQUERY query);

    IPage<WorkflowStreetVO> streetPage(WorkflowQUERY query);


    /**
     * app 端接口
     *
     * @param query
     * @return
     */
    WorkflowCount count(WorkflowCountQUERY query);

    /**
     * app 端接口
     *
     * @param query
     * @return
     */
    IPage<AppWorkflowVO> pageByQuery(WorkflowCountQUERY query);

    /**
     * app
     *
     * @param handle
     * @return
     */
    Long handle(HandleDTO handle, RoleCodeEnum roleCodeEnum);

    /**
     * app
     *
     * @param workflowId
     * @return
     */
    List<HandleVO> getHandle(Long workflowId);

    /**
     * app
     *
     * @param workflowId
     * @param departmentId
     * @return
     */
    Long report(Long workflowId, Long departmentId, String remark);

    Long assignGrid(Long workflowId, Long gridUserId, String remark);

    Long supervise(Long workflowId, NewsEnum newsEnum, String remark);

    Long back(Long workflowId,String remark);

    Long  streetTransfer(Long workflowId, String remark, Long departmentId);

    /**
     * APP-获取网格员列表
     * @return
     */
    List<GridUserVO> gridList(String bussinessKey);

    Long cancel(Long workflowId, String remark);


    void sendRemindMsg(EventWarning entity);

    Workflow selectDataById(Long id);

    AppWorkflowVO initFinishUserInfo(AppWorkflowVO vo, Integer status);

    void initGridUserInfo();

}
