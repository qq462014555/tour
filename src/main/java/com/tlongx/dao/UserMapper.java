package com.tlongx.dao;

import org.apache.ibatis.annotations.Param;

import com.tlongx.pojo.User;

public interface UserMapper {
    int deleteByPrimaryKey(Long uid);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Long uid);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKeyWithBLOBs(User record);

    int updateByPrimaryKey(User record);

	int selectCheckEmail(@Param(value="email")String email);

	int selectCheckLoginName(@Param("loginName")String loginName);

	User selectByLoginNameAndPwd(@Param(value="loginName")String loginName,@Param(value="password")String mD5PWD);

	User selectByEmailAndPwd(@Param(value="email")String loginName,@Param(value="password")String mD5PWD);

	void updateByEmail(User user);
}