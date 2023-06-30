package cn.com.citydo.module.screen.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * 预警事件查阅记录实体类
 *
 * @author shaodl
 * @since 2021-06-29
 */
@Data
@ApiModel(description = "预警事件查阅记录")
public class EventWarningReadedRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull()
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "事件ID")
    private Long workflowId;

    @ApiModelProperty(value = "预警事件ID")
    private Long eventWarningId;

    @ApiModelProperty(value = "查阅人员ID")
    private Long readerId;

    @ApiModelProperty(value = "查阅人员姓名")
    private String readerName;

    @ApiModelProperty(value = "查阅时间")
    private Date readTime;


}
