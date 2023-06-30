package cn.com.citydo.common.enums;

/**
 * @Author blackcat
 * @create 2021/6/1 13:59
 * @version: 1.0
 * */
public enum NewsTypeEnum {
    /**
     * 消息类型名称枚举类
     */
    REMIND("0", "提醒"),

    WARN_GRID("1", "预警"),

    WARN_GRID_LEAD("2", "预警"),

    SUPRISE_GRID_LEAD("3", "督办"),

    SUPRISE_SOCIAL("4", "督办"),

    ASSIGN("5", "指派"),

    BACK("6", "退回"),

    REPORT_GRID("7", "上报"),

    REPORT_STREET("8", "上报");

    private String code;
    private String name;

    NewsTypeEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public static String getNameByCode(String code) {
        for (NewsTypeEnum typeEnum : values()) {
            if (typeEnum.getCode().equals(code)) return typeEnum.getName();
        }
        return null;
    }

}
