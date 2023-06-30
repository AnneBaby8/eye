package cn.com.citydo.module.screen.entity;


import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * 华为视频监测事件信息实体类
 *
 * @author blackcat
 * @since 2021-05-12
 */
@Data
@ApiModel(description = "华为视频监测事件信息")
public class EventWarning implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull()
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "摄像头编号")
    private String streamId;

    @ApiModelProperty(value = "事件类型，即视频监测算法值（占道经营：1114112，出店经营：1245184，垃圾检测：1310720）")
    private Long eventType;

    @ApiModelProperty(value = "触发告警时间点的时间戳")
    private Long timestamp;

    @ApiModelProperty(value = "告警监测到的视频截图，存储图片访问路由")
    private String imageBase64;

    @ApiModelProperty(value = "推送所有的参数")
    private String json;

    @ApiModelProperty(value = "创建时间")
    @TableField(
            fill = FieldFill.INSERT
    )
    private Date createTime;

    @ApiModelProperty(value = "0-占道经营 1-垃圾检测")
    private String warnType;

    private Boolean isWarning;

    private Boolean isStart;

    private String reason;

}
