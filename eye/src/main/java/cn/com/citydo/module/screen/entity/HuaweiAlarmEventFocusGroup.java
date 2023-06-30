package cn.com.citydo.module.screen.entity;

import lombok.Data;

/**
 * 重点人群实体类
 *
 * @author
 * @since 2021-06-07
 */
@Data
public class HuaweiAlarmEventFocusGroup extends HuaweiAlaramEvent {

    /** 姓名 */
    private String name;
    /** 照片 */
    private String pic;
    /** 身份证号 */
    private String idCard;
    /** 性别 */
    private String gender;
    /** 人员类型 */
    private Integer type;
    /** 居住地址 */
    private String address;
    /** 联系方式 */
    private String contactWay;
    /** 人员标签 */
    private String tag;

}
