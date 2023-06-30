package cn.com.citydo.module.screen.service.impl;


import cn.com.citydo.module.screen.entity.EventWarningReadedRecord;
import cn.com.citydo.module.screen.mapper.EventWarningReadedRecordMapper;
import cn.com.citydo.module.screen.service.EventWarningReadedRecordService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * 预警事件查阅记录 服务实现类
 *
 * @author Shaodl
 * @since 2021-06-29
 */
@Slf4j
@Service
public class EventWarningReadedRecordServiceImpl extends ServiceImpl<EventWarningReadedRecordMapper, EventWarningReadedRecord> implements EventWarningReadedRecordService {


    @Override
    @Transactional(rollbackFor = Exception.class)
    public int create(EventWarningReadedRecord eventWarningReadedRecord) {
        if( eventWarningReadedRecord == null ){
            return 0;
        }
        eventWarningReadedRecord.setReadTime(new Date());
        return baseMapper.insert(eventWarningReadedRecord);
    }
}
