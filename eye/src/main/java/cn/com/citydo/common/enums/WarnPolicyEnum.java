package cn.com.citydo.common.enums;

/**
 * @Author blackcat
 * @create 2021/6/1 13:59
 * @version: 1.0
 * @description:
 */
public enum  WarnPolicyEnum {


    RIGHTNOW("0", "实时预警"),

    NOT_FING("1", "未发现预警"),

    IN_AND_OUT("2", "出入预警"),

    INTERCEPT("3", "拦截预警"),

            ;

    private String code;
    private String name;

    WarnPolicyEnum(String code, String name) {
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
