package cn.com.citydo.common.enums;

/**
 * @Author blackcat
 * @create 2021/6/1 13:59
 * @version: 1.0
 * @description:0-提醒 1-预警网格员 2-预警网格长 3-网格长督办  4-社区网格中心督办  5-网格长指派 6-处置部门退回 7-网格员上报 8-街道上报
 */
public enum NewsReadEnum {

    UNREAD(0, "未读"),

    READ(1, "已读")
            ;

    private Integer code;
    private String name;

    NewsReadEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public Integer getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public static String getNameByCode(Integer code) {
        for (NewsReadEnum typeEnum : values()) {
            if (typeEnum.getCode().equals(code)) return typeEnum.getName();
        }
        return null;
    }
}
