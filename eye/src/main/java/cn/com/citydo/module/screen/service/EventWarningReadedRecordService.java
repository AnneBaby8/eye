package cn.com.citydo.module.screen.service;

import cn.com.citydo.module.screen.entity.EventWarningReadedRecord;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 预警事件查阅记录 服务类
 *
 * @author Shaodl
 * @since 2021-06-29
 */
public interface EventWarningReadedRecordService extends IService<EventWarningReadedRecord> {

    public int create(EventWarningReadedRecord eventWarningReadedRecord);

}
