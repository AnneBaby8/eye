package cn.com.citydo.module.screen.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * <p>
 *  环比数据展示类
 * </p>
 *
 * @author wx227336
 * @Date 2021/6/7 13:38
 * @Version 1.0
 */
@Data
public class CycleDetailDataVO {

    @ApiModelProperty(value = "AI事件统计")
    private EventCountVO eventCountVO;

    @ApiModelProperty(value = "事件-上周期数")
    private Long eventLastCycleCount;

    @ApiModelProperty(value = "事件-下周期数")
    private Long eventNextCycleCount;

    @ApiModelProperty(value = "误报-上周期数")
    private Long falseAlarmLastCycleCount;

    @ApiModelProperty(value = "误报-下周期数")
    private Long falseAlarmNextCycleCount;

    @ApiModelProperty(value = "事件环比率")
    private BigDecimal eventCycleRate;

    @ApiModelProperty(value = "误报环比率")
    private BigDecimal falseAlarmCycleRate;

    @ApiModelProperty(value = "误报率")
    private BigDecimal falseAlarmRate;


}
