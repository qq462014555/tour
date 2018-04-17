package com.tlongx.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import com.tlongx.common.Const;
import com.tlongx.common.ServiceResponse;
import com.tlongx.common.TokenCache;
import com.tlongx.controller.AdminManaggerController;
import com.tlongx.dao.AdminUserMapper;
import com.tlongx.pojo.AdminUser;
import com.tlongx.pojo.User;
import com.tlongx.service.IAdminUserService;
import com.tlongx.service.IUserService;
import com.tlongx.util.MD5Util;
import com.tlongx.util.MUtil;
import com.tlongx.util.PropertiesUtil;
import com.tlongx.util.SendMailUtil;

import net.sf.json.JSONObject;
import sun.print.resources.serviceui;

import com.tlongx.dao.UserMapper;

@Service("iUserService")
public class UserServiceImpl implements IUserService {
	
	 private static final Logger logger = LoggerFactory.getLogger(AdminManaggerController.class);
	
	@Autowired
	private com.tlongx.dao.UserMapper UserMapper;
	
	@Autowired
	private IAdminUserService iAdminUserService;

	/*用户登陆*/
	public ServiceResponse userManagerLogin(Map<String, Object> paramMap) {
		// TODO Auto-generated method stub
		ServiceResponse serviceResponse=checkLoginNameAndPwd(paramMap);
		if(!serviceResponse.isSuccess()){
			return serviceResponse;
		}
		User user=new User();
		String loginName=MUtil.strObject(paramMap.get("loginName"));
		String MD5PWD=MD5Util.MD5EncodeUtf8(PropertiesUtil.getProperty("md5.password")+MUtil.strObject(paramMap.get("password")));	
		//暂时   ，登陆成功返回 所有字段
		user=UserMapper.selectByLoginNameAndPwd(loginName, MD5PWD);
		if(user==null){
			user=UserMapper.selectByEmailAndPwd(loginName, MD5PWD);
			if(user==null){
			return ServiceResponse.createByErrorCodeMessage(Const.ErrorMessage.STATUS_USER_NAME_AND_PASSWORD_ALREADY_EXISTS.getCode(),Const.ErrorMessage.STATUS_USER_NAME_AND_PASSWORD_ALREADY_EXISTS.getValue());
			}
		}
		return serviceResponse.createSuccessData(user);
		
	}
	

