package com.smart.campus.entity.po;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;


/**
 * 课时资源关联表
 */
public class CourseLessonResource implements Serializable {


	/**
	 * 关联ID（UUID）
	 */
	private String id;

	/**
	 * 所属课时ID
	 */
	private String lessonId;

	/**
	 * 资源类型：1-视频, 2-课件, 3-课后习题
	 */
	private Integer resourceType;

	/**
	 * 关联资源ID（resource_info 或 paper_info）
	 */
	private String resourceId;

	/**
	 * 是否是主资源1:是  0:不是
	 */
	private Integer isPrimary;

	/**
	 * 排序权重
	 */
	private Integer sortOrder;


	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return this.id;
	}

	public void setLessonId(String lessonId){
		this.lessonId = lessonId;
	}

	public String getLessonId(){
		return this.lessonId;
	}

	public void setResourceType(Integer resourceType){
		this.resourceType = resourceType;
	}

	public Integer getResourceType(){
		return this.resourceType;
	}

	public void setResourceId(String resourceId){
		this.resourceId = resourceId;
	}

	public String getResourceId(){
		return this.resourceId;
	}

	public void setIsPrimary(Integer isPrimary){
		this.isPrimary = isPrimary;
	}

	public Integer getIsPrimary(){
		return this.isPrimary;
	}

	public void setSortOrder(Integer sortOrder){
		this.sortOrder = sortOrder;
	}

	public Integer getSortOrder(){
		return this.sortOrder;
	}

	@Override
	public String toString (){
		return "关联ID（UUID）:"+(id == null ? "空" : id)+"，所属课时ID:"+(lessonId == null ? "空" : lessonId)+"，资源类型：1-视频, 2-课件, 3-课后习题:"+(resourceType == null ? "空" : resourceType)+"，关联资源ID（resource_info 或 paper_info）:"+(resourceId == null ? "空" : resourceId)+"，是否是主资源1:是  0:不是:"+(isPrimary == null ? "空" : isPrimary)+"，排序权重:"+(sortOrder == null ? "空" : sortOrder);
	}
}
