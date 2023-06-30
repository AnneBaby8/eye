package cn.com.citydo.common.enums;

/**
 * @Author blackcat
 * @create 2021/6/1 13:59
 * @version: 1.0
 * @description:0-提醒 1-预警网格员 2-预警网格长 3-网格长督办  4-社区网格中心督办  5-网格长指派 6-处置部门退回 7-网格员上报 8-街道上报
 */
public enum NewsEnum {


    REMIND("0", "提醒"),

    WARN_GRID("1", "预警网格员"),

    WARN_GRID_LEAD("2", "预警网格长"),

    SUPRISE_GRID_LEAD("3", "网格长督办"),

    SUPRISE_SOCIAL("4", "社区网格中心督办"),

    ASSIGN("5", "指派"),

    BACK("6", "退回"),

    REPORT_GRID("7", "网格员上报"),

    REPORT_STREET("8", "街道上报"),

            ;

    private String code;
    private String name;

    NewsEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}
