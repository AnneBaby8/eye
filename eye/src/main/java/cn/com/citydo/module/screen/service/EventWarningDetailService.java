package cn.com.citydo.module.screen.service;

import cn.com.citydo.module.screen.entity.EventWarningDetail;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 事件预警详情表 服务类
 * </p>
 *
 * @author blackcat
 * @since 2021-06-18
 */
public interface EventWarningDetailService extends IService<EventWarningDetail> {

    /**
     * 根据预警事件ID查询预警事件
     *
     * @param eventWarningId 预警事件ID
     * @return
     */
    List<EventWarningDetail> queryByEventWarningId(Long eventWarningId);
}
