package com.bawei.paotui.utils.jiami;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringIsUtils {
	
	
	/**
	 * 判断输入参数String是否为空
	 * @param str
	 * @return
	 */
	public boolean check_str(String... str){
		
		boolean flg = true;
		
		for(int i=0;i<str.length;i++){
			
			if(str[i]!=null && !str[i].equals("")){
				flg = true;
			}else{
				return false;
			}
		}
		return flg;
	}
	
	
	
	/**
	 * 判断返回参数是否为null  如果null则返回""
	 */
	public String return_str(Object str){
		
		Object ch_str = "";
		
		if(null!=str){
			ch_str = str;
		}else{
			ch_str="";
		}
		
		return ch_str.toString();
		
	}
	
	
	
	/**
	 *手机号正则表达式
	 * @param mobiles
	 * @return
	 */
	public boolean isMobileNO(String mobiles){

		Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(17[7,0-9])|(18[0-3,5-9]))\\d{8}$");

		Matcher m = p.matcher(mobiles);

		System.out.println(m.matches()+"---");

		return m.matches();

}
	

}
