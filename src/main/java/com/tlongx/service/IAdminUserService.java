package com.tlongx.service;

import java.util.Map;

import javax.servlet.http.HttpSession;

import com.tlongx.common.ServiceResponse;
import com.tlongx.pojo.AdminUser;

public interface IAdminUserService {

	ServiceResponse insertAdmin(AdminUser admin);

	ServiceResponse checkAdminProperty(Map<String, Object> paramMap);
	
	
	
	/**
	 *   判断账号密码 是否为空
	 * @param paramMap
	 * @return
	 */
	ServiceResponse checkAdminOrUserLogin(Map<String, Object> paramMap);


	ServiceResponse adminManagerLogin(Map<String, Object> paramMap);

	ServiceResponse deleteAdmin(HttpSession session, Map<String, Object> paramMap);
	

	
	
	

}
