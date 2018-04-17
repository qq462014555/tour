package com.tlongx.controller;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletWebRequest;
import com.tlongx.common.Const;
import com.tlongx.common.ResponseCode;
import com.tlongx.common.ServiceResponse;
import com.tlongx.pojo.AdminUser;
import com.tlongx.service.IAdminUserService;
import com.tlongx.util.MUtil;
import net.sf.json.JSONObject;

@Controller
/*@Scope("singleton")*/
@RequestMapping("/admin")
public class AdminManaggerController{
	
	 private static final Logger logger = LoggerFactory.getLogger(AdminManaggerController.class);

	private String jsonString;
	
	
	Map<String, Object> resultMap = null;
	

	@Autowired
	private IAdminUserService iAdminUserService;
	
	 /**
	     *【后台】管理员登录
	     * @param account 登录名
	     * @param pwd 密码 
	     * @param http://14.10.1.162:8080/aspire/manager/managerLogin
	     */
	    @RequestMapping("/login.do")
	    @ResponseBody
	    public ServiceResponse adminManagerLogin(HttpSession session,@RequestParam(value="jsonString")String jsonString){
		    logger.info("进入超级管理员 的 登陆方法<== [输入参数]："+jsonString); 
		   
 			//jsonToMap方法 参数json有为空的，报异常，fromObject防止 json 参数正确导入
		    JSONObject jsonObject = JSONObject.fromObject(jsonString);
		    Map<String,Object> paramMap = MUtil.jsonToMap(jsonString);
		
				ServiceResponse	serviceResponse=iAdminUserService.adminManagerLogin(paramMap);
					if(serviceResponse.isSuccess()){
					    AdminUser adminUser=(AdminUser) serviceResponse.getData();
						session.setAttribute(Const.SESSION,adminUser);
						return serviceResponse;
					}
				 logger.info("退出  admin 的 adminManagerLogin()==> [输出参数]："+serviceResponse);
				return serviceResponse;
	    }

	 /**
	     *【后台】添加管理员
	     * @param role 管理员角色  0：超级管理员  1：普通管理员
	     * @param loginName 登录名
	     * @param adminName 管理员姓名
	     * @param pwd 密码(不能修改)
	     * @param adminPhone 管理员手机号
	     * @param status 管理员状态（1正常  2停用  0删除）
	     * @param manager_id 管理员id（修改时）
	     * @param http://14.10.1.162:8080/aspire/manager/addOrUpManager 
	     */
	 @RequestMapping("register.do")
	 @ResponseBody
	public ServiceResponse AdminRegister(HttpSession session,@RequestParam(value="jsonString")String jsonString){  
	 //HttpServletResponse response = ((ServletWebRequest)RequestContextHolder.getRequestAttributes()).getResponse();
		logger.info("进入 AdminRegister 的 AdminRegister() <== [输入参数] ："+jsonString);  
		AdminUser sessionAdminUser = (AdminUser)session.getAttribute(Const.SESSION);
		if(sessionAdminUser==null){
			 return ServiceResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());			  
		}
		//1=true=超级管理员  | 0=false=普通管理员 
		if(sessionAdminUser.getRole()==false){
			return ServiceResponse.createFileCodeMsg(Const.ErrorMessage.STATUS_NO_USPER_ADMIN_ALREADY_EXISTS.getCode(),Const.ErrorMessage.STATUS_NO_USPER_ADMIN_ALREADY_EXISTS.getValue());
		}
		else{
			try{
				//jsonToMap方法 参数json有为空的，报异常，fromObject防止 json 参数正确导入
	 			JSONObject jsonObject = JSONObject.fromObject(jsonString);
	 			Map<String,Object> paramMap = MUtil.jsonToMap(jsonString);
					ServiceResponse serviceResponse=iAdminUserService.checkAdminProperty(paramMap);
					if(serviceResponse.isSuccess()){
						//正常数据
						AdminUser admin=(AdminUser) serviceResponse.getData();
						return  iAdminUserService.insertAdmin(admin);
						
						
					}
				}catch(Exception e){
					logger.error("发生错误",e);
				}
		    }
		 logger.info("退出  admin 的 AdminRegister()==>");
		return null;
	 }
	
	 /**
	     *【后台】删除管理员
	     * @param role 管理员角色  0：超级管理员  1：普通管理员
	     * @param loginName 登录名
	     * @param pwd 密码(不能修改)
	     * @param adminPhone 管理员手机号
	     * @param status 管理员状态（1正常  2停用  0删除）
	     * @param manager_id 管理员id（修改时）
	     * @param http://14.10.1.162:8080/aspire/manager/addOrUpManager 
	     */
	 @RequestMapping("delete.do")
	 @ResponseBody
	public ServiceResponse AdminDelete(HttpSession session){  
		logger.info("进入 AdminRegister 的 AdminRegister() <== [输入参数] ："+jsonString);  
				//jsonToMap方法 参数json有为空的，报异常，fromObject防止 json 参数正确导入
	 			JSONObject jsonObject = JSONObject.fromObject(jsonString);
	 			Map<String,Object> paramMap = MUtil.jsonToMap(jsonString);
						//正常数据
	 			//删除参数:session的  对象，paramMap{删除的id，和操作人id}
					ServiceResponse serviceResponse =iAdminUserService.deleteAdmin(session,paramMap);
					logger.info("退出  admin 的 AdminRegister()==> [输出参数]："+serviceResponse);
					return serviceResponse;
				
	 }
	 /**
	     管理员注销
	     */
		@RequestMapping(value = "logout.do")
	    @ResponseBody
	    public ServiceResponse<String> logout(HttpSession session){
	        session.removeAttribute(Const.SESSION);
	        return ServiceResponse.createSuccess();
	    }
	 
	 
	 
 //超级管理员  账号接口 才调用 这个方法
	 public ServiceResponse  checkRole(AdminUser adminUser){
			if(adminUser==null){
				 return ServiceResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());			  
			}
			//1=true=超级管理员  | 0=false=普通管理员 
			if(adminUser.getRole()==false){
				return ServiceResponse.createFileCodeMsg(Const.ErrorMessage.STATUS_NO_USPER_ADMIN_ALREADY_EXISTS.getCode(),Const.ErrorMessage.STATUS_NO_USPER_ADMIN_ALREADY_EXISTS.getValue());
			}
			return ServiceResponse.createSuccess();
	 }
}