	//判断  注册  数据的 有效性
	public ServiceResponse checkUserProperty(Map<String,Object> paramMap){
	 	
		  User user=new User();
		   ServiceResponse serviceResponse=checkLoginNameAndPwd(paramMap);
		   if(!serviceResponse.isSuccess()){
			   return serviceResponse;
		   }
		  
		  //昵称
		 	String loginName=paramMap.get("loginName").toString();
		 	String password=paramMap.get("password").toString();
		 	
		 	if(StringUtils.isNotBlank(MUtil.strObject(paramMap.get("email")))){
		 		 int resultCount=0;
		 		
		 		String email=paramMap.get("email").toString();
		 		resultCount=UserMapper.selectCheckLoginName(loginName);
		 		if(resultCount>0){
		 			return ServiceResponse.createFileCodeMsg(Const.ErrorMessage.STATUS_USER_NAME_IS_ALREADY_EXISTS.getCode(),Const.ErrorMessage.STATUS_USER_NAME_IS_ALREADY_EXISTS.getValue());
		 		 }
		 		 resultCount=UserMapper.selectCheckEmail(email);
		 		 if(resultCount>0){
		 			return ServiceResponse.createFileCodeMsg(Const.ErrorMessage.STATUS_FALIED_CEMAIL_ALREADY_EXISTS.getCode(),Const.ErrorMessage.STATUS_FALIED_CEMAIL_ALREADY_EXISTS.getValue());
		 		 }
		 		
		 		if(StringUtils.isBlank(MUtil.strObject(paramMap.get("validatacode")))){
		 			return ServiceResponse.createFileCodeMsg(Const.ErrorMessage.STATUS_FALIED_VAILDATACODE_EXISTS.getCode(),Const.ErrorMessage.STATUS_FALIED_VAILDATACODE_EXISTS.getValue());
		 		}
		 		String validatacode=paramMap.get("validatacode").toString();
		 		System.out.println(TokenCache.getLocalCacheEmailKey(TokenCache.TOKEN_VALIDATACODE+email));
		 		//输入的验证码跟内存上存储的验证码比较
		 		if(!StringUtils.equals(TokenCache.getLocalCacheEmailKey(TokenCache.TOKEN_VALIDATACODE+email),validatacode.trim())){
		 			return ServiceResponse.createFileMessage("验证码不正确");
		 		}
		 		user.setEmail(email);
		 		
		 	}
		 	user.setLoginName(loginName);
		 	user.setPassword(password);
			return ServiceResponse.createSuccessData(user);
	 } 
	/**
	 *   昵称 密码  不可空
	 * @param paramMap
	 * @return
	 */
	public ServiceResponse checkLoginNameAndPwd(Map<String, Object> paramMap) {
		// TODO Auto-generated method stub
		if(StringUtils.isBlank(MUtil.strObject(paramMap.get("loginName")))){
			return ServiceResponse.createFileCodeMsg(Const.ErrorMessage.STATUS_FALIED_UNLOGINNAME_ALREADY_EXISTS.getCode(), Const.ErrorMessage.STATUS_FALIED_UNLOGINNAME_ALREADY_EXISTS.getValue());
		}
		if(StringUtils.isBlank(MUtil.strObject(paramMap.get("password")))){
			return ServiceResponse.createFileCodeMsg(Const.ErrorMessage.STATUS_FALIED_UNPASSWORD_ALREADY_EXISTS.getCode(),Const.ErrorMessage.STATUS_FALIED_UNPASSWORD_ALREADY_EXISTS.getValue());
		}
		return ServiceResponse.createSuccess();
		
	}


	@Override
	//发送验证码给邮箱，在内存存 半小时
	public ServiceResponse checkEmail(Map<String, Object> paramMap) {
		// TODO Auto-generated method stub
		//邮箱判断是否为空
		if(StringUtils.isBlank(MUtil.strObject(paramMap.get("email")))){
			return ServiceResponse.createFileCodeMsg(Const.ErrorMessage.STATUS_FALIED_EMAIL_ALREADY_EXISTS.getCode(), Const.ErrorMessage.STATUS_FALIED_EMAIL_ALREADY_EXISTS.getValue());
		}
		try {
			//收件人邮箱
			String email = paramMap.get("email").toString();
			int resultCount = UserMapper.selectCheckEmail(email);
			if (resultCount > 0) {
				return ServiceResponse.createFileMessage("邮箱已存在");
			}
			//创建邮箱随机5位数 验证码
			String validatacode = String.valueOf(new Random().nextInt(100000));
			//存到内存 半小时后 过期
			TokenCache.setLocalCacheEmailKey(TokenCache.TOKEN_VALIDATACODE + email, validatacode);
			//后台发送验证码到  收件人邮箱
			SendMailUtil.send(email, validatacode);
		} catch (Exception e) {
			// TODO: handle exception
			logger.info("发送邮件失败", e);
			return ServiceResponse.createFileMessage("发送邮件失败");
		}
		return ServiceResponse.createSuccess();
	
	}

	//注册校验通过 ，添加用户
	@Override
	public ServiceResponse insertUser(User user) {
		// TODO Auto-generated method stub
		String md5Passoword=MD5Util.MD5EncodeUtf8(PropertiesUtil.getProperty("md5.password")+user.getPassword());
		//生成 用uid
		  long uid =System.currentTimeMillis()+new Random().nextInt(100);
		
		user.setPassword(md5Passoword);
		user.setUid(uid);
		int resultCount=UserMapper.insertSelective(user);
		if(resultCount>0){
			return ServiceResponse.createSuccess();
			
		}
		return ServiceResponse.createFileMessage("注册失败");
		
		
	}


