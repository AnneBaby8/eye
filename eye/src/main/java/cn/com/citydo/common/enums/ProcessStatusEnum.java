package cn.com.citydo.common.enums;

/**
 * @Author blackcat
 * @create 2021/6/8 13:58
 * @version: 1.0
 * @description:
 */
public enum ProcessStatusEnum {

    START(0, "网格员"),

    REPORT_HANDLE(1, "网格员上报处置部门"),

    BACK_GRID(2, "处置部门退回给网格员"),

    REPORT_STREET(3, "网格员上报街道"),

    STREET_TO_HANDLE(4, "街道转处置部门"),

    HANDLE_TO_STREET(5, "处置部门转街道"),

    CANCEL(6, "作废"),

    END(7, "结束")
    ;

    /**
     * 状态码
     */
    private Integer code;

    /**
     * 返回信息
     */
    private String message;

    ProcessStatusEnum(Integer code, String message) {
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
}
