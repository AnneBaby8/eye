package cn.com.citydo.common.enums;

/**
 * <p>
 *  是否
 *  0-否 1-是
 * </p>
 *
 * @author wx227336
 * @Date 2021/6/1 11:39
 * @Version 1.0
 */
public enum RoleCodeEnum {

    ADMIN("admin", "管理员"),

    GRID("grid", "网格员"),

    GRID_LEAD("grid_lead", "网格长"),

    SOCIAL("social", "社区网格中心"),

    STREET("street", "街道网格中心"),

    HANDLE("handle", "处置部门"),

    AREA("area", "区网格中心");

    /**
     * 状态码
     */
    private String code;

    /**
     * 返回信息
     */
    private String message;

    RoleCodeEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    public static String getNameByCode(String code) {
        for (RoleCodeEnum typeEnum : values()) {
            if (typeEnum.getCode().equals(code)) return typeEnum.getMessage();
        }
        return null;
    }
}
