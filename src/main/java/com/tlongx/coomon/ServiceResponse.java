package com.tlongx.coomon;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.map.annotate.JsonSerialize;

@JsonSerialize(include =JsonSerialize.Inclusion.NON_NULL)
public class ServiceResponse<T> implements Serializable{
	
	private String msg;
	private Integer status;
	private  T data;
	
	
	public ServiceResponse(Integer status,String msg,T data) {
		this.status=status;
		this.msg=msg;
		this.data=data;
	}
	public ServiceResponse(Integer status,String msg) {
		this.status=status;
		this.msg=msg;
	}
	public ServiceResponse(Integer status,T data) {
		this.status=status;
		this.data=data;
	}
	
	public ServiceResponse(Integer status) {
		this.status=status;
	}
	@JsonIgnore
	public boolean isSuccess(){
		if(this.status==ResponseEnum.SUCCESS.getCode()){
			return true;
		}
		else{
			return false;
		}
	}
	
	
	

	
	public static ServiceResponse createSuccess(){
		
		 return new ServiceResponse(ResponseEnum.SUCCESS.getCode());
		
	}
	public static ServiceResponse createSuccessMessage(String msg){
		 return new ServiceResponse(ResponseEnum.SUCCESS.getCode(),msg);
	}
	public static <T>ServiceResponse createSuccessData(T data){
		 return new ServiceResponse(ResponseEnum.SUCCESS.getCode(),data);
	}
	public static <T>ServiceResponse createSuccessData(String msg,T data){
		 return new ServiceResponse(ResponseEnum.SUCCESS.getCode(),msg,data);
	}
	
	public static ServiceResponse createFileCodeMsg(int code, String desc) {
		// TODO Auto-generated method stub
		 return new ServiceResponse(code,desc);
	}
	
	
	
	public static ServiceResponse createFile(){
		
		 return new ServiceResponse(ResponseEnum.FAIL.getCode());
		
	}
	public static ServiceResponse createFileMessage(String msg){
		 return new ServiceResponse(ResponseEnum.FAIL.getCode(),msg);
	}
	public static <T> ServiceResponse createFileData(T data){
		 return new ServiceResponse(ResponseEnum.FAIL.getCode(),data);
	}
	public static <T> ServiceResponse createFileData(String msg,T data){
		 return new ServiceResponse(ResponseEnum.FAIL.getCode(),msg,data);
	}
	
	
	
	

	
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
