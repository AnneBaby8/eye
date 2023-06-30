package cn.com.citydo.module.screen.vo;

import cn.com.citydo.module.screen.enums.CycleTypeEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 *
 * </p>
 *
 * @author wx227336
 * @Date 2021/6/7 15:51
 * @Version 1.0
 */
@Data
public class CycleDataVO {

    @ApiModelProperty(value = "环比数据")
    private CycleDetailDataVO cycleDetailDataVO;

    @ApiModelProperty(value = "环比增长率类型 0-今日 1-本周  2-本月 3-本年")
    private String chainType;

    public CycleDataVO(CycleDetailDataVO cycleDetailDataVO, String chainType) {
        this.cycleDetailDataVO = cycleDetailDataVO;
        this.chainType = chainType;
    }

    @ApiModelProperty(value = "环比增长率类型名称")
    private String chainTypeName;

    public String getChainTypeName() {
        return CycleTypeEnum.getValueByKey(this.getChainType());
    }
}
