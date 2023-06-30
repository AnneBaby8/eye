package cn.com.citydo.common;

/**
 * <p>
 * REST API 错误码接口
 * </p>
 * @author blackcat
 * @since 2020-07-21
 */
public interface IStatus {

    /**
     * 状态码
     *
     * @return 状态码
     */
    Integer getCode();

    /**
     * 返回信息
     *
     * @return 返回信息
     */
    String getMessage();

}