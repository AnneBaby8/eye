package cn.com.citydo.module.screen.mapper;


import cn.com.citydo.module.core.vo.OverviewDeviceVO;
import cn.com.citydo.module.screen.dto.OverviewDataDTO;
import cn.com.citydo.module.screen.entity.EventWarning;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * 事件预警 Mapper 接口
 *
 * @author blackcat
 * @since 2021-05-12
 */
public interface EventWarningMapper extends BaseMapper<EventWarning> {

    List<OverviewDeviceVO> selectRealTimeEventsData(OverviewDataDTO overviewDataDTO);
}
