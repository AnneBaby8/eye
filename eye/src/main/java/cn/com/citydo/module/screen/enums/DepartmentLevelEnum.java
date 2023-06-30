package cn.com.citydo.module.screen.enums;

/**
 * @Author blackcat
 * @create 2021/5/12 14:44
 * @version: 1.0
 * @description:
 */
public enum DepartmentLevelEnum {


    CITY_DATA("0", "市级数据"),
    AREA_DATA("1", "区级数据"),
    STREET_DATA("2", "街道数据"),
    SOCIAL_DATA("3", "社区数据");

    private String key;

    private String value;

    DepartmentLevelEnum(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    public static String getValueByKey(String key) {
        for (DepartmentLevelEnum typeEnum : values()) {
            if (typeEnum.getKey().equals(key)) return typeEnum.getValue();
        }
        return null;
    }
}
