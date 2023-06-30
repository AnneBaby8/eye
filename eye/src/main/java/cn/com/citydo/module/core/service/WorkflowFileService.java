package cn.com.citydo.module.core.service;

import cn.com.citydo.module.core.entity.WorkflowFile;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 流程文件表 服务类
 * </p>
 *
 * @author blackcat
 * @since 2021-07-29
 */
public interface WorkflowFileService extends IService<WorkflowFile> {

   List<WorkflowFile> getFiles(Long workflowId);

}
