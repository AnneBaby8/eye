package cn.com.citydo.common.enums;

/**
 * @Author blackcat
 * @create 2021/6/8 13:58
 * @version: 1.0
 * @description:
 */
public enum QueryStatusEnum {

    UNSTART(0, "未开始"),

    PROCESS(1, "处理中"),

    FINISH(2, "办结")
    ;

    /**
     * 状态码
     */
    private Integer code;

    /**
     * 返回信息
     */
    private String message;

    QueryStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static String getNameByCode(Integer code) {
        for (QueryStatusEnum typeEnum : values()) {
            if (typeEnum.getCode().equals(code)) return typeEnum.getMessage();
        }
        return null;
    }
}
