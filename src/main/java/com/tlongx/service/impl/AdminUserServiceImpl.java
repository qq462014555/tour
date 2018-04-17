package com.tlongx.service.impl;

import java.util.Date;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Maps;
import com.google.common.util.concurrent.Runnables;
import com.tlongx.common.Const;
import com.tlongx.common.ServiceResponse;
import com.tlongx.controller.AdminManaggerController;
import com.tlongx.dao.AdminUserMapper;
import com.tlongx.pojo.AdminUser;
import com.tlongx.service.IAdminUserService;
import com.tlongx.util.MD5Util;
import com.tlongx.util.MUtil;



@Service("iAdminUserService")
public class AdminUserServiceImpl implements IAdminUserService {
	
	 private static final Logger logger = LoggerFactory.getLogger(AdminManaggerController.class);

	
	@Autowired
	private AdminUserMapper adminUserMapper;
	
	
	public ServiceResponse insertAdmin(AdminUser adminUser) {
		ServiceResponse  serviceResponse=null;
		if(StringUtils.isNotBlank(adminUser.getPhone())){
			serviceResponse = checkValid(adminUser.getPhone(), Const.CHECK_VALID.PHONE);
		}
		if(StringUtils.isNotBlank(adminUser.getLoginName())){
			serviceResponse = checkValid(adminUser.getLoginName(), Const.CHECK_VALID.LOGIN_NAME);
		}
		if(serviceResponse.isSuccess()){

			int resultCount=0;
		
			//增加
			if(adminUser.getUid()!=null){
				  return serviceResponse.createFileMessage("增加不能传id");
			}
			long newUID=System.currentTimeMillis()+new Random().nextInt(100)+Long.valueOf(UUID.randomUUID().toString());
			  adminUser.setUid(newUID);
			
			resultCount=adminUserMapper.insert(adminUser);
			if(resultCount>0){
				return serviceResponse.createSuccess();
			}
		}
		return serviceResponse;
	}

	//判断  注册  数据的 有效性
	public ServiceResponse checkAdminProperty(Map<String,Object> paramMap){
	 	
		 
		   AdminUser adminUser=new AdminUser();
		 	
		   ServiceResponse serviceResponse=checkAdminOrUserLogin(paramMap);
		   if(!serviceResponse.isSuccess()){
			   return serviceResponse;
		   }
		 //MUtil.strObject  方法参数  为null的话 返回:""
		 	if(StringUtils.isBlank(MUtil.strObject(paramMap.get("adminName")))){
				return ServiceResponse.createFileMessage(Const.AdminErrorMes.UNADMINNAME);
			}
		 	
		 	String resultRole=MUtil.strObject(paramMap.get("role"));
		 	if(StringUtils.isBlank(resultRole)){
		 		return ServiceResponse.createFileMessage(Const.AdminErrorMes.UNROLE);
			}
		 	int status=Integer.valueOf(MUtil.strObject(paramMap.get("status")).toString());
		 	int role=Integer.valueOf(resultRole);
	 		
	 		/*1-超级管理员,0-普通管理员'*/
	 		if(Const.ADMIN.ROLE.SUPER==role){
	 			adminUser.setRole(true);
			}if(Const.ADMIN.ROLE.General==role){
				adminUser.setRole(false);
			}
			adminUser.setLoginName(MUtil.strObject(paramMap.get("loginName")));
			adminUser.setAdminName(MUtil.strObject(paramMap.get("adminName")));
			//电话号规格 前端 判断，后台可不需验证，可空
			adminUser.setPhone(MUtil.strObject(paramMap.get("adminPhone")));
			
			return ServiceResponse.createSuccessData(adminUser);
	 } 
		
