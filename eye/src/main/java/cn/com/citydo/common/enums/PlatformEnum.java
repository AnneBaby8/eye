package cn.com.citydo.common.enums;

/**
 * @Author blackcat
 * @create 2021/8/6 15:19
 * @version: 1.0
 * @description:
 */
public enum PlatformEnum {

    INTER("0", "互联网3800X"),

    GOV("1", "政务外网3800X");


    /**
     * 状态码
     */
    private String code;

    /**
     * 返回信息
     */
    private String message;

    PlatformEnum(String code, String message) {
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
