package com.smart.campus.entity.query;

import java.util.Date;


/**
 * 专业表参数
 */
public class MajorInfoQuery extends BaseParam {


	/**
	 * 
	 */
	private Integer majorId;

	/**
	 * 专业名称
	 */
	private String majorName;

	private String majorNameFuzzy;

	/**
	 * 专业编码（唯一）
	 */
	private String majorCode;

	private String majorCodeFuzzy;

	/**
	 * 所属院系ID
	 */
	private Integer facultyId;

	/**
	 * 专业描述
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


	public void setMajorId(Integer majorId){
		this.majorId = majorId;
	}

	public Integer getMajorId(){
		return this.majorId;
	}

	public void setMajorName(String majorName){
		this.majorName = majorName;
	}

	public String getMajorName(){
		return this.majorName;
	}

	public void setMajorNameFuzzy(String majorNameFuzzy){
		this.majorNameFuzzy = majorNameFuzzy;
	}

	public String getMajorNameFuzzy(){
		return this.majorNameFuzzy;
	}

	public void setMajorCode(String majorCode){
		this.majorCode = majorCode;
	}

	public String getMajorCode(){
		return this.majorCode;
	}

	public void setMajorCodeFuzzy(String majorCodeFuzzy){
		this.majorCodeFuzzy = majorCodeFuzzy;
	}

	public String getMajorCodeFuzzy(){
		return this.majorCodeFuzzy;
	}

	public void setFacultyId(Integer facultyId){
		this.facultyId = facultyId;
	}

	public Integer getFacultyId(){
		return this.facultyId;
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
