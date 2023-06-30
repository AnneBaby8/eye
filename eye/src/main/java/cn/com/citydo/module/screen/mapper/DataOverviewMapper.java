package cn.com.citydo.module.screen.mapper;


import cn.com.citydo.module.screen.dto.OverviewDataDTO;
import cn.com.citydo.module.screen.vo.EventCountVO;

import java.util.List;

/**
 * 事件预警 Mapper 接口
 *
 * @author blackcat
 * @since 2021-05-12
 */
public interface DataOverviewMapper {
    /**
     * 查询近七日数据
     * @param overviewDTO 入参
     * @return
     */
    List<EventCountVO> selectPeriodData(OverviewDataDTO overviewDTO);

    /**
     * 获取本期数据
     * @param overviewDTO
     * @return
     */
    Long selectNextCycleData(OverviewDataDTO overviewDTO);
    /**
     * 获取上期数据
     * @param overviewDTO
     * @return
     */
    Long selectLastCycleData(OverviewDataDTO overviewDTO);

}
