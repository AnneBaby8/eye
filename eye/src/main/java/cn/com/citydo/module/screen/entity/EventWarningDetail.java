package cn.com.citydo.module.screen.entity;

import cn.com.citydo.common.frame.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 事件预警详情表
 * </p>
 *
 * @author blackcat
 * @since 2021-06-18
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("event_warning_detail")
@ApiModel(value="EventWarningDetail对象", description="事件预警详情表")
public class EventWarningDetail extends BaseEntity {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "事件预警ID")
    private Long eventWarningId;

    @ApiModelProperty(value = "人员姓名")
    private String username;

    @ApiModelProperty(value = "人员图片")
    private String picture;

    @ApiModelProperty(value = "性别，男-0，女-1 未知：-1")
    private Integer sex;

    @ApiModelProperty(value = "手机")
    private String phone;

    @ApiModelProperty(value = "标签")
    private String tag;

    @ApiModelProperty(value = "地址")
    private String address;

    @ApiModelProperty(value = "进-1，出-2")
    private Integer track;

    @ApiModelProperty(value = "抓拍图片")
    private String capture;

    @ApiModelProperty(value = "相似度")
    private String similarity;

    @ApiModelProperty(value = "身份证号码")
    private String credentialNumber;

    @ApiModelProperty(value = "人脸坐标-左")
    private int picLeft;

    @ApiModelProperty(value = "人脸坐标-上")
    private int picTop;

    @ApiModelProperty(value = "人脸坐标-右")
    private int picRight;

    @ApiModelProperty(value = "人脸坐标-下")
    private int picBottom;

}
