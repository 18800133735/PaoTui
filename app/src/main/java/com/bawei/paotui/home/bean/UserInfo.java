package com.bawei.paotui.home.bean;

public class UserInfo {

	
	private String id_card;//身份证号
	private String real_name;//真实姓名
	private String nick_name;//用户昵称
	private String userId;//用户id
	private String phone_num;//手机号
	private String attestation_type;//认证类型
	private String user_head;//用户头像
	private String addressId;//地址id
	
	private String token;//
	private String rongToken;//融云的token
	
	public String getRongToken() {
		return rongToken;
	}
	
	public void setRongToken(String rongToken) {
		this.rongToken = rongToken;
	}
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getAddressId() {
		return addressId;
	}
	public void setAddressId(String addressId) {
		this.addressId = addressId;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getId_card() {
		return id_card;
	}
	public void setId_card(String id_card) {
		this.id_card = id_card;
	}
	public String getReal_name() {
		return real_name;
	}
	public void setReal_name(String real_name) {
		this.real_name = real_name;
	}
	public String getNick_name() {
		return nick_name;
	}
	public void setNick_name(String nick_name) {
		this.nick_name = nick_name;
	}
	
	public String getPhone_num() {
		return phone_num;
	}
	public void setPhone_num(String phone_num) {
		this.phone_num = phone_num;
	}
	public String getAttestation_type() {
		return attestation_type;
	}
	public void setAttestation_type(String attestation_type) {
		this.attestation_type = attestation_type;
	}
	public String getUser_head() {
		return user_head;
	}
	public void setUser_head(String user_head) {
		this.user_head = user_head;
	}
	
	
	
	
	
	

}
