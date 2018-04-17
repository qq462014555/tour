package com.tlongx.util;
import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;

import com.tls.sigcheck.tls_sigcheck;


// ç±äºçæ sig åæ ¡éª sig çæ¥å£ä½¿ç¨æ¹æ³ç±»ä¼¼ï¼ææ­¤å¤åªæ¯æ¼ç¤ºäºçæ sig çæ¥å£è°ç¨

// ä½¿ç¨çç¼è¯å½ä»¤æ¯
// javac -encoding utf-8 Demo.java
// ä½¿ç¨çè¿è¡å½ä»¤æ¯
// java Demo

public class SigUtil { 

  //  public static void getSigUtil() throws Exception {
	 public static void main(String[] args) throws IOException, URISyntaxException {
	
        tls_sigcheck demo = new tls_sigcheck();
        
        // ä½¿ç¨åè¯·ä¿®æ¹å¨æåºçå è½½è·¯å¾
        // demo.loadJniLib("D:\\src\\oicq64\\tinyid\\tls_sig_api\\windows\\64\\lib\\jni\\jnisigcheck.dll");
   
    
        String loadJniLib = SigUtil.class.getResource("/tengxun/dll/jnisigcheck.dll").toURI().getPath() ;  
     
      demo.loadJniLib(loadJniLib);
      String priKeyFilePath = SigUtil.class.getResource("/tengxun/key/ec_key.pem").toURI().getPath() ;  
      String publicKeyFilePath = SigUtil.class.getResource("/tengxun/key/public.pem").toURI().getPath() ;  
        File priKeyFile = new File(priKeyFilePath);
        StringBuilder strBuilder = new StringBuilder();
        String s = "";
        
        BufferedReader br = new BufferedReader(new FileReader(priKeyFile));
        while ((s = br.readLine()) != null) {
            strBuilder.append(s + '\n');
        }
        br.close();
        String priKey = strBuilder.toString();        
		int ret = demo.tls_gen_signature_ex2(PropertiesUtil.getProperty("SdkAppId"),PropertiesUtil.getProperty("admin"), priKey);
        
        if (0 != ret) {
            System.out.println("ret " + ret + " " + demo.getErrMsg());
        }
        else
        {
            System.out.println("sig:\n" + demo.getSig());
        }      

        File pubKeyFile = new File(publicKeyFilePath);
        br = new BufferedReader(new FileReader(pubKeyFile));
		strBuilder.setLength(0);
        while ((s = br.readLine()) != null) {
            strBuilder.append(s + '\n');
        }
        br.close();
        String pubKey = strBuilder.toString();        
		ret = demo.tls_check_signature_ex2(demo.getSig(), pubKey,PropertiesUtil.getProperty("SdkAppId"),PropertiesUtil.getProperty("admin"));
        if (0 != ret) {
            System.out.println("ret " + ret + " " + demo.getErrMsg());
        }
        else
        {
            System.out.println("--\nverify ok -- expire time " + demo.getExpireTime() + " -- init time " + demo.getInitTime());
        }      
    }
}
