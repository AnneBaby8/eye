package cn.com.citydo.module.screen.service;

import cn.com.citydo.module.core.vo.OverviewDeviceVO;
import cn.com.citydo.module.screen.dto.NoticeDTO;
import cn.com.citydo.module.screen.dto.OverviewDataDTO;
import cn.com.citydo.module.screen.dto.alarmevent.AlarmEventContentDTO;
import cn.com.citydo.module.screen.entity.EventWarning;
import cn.com.citydo.module.screen.enums.WarnTypeEnum;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 华为云监测告警事件 服务类
 *
 * @author blackcat
 * @since 2021-05-12
 */
public interface EventWarningService extends IService<EventWarning> {

    /**
     * 保存告警事件信息
     *
     * @param notice 告警内容
     * @param warnTypeEnum 告警类型
     * @return 事件记录主键ID
     */
    Long saveNotice(NoticeDTO notice, WarnTypeEnum warnTypeEnum);

    /**
     *
     */
    List<OverviewDeviceVO> selectRealTimeEventsData(OverviewDataDTO overviewDataDTO);

    /**
     * 保存华为北向外部对接推送过来的事件信息
     * @param content
     */
    void saveHuaweiNorthOrientationEvent(AlarmEventContentDTO content);
}
