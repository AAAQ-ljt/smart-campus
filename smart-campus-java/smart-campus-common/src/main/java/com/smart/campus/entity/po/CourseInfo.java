package com.smart.campus.entity.po;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Date;
import com.smart.campus.entity.enums.DateTimePatternEnum;
import com.smart.campus.utils.DateUtil;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;


/**
 * 课程信息表
 */
public class CourseInfo implements Serializable {


	/**
	 * 课程ID（UUID）
	 */
	private String courseId;

	/**
	 * 课程名称
	 */
	private String courseName;

	/**
	 * 授课教师用户ID
	 */
	private Integer teacherId;

	/**
	 * 课程描述
	 */
	private String description;

	/**
	 * 封面对应的resource_info的资源ID
	 */
	private String coverResourceId;

	/**
	 * 状态：1-启用, 0-禁用
	 */
	private Integer status;

	/**
	 * 状态：0-录制中, 1-录制完成
	 */
	private Integer recordStatus;

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

	/**
	 * 更新时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date updateTime;


	public void setCourseId(String courseId){
		this.courseId = courseId;
	}

	public String getCourseId(){
		return this.courseId;
	}

	public void setCourseName(String courseName){
		this.courseName = courseName;
	}

	public String getCourseName(){
		return this.courseName;
	}

	public void setTeacherId(Integer teacherId){
		this.teacherId = teacherId;
	}

	public Integer getTeacherId(){
		return this.teacherId;
	}

	public void setDescription(String description){
		this.description = description;
	}

	public String getDescription(){
		return this.description;
	}

	public void setCoverResourceId(String coverResourceId){
		this.coverResourceId = coverResourceId;
	}

	public String getCoverResourceId(){
		return this.coverResourceId;
	}

	public void setStatus(Integer status){
		this.status = status;
	}

	public Integer getStatus(){
		return this.status;
	}

	public void setRecordStatus(Integer recordStatus){
		this.recordStatus = recordStatus;
	}

	public Integer getRecordStatus(){
		return this.recordStatus;
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

	public void setUpdateTime(Date updateTime){
		this.updateTime = updateTime;
	}

	public Date getUpdateTime(){
		return this.updateTime;
	}

	@Override
	public String toString (){
		return "课程ID（UUID）:"+(courseId == null ? "空" : courseId)+"，课程名称:"+(courseName == null ? "空" : courseName)+"，授课教师用户ID:"+(teacherId == null ? "空" : teacherId)+"，课程描述:"+(description == null ? "空" : description)+"，封面对应的resource_info的资源ID:"+(coverResourceId == null ? "空" : coverResourceId)+"，状态：1-启用, 0-禁用:"+(status == null ? "空" : status)+"，状态：0-录制中, 1-录制完成:"+(recordStatus == null ? "空" : recordStatus)+"，排序权重:"+(sortOrder == null ? "空" : sortOrder)+"，创建时间:"+(createTime == null ? "空" : DateUtil.format(createTime, DateTimePatternEnum.YYYY_MM_DD_HH_MM_SS.getPattern()))+"，更新时间:"+(updateTime == null ? "空" : DateUtil.format(updateTime, DateTimePatternEnum.YYYY_MM_DD_HH_MM_SS.getPattern()));
	}
}
