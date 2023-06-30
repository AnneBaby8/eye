package cn.com.citydo.common.enums;

/**
 * @Author blackcat
 * @create 2021/6/8 13:58
 * @version: 1.0
 * @description:
 */
public enum OperationEnum {

    MACHINE("机器人", "预警"),

    SELF_HANDLE("自行处置", "自行处置"),

    REPORT_HANDLE("上报处置部门", "网格员上报处置部门"),

    ASSIGN_GRID("指派网格员", "指派网格员"),

    SUPERVISE("督办","督办"),

    BACK_GRID("退回给网格员", "处置部门退回给网格员"),

    STREET_TO_HANDLE("街道转处置部门", "街道转处置部门"),

    HANDLE_TO_STREET("退回给街道", "退回给街道"),

    CANCEL("作废", "作废"),

    REPORT_STREET("上报街道", "网格员上报街道");

    /**
     * 状态码
     */
    private String code;

    /**
     * 返回信息
     */
    private String message;

    OperationEnum(String code, String message) {
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
