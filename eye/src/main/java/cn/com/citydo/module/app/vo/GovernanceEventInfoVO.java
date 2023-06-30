package cn.com.citydo.module.app.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class GovernanceEventInfoVO {

    /** 未处理 */
    @ApiModelProperty(value = "unDealCount")
    private long unDealCount;
    /** 处理中 */
    @ApiModelProperty(value = "dealingCount")
    private long dealingCount;
    /** 已办结 */
    @ApiModelProperty(value = "finishedCount")
    private long finishedCount;

    /** 办结率 */
    @ApiModelProperty(value = "completionRate")
    private long completionRate;

}
