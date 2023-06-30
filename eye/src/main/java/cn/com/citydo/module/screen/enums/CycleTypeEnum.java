package cn.com.citydo.module.screen.enums;

/**
 * @Author blackcat
 * @create 2021/5/12 14:44
 * @version: 1.0
 * @description:
 */
public enum CycleTypeEnum {


    DAY("0", "今日"),
    WEEK("1", "本周"),
    MONTH("2", "本月"),
    YEAR("3", "本年");

    private String key;

    private String value;

    CycleTypeEnum(String key, String value) {
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
        for (CycleTypeEnum typeEnum : values()) {
            if (typeEnum.getKey().equals(key)) return typeEnum.getValue();
        }
        return null;
    }
}
