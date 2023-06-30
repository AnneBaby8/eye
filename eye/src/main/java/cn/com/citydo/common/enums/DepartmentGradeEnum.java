package cn.com.citydo.common.enums;

/**
 * <p>
 *  部门等级
 * </p>
 *
 * @author wx227336
 * @Date 2021/6/29 14:06
 * @Version 1.0
 */
public enum DepartmentGradeEnum {
    CITY("city", "市级"),

    AREA("area", "区级"),

    STREET("street", "街道"),

    SOCIAL("social", "社区"),

    GRID("grid", "网格"),
    ;

    private String code;
    private String name;

    DepartmentGradeEnum(String code, String name) {
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
        for (DepartmentGradeEnum typeEnum : values()) {
            if (typeEnum.getCode().equals(code)) return typeEnum.getName();
        }
        return null;
    }
}
