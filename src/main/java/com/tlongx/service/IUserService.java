package com.tlongx.service;

import java.util.Map;

import com.tlongx.common.ServiceResponse;
import com.tlongx.pojo.User;

public interface IUserService {

	ServiceResponse userManagerLogin(Map<String, Object> paramMap);


	ServiceResponse checkUserProperty(Map<String, Object> paramMap);


	ServiceResponse checkEmail(Map<String, Object> paramMap);


	ServiceResponse insertUser(User user);


	ServiceResponse forgetpassword(Map<String, Object> paramMap);


	ServiceResponse registerAdd(User user, Map<String, Object> paramMap);

}
