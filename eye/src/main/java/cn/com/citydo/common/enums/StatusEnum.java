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
public enum StatusEnum {

    NO("0", "否"),

    YES("1", "是");

    /**
     * 状态码
     */
    private String code;

    /**
     * 返回信息
     */
    private String message;

    StatusEnum(String code, String message) {
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
}
