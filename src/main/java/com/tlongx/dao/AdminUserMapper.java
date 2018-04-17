package com.tlongx.dao;

import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.tlongx.pojo.AdminUser;


public interface AdminUserMapper {
    int deleteByPrimaryKey(Long uid);

    int insert(AdminUser record);

    int insertSelective(AdminUser record);

    AdminUser selectByPrimaryKey(Long uid);

    int updateByPrimaryKeySelective(AdminUser record);

    int updateByPrimaryKey(AdminUser record);

	AdminUser selectByLoginNameAndPwd(@Param(value="loginName")String loginName,@Param(value="MD5PWD") String MD5PWD);

	int checkPhone(String phone);

	int checkLoginName(String login_name);
}