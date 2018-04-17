package com.tlongx.pojo;

import java.util.Date;

public class AdminUser {
    private Long uid;
//登陆账号
    private String loginName;
//真实姓名
    private String adminName;
  //登陆密码
    private String password;
//账号 状态 1-正常,0-注销'
    private Boolean status;
//电话号码
    private String phone;

   // 1-超级管理员,0-普通管理员'
    private Boolean role;
    
    
    
    private Date createTime;

    private Date updateTime;


    public AdminUser(Long uid, String loginName, String adminName, String password, Boolean status, String phone,
			Boolean role, Date createTime, Date updateTime) {
		super();
		this.uid = uid;
		this.loginName = loginName;
		this.adminName = adminName;
		this.password = password;
		this.status = status;
		this.phone = phone;
		this.role = role;
		this.createTime = createTime;
		this.updateTime = updateTime;
	}

	public AdminUser() {
        super();
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }
 

	public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName == null ? null : loginName.trim();
    }

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName == null ? null : adminName.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public Boolean getRole() {
        return role;
    }

    public void setRole(Boolean role) {
        this.role = role;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}