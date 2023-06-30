package cn.com.citydo.module.core.vo;

import cn.com.citydo.module.screen.enums.WarnTypeEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 *  数据概览-设备
 * </p>
 *
 * @author wx227336
 * @Date 2021/6/8 10:36
 * @Version 1.0
 */
@Data
public class OverviewWarnTypeVO {

    private String warnType;


    @ApiModelProperty(value = "预警类型名称")
    private String warnTypeName;

    public String getWarnTypeName() {
        return WarnTypeEnum.getValueByKey(this.getWarnType());
    }
}
