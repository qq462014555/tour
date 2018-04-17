package com.tlongx.coomon;
public class Result {
      
	 private int status;
	 private String info;
     private Object data;
	 public int getStatus() {
		return status;
	 }
	 public void setStatus(int status) {
		this.status = status;
	 }
	 public String getInfo() {
	 	return info;
	 }
	 public void setInfo(String info) {
		this.info = info;
	 }
	 public Object getData() {
		return data;
	 }
	 public void setData(Object data) {
	   if(data == null)
	    {
	        return;
	    } else
	    {
	        this.data = data;
	        return;
	    } 
	 } 
}
