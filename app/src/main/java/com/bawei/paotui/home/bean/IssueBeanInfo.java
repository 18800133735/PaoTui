package com.bawei.paotui.home.bean;

public class IssueBeanInfo {

	private String real_name;
	private String nick_name;
	
	private String phone_num;
	private String attestation_type;
	private String user_head;

	private String assignmentId;
	private String assignment_content;// 任务内容
	private String start_time;// 任务开始时间
	private String end_time;// 任务结束时间

	String consignee_longitude;// 收货人经度
	String consignee_latitude;// 收货人纬度
	
	private String start_address;// 任务开始地址
	private String issue_peopleID;// 发布人员id
	private String assignment_money;// 任务金额
	private String assignment_distance;// 任务距离
	private String accept_peopleID;// 接受人员id
	
	
	private String assignment_type;// 任务类别 1当前为帮我送

	private String assignment_state;// 任务状态
	private String goods_type;// 物品类别

	private String consigneeID;// 收货人id
	private String consignee_name;// 收货人姓名
	private String consignee_phone;// 收货人电话
	private String consignee_address;// 收货人地址
	//陈浩添加
	private String assignment_district;//任务所在区域
	//结束
	
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

	

	public String getConsignee_longitude() {
		return consignee_longitude;
	}

	public void setConsignee_longitude(String consignee_longitude) {
		this.consignee_longitude = consignee_longitude;
	}

	public String getConsignee_latitude() {
		return consignee_latitude;
	}

	public void setConsignee_latitude(String consignee_latitude) {
		this.consignee_latitude = consignee_latitude;
	}

	

	public String getAccept_peopleID() {
		return accept_peopleID;
	}

	public void setAccept_peopleID(String accept_peopleID) {
		this.accept_peopleID = accept_peopleID;
	}

	

	public String getAssignmentId() {
		return assignmentId;
	}

	public void setAssignmentId(String assignmentId) {
		this.assignmentId = assignmentId;
	}

	public String getAssignment_content() {
		return assignment_content;
	}

	public void setAssignment_content(String assignment_content) {
		this.assignment_content = assignment_content;
	}

	public String getStart_time() {
		return start_time;
	}

	public void setStart_time(String start_time) {
		this.start_time = start_time;
	}

	public String getEnd_time() {
		return end_time;
	}

	public void setEnd_time(String end_time) {
		this.end_time = end_time;
	}

	public String getStart_address() {
		return start_address;
	}

	public void setStart_address(String start_address) {
		this.start_address = start_address;
	}

	public String getIssue_peopleID() {
		return issue_peopleID;
	}

	public void setIssue_peopleID(String issue_peopleID) {
		this.issue_peopleID = issue_peopleID;
	}

	public String getAssignment_money() {
		return assignment_money;
	}

	public void setAssignment_money(String assignment_money) {
		this.assignment_money = assignment_money;
	}

	public String getAssignment_distance() {
		return assignment_distance;
	}

	public void setAssignment_distance(String assignment_distance) {
		this.assignment_distance = assignment_distance;
	}

	public String getAssignment_type() {
		return assignment_type;
	}

	public void setAssignment_type(String assignment_type) {
		this.assignment_type = assignment_type;
	}

	public String getAssignment_state() {
		return assignment_state;
	}

	public void setAssignment_state(String assignment_state) {
		this.assignment_state = assignment_state;
	}

	public String getGoods_type() {
		return goods_type;
	}

	public void setGoods_type(String goods_type) {
		this.goods_type = goods_type;
	}

	public String getConsigneeID() {
		return consigneeID;
	}

	public void setConsigneeID(String consigneeID) {
		this.consigneeID = consigneeID;
	}

	public String getConsignee_name() {
		return consignee_name;
	}

	public void setConsignee_name(String consignee_name) {
		this.consignee_name = consignee_name;
	}

	public String getConsignee_phone() {
		return consignee_phone;
	}

	public void setConsignee_phone(String consignee_phone) {
		this.consignee_phone = consignee_phone;
	}

	public String getConsignee_address() {
		return consignee_address;
	}

	public void setConsignee_address(String consignee_address) {
		this.consignee_address = consignee_address;
	}

	public String getAssignment_district() {
		return assignment_district;
	}

	public void setAssignment_district(String assignmentDistrict) {
		assignment_district = assignmentDistrict;
	}

	
}
