package com.jiajun.imagehosting.common.bo;

import com.jiajun.imagehosting.common.util.JsonUtils;

/**
 * 统一API响应结果封装
 */
public class Result {
	
	private static final String SUCCESS_MESSAGE = "success";
	
    private int code;
    private String message;
    private Object data;

    public Result() {
   
    }
	public Result(int code, String message, Object data) {
		super();
		this.code = code;
		this.message = message;
		this.data = data;
	}
	
	public static Result success(String message, Object data) {
		return new Result(ResultCode.SUCCESS.code, message, data);
	}
	public static Result success(String message) {
		return new Result(ResultCode.SUCCESS.code, message, null);
	}
	public static Result success(Object data) {
		return new Result(ResultCode.SUCCESS.code, SUCCESS_MESSAGE, data);
	}
	public static Result fail(String message) {
		return new Result(ResultCode.FAIL.code, message, null);
	}
	public static Result forbidden(String message) {
		return new Result(ResultCode.FORBIDDEN.code, message, null);
	}
	public static Result error(String message) {
		return new Result(ResultCode.ERROR.code, message, null);
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return JsonUtils.toString(this);
	}
}
