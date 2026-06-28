package com.smart.campus.entity.po;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Date;
import com.smart.campus.entity.enums.DateTimePatternEnum;
import com.smart.campus.utils.DateUtil;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;


/**
 * 院系表
 */
public class FacultyInfo implements Serializable {


	/**
	 * 
	 */
	private Integer facultyId;

	/**
	 * 院系名称
	 */
	private String facultyName;

	/**
	 * 院系编码（唯一）
	 */
	private String facultyCode;

	/**
	 * 院系描述
	 */
	private String description;

	/**
	 * 排序权重
	 */
	private Integer sortOrder;

	/**
	 * 创建时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createTime;


	public void setFacultyId(Integer facultyId){
		this.facultyId = facultyId;
	}

	public Integer getFacultyId(){
		return this.facultyId;
	}

	public void setFacultyName(String facultyName){
		this.facultyName = facultyName;
	}

	public String getFacultyName(){
		return this.facultyName;
	}

	public void setFacultyCode(String facultyCode){
		this.facultyCode = facultyCode;
	}

	public String getFacultyCode(){
		return this.facultyCode;
	}

	public void setDescription(String description){
		this.description = description;
	}

	public String getDescription(){
		return this.description;
	}

	public void setSortOrder(Integer sortOrder){
		this.sortOrder = sortOrder;
	}

	public Integer getSortOrder(){
		return this.sortOrder;
	}

	public void setCreateTime(Date createTime){
		this.createTime = createTime;
	}

	public Date getCreateTime(){
		return this.createTime;
	}

	@Override
	public String toString (){
		return "facultyId:"+(facultyId == null ? "空" : facultyId)+"，院系名称:"+(facultyName == null ? "空" : facultyName)+"，院系编码（唯一）:"+(facultyCode == null ? "空" : facultyCode)+"，院系描述:"+(description == null ? "空" : description)+"，排序权重:"+(sortOrder == null ? "空" : sortOrder)+"，创建时间:"+(createTime == null ? "空" : DateUtil.format(createTime, DateTimePatternEnum.YYYY_MM_DD_HH_MM_SS.getPattern()));
	}
}
