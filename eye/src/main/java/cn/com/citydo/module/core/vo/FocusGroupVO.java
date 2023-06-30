package cn.com.citydo.module.core.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author shaodl
 * @create 2021/6/22 9:10
 * @version: 1.0
 * @description:
 */
@Data
public class FocusGroupVO {

    /** 事件 */
    @ApiModelProperty(value = "事件")
    private String event;
    /** 人员姓名 */
    @ApiModelProperty(value = "人员姓名")
    private String name;
    /** 人员照片 */
    @ApiModelProperty(value = "人员照片")
    private String photo;
    /** 性别 */
    @ApiModelProperty(value = "性别")
    private String gender;
    /** 联系方式 */
    @ApiModelProperty(value = "联系方式")
    private String contactWay;
    /** 标签 */
    @ApiModelProperty(value = "标签")
    private String tag;
    /** 地址 */
    @ApiModelProperty(value = "地址")
    private String address;
    /** 人员轨迹 */
    @ApiModelProperty(value = "人员轨迹")
    private String trajectory;
    /** 设备 */
    @ApiModelProperty(value = "设备")
    private String device;
    /** 抓拍照片 */
    @ApiModelProperty(value = "抓拍照片")
    private String pic;


}
