package cn.com.citydo.module.screen.vo;

import cn.com.citydo.module.screen.enums.DepartmentLevelEnum;
import cn.com.citydo.utils.BaseTreeNode;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author wx227336
 * @Date 2021/6/11 11:30
 * @Version 1.0
 */
@Data
public class OverviewDepartmentVO {
    @ApiModelProperty(value = "树形结构")
    List<BaseTreeNode> baseTreeNodeList;

    @ApiModelProperty(value = "0-市级数据；1-区级数据；2-街道数据；3-社区数据")
    private String departmentLevel;

    @ApiModelProperty(value = "数据标识")
    private String departmentLevelName;

    public String getDepartmentLevelName() {
        return DepartmentLevelEnum.getValueByKey(this.getDepartmentLevel());
    }
}
