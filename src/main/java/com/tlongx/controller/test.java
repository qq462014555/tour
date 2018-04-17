package com.tlongx.controller;

import org.apache.commons.lang3.StringUtils;

import com.tlongx.common.ServiceResponse;
import com.tlongx.common.TokenCache;

public class test {
public static void main(String[] args) {
		String email="462014555@qq.com";
		//输入的验证码跟内存上存储的验证码比较
		System.out.println(TokenCache.getLocalCacheEmailKey(TokenCache.TOKEN_VALIDATACODE+email));
	if(StringUtils.equals(TokenCache.getLocalCacheEmailKey(TokenCache.TOKEN_VALIDATACODE+email),"91211")){
		System.out.println("111");
		}
		System.out.println("123123");

}
}
