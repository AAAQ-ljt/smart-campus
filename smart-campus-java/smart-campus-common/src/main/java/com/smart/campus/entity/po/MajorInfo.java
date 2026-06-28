package com.smart.campus.entity.po;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Date;
import com.smart.campus.entity.enums.DateTimePatternEnum;
import com.smart.campus.utils.DateUtil;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;


/**
 * 专业表
 */
public class MajorInfo implements Serializable {


	/**
	 * 
	 */
	private Integer majorId;

	/**
	 * 专业名称
	 */
	private String majorName;

	/**
	 * 专业编码（唯一）
	 */
	private String majorCode;

	/**
	 * 所属院系ID
	 */
	private Integer facultyId;

	/**
	 * 专业描述
	 */
	private String description;

	/**
	 * 所属院系名称（关联查询，非表字段）
	 */
	private String facultyName;

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

	public void setMajorCode(String majorCode){
		this.majorCode = majorCode;
	}

	public String getMajorCode(){
		return this.majorCode;
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

	public void setFacultyName(String facultyName){
		this.facultyName = facultyName;
	}

	public String getFacultyName(){
		return this.facultyName;
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
		return "majorId:"+(majorId == null ? "空" : majorId)+"，专业名称:"+(majorName == null ? "空" : majorName)+"，专业编码（唯一）:"+(majorCode == null ? "空" : majorCode)+"，所属院系ID:"+(facultyId == null ? "空" : facultyId)+"，专业描述:"+(description == null ? "空" : description)+"，排序权重:"+(sortOrder == null ? "空" : sortOrder)+"，创建时间:"+(createTime == null ? "空" : DateUtil.format(createTime, DateTimePatternEnum.YYYY_MM_DD_HH_MM_SS.getPattern()));
	}
}
