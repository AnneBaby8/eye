package cn.com.citydo.module.core.entity;

import cn.com.citydo.common.frame.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 预警配置表
 * </p>
 *
 * @author shaodl
 * @since 2021-06-01
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("huawei_alarm_event_focusgroup")
@ApiModel(value="FocusGroup对象", description="重点人员事件表")
public class FocusGroup extends BaseEntity {

    private static final long serialVersionUID=1L;

    /** 姓名 */
    @ApiModelProperty(value = "姓名")
    private String name;
    /** 照片 */
    @ApiModelProperty(value = "照片")
    private String pic;
    /** 身份证号 */
    @ApiModelProperty(value = "身份证号")
    private String idCard;
    /** 性别 */
    @ApiModelProperty(value = "性别")
    private String gender;
    /** 人员类型 */
    @ApiModelProperty(value = "人员类型")
    private Integer type;
    /** 居住地址 */
    @ApiModelProperty(value = "居住地址")
    private String address;
    /** 联系方式 */
    @ApiModelProperty(value = "联系方式")
    private String contactWay;
    /** 人员标签 */
    @ApiModelProperty(value = "人员标签")
    private String tag;


}
