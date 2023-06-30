package cn.com.citydo.common.frame;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @Author blackcat
 * @create 2021/3/28 23:13
 * @version: 1.0
 * @description:
 */
@Data
public class BaseEntity {


    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "创建人ID")
    private String creator;

    @ApiModelProperty(value = "创建人时间")
    @TableField(
            fill = FieldFill.INSERT
    )
    private Date createTime;

    @ApiModelProperty(value = "修改人ID")
    private String updator;

    @ApiModelProperty(value = "修改时间")
    @TableField(
            fill = FieldFill.INSERT_UPDATE
    )
    private Date updateTime;

    @TableLogic
    private Boolean del;

}
