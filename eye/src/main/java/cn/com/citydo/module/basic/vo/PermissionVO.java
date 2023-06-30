package cn.com.citydo.module.basic.vo;

import cn.com.citydo.module.basic.entity.Permission;
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
 * @Date 2021/6/1 17:27
 * @Version 1.0
 */
@Data
public class PermissionVO extends BaseTreeNode {
    /**
     * 是否已经设置 0-否 1-是
     */
    private Boolean isSelect;

    @ApiModelProperty(value = "权限类型，页面-1，按钮-2")
    private Integer type;

    @ApiModelProperty(value = "权限表达式")
    private String permission;

    @ApiModelProperty(value = "排序")
    private Integer sort;

}
