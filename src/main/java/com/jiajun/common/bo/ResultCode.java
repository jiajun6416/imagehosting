package com.jiajun.common.bo;

public enum ResultCode {
	
	SUCCESS(200), //success
	FAIL(400), //参数之类错误
	FORBIDDEN(403), //服务器拒绝
	ERROR(500); //服务器内部错误 
	
	public  int code;
	ResultCode(int code) {
		this.code = code;
	}
}
