package com.tlongx.util;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.imageio.ImageIO;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

public class MUtil {
    
	/**
	 * 日期转换: Date To String
	 * @param date
	 * @param dateformat
	 * @return
	 */
	public static String formDateFormat(Date date, String dateformat){
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateformat);
		simpleDateFormat.format(date);
		return simpleDateFormat.format(date);
	}
	
    public static String strObject(Object object) {
	    String result = "";
	    if ((object != null) && (!"".equals(object))) {
	      result = object.toString();
	    }
	    return result;
	  }

	/**
	 * 日期转换: String To Date
	 * @param date
	 * @param dateformat
	 * @return
	 * @throws ParseException 
	 */
	public static Date formDateFormat(String date, String dateformat) throws ParseException{
		 SimpleDateFormat dateFormat = new SimpleDateFormat(dateformat); 
		 Date d = dateFormat.parse(date); 
		return d;
	}
	/**
	 * 32位随机不重复字符串id
	 * @return
	 */
	public static String uuid(){
		  String uuid = UUID.randomUUID().toString().trim().replaceAll("-", "");    
		  return uuid;
	}
	
	 public static String getUTF8XMLString(String xml) {  
		    // A StringBuffer Object  
		    StringBuffer sb = new StringBuffer();  
		    sb.append(xml);  
		    String xmString = "";  
		    String xmlUTF8="";  
		    try {  
		    xmString = new String(sb.toString().getBytes("utf-8"));  
		    xmlUTF8 = URLEncoder.encode(xmString, "gb2312");  
		    System.out.println("utf-8 编码：" + xmlUTF8) ;  
		    } catch (UnsupportedEncodingException e) {  
		    // TODO Auto-generated catch block  
		    e.printStackTrace();  
		    }  
		    // return to String Formed  
		    return xmlUTF8;  
		    }  
	
	
	
	/**
	 * map转xml
	 * @return
	 */
	 public static String parseXML(Map<String, Object> map) {  
	        StringBuffer sb = new StringBuffer();  
	        sb.append("<xml>");  
	        Set es = map.entrySet();  
	        Iterator it = es.iterator();  
	        while(it.hasNext()) {  
	             Map.Entry entry = (Map.Entry)it.next();  
	             String k = (String)entry.getKey();  
	             String v = (String)entry.getValue();  
	             if(null != v && !"".equals(v)) {  
	                 sb.append("<" + k +">" + map.get(k) + "</" + k + ">\n");  
	             }  
	         }  
	         sb.append("</xml>");  
	         return sb.toString();  
	     }
	
	 
	 
	 /**
	  * 图片转二进制
	  */
	 public static String getImageBinary(String filePath){     
		    BASE64Encoder encoder = new sun.misc.BASE64Encoder();  
	        File f = new File(filePath);   
	        BufferedImage bi;      
	        try {      
	            bi = ImageIO.read(f);      
	            ByteArrayOutputStream baos = new ByteArrayOutputStream();      
	            ImageIO.write(bi, "jpg", baos);      
	            byte[] bytes = baos.toByteArray();      
	            return encoder.encodeBuffer(bytes).trim();      
	        } catch (IOException e) {      
	            e.printStackTrace();      
	        }      
	        return null;      
	    }      
	
	 
	 /**
	  * 二进制转图片
	  */
	 public static void base64StringToImage(String base64String,String filePath){      
		    BASE64Decoder decoder = new sun.misc.BASE64Decoder(); 
	        try {      
	            byte[] bytes1 = decoder.decodeBuffer(base64String);      
	            ByteArrayInputStream bais = new ByteArrayInputStream(bytes1);      
	            BufferedImage bi1 =ImageIO.read(bais);      
	            File w2 = new File(filePath);//可以是jpg,png,gif格式      
	            ImageIO.write(bi1, "jpg", w2);//不管输出什么格式图片，此处不需改动      
	        } catch (IOException e) {      
	            e.printStackTrace();      
	        }      
	    }      
	 
	 /**
	  * 合成多张图片
	  * @param x
	  * @param y
	  * @param qrCodeImg
	  * @param faceImg
	  * @return
	  */
	 public static BufferedImage createPicTwo2(String qrCodeImg,String qrCodeBackgound)
	 {
		 BufferedImage ImageNew = null;
	     try
	     {
	       zoomImage(qrCodeImg, qrCodeImg ,120,120);
	       //读取第一张图片
	       File fileOne = new File(qrCodeImg);
	       BufferedImage ImageOne = ImageIO.read(fileOne);
	       int width = ImageOne.getWidth();//图片宽度
	       int height = ImageOne.getHeight();//图片高度
	       //从图片中读取RGB
	       int[] ImageArrayOne = new int[width*height];
	       ImageArrayOne = ImageOne.getRGB(0,0,width,height,ImageArrayOne,0,width);
	       
	       
	       //对第二张图片做相同的处理
	       File fileTwo = new File(qrCodeBackgound);
	       BufferedImage ImageTwo = ImageIO.read(fileTwo);
	       int widthTwo = ImageTwo.getWidth();//图片宽度
	       int heightTwo = ImageTwo.getHeight();//图片高度
	       if(widthTwo>320&&heightTwo>568){
	    	   zoomImage(qrCodeBackgound, qrCodeBackgound ,320,568);
	       }
	       int[] ImageArrayTwo = new int[widthTwo*heightTwo];
	       ImageArrayTwo = ImageTwo.getRGB(0,0,widthTwo,heightTwo,ImageArrayTwo,0,widthTwo);
	       
	       //生成新图片
	       ImageNew = new BufferedImage(widthTwo,heightTwo,BufferedImage.TYPE_INT_RGB);
	       ImageNew.setRGB(100,310,width,height,ImageArrayOne,10,width);//设置左半部分的RGB
	       ImageNew.setRGB(0,0,widthTwo,heightTwo,ImageArrayTwo,0,widthTwo);//设置右半部分的RGB
	       ImageNew.setRGB(0,0,widthTwo,heightTwo,ImageArrayTwo,0,widthTwo);//设置右半部分的RGB
	       try {
	    	    File outFile = new File(qrCodeImg);
				ImageIO.write(ImageNew, "png", outFile);//写图片
			} catch (IOException e) {
				e.printStackTrace();
			}
	     }
	     catch(Exception e)
	     {
	       e.printStackTrace();
	     }
	     
	     return ImageNew;
	 }
	 
	 /**
	  * 合成2张图片，附加图在背景图中央
	  * @param qrCodeImg
	  * @param qrCodeBackgound
	  * @throws IOException
	  */
	 public static void mergeBothImageCenter(String qrCodeImg,String qrCodeBackgound) throws IOException{
	        InputStream is= null;
	        InputStream is2= null;
	        OutputStream os = null;
	        try{
	        	zoomImage(qrCodeImg, qrCodeImg ,120,120);
	            is=new FileInputStream(qrCodeBackgound);
	            is2=new FileInputStream(qrCodeImg);
	            BufferedImage image=ImageIO.read(is);
	            BufferedImage image2=ImageIO.read(is2);
	            Graphics g=image.getGraphics();
	            g.drawImage(image2,image.getWidth()/2-image2.getWidth()/2,image.getHeight()/2-image2.getHeight()/2,null);
	            os = new FileOutputStream("C:\\a.jpg");
	            JPEGImageEncoder enc=JPEGCodec.createJPEGEncoder(os);
	            enc.encode(image);
	        }catch(Exception e){
	            e.printStackTrace();
	        }finally{
	            if(os != null){
	                os.close();
	            }
	            if(is2 != null){
	                is2.close();
	            }
	            if(is != null){
	                is.close();
	            }
	        }
	    }
	 
	 /**
	  * 下载图片
	  * @param urlString
	  * @param filePath
	  * @return
	  */
	 public static Boolean downloadFile(String urlString, String filePath){
         // 构造URL
         URL url;
          try {
              url = new URL(urlString);
               // 打开连接
              URLConnection con;
              try {
                   con = url.openConnection();
                   // 输入流
                  InputStream is = con.getInputStream();
                  // 1K的数据缓冲
                  byte[] bs = new byte[1024];
                  // 读取到的数据长度
                  int len;
                  // 输出的文件流
                  OutputStream os = new FileOutputStream(filePath);
                  // 开始读取
                  while ((len = is.read(bs)) != -1) {
                   os.write(bs, 0, len);
                  }
                  // 完毕，关闭所有链接
                  os.close();
                  is.close();
                  return true;
              } catch (IOException e) {
                   e.printStackTrace();
                   return false;
              }
             
          } catch (MalformedURLException e) {
              e.printStackTrace();
              return false;
          }
       
     }  
	 
	 
	    /*
	     * 图片缩放,w，h为缩放的目标宽度和高度
	     * src为源文件目录，dest为缩放后保存目录
	     */
	    public static void zoomImage(String src,String dest,int w,int h) throws Exception {
	        
	        double wr=0,hr=0;
	        File srcFile = new File(src);
	        File destFile = new File(dest);

	        BufferedImage bufImg = ImageIO.read(srcFile); //读取图片
	        Image Itemp = bufImg.getScaledInstance(w, h, bufImg.SCALE_SMOOTH);//设置缩放目标图片模板
	        
	        wr=w*1.0/bufImg.getWidth();     //获取缩放比例
	        hr=h*1.0 / bufImg.getHeight();

	        AffineTransformOp ato = new AffineTransformOp(AffineTransform.getScaleInstance(wr, hr), null);
	        Itemp = ato.filter(bufImg, null);
	        try {
	            ImageIO.write((BufferedImage) Itemp,dest.substring(dest.lastIndexOf(".")+1), destFile); //写入缩减后的图片
	        } catch (Exception ex) {
	            ex.printStackTrace();
	        }
	    }
	    
	    
	    /*
	     * 图片按比率缩放
	     * size为文件大小
	     */
	    public static void zoomImage(String src,String dest,Integer size) throws Exception {
	        File srcFile = new File(src);
	        File destFile = new File(dest);
	        
	        long fileSize = srcFile.length();
	        if(fileSize < size * 1024)   //文件大于size k时，才进行缩放
	            return;
	        
	        Double rate = (size * 1024 * 0.5) / fileSize; // 获取长宽缩放比例
	        
	        BufferedImage bufImg = ImageIO.read(srcFile);
	        Image Itemp = bufImg.getScaledInstance(bufImg.getWidth(), bufImg.getHeight(), bufImg.SCALE_SMOOTH);
	            
	        AffineTransformOp ato = new AffineTransformOp(AffineTransform.getScaleInstance(rate, rate), null);
	        Itemp = ato.filter(bufImg, null);
	        try {
	            ImageIO.write((BufferedImage) Itemp,dest.substring(dest.lastIndexOf(".")+1), destFile);
	        } catch (Exception ex) {
	            ex.printStackTrace();
	        }
	    }
	 
	/**
	 * 生成随机6位数密码  有数字大小写字母组成
	 */
	public static String uupwd(){ 
	   char[] ss = new char[6];
		  int i=0;
		 while(i<6) {
		     int f = (int) (Math.random()*3);
		     if(f==0)  
		      ss[i] = (char) ('A'+Math.random()*26);
		     else if(f==1)  
		      ss[i] = (char) ('a'+Math.random()*26);
		     else 
		      ss[i] = (char) ('0'+Math.random()*10);    
		     i++;
		  }
		 String is = new String(ss);
		 return is; 
	}
	
	/**
	 * json 转换   object to jsonString
	 * @param object
	 * @return
	 */
	public static String objectToJsonString(Object object){
		Gson gson = new Gson();
		String jsonString = gson.toJson(object);
		return jsonString;
	}
	
	
	/**
	 * 日期转换
	 * @param time 时间毫秒数
	 */
	public static String  dateFormatForLongTime(String time){
		Date date = new Date();
		date.setTime(Long.valueOf(time));
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		simpleDateFormat.format(date); 
		return simpleDateFormat.format(date);
	}
	
	/**
	 * 生成随机6位数字的验证码
	 */
	public static String getcode(){
		String validCode = "";
		Random random = new Random(); 
		for(int i=0;i<6;i++){
			validCode += random.nextInt(10)+"";
		}  
		return validCode;
	}
	
	/**
	 * 获取当前日期是这一年的第几周
	 */
	public static int getWeekOfYear(Date date){ 
		GregorianCalendar g = new GregorianCalendar(); 
			g.setTime(date); 
	    return g.get(Calendar.WEEK_OF_YEAR);
	}
	
	public static double Distance(double long1, double lat1, double long2,  
	        double lat2) {  
	    double a, b, R;  
	    R = 6378137; // 地球半径  
	    lat1 = lat1 * Math.PI / 180.0;  
	    lat2 = lat2 * Math.PI / 180.0;  
	    a = lat1 - lat2;  
	    b = (long1 - long2) * Math.PI / 180.0;  
	    double d;  
	    double sa2, sb2;  
	    sa2 = Math.sin(a / 2.0);  
	    sb2 = Math.sin(b / 2.0);  
	    d = 2  
	            * R  
	            * Math.asin(Math.sqrt(sa2 * sa2 + Math.cos(lat1)  
	                    * Math.cos(lat2) * sb2 * sb2));  
	    return d;  
	}  
	
	public static boolean toJudgeInput(Object object, int[] types)
	  {
		Pattern pattern = null;
		Matcher matcher = null;
	    boolean result = true;

	    if (types.length == 0)
	      types = new int[1];
	    int i;
	    if (Arrays.binarySearch(types, 0) < 0) {
	      int[] newTypes = new int[types.length + 1];
	      newTypes[0] = 0;
	      for (i = 0; i < types.length; ) {
	        newTypes[(i + 1)] = types[(i++)];
	      }
	      types = newTypes;
	    }

	    for (int type : types) {
	      switch (type)
	      {
	      case 0:
	    	//String类型
	        if ((object != null) && (!"".equals(object)))
	          result = true;
	        else {
	          result = false;
	        }
	        break;
	      case 1://整型
	        try
	        {
	          Integer.parseInt(object.toString());
	          result = true;
	        } catch (Exception ex) {
	          result = false;
	        } 
	        break;
	      case 2:
	    	//手机号
	        pattern = Pattern.compile("^((13[0-9])|(17[0-9])|(14[0-9])|(15[^4,\\D])|(18[0-9]))\\d{8}$");
	        matcher = pattern.matcher(object.toString());
	        result = matcher.matches();
	        break;
	      case 3: 
		    //邮箱
	         String check = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
	         pattern = Pattern.compile(check);
	         matcher = pattern.matcher(object.toString());
	        result = matcher.matches();
	        break;
	      case 4:
	        try
	        {
	          parseDateAuto(object.toString());
	          result = true;
	        } catch (ParseException ex) {
	          result = false;
	        }
              break;
	      case 5:
	        pattern = Pattern.compile("^[һ-�]*$");
	        matcher = pattern.matcher(object.toString());
	        result = matcher.matches();
	        break;
	      case 6:
	        pattern = Pattern.compile("[^\\d\\:\\!\"\\#\\$\\%\\&\\'\\(\\)\\*\\+\\,\\-\\.\\/\\:\\;\\<\\=\\>\\?\\@\\[\\\\\\]\\^\\_\\`\\{\\|\\}\\~��������]*");
	        matcher = pattern.matcher(object.toString());
	        result = matcher.matches();
	        break;
	      case 7:
	        Pattern idNumPattern = Pattern.compile("(\\d{14}[0-9a-zA-Z])|(\\d{17}[0-9a-zA-Z])");

	        Matcher idNumMatcher = idNumPattern.matcher(object.toString());

	        result = idNumMatcher.matches();
	        break;
	      case 8:
	        pattern = Pattern.compile("^[-\\+]?[.\\d]*$");
	        matcher = pattern.matcher(object.toString());
	        result = matcher.matches();
	        break;
	      case 9:
	        pattern = Pattern.compile("^[a-z]+$");
	        matcher = pattern.matcher(object.toString());
	        result = matcher.matches();
	        break;
	      case 10:
	        pattern = Pattern.compile("^(?!_)(?!.*?_$)[a-zA-Z0-9_]+$");
	        matcher = pattern.matcher(object.toString());
	        result = matcher.matches();
	        break;
	      case 11:
	        pattern = Pattern.compile("^(((13[0-9])|(17[0-9])|(14[0-9])|(15[0-9])|(18[0-9]))\\d{8})|^((\\d{7,8})|(\\d{4}|\\d{3})-(\\d{7,8})|(\\d{4}|\\d{3})-(\\d{7,8})-(\\d{4}|\\d{3}|\\d{2}|\\d{1})|(\\d{7,8})-(\\d{4}|\\d{3}|\\d{2}|\\d{1}))$");
	        matcher = pattern.matcher(object.toString());
	        result = matcher.matches();
	        break;
	      case 12:
	        pattern = Pattern.compile("^\\d{1,12}(?:\\.\\d{1,4})?$");
	        matcher = pattern.matcher(object.toString());
	        result = matcher.matches();
	        break;
	      case 13:
	        pattern = Pattern.compile("^(?![0-9])[a-zA-Z0-9]+$");
	        matcher = pattern.matcher(object.toString());
	        result = matcher.matches();
	        break;
	      case 14:
	        result = object.toString().indexOf("@") != -1;
	        break;
	      case 15:
	    	//以大小写字母开头，以大小写字母数字结尾
	        pattern = Pattern.compile("^[a-zA-Z][a-zA-Z0-9]+$");
	        matcher = pattern.matcher(object.toString());
	        result = matcher.matches();
	        break;
	      case 16:
	        pattern = Pattern.compile("(\\d{1,10})|(\\d{1,10}\\.\\d{1,2})");
	        matcher = pattern.matcher(object.toString());
	        result = matcher.matches();
	        break;
	      case 17:
	        pattern = Pattern.compile("(D|d){1}-[0-9]{4}-[0-9]{6}");
	        matcher = pattern.matcher(object.toString());
	        result = matcher.matches();
	        break;
	      case 18://long
	        try
	        {
	          Long.parseLong(object.toString());
	          result = true;
	        } catch (Exception ex) {
	          result = false;
	        } 
	        break;
	      }
	     

	      if (!result)
	      {
	        break;
	      }
	    }
	    return result;
	  }
	
	  public static Date parseDateAuto(String dateStr)
			    throws ParseException
			  {
			    SimpleDateFormat dateFormat = new SimpleDateFormat();
			    Date date = null;
			    for (String dsStr : dateFormatStrList) {
			      dateFormat.applyPattern(dsStr);
			      try {
			        date = dateFormat.parse(dateStr);
			      }
			      catch (ParseException localParseException)
			      {
			      }
			    }
			    if (date == null) {
			      throw new ParseException("日期转换错误！", 0);
			    }
			    return date;
			  }
	  
	  
	  public static List<String> dateFormatStrList = null;
	  static
	  {
	    if (dateFormatStrList == null) {
	      dateFormatStrList = new ArrayList<String>();
	    }
		    dateFormatStrList.add("yyyy-MM-dd HH:mm:ss z");
		    dateFormatStrList.add("yyyy-MM-dd HH:mm:ss");
		    dateFormatStrList.add("yyyy-MM-dd");
		    dateFormatStrList.add("MM-dd-yyyy HH:mm:ss z");
		    dateFormatStrList.add("MM-dd-yyyy HH:mm:ss");
		    dateFormatStrList.add("MM-dd-yyyy");
		    dateFormatStrList.add("yyyy/MM/dd HH:mm:ss z");
		    dateFormatStrList.add("yyyy/MM/dd HH:mm:ss");
		    dateFormatStrList.add("yyyy/MM/dd");
		    dateFormatStrList.add("yyyy.MM.dd HH:mm:ss z");
		    dateFormatStrList.add("yyyy.MM.dd HH:mm:ss");
		    dateFormatStrList.add("yyyy.MM.dd");
		    dateFormatStrList.add("yyyy.MM.dd G 'at' HH:mm:ss z");
		    dateFormatStrList.add("yyyy.MM.dd");
		    dateFormatStrList.add("yyyyMMddHHmmssz");
		    dateFormatStrList.add("yyyyMMddHHmmss");
		    dateFormatStrList.add("yyyyMMdd");
		    dateFormatStrList.add("yyyy-MM-dd KK:mm:ss a");
		    dateFormatStrList.add("yyyy-MM-dd KK:mm:ss");
		    dateFormatStrList.add("yyyy-MM-dd");
		    dateFormatStrList.add("yyyy年MM月dd日  HH时mm分ss秒");
		    dateFormatStrList.add("yyyy年MM月dd日");
	    }
	  
	  public static Map<String, Object> jsonToMap(String json)
			    throws JSONException
			  {
			    Map<String,Object> retMap = new HashMap<String,Object>();

			    if (json != null) {
			      retMap = toMap(JSONObject.parseObject(json));
			    }
			    return retMap;
			  }

	  public static Map<String, Object> toMap(JSONObject object) throws JSONException {
	    Map<String,Object> map = new HashMap<String,Object>(); 
	    Set<String> keysItr = object.keySet();
	    for (String str : keysItr) {
	      Object value = object.get(str);
	      if ((value instanceof JSONArray))
	        value = toList((JSONArray)value);
	      else if ((value instanceof JSONObject)) {
	        value = toMap((JSONObject)value);
	      }
	      map.put(str, value);
	    }
	    return map;
	  }

	  public static List<Object> toList(JSONArray array) throws JSONException {
		    List<Object> list = new ArrayList<Object>();
		    for (int i = 0; i < array.size(); i++) {
		      Object value = array.get(i);
		      if ((value instanceof JSONArray))
		        value = toList((JSONArray)value);
		      else if ((value instanceof JSONObject)) {
		        value = toMap((JSONObject)value);
		      }
		      list.add(value);
		    }
		    return list;
		  }
	  
	  public static String paseJson(String jsr, String[] layerStr) {
		    String str = "json层字符串找不到该层字符串";
		    JSONObject jsonObject = JSONObject.parseObject(jsr);

		    for (int i = 0; i < layerStr.length; i++) {
		      if (jsonObject.containsKey(layerStr[i])) {
		        str = jsonObject.getString(layerStr[i]);
		        if (i < layerStr.length - 1) {
		          jsonObject = JSONObject.parseObject(str);
		        }
		      }
		    }
		    return str;
		  }
	  
	  public static String random(int n)
	  {
	    if ((n < 1) || (n > 10)) {
	      throw new IllegalArgumentException("cannot random " + n + " bit number");
	    }
	    Random ran = new Random();
	    if (n == 1) {
	      return String.valueOf(ran.nextInt(10));
	    }
	    int bitField = 0;
	    char[] chs = new char[n];
	    for (int i = 0; i < n; i++) { int k;
	      do k = ran.nextInt(10);
	      while ((bitField & 1 << k) != 0);
	      bitField |= 1 << k;
	      chs[i] = (char)(k + 48);
	    }

	    String returnStr = new String(chs);
	    if ("0".equals(returnStr.substring(0, 1))) {
	      returnStr = random(n);
	    }
	    return returnStr;
	  }
}
