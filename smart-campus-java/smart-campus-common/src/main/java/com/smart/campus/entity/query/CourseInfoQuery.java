package com.smart.campus.entity.query;

import java.util.Date;


/**
 * 课程信息表参数
 */
public class CourseInfoQuery extends BaseParam {


	/**
	 * 课程ID（UUID）
	 */
	private String courseId;

	private String courseIdFuzzy;

	/**
	 * 课程名称
	 */
	private String courseName;

	private String courseNameFuzzy;

	/**
	 * 授课教师用户ID
	 */
	private Integer teacherId;

	/**
	 * 课程描述
	 */
	private String description;

	private String descriptionFuzzy;

	/**
	 * 封面对应的resource_info的资源ID
	 */
	private String coverResourceId;

	private String coverResourceIdFuzzy;

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
	private String createTime;

	private String createTimeStart;

	private String createTimeEnd;

	/**
	 * 更新时间
	 */
	private String updateTime;

	private String updateTimeStart;

	private String updateTimeEnd;


	public void setCourseId(String courseId){
		this.courseId = courseId;
	}

	public String getCourseId(){
		return this.courseId;
	}

	public void setCourseIdFuzzy(String courseIdFuzzy){
		this.courseIdFuzzy = courseIdFuzzy;
	}

	public String getCourseIdFuzzy(){
		return this.courseIdFuzzy;
	}

	public void setCourseName(String courseName){
		this.courseName = courseName;
	}

	public String getCourseName(){
		return this.courseName;
	}

	public void setCourseNameFuzzy(String courseNameFuzzy){
		this.courseNameFuzzy = courseNameFuzzy;
	}

	public String getCourseNameFuzzy(){
		return this.courseNameFuzzy;
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

	public void setDescriptionFuzzy(String descriptionFuzzy){
		this.descriptionFuzzy = descriptionFuzzy;
	}

	public String getDescriptionFuzzy(){
		return this.descriptionFuzzy;
	}

	public void setCoverResourceId(String coverResourceId){
		this.coverResourceId = coverResourceId;
	}

	public String getCoverResourceId(){
		return this.coverResourceId;
	}

	public void setCoverResourceIdFuzzy(String coverResourceIdFuzzy){
		this.coverResourceIdFuzzy = coverResourceIdFuzzy;
	}

	public String getCoverResourceIdFuzzy(){
		return this.coverResourceIdFuzzy;
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

	public void setUpdateTime(String updateTime){
		this.updateTime = updateTime;
	}

	public String getUpdateTime(){
		return this.updateTime;
	}

	public void setUpdateTimeStart(String updateTimeStart){
		this.updateTimeStart = updateTimeStart;
	}

	public String getUpdateTimeStart(){
		return this.updateTimeStart;
	}
	public void setUpdateTimeEnd(String updateTimeEnd){
		this.updateTimeEnd = updateTimeEnd;
	}

	public String getUpdateTimeEnd(){
		return this.updateTimeEnd;
	}

}
