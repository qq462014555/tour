package com.tlongx.common;

import java.util.Set;

import com.google.common.collect.Sets;



     
  

public class Const {
	
	
	
	
	public static final String SESSION="SESSION";
	
	public interface CHECK_VALID{
		 final String PHONE="PHONE";
		final String LOGIN_NAME="LOGIN_NAME";
	}
	public interface PRODUCT_STATUS{
		final int SHNAGJIA=1;
		final int XIAJIA=2;
		final int DELETE=3;
	}
	public interface ProductListOrderBy{
		   Set<String> PRICE_ASC_DESC = Sets.newHashSet("price_desc","price_asc");
	}
	public interface ADMIN{
		public interface ROLE{
			final  int General = 0;
			final int  SUPER=1;
			
		}
		public interface STATUS{
			final  int NORMAL  = 1;
			final int  UN_NORMAL=0;
		}
		
		
	}
	public interface USER{
		public interface ROLE{
			final  int General = 0;
			final int  VIP=1;
			
		}
		public interface STATUS{
			final  int NORMAL  = 1;
			final int  UN_NORMAL=0;
		}
		
		
	}
	
	
	public interface AilpayCallback{
		String WAIT_BUYER_PAY	="WAIT_BUYER_PAY";//交易创建，等待买家付款
		String TRADE_CLOSED	="TRADE_CLOSED";//未付款交易超时关闭，或支付完成后全额退款
		String TRADE_SUCCESS="TRADE_SUCCESS";//	交易支付成功
		String TRADE_FINISHED="TRADE_FINISHED";//	交易结束，不可退款
	}
	public interface AdminErrorMes{
	
		String UNADMINNAME="请输入姓名";
		String UNPASSWORD="请输入密码";
		String UNPHONE="请输入管理员电话号";
		String UNROLE="请选择管理员权限";
		
	}
	public enum ErrorMessage{
		 STATUS__FALIED (401,"操作失败"),
		 STATUS_USER_NAME_IS_ALREADY_EXISTS(10000,"昵称已存在，不可重复命名"),
		 STATUS_USER_PHONE_IS_ALREADY_EXISTS (10001,"该手机号已被使用，不可重复注册"),
		 STATUS_FALIED_NO_PHONE_IS_ALREADY_EXISTS (10002,"该输入手机号"),
		 STATUS_USER_NAME_AND_PASSWORD_ALREADY_EXISTS(10002,"用户名或密码错误"),
		 STATUS_NO_USPER_ADMIN_ALREADY_EXISTS(10003,"您不是超级管理员，无权限操作"),
		 STATUS_FALIED_UNLOGINNAME_ALREADY_EXISTS(10004,"请正确格式输入昵称"),
		STATUS_FALIED_UNPASSWORD_ALREADY_EXISTS(10005,"请正确格式输入密码"),
		STATUS_FALIED_UID_ALREADY_EXISTS(10006,"ID为空"),
		 STATUS_FALIED_REQUEST_EXISTS(10007,"非法请求"),
		 STATUS_FALIED_EMAIL_ALREADY_EXISTS(10008,"请正确格式输入邮箱"),
		 STATUS_FALIED_VAILDATACODE_EXISTS(10009,"请正确格式输入验证码"),
		STATUS_FALIED_CEMAIL_ALREADY_EXISTS(10010,"邮箱已存在"),
		 STATUS_FALIED_NO_AGE_IS_ALREADY_EXISTS (10011,"该选择性别"),
		 STATUS_FALIED_NO_SEX_IS_ALREADY_EXISTS (10012,"请填填写年龄"),
		 STATUS_FALIED_NO_ADDRESS_IS_ALREADY_EXISTS (10013,"请填填写年龄"),
		STATUS_VALID_FILE_IS_TOO_LONG_ALREADY (672,"上传文件太大,超出限制，请重新选择");

		
		
		
		 ErrorMessage(int code,String value){
            this.code = code;
            this.value = value;
        }
        private String value;
        private int code;

        public String getValue() {
            return value;
        }

        public int getCode() {
            return code;
        }
	
	
	 /*ublic enum OrderStatusEnum{
	        CANCELED(0,"已取消"),
	        NO_PAY(10,"未支付"),
	        PAID(20,"已付款"),
	        SHIPPED(40,"已发货"),
	        ORDER_SUCCESS(50,"订单完成"),
	        ORDER_CLOSE(60,"订单关闭");


	        OrderStatusEnum(int code,String value){
	            this.code = code;
	            this.value = value;
	        }
	        private String value;
	        private int code;

	        public String getValue() {
	            return value;
	        }

	        public int getCode() {
	            return code;
	        }

	        public static OrderStatusEnum codeOf(int code){
	            for(OrderStatusEnum orderStatusEnum : values()){
	                if(orderStatusEnum.getCode() == code){
	                    return orderStatusEnum;
	                }
	            }
	            throw new RuntimeException("么有找到对应的枚举");
	        }
	    }

	 public interface Cart{
	        int CHECKED = 1;//即购物车选中状态
	        int UN_CHECKED = 0;//购物车中未选中状态

	        String LIMIT_NUM_FAIL = "LIMIT_NUM_FAIL";
	        String LIMIT_NUM_SUCCESS = "LIMIT_NUM_SUCCESS";
	    }
	 public enum OrderStatusEum{
		   CANCELED(0,"已取消"),
	        NO_PAY(10,"未支付"),
	        PAID(20,"已付款"),
	        SHIPPED(40,"已发货"),
	        ORDER_SUCCESS(50,"订单完成"),
	        ORDER_CLOSE(60,"订单关闭");
		 private  String desc;
		 private Integer code;
		
		 
		 OrderStatusEum(Integer code,String desc) {
			this.desc=desc;
			this.code=code;
			// TODO Auto-generated constructor stub
		}
		public String getDesc() {
			return desc;
		}
		public void setDesc(String desc) {
			this.desc = desc;
		}
		public Integer getCode() {
			return code;
		}
		public void setCode(Integer code) {
			this.code = code;
		}
        public static String getOrderStatusEumDesc(int code){
        	for (OrderStatusEum OrderStatusEum : values()) {
        		if(OrderStatusEum.code==code){
        			return OrderStatusEum.getDesc();
        		}
        		
			}
        return null;
        
        }
	
		 
		 
	 }
	  public enum PaymentTypeEnum{
	        ONLINE_PAY(1,"在线支付");

	        PaymentTypeEnum(int code,String value){
	            this.code = code;
	            this.value = value;
	        }
	        private String value;
	        private int code;

	        public String getValue() {
	            return value;
	        }

	        public int getCode() {
	            return code;
	        }
	        public static String getPaymentTypeDesc(int code){
	        	for (PaymentTypeEnum PaymentTypeEnum : values()) {
	        		if(PaymentTypeEnum.code==code){
	        			return PaymentTypeEnum.getValue();
	        		}
	        		
				}
	        return null;
	        
	        }
	  }*/
	}
}