	@Override
	public ServiceResponse forgetpassword(Map<String, Object> paramMap) {
		// TODO Auto-generated method stub
		if(StringUtils.isBlank(MUtil.strObject(paramMap.get("email")))){
			return ServiceResponse.createFileCodeMsg(Const.ErrorMessage.STATUS_FALIED_EMAIL_ALREADY_EXISTS.getCode(), Const.ErrorMessage.STATUS_FALIED_EMAIL_ALREADY_EXISTS.getValue());
		}
		if(StringUtils.isBlank(MUtil.strObject(paramMap.get("validatacode")))){
			return ServiceResponse.createFileCodeMsg(Const.ErrorMessage.STATUS_FALIED_VAILDATACODE_EXISTS.getCode(),Const.ErrorMessage.STATUS_FALIED_VAILDATACODE_EXISTS.getValue());
		}
		if(StringUtils.isBlank(MUtil.strObject(paramMap.get("password")))){
			return ServiceResponse.createFileCodeMsg(Const.ErrorMessage.STATUS_FALIED_UNPASSWORD_ALREADY_EXISTS.getCode(),Const.ErrorMessage.STATUS_FALIED_UNPASSWORD_ALREADY_EXISTS.getValue());
		}
		
		String validatacode=paramMap.get("validatacode").toString();
		
		String email=paramMap.get("email").toString();
		String password =paramMap.get("email").toString();
		
		if(!StringUtils.equals(TokenCache.getLocalCacheEmailKey(TokenCache.TOKEN_VALIDATACODE+email),validatacode.trim())){
 			return ServiceResponse.createFileMessage("验证码不正确");
 		}
		
		try {
			String md5Passoword=MD5Util.MD5EncodeUtf8(PropertiesUtil.getProperty("md5.password")+password);
			User user=new User();
			user.setEmail(email);
			user.setValidatacode(validatacode);
			user.setPassword(password);
			UserMapper.updateByEmail(user);
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("设置密码失败",e);
			return ServiceResponse.createFileMessage("设置吗失败"); 
		}
		return ServiceResponse.createSuccess();
		
		
		
	}


	@Override
	public ServiceResponse registerAdd(User user,Map<String, Object> paramMap) {
		// TODO Auto-generated method stub
		if(StringUtils.isBlank(MUtil.strObject(paramMap.get("phone")))){
			return ServiceResponse.createFileCodeMsg(Const.ErrorMessage.STATUS_FALIED_NO_PHONE_IS_ALREADY_EXISTS.getCode(), Const.ErrorMessage.STATUS_FALIED_NO_PHONE_IS_ALREADY_EXISTS.getValue());
		}
		if(StringUtils.isBlank(MUtil.strObject(paramMap.get("age")))){
			return ServiceResponse.createFileCodeMsg(Const.ErrorMessage.STATUS_FALIED_NO_AGE_IS_ALREADY_EXISTS.getCode(),Const.ErrorMessage.STATUS_FALIED_NO_AGE_IS_ALREADY_EXISTS.getValue());
		}
		if(StringUtils.isBlank(MUtil.strObject(paramMap.get("sex")))){
			return ServiceResponse.createFileCodeMsg(Const.ErrorMessage.STATUS_FALIED_NO_SEX_IS_ALREADY_EXISTS.getCode(),Const.ErrorMessage.STATUS_FALIED_NO_SEX_IS_ALREADY_EXISTS.getValue());
		}
		if(StringUtils.isBlank(MUtil.strObject(paramMap.get("address")))){
			return ServiceResponse.createFileCodeMsg(Const.ErrorMessage.STATUS_FALIED_NO_ADDRESS_IS_ALREADY_EXISTS.getCode(),Const.ErrorMessage.STATUS_FALIED_NO_ADDRESS_IS_ALREADY_EXISTS.getValue());
		}
		
		user.setPhone(paramMap.get("phone").toString());
		user.setAge(Integer.valueOf(paramMap.get("age").toString()));
		user.setSex(Integer.valueOf(paramMap.get("sex").toString()));
		user.setAddress(paramMap.get("address").toString());
		//分割图片路径
		String phoneListPath=paramMap.get("photo").toString();
		
		

		return null;
		
	}
}
