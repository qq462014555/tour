package com.tlongx.common;

public enum ResponseEnum {
	
	SUCCESS(0,"SUCCESS"),
	FAIL(1,"FAIL"),
	NEED_LOGIN(2,"NEDD_LOGIN");
	
	
	
	private int code;
	private String desc;
	 ResponseEnum(int code,String desc) {
		this.code=code;
		this.desc=desc;
			// TODO Auto-generated constructor stub
		}




	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	
	
	
	

}
