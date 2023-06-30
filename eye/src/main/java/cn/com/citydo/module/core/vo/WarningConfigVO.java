package cn.com.citydo.module.core.vo;

import cn.com.citydo.module.core.entity.WarningConfig;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @Author blackcat
 * @create 2021/6/1 13:52
 * @version: 1.0
 * @description:
 */
@Data
public class WarningConfigVO extends WarningConfig {
    @ApiModelProperty(value = "0-实时预警,1-未发现预警,2-出入预警,3-拦截预警")
    private List<String> policyTypeList;
}
