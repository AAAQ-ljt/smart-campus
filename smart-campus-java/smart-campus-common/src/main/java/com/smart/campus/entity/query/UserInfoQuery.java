package com.smart.campus.entity.query;

import java.util.Date;


/**
 * 用户表（管理员/教师/学生统一存储）参数
 */
public class UserInfoQuery extends BaseParam {


	/**
	 * 
	 */
	private Integer userId;

	/**
	 * 用户编号/工号/学号（登录账号，唯一）
	 */
	private String userNo;

	private String userNoFuzzy;

	/**
	 * 密码（加密存储）
	 */
	private String password;

	private String passwordFuzzy;

	/**
	 * 真实姓名
	 */
	private String realName;

	private String realNameFuzzy;

	/**
	 * 角色类型：admin-管理员, teacher-教师, student-学生
	 */
	private String roleType;

	private String roleTypeFuzzy;

	/**
	 * 学生所属班级ID,老师所属班级多个ID用逗号隔开
	 */
	private String classId;

	private String classIdFuzzy;

	/**
	 * 手机号
	 */
	private String phone;

	private String phoneFuzzy;

	/**
	 * 邮箱
	 */
	private String email;

	private String emailFuzzy;

	/**
	 * 头像URL
	 */
	private String avatar;

	private String avatarFuzzy;

	/**
	 * 性别：M-男, F-女
	 */
	private String gender;

	private String genderFuzzy;

	/**
	 * 状态：1-启用, 0-禁用
	 */
	private Integer status;

	/**
	 * 最后登录时间
	 */
	private String lastLoginTime;

	private String lastLoginTimeStart;

	private String lastLoginTimeEnd;

	/**
	 * 创建时间
	 */
	private String createTime;

	private String createTimeStart;

	private String createTimeEnd;


	public void setUserId(Integer userId){
		this.userId = userId;
	}

	public Integer getUserId(){
		return this.userId;
	}

	public void setUserNo(String userNo){
		this.userNo = userNo;
	}

	public String getUserNo(){
		return this.userNo;
	}

	public void setUserNoFuzzy(String userNoFuzzy){
		this.userNoFuzzy = userNoFuzzy;
	}

	public String getUserNoFuzzy(){
		return this.userNoFuzzy;
	}

	public void setPassword(String password){
		this.password = password;
	}

	public String getPassword(){
		return this.password;
	}

	public void setPasswordFuzzy(String passwordFuzzy){
		this.passwordFuzzy = passwordFuzzy;
	}

	public String getPasswordFuzzy(){
		return this.passwordFuzzy;
	}

	public void setRealName(String realName){
		this.realName = realName;
	}

	public String getRealName(){
		return this.realName;
	}

	public void setRealNameFuzzy(String realNameFuzzy){
		this.realNameFuzzy = realNameFuzzy;
	}

	public String getRealNameFuzzy(){
		return this.realNameFuzzy;
	}

	public void setRoleType(String roleType){
		this.roleType = roleType;
	}

	public String getRoleType(){
		return this.roleType;
	}

	public void setRoleTypeFuzzy(String roleTypeFuzzy){
		this.roleTypeFuzzy = roleTypeFuzzy;
	}

	public String getRoleTypeFuzzy(){
		return this.roleTypeFuzzy;
	}

	public void setClassId(String classId){
		this.classId = classId;
	}

	public String getClassId(){
		return this.classId;
	}

	public void setClassIdFuzzy(String classIdFuzzy){
		this.classIdFuzzy = classIdFuzzy;
	}

	public String getClassIdFuzzy(){
		return this.classIdFuzzy;
	}

	public void setPhone(String phone){
		this.phone = phone;
	}

	public String getPhone(){
		return this.phone;
	}

	public void setPhoneFuzzy(String phoneFuzzy){
		this.phoneFuzzy = phoneFuzzy;
	}

	public String getPhoneFuzzy(){
		return this.phoneFuzzy;
	}

	public void setEmail(String email){
		this.email = email;
	}

	public String getEmail(){
		return this.email;
	}

	public void setEmailFuzzy(String emailFuzzy){
		this.emailFuzzy = emailFuzzy;
	}

	public String getEmailFuzzy(){
		return this.emailFuzzy;
	}

	public void setAvatar(String avatar){
		this.avatar = avatar;
	}

	public String getAvatar(){
		return this.avatar;
	}

	public void setAvatarFuzzy(String avatarFuzzy){
		this.avatarFuzzy = avatarFuzzy;
	}

	public String getAvatarFuzzy(){
		return this.avatarFuzzy;
	}

	public void setGender(String gender){
		this.gender = gender;
	}

	public String getGender(){
		return this.gender;
	}

	public void setGenderFuzzy(String genderFuzzy){
		this.genderFuzzy = genderFuzzy;
	}

	public String getGenderFuzzy(){
		return this.genderFuzzy;
	}

	public void setStatus(Integer status){
		this.status = status;
	}

	public Integer getStatus(){
		return this.status;
	}

	public void setLastLoginTime(String lastLoginTime){
		this.lastLoginTime = lastLoginTime;
	}

	public String getLastLoginTime(){
		return this.lastLoginTime;
	}

	public void setLastLoginTimeStart(String lastLoginTimeStart){
		this.lastLoginTimeStart = lastLoginTimeStart;
	}

	public String getLastLoginTimeStart(){
		return this.lastLoginTimeStart;
	}
	public void setLastLoginTimeEnd(String lastLoginTimeEnd){
		this.lastLoginTimeEnd = lastLoginTimeEnd;
	}

	public String getLastLoginTimeEnd(){
		return this.lastLoginTimeEnd;
	}

	public void setCreateTime(String createTime){
		this.createTime = createTime;
	}

	public String getCreateTime(){
		return this.createTime;
	}

	public void setCreateTimeStart(String createTimeStart){
		this.createTimeStart = createTimeStart;
	}

	public String getCreateTimeStart(){
		return this.createTimeStart;
	}
	public void setCreateTimeEnd(String createTimeEnd){
		this.createTimeEnd = createTimeEnd;
	}

	public String getCreateTimeEnd(){
		return this.createTimeEnd;
	}

}
