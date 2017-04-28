package com.bawei.paotui.home.bean;

public class UserBean {
	
	private String code;
	
	private String message;
	
	public String getMessage() {
		return message;
	}


	public void setMessage(String message) {
		this.message = message;
	}

	public UserInfo userinfo = new UserInfo();
	
	
	@Override
	public String toString() {
		return "UserBean [code=" + code + ", message=" + message
				+ ", userinfo=" + userinfo + "]";
	}


	public String getCode() {
		return code;
	}


	public void setCode(String code) {
		this.code = code;
	}


	public UserInfo getUserinfo() {
		return userinfo;
	}


	public void setUserinfo(UserInfo userinfo) {
		this.userinfo = userinfo;
	}
}
