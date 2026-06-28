package com.smart.campus.entity.query;

import java.util.Date;


/**
 * 院系表参数
 */
public class FacultyInfoQuery extends BaseParam {


	/**
	 * 
	 */
	private Integer facultyId;

	/**
	 * 院系名称
	 */
	private String facultyName;

	private String facultyNameFuzzy;

	/**
	 * 院系编码（唯一）
	 */
	private String facultyCode;

	private String facultyCodeFuzzy;

	/**
	 * 院系描述
	 */
	private String description;

	private String descriptionFuzzy;

	/**
	 * 排序权重
	 */
	private Integer sortOrder;

	/**
	 * 创建时间
	 */
	private String createTime;

	private String createTimeStart;

	private String createTimeEnd;


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

	public void setFacultyNameFuzzy(String facultyNameFuzzy){
		this.facultyNameFuzzy = facultyNameFuzzy;
	}

	public String getFacultyNameFuzzy(){
		return this.facultyNameFuzzy;
	}

	public void setFacultyCode(String facultyCode){
		this.facultyCode = facultyCode;
	}

	public String getFacultyCode(){
		return this.facultyCode;
	}

	public void setFacultyCodeFuzzy(String facultyCodeFuzzy){
		this.facultyCodeFuzzy = facultyCodeFuzzy;
	}

	public String getFacultyCodeFuzzy(){
		return this.facultyCodeFuzzy;
	}

	public void setDescription(String description){
		this.description = description;
	}

	public String getDescription(){
		return this.description;
	}

	public void setDescriptionFuzzy(String descriptionFuzzy){
		this.descriptionFuzzy = descriptionFuzzy;
	}

	public String getDescriptionFuzzy(){
		return this.descriptionFuzzy;
	}

	public void setSortOrder(Integer sortOrder){
		this.sortOrder = sortOrder;
	}

	public Integer getSortOrder(){
		return this.sortOrder;
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
