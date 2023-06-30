package cn.com.citydo.module.core.service.impl;

import cn.com.citydo.module.core.entity.WorkflowFile;
import cn.com.citydo.module.core.mapper.WorkflowFileMapper;
import cn.com.citydo.module.core.service.WorkflowFileService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 流程文件表 服务实现类
 * </p>
 *
 * @author blackcat
 * @since 2021-07-29
 */
@Service
public class WorkflowFileServiceImpl extends ServiceImpl<WorkflowFileMapper, WorkflowFile> implements WorkflowFileService {

    @Override
    public List<WorkflowFile> getFiles(Long workflowId) {
        return this.list(new QueryWrapper<WorkflowFile>().lambda().eq(WorkflowFile::getWorkflowId,workflowId).orderByDesc(WorkflowFile::getCreateTime));
    }
}
