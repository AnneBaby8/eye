package cn.com.citydo.common;

import lombok.Getter;

/**
 * @Author blackcat
 * @create 2021/5/12 9:27
 * @version: 1.0
 * @description:
 */
@Getter
public enum EyeStatus implements IStatus {

    VIDEO_CONVER_ERROR(100000, "视频流文件转换失败"),

    WARN_NAME_EXIST_ERROR(50010, "预警名称已经存在"),

    WARN_EXIST_ERROR(50010, "预警编号已经存在"),

    GRID_ID_ERROR(50009, "网格员id错误"),

    UPDATE_FLOW_ERROR(50008, "更新失败,当前存在同时处理"),

    ROLE_ERROR(50007, "角色错误没有权限操作"),

    SAVE_ERROR(50006, "新增失败"),

    USERNAME_EXIST(50005, "当前账号名称已存在"),

    UPDATE_ERROR(50004, "更新失败"),

    ID_ERROR(50003, "id错误"),

    PARAM_ERROR(50002, "参数错误"),

    LOGIN(50001, "登陆接口请求失败！");

    /**
     * 状态码
     */
    private Integer code;

    /**
     * 返回信息
     */
    private String message;

    EyeStatus(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

}
