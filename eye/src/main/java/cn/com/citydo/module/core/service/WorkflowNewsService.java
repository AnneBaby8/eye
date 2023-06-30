package cn.com.citydo.module.core.service;

import cn.com.citydo.module.core.dto.WorkflowNewsDTO;
import cn.com.citydo.module.core.entity.WorkflowNews;
import cn.com.citydo.module.core.vo.WorkflowNewsVO;
import cn.com.citydo.utils.Msg;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 流程消息表 服务类
 * </p>
 *
 * @author blackcat
 * @since 2021-06-08
 */
public interface WorkflowNewsService extends IService<WorkflowNews> {
    /**
     * APP端-查看消息详情
     * @param workflowNewsDTO
     * @return
     */
    WorkflowNewsVO detail(WorkflowNewsDTO workflowNewsDTO);

    Boolean setReadStatus(WorkflowNewsDTO workflowNewsDTO);

    void saveMsg(List<Msg> msgList);

    void saveMsg(Msg msg);

    List<WorkflowNews> selectDataListByNewType(Long businessKey,String newType);

}
