package com.smart.campus.entity.query;



/**
 * 课时资源关联表参数
 */
public class CourseLessonResourceQuery extends BaseParam {


	/**
	 * 关联ID（UUID）
	 */
	private String id;

	private String idFuzzy;

	/**
	 * 所属课时ID
	 */
	private String lessonId;

	private String lessonIdFuzzy;

	/**
	 * 资源类型：1-视频, 2-课件, 3-课后习题
	 */
	private Integer resourceType;

	/**
	 * 关联资源ID（resource_info 或 paper_info）
	 */
	private String resourceId;

	private String resourceIdFuzzy;

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

	public void setIdFuzzy(String idFuzzy){
		this.idFuzzy = idFuzzy;
	}

	public String getIdFuzzy(){
		return this.idFuzzy;
	}

	public void setLessonId(String lessonId){
		this.lessonId = lessonId;
	}

	public String getLessonId(){
		return this.lessonId;
	}

	public void setLessonIdFuzzy(String lessonIdFuzzy){
		this.lessonIdFuzzy = lessonIdFuzzy;
	}

	public String getLessonIdFuzzy(){
		return this.lessonIdFuzzy;
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

	public void setResourceIdFuzzy(String resourceIdFuzzy){
		this.resourceIdFuzzy = resourceIdFuzzy;
	}

	public String getResourceIdFuzzy(){
		return this.resourceIdFuzzy;
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

}
