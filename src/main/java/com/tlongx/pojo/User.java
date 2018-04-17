package com.tlongx.pojo;

import java.util.Date;

public class User {
    private Long uid;

    private String loginName;

    private String adminName;

    private String password;

    private Boolean status;

    private Integer sex;

    private Integer age;

    private String phone;

    private String email;

    private Boolean role;

    private String address;

    private String validatacode;

    private Date registerOutTime;

    private Date createTime;

    private Date updateTime;

    private String subImages;

    public User(Long uid, String loginName, String adminName, String password, Boolean status, Integer sex, Integer age, String phone, String email, Boolean role, String address, String validatacode, Date registerOutTime, Date createTime, Date updateTime, String subImages) {
        this.uid = uid;
        this.loginName = loginName;
        this.adminName = adminName;
        this.password = password;
        this.status = status;
        this.sex = sex;
        this.age = age;
        this.phone = phone;
        this.email = email;
        this.role = role;
        this.address = address;
        this.validatacode = validatacode;
        this.registerOutTime = registerOutTime;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.subImages = subImages;
    }

    public User() {
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

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public Boolean getRole() {
        return role;
    }

    public void setRole(Boolean role) {
        this.role = role;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public String getValidatacode() {
        return validatacode;
    }

    public void setValidatacode(String validatacode) {
        this.validatacode = validatacode == null ? null : validatacode.trim();
    }

    public Date getRegisterOutTime() {
        return registerOutTime;
    }

    public void setRegisterOutTime(Date registerOutTime) {
        this.registerOutTime = registerOutTime;
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

    public String getSubImages() {
        return subImages;
    }

    public void setSubImages(String subImages) {
        this.subImages = subImages == null ? null : subImages.trim();
    }
}