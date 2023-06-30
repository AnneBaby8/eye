package cn.com.citydo.module.screen.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

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
public class EventCountVO {

    @ApiModelProperty(value = "日期")
    private String dateFormat;

    @ApiModelProperty(value = "个数")
    private Integer countNumber;
}
