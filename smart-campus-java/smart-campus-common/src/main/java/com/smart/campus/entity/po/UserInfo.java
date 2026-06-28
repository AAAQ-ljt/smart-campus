package com.smart.campus.entity.po;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Date;
import com.smart.campus.entity.enums.DateTimePatternEnum;
import com.smart.campus.utils.DateUtil;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;


/**
 * 用户表（管理员/教师/学生统一存储）
 */
public class UserInfo implements Serializable {


	/**
	 * 
	 */
	private Integer userId;

	/**
	 * 用户编号/工号/学号（登录账号，唯一）
	 */
	private String userNo;

	/**
	 * 密码（加密存储）
	 */
	private String password;

	/**
	 * 真实姓名
	 */
	private String realName;

	/**
	 * 角色类型：admin-管理员, teacher-教师, student-学生
	 */
	private String roleType;

	/**
	 * 学生所属班级ID,老师所属班级多个ID用逗号隔开
	 */
	private String classId;

	/**
	 * 手机号
	 */
	@JsonIgnore
	private String phone;

	/**
	 * 邮箱
	 */
	private String email;

	/**
	 * 头像URL
	 */
	private String avatar;

	/**
	 * 性别：M-男, F-女
	 */
	private String gender;

	/**
	 * 状态：1-启用, 0-禁用
	 */
	private Integer status;

	/**
	 * 最后登录时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date lastLoginTime;

	/**
	 * 创建时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createTime;


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

	public void setPassword(String password){
		this.password = password;
	}

	public String getPassword(){
		return this.password;
	}

	public void setRealName(String realName){
		this.realName = realName;
	}

	public String getRealName(){
		return this.realName;
	}

	public void setRoleType(String roleType){
		this.roleType = roleType;
	}

	public String getRoleType(){
		return this.roleType;
	}

	public void setClassId(String classId){
		this.classId = classId;
	}

	public String getClassId(){
		return this.classId;
	}

	public void setPhone(String phone){
		this.phone = phone;
	}

	public String getPhone(){
		return this.phone;
	}

	public void setEmail(String email){
		this.email = email;
	}

	public String getEmail(){
		return this.email;
	}

	public void setAvatar(String avatar){
		this.avatar = avatar;
	}

	public String getAvatar(){
		return this.avatar;
	}

	public void setGender(String gender){
		this.gender = gender;
	}

	public String getGender(){
		return this.gender;
	}

	public void setStatus(Integer status){
		this.status = status;
	}

	public Integer getStatus(){
		return this.status;
	}

	public void setLastLoginTime(Date lastLoginTime){
		this.lastLoginTime = lastLoginTime;
	}

	public Date getLastLoginTime(){
		return this.lastLoginTime;
	}

	public void setCreateTime(Date createTime){
		this.createTime = createTime;
	}

	public Date getCreateTime(){
		return this.createTime;
	}

	@Override
	public String toString (){
		return "userId:"+(userId == null ? "空" : userId)+"，用户编号/工号/学号（登录账号，唯一）:"+(userNo == null ? "空" : userNo)+"，密码（加密存储）:"+(password == null ? "空" : password)+"，真实姓名:"+(realName == null ? "空" : realName)+"，角色类型：admin-管理员, teacher-教师, student-学生:"+(roleType == null ? "空" : roleType)+"，学生所属班级ID,老师所属班级多个ID用逗号隔开:"+(classId == null ? "空" : classId)+"，手机号:"+(phone == null ? "空" : phone)+"，邮箱:"+(email == null ? "空" : email)+"，头像URL:"+(avatar == null ? "空" : avatar)+"，性别：M-男, F-女:"+(gender == null ? "空" : gender)+"，状态：1-启用, 0-禁用:"+(status == null ? "空" : status)+"，最后登录时间:"+(lastLoginTime == null ? "空" : DateUtil.format(lastLoginTime, DateTimePatternEnum.YYYY_MM_DD_HH_MM_SS.getPattern()))+"，创建时间:"+(createTime == null ? "空" : DateUtil.format(createTime, DateTimePatternEnum.YYYY_MM_DD_HH_MM_SS.getPattern()));
	}
}
