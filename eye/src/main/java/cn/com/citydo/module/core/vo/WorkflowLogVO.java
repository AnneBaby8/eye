package cn.com.citydo.module.core.vo;

import cn.com.citydo.module.core.entity.WorkflowStep;
import lombok.Data;

import java.util.List;

/**
 * @Author blackcat
 * @create 2021/7/6 9:15
 * @version: 1.0
 * @description:
 */
@Data
public class WorkflowLogVO {

    private List<WorkflowStep> logList;

    private Boolean isEnd = Boolean.FALSE;
}
