package cn.com.citydo.common;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 异常基类
 * </p>
 * @author blackcat
 * @since 2020-07-21
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class BaseException extends RuntimeException {
	private Integer code;
	private String message;
	private Object data;

	public BaseException(IStatus status) {
		super(status.getMessage());
		this.code = status.getCode();
		this.message = status.getMessage();
	}

	public BaseException(IStatus status, Object data) {
		this(status);
		this.data = data;
	}

	public BaseException(Integer code, String message) {
		super(message);
		this.code = code;
		this.message = message;
	}

	public BaseException(Integer code, String message, Object data) {
		this(code, message);
		this.data = data;
	}
}
