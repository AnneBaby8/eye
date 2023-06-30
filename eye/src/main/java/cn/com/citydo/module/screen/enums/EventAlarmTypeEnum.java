package cn.com.citydo.module.screen.enums;

/**
 * 华为事件告警枚举
 *
 * @Author shaodl
 * @create 2021/6/25
 * @version: 1.0
 * @description:
 */
public enum EventAlarmTypeEnum {

    FACE_DATA("faceData", "人脸数据");

    private String key;

    private String value;

    EventAlarmTypeEnum(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

}