	/**
	 *   判断账号密码 是否已存在
	 * @param str
	 * @param type
	 * @return
	 */
	public ServiceResponse checkValid(String str,String type){
	        if(org.apache.commons.lang3.StringUtils.isNotBlank(type)){
	        	 int resultCount=0;
	            //开始校验
	            if(Const.CHECK_VALID.PHONE.equals(type)){
	            	resultCount  =adminUserMapper.checkPhone(str);
	                if(resultCount > 0 ){
	                    return ServiceResponse.createFileCodeMsg(Const.ErrorMessage.STATUS_USER_PHONE_IS_ALREADY_EXISTS.getCode(),
	                    	Const.ErrorMessage.STATUS_USER_PHONE_IS_ALREADY_EXISTS.getValue());
	                }
	            }
	            if(Const.CHECK_VALID.LOGIN_NAME.equals(type)){
	                 resultCount=adminUserMapper.checkLoginName(str);
	                if(resultCount > 0 ){
	                      return ServiceResponse.createFileCodeMsg(Const.ErrorMessage.STATUS_USER_NAME_IS_ALREADY_EXISTS.getCode(),
	                    		Const.ErrorMessage.STATUS_USER_NAME_IS_ALREADY_EXISTS.getValue());
	                }
	            }
	      
	        }else{
	            return ServiceResponse.createFileCodeMsg(Const.ErrorMessage.STATUS__FALIED.getCode(),
	            				Const.ErrorMessage.STATUS__FALIED.getValue());
	        }
	        return ServiceResponse.createBySuccessMessage("校验成功");
	    }
  

   	/**
	 *   判断账号密码 是否为空
	 * @param paramMap
	 * @return
	 */
	public ServiceResponse checkAdminOrUserLogin(Map<String, Object> paramMap) {
		// TODO Auto-generated method stub
		if(StringUtils.isBlank(MUtil.strObject(paramMap.get("loginName")))){
			return ServiceResponse.createFileCodeMsg(Const.ErrorMessage.STATUS_FALIED_UNLOGINNAME_ALREADY_EXISTS.getCode(), Const.ErrorMessage.STATUS_FALIED_UNLOGINNAME_ALREADY_EXISTS.getValue());
		}
		if(StringUtils.isBlank(MUtil.strObject(paramMap.get("pwd")))){
			return ServiceResponse.createFileCodeMsg(Const.ErrorMessage.STATUS_FALIED_UNPASSWORD_ALREADY_EXISTS.getCode(),Const.ErrorMessage.STATUS_FALIED_UNPASSWORD_ALREADY_EXISTS.getValue());
		}
		return ServiceResponse.createSuccess();
		
	}

  
	public ServiceResponse adminManagerLogin(Map<String, Object> paramMap) {
		
		ServiceResponse serviceResponse=checkAdminOrUserLogin(paramMap);
		if(!serviceResponse.isSuccess()){
			return serviceResponse;
		}
		// TODO Auto-generated method stub
		AdminUser adminUser=new AdminUser();
		String loginName=MUtil.strObject(paramMap.get("loginName"));
		String MD5PWD=MD5Util.MD5EncodeUtf8(MUtil.strObject(paramMap.get("pwd")));	
		//暂时   ，登陆成功返回 所有字段
		adminUser=adminUserMapper.selectByLoginNameAndPwd(loginName, MD5PWD);

		if(adminUser!=null){
			return serviceResponse.createSuccessData(adminUser);
		}

		return ServiceResponse.createByErrorCodeMessage(Const.ErrorMessage.STATUS_USER_NAME_AND_PASSWORD_ALREADY_EXISTS.getCode(),Const.ErrorMessage.STATUS_USER_NAME_AND_PASSWORD_ALREADY_EXISTS.getValue());
	}
	/**
	 * 超级管理员  删除
	 * @param adminUser
	 * @param paramMap
	 * @return
	 */
	public ServiceResponse deleteAdmin(HttpSession session, Map<String, Object> paramMap) {
		// TODO Auto-generated method stub
		//防止越权问题  跟session验证
		 AdminUser adminUser=(AdminUser)session.getAttribute(Const.SESSION);
		if(StringUtils.equals(adminUser.getUid().toString(),paramMap.get("uid").toString())){
			return ServiceResponse.createFileCodeMsg(Const.ErrorMessage.STATUS_FALIED_REQUEST_EXISTS.getCode(),Const.ErrorMessage.STATUS_FALIED_REQUEST_EXISTS.getValue());
		}
		AdminUser deleteUser=new AdminUser();
		deleteUser.setUid((Long)paramMap.get("dUid"));
		deleteUser.setStatus(false);
		adminUserMapper.updateByPrimaryKey(deleteUser);
		return ServiceResponse.createSuccess();
	}




}
