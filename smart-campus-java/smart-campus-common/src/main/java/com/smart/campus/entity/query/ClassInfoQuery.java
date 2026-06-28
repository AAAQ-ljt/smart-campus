package com.smart.campus.entity.query;

import java.util.Date;


/**
 * 班级表参数
 */
public class ClassInfoQuery extends BaseParam {


	/**
	 * 
	 */
	private Integer classId;

	/**
	 * 班级名称
	 */
	private String className;

	private String classNameFuzzy;

	/**
	 * 班级编码（唯一）
	 */
	private String classCode;

	private String classCodeFuzzy;

	/**
	 * 所属专业ID
	 */
	private Integer majorId;

	/**
	 * 年级（如 2024）
	 */
	private String grade;

	private String gradeFuzzy;

	/**
	 * 班级描述
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


	public void setClassId(Integer classId){
		this.classId = classId;
	}

	public Integer getClassId(){
		return this.classId;
	}

	public void setClassName(String className){
		this.className = className;
	}

	public String getClassName(){
		return this.className;
	}

	public void setClassNameFuzzy(String classNameFuzzy){
		this.classNameFuzzy = classNameFuzzy;
	}

	public String getClassNameFuzzy(){
		return this.classNameFuzzy;
	}

	public void setClassCode(String classCode){
		this.classCode = classCode;
	}

	public String getClassCode(){
		return this.classCode;
	}

	public void setClassCodeFuzzy(String classCodeFuzzy){
		this.classCodeFuzzy = classCodeFuzzy;
	}

	public String getClassCodeFuzzy(){
		return this.classCodeFuzzy;
	}

	public void setMajorId(Integer majorId){
		this.majorId = majorId;
	}

	public Integer getMajorId(){
		return this.majorId;
	}

	public void setGrade(String grade){
		this.grade = grade;
	}

	public String getGrade(){
		return this.grade;
	}

	public void setGradeFuzzy(String gradeFuzzy){
		this.gradeFuzzy = gradeFuzzy;
	}

	public String getGradeFuzzy(){
		return this.gradeFuzzy;
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
