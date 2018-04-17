package com.tlongx.controller;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tlongx.common.Const;
import com.tlongx.common.ResponseCode;
import com.tlongx.common.ServiceResponse;
import com.tlongx.common.UserList;
import com.tlongx.pojo.AdminUser;
import com.tlongx.pojo.User;
import com.tlongx.service.IRegionService;
import com.tlongx.service.IUserService;
import com.tlongx.util.MD5Util;
import com.tlongx.util.MUtil;
import com.tlongx.util.SendMailUtil;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("/user")
public class UserManagementController {
	
	 private static final Logger logger = LoggerFactory.getLogger(AdminManaggerController.class);
	private Map<String,Object> paramMap;
	
	ServiceResponse serviceResponse = null;
	
	@Autowired
	private IUserService iUserService;
	@Autowired
	private IRegionService iRegionService;
	

	
	
	
	 /**
     *【后台】用户登录
     * @param 登录名
     * @param pwd 密码 
     * @param http://14.10.1.162:8080/aspire/manager/managerLogin
     */
	@RequestMapping("/login.do")
	@ResponseBody
	public ServiceResponse userLogin(HttpSession session,@RequestParam(value="jsonString")String jsonString){
			    logger.info("进入用户 的userLogin<== [输入参数]："+jsonString); 
				//jsonToMap方法 参数json有为空的，报异常，fromObject防止 json 参数正确导入
			    JSONObject jsonObject = JSONObject.fromObject(jsonString);
			    Map<String,Object> paramMap = MUtil.jsonToMap(jsonString);
			
					ServiceResponse	serviceResponse=iUserService.userManagerLogin(paramMap);
						if(serviceResponse.isSuccess()){
						    User user=(User) serviceResponse.getData();
							session.setAttribute(Const.SESSION,user);
							return serviceResponse;
						}
					 logger.info("退出  admin 的 adminManagerLogin()==> [输出参数]："+serviceResponse);
					return serviceResponse;
		    }
	
	
	
	
	
	
	 /**
     *【后台】安全退出
     * @param http://14.10.1.162:8080/aspire/manager/managerLogin
     */
	@RequestMapping(value = "logout.do")
    @ResponseBody
    public ServiceResponse<String> logout(HttpSession session){
        session.removeAttribute(Const.SESSION);
        return ServiceResponse.createSuccess();
    }
	 /**
     *【后台】用户注册
     * @param 昵称
     * @param 邮箱  （可空）
     * @param 验证码(可空)
     * @param pwd 密码 
     * @param http://14.10.1.162:8080/aspire/manager/managerLogin
     */
	
	@RequestMapping("/register.do")
	@ResponseBody
	public ServiceResponse register(@RequestParam(value="jsonString")String jsonString){
		//前台需要传两参数   ，value   Type=phone ,user
		JSONObject jsonObject = JSONObject.fromObject(jsonString);
			Map<String,Object> paramMap = MUtil.jsonToMap(jsonString);
			ServiceResponse serviceResponse=iUserService.checkUserProperty(paramMap);
			if(serviceResponse.isSuccess()){
				//正常数据
				User user=(User) serviceResponse.getData();
				return  iUserService.insertUser(user);
			}
			return serviceResponse;
	
	}
	@RequestMapping("/registerAdd.do")
	@ResponseBody
	public ServiceResponse registerAdd(HttpSession session,@RequestParam(value="jsonString")String jsonString){
		//前台需要传两参数   ，value   Type=phone ,user
		JSONObject jsonObject = JSONObject.fromObject(jsonString);
			Map<String,Object> paramMap = MUtil.jsonToMap(jsonString);
			User user=(User)session.getAttribute(Const.SESSION);
			if(user==null){
				return serviceResponse.createFileCodeMsg(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
			}
			ServiceResponse serviceResponse=iUserService.registerAdd(user,paramMap);
			if(serviceResponse.isSuccess()){
				//正常数据
				User requestUser=(User) serviceResponse.getData();
				return  iUserService.insertUser(user);
			}
			return serviceResponse;
	
	}
	
	
	
	
	/**
     *【后台】邮箱注册 发送验证码
     * @param 邮箱  （可空）
     * @param http://14.10.1.162:8080/aspire/manager/managerLogin
     */
	
	@RequestMapping("/sendEmail.do")
	@ResponseBody
	public ServiceResponse sendEmail(@RequestParam(value="jsonString")String jsonString){
		JSONObject jsonObject = JSONObject.fromObject(jsonString);
		Map<String,Object> paramMap = MUtil.jsonToMap(jsonString);
		return iUserService.checkEmail(paramMap);
	}
	
	
	/**
     *【后台】忘记密码邮箱找回
     * @param 邮箱 
     * @param http://14.10.1.162:8080/aspire/manager/managerLogin
     */
	@RequestMapping("/forgetPassword.do")
	@ResponseBody
	public ServiceResponse forgetPassword(@RequestParam(value="jsonString")String jsonString){
		JSONObject jsonObject = JSONObject.fromObject(jsonString);
		Map<String,Object> paramMap = MUtil.jsonToMap(jsonString);
		return iUserService.forgetpassword(paramMap);
	}
	/**
     *【后台】登陆状态修改密码
     * @param 邮箱 
     * @param http://14.10.1.162:8080/aspire/manager/managerLogin
     */
	@RequestMapping("/revisePassword.do")
	@ResponseBody
	public ServiceResponse revisePassword(HttpSession session,@RequestParam(value="jsonString")String jsonString){
		JSONObject jsonObject = JSONObject.fromObject(jsonString);
		Map<String,Object> paramMap = MUtil.jsonToMap(jsonString);
		User user=(User)session.getAttribute(Const.SESSION);
		if(user==null){
			return serviceResponse.createFileCodeMsg(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
		}
		return iUserService.forgetpassword(paramMap);
	}
	
	 /**
     *【后台】下拉地域名
     * @param 登录名
     * @param pwd 密码 
     * @param http://14.10.1.162:8080/aspire/manager/managerLogin
     */
	@RequestMapping("/selectRegion.do")
	@ResponseBody
	public ServiceResponse selectRegion(HttpSession session,@RequestParam(value="jsonString")String jsonString){
		JSONObject jsonObject = JSONObject.fromObject(jsonString);
		Map<String,Object> paramMap = MUtil.jsonToMap(jsonString);
		return iRegionService.selectRegion(paramMap);
	}
}
