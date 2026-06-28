package com.smart.campus.entity.query;

import java.util.Date;


/**
 * 资源信息表（目录+文件统一存储）参数
 */
public class ResourceInfoQuery extends BaseParam {


	/**
	 * 资源ID（UUID）
	 */
	private String resourceId;

	private String resourceIdFuzzy;

	/**
	 * 资源名称
	 */
	private String resourceName;

	private String resourceNameFuzzy;

	/**
	 * 节点类型：1-目录, 2-文件
	 */
	private Integer nodeType;

	/**
	 * 父级资源ID（0=根目录）
	 */
	private String parentId;

	private String parentIdFuzzy;

	/**
	 * 资源类型（仅文件使用）：1-视频, 2-图片, 3-文档, 4-压缩包, 5-其他
	 */
	private Integer resourceType;

	/**
	 * 资源存储路径/URL（仅文件使用）
	 */
	private String resourcePath;

	private String resourcePathFuzzy;

	/**
	 * 原始文件名（仅文件使用）
	 */
	private String originalName;

	private String originalNameFuzzy;

	/**
	 * 文件大小（字节，仅文件使用）
	 */
	private Long fileSize;

	/**
	 * 文件扩展名（仅文件使用）
	 */
	private String fileExt;

	private String fileExtFuzzy;

	/**
	 * MIME类型（仅文件使用）
	 */
	private String mimeType;

	private String mimeTypeFuzzy;

	/**
	 * 视频时长（秒，仅视频使用）
	 */
	private Integer duration;

	/**
	 * 封面图URL（仅视频使用）
	 */
	private String coverUrl;

	private String coverUrlFuzzy;

	/**
	 * 资源描述
	 */
	private String description;

	private String descriptionFuzzy;

	/**
	 * 上传用户ID
	 */
	private Integer uploadUserId;

	/**
	 * 状态（仅文件使用）：1-上传中, 2-转码中, 3-上传成功, 4-转码失败, 5-上传失败
	 */
	private Integer status;

	/**
	 * 排序权重（仅目录使用）
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

	public void setResourceName(String resourceName){
		this.resourceName = resourceName;
	}

	public String getResourceName(){
		return this.resourceName;
	}

	public void setResourceNameFuzzy(String resourceNameFuzzy){
		this.resourceNameFuzzy = resourceNameFuzzy;
	}

	public String getResourceNameFuzzy(){
		return this.resourceNameFuzzy;
	}

	public void setNodeType(Integer nodeType){
		this.nodeType = nodeType;
	}

	public Integer getNodeType(){
		return this.nodeType;
	}

	public void setParentId(String parentId){
		this.parentId = parentId;
	}

	public String getParentId(){
		return this.parentId;
	}

	public void setParentIdFuzzy(String parentIdFuzzy){
		this.parentIdFuzzy = parentIdFuzzy;
	}

	public String getParentIdFuzzy(){
		return this.parentIdFuzzy;
	}

	public void setResourceType(Integer resourceType){
		this.resourceType = resourceType;
	}

	public Integer getResourceType(){
		return this.resourceType;
	}

	public void setResourcePath(String resourcePath){
		this.resourcePath = resourcePath;
	}

	public String getResourcePath(){
		return this.resourcePath;
	}

	public void setResourcePathFuzzy(String resourcePathFuzzy){
		this.resourcePathFuzzy = resourcePathFuzzy;
	}

	public String getResourcePathFuzzy(){
		return this.resourcePathFuzzy;
	}

	public void setOriginalName(String originalName){
		this.originalName = originalName;
	}

	public String getOriginalName(){
		return this.originalName;
	}

	public void setOriginalNameFuzzy(String originalNameFuzzy){
		this.originalNameFuzzy = originalNameFuzzy;
	}

	public String getOriginalNameFuzzy(){
		return this.originalNameFuzzy;
	}

	public void setFileSize(Long fileSize){
		this.fileSize = fileSize;
	}

	public Long getFileSize(){
		return this.fileSize;
	}

	public void setFileExt(String fileExt){
		this.fileExt = fileExt;
	}

	public String getFileExt(){
		return this.fileExt;
	}

	public void setFileExtFuzzy(String fileExtFuzzy){
		this.fileExtFuzzy = fileExtFuzzy;
	}

	public String getFileExtFuzzy(){
		return this.fileExtFuzzy;
	}

	public void setMimeType(String mimeType){
		this.mimeType = mimeType;
	}

	public String getMimeType(){
		return this.mimeType;
	}

	public void setMimeTypeFuzzy(String mimeTypeFuzzy){
		this.mimeTypeFuzzy = mimeTypeFuzzy;
	}

	public String getMimeTypeFuzzy(){
		return this.mimeTypeFuzzy;
	}

	public void setDuration(Integer duration){
		this.duration = duration;
	}

	public Integer getDuration(){
		return this.duration;
	}

	public void setCoverUrl(String coverUrl){
		this.coverUrl = coverUrl;
	}

	public String getCoverUrl(){
		return this.coverUrl;
	}

	public void setCoverUrlFuzzy(String coverUrlFuzzy){
		this.coverUrlFuzzy = coverUrlFuzzy;
	}

	public String getCoverUrlFuzzy(){
		return this.coverUrlFuzzy;
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

	public void setUploadUserId(Integer uploadUserId){
		this.uploadUserId = uploadUserId;
	}

	public Integer getUploadUserId(){
		return this.uploadUserId;
	}

	public void setStatus(Integer status){
		this.status = status;
	}

	public Integer getStatus(){
		return this.status;
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
