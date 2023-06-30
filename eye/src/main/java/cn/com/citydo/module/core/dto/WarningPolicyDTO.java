package cn.com.citydo.module.core.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @Author blackcat
 * @create 2021/6/1 14:30
 * @version: 1.0
 * @description:
 */
@Data
public class WarningPolicyDTO {

    @ApiModelProperty(value = "预警配置id")
    private Long configId;

    private List<PolicyDTO> policyList;
}
