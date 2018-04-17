package com.tlongx.common;

import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
public class UserListener implements HttpSessionAttributeListener {
 //用户登录身份证
    private String USERNAME;
    private UserList u1 = UserList.getInstance();   
   
    //判断用户是否存在
    public boolean IsExist(String sfz)throws Exception
    {
        try
        {
           
          return u1.IsExist(sfz);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            return false;
        }
    }
  
  
 public String getUSERNAME() {
  return USERNAME;
 }
 public void setUSERNAME(String username) {
  USERNAME = username;
 }
 public void attributeAdded(HttpSessionBindingEvent event) {
  try{
  if("USERNAME".equals(event.getName())){
   u1.addUser((String)event.getValue());
  }
  }catch(Exception e){
   e.printStackTrace();
  }
  
 }
 public void attributeRemoved(HttpSessionBindingEvent event) {
  try{
  if("USERNAME".equals(event.getName())){
   u1.RemoveUser((String)event.getValue());
  }
  }catch(Exception e){
   e.printStackTrace();
  }
  
 }
 public void attributeReplaced(HttpSessionBindingEvent arg0) {
  // TODO Auto-generated method stub
  
 }
}