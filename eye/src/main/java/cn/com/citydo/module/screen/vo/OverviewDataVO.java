package cn.com.citydo.module.screen.vo;

import cn.com.citydo.module.core.vo.OverviewDeviceVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * <p>
 *  事件统计展示类
 * </p>
 *
 * @author wx227336
 * @Date 2021/6/7 13:38
 * @Version 1.0
 */
@Data
public class OverviewDataVO {

    @ApiModelProperty(value = "AI事件统计")
    private List<EventCountVO> eventCountVOList;

    @ApiModelProperty(value = "AI事件趋势")
    private List<CycleDataVO> cycleDataVOList;

    @ApiModelProperty(value = "AI实时事件")
    List<OverviewDeviceVO> overviewDeviceVOSList;
}
