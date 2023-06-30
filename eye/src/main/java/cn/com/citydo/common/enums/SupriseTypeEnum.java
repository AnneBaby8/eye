package cn.com.citydo.common.enums;

/**
 * <p>
 * 督办
 * </p>
 *
 * @author wx227336
 * @Date 2021/9/2 12:21
 * @Version 1.0
 */
public enum SupriseTypeEnum {

    SUPRISE_GRID_LEAD("1", "网格长督办"),

    SUPRISE_SOCIAL("2", "社区网格中心督办")
    ;
    private String code;
    private String name;

    SupriseTypeEnum(String code, String name) {
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
        for (SupriseTypeEnum typeEnum : values()) {
            if (typeEnum.getCode().equals(code)) {
                return typeEnum.getName();
            }
        }
        return null;
    }
}
