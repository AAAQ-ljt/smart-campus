package com.smart.campus.entity.po;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Date;
import com.smart.campus.entity.enums.DateTimePatternEnum;
import com.smart.campus.utils.DateUtil;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;


/**
 * 班级表
 */
public class ClassInfo implements Serializable {


	/**
	 * 
	 */
	private Integer classId;

	/**
	 * 班级名称
	 */
	private String className;

	/**
	 * 班级编码（唯一）
	 */
	private String classCode;

	/**
	 * 所属专业ID
	 */
	private Integer majorId;

	/**
	 * 所属专业名称（关联查询，非表字段）
	 */
	private String majorName;

	/**
	 * 所属院系名称（关联查询，非表字段）
	 */
	private String facultyName;

	/**
	 * 年级（如 2024）
	 */
	private String grade;

	/**
	 * 班级描述
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

	public void setClassCode(String classCode){
		this.classCode = classCode;
	}

	public String getClassCode(){
		return this.classCode;
	}

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

	public void setFacultyName(String facultyName){
		this.facultyName = facultyName;
	}

	public String getFacultyName(){
		return this.facultyName;
	}

	public void setGrade(String grade){
		this.grade = grade;
	}

	public String getGrade(){
		return this.grade;
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
		return "classId:"+(classId == null ? "空" : classId)+"，班级名称:"+(className == null ? "空" : className)+"，班级编码（唯一）:"+(classCode == null ? "空" : classCode)+"，所属专业ID:"+(majorId == null ? "空" : majorId)+"，年级（如 2024）:"+(grade == null ? "空" : grade)+"，班级描述:"+(description == null ? "空" : description)+"，排序权重:"+(sortOrder == null ? "空" : sortOrder)+"，创建时间:"+(createTime == null ? "空" : DateUtil.format(createTime, DateTimePatternEnum.YYYY_MM_DD_HH_MM_SS.getPattern()));
	}
}
