package cn.com.citydo.module.screen.enums;

/**
 * @Author blackcat
 * @create 2021/5/12 14:44
 * @version: 1.0
 * @description:
 */
public enum WarnTypeEnum {

    CHANNEL("0", "通道占用"),
    GARBAGE("1", "垃圾堆占"),
    HELP_GROUP("2", "帮扶人员"),
    FOCUS_VISIT("3", "重点人员"),
    EMPTY_NESTER("4", "空巢老人"),
    CROWDED_PLACE("5","人群密集"),
    SEWAGE_BUBBLING("6","污水跑冒"),
    VEHICLE_ILLEGAL_STOP("7","机动车违停"),
    VEHICLE_NON_ILLEGAL_STOP("8","非机动车违停")
    ;

    private String key;

    private String value;

    WarnTypeEnum(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public static WarnTypeEnum findEnumByCode(String key) {
        for (WarnTypeEnum warnTypeEnum : WarnTypeEnum.values()) {
            if (warnTypeEnum.getKey().equals(key)) {
                return warnTypeEnum;
            }
        }
        throw new IllegalArgumentException("key is not support");

    }

    public static WarnTypeEnum findEnumByName(String value) {
        for (WarnTypeEnum warnTypeEnum : WarnTypeEnum.values()) {
            if (warnTypeEnum.getValue().equals(value)) {
                return warnTypeEnum;
            }
        }
        throw new IllegalArgumentException("value is not support");
    }

    public static String getValueByKey(String key) {
        for (WarnTypeEnum typeEnum : values()) {
            if (typeEnum.getKey().equals(key)) return typeEnum.getValue();
        }
        return null;
    }


    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }
}
