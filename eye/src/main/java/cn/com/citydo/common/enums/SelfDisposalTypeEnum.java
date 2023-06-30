package cn.com.citydo.common.enums;

/**
 * <p>
 *  自行处置类型
 * </p>
 *
 * @author wx227336
 * @Date 2021/9/2 16:07
 * @Version 1.0
 */
public enum SelfDisposalTypeEnum {
    /**
     * 自行处置类型 ：0-正常 1-核实后无情况 2-误报
     */
    NORMAL("0", "正常"),

    VERIFY_NO_SITUATION("1", "核实后无情况"),

    FALSE_ALARM("2", "误报")
    ;
    private String code;
    private String name;

    SelfDisposalTypeEnum(String code, String name) {
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
        for (SelfDisposalTypeEnum typeEnum : values()) {
            if (typeEnum.getCode().equals(code)) {
                return typeEnum.getName();
            }
        }
        return null;
    }
}
