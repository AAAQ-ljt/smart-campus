package com.smart.campus.entity.po;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Date;
import com.smart.campus.entity.enums.DateTimePatternEnum;
import com.smart.campus.utils.DateUtil;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;


/**
 * 资源信息表（目录+文件统一存储）
 */
public class ResourceInfo implements Serializable {


	/**
	 * 资源ID（UUID）
	 */
	private String resourceId;

	/**
	 * 资源名称
	 */
	private String resourceName;

	/**
	 * 节点类型：1-目录, 2-文件
	 */
	private Integer nodeType;

	/**
	 * 父级资源ID（0=根目录）
	 */
	private String parentId;

	/**
	 * 资源类型（仅文件使用）：1-视频, 2-图片, 3-文档, 4-压缩包, 5-其他
	 */
	private Integer resourceType;

	/**
	 * 资源存储路径/URL（仅文件使用）
	 */
	private String resourcePath;

	/**
	 * 原始文件名（仅文件使用）
	 */
	private String originalName;

	/**
	 * 文件大小（字节，仅文件使用）
	 */
	private Long fileSize;

	/**
	 * 文件扩展名（仅文件使用）
	 */
	private String fileExt;

	/**
	 * MIME类型（仅文件使用）
	 */
	private String mimeType;

	/**
	 * 视频时长（秒，仅视频使用）
	 */
	private Integer duration;

	/**
	 * 封面图URL（仅视频使用）
	 */
	private String coverUrl;

	/**
	 * 资源描述
	 */
	private String description;

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
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createTime;

	/**
	 * 更新时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date updateTime;


	public void setResourceId(String resourceId){
		this.resourceId = resourceId;
	}

	public String getResourceId(){
		return this.resourceId;
	}

	public void setResourceName(String resourceName){
		this.resourceName = resourceName;
	}

	public String getResourceName(){
		return this.resourceName;
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

	public void setOriginalName(String originalName){
		this.originalName = originalName;
	}

	public String getOriginalName(){
		return this.originalName;
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

	public void setMimeType(String mimeType){
		this.mimeType = mimeType;
	}

	public String getMimeType(){
		return this.mimeType;
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

	public void setDescription(String description){
		this.description = description;
	}

	public String getDescription(){
		return this.description;
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
		return "资源ID（UUID）:"+(resourceId == null ? "空" : resourceId)+"，资源名称:"+(resourceName == null ? "空" : resourceName)+"，节点类型：1-目录, 2-文件:"+(nodeType == null ? "空" : nodeType)+"，父级资源ID（0=根目录）:"+(parentId == null ? "空" : parentId)+"，资源类型（仅文件使用）：1-视频, 2-图片, 3-文档, 4-压缩包, 5-其他:"+(resourceType == null ? "空" : resourceType)+"，资源存储路径/URL（仅文件使用）:"+(resourcePath == null ? "空" : resourcePath)+"，原始文件名（仅文件使用）:"+(originalName == null ? "空" : originalName)+"，文件大小（字节，仅文件使用）:"+(fileSize == null ? "空" : fileSize)+"，文件扩展名（仅文件使用）:"+(fileExt == null ? "空" : fileExt)+"，MIME类型（仅文件使用）:"+(mimeType == null ? "空" : mimeType)+"，视频时长（秒，仅视频使用）:"+(duration == null ? "空" : duration)+"，封面图URL（仅视频使用）:"+(coverUrl == null ? "空" : coverUrl)+"，资源描述:"+(description == null ? "空" : description)+"，上传用户ID:"+(uploadUserId == null ? "空" : uploadUserId)+"，状态（仅文件使用）：1-上传中, 2-转码中, 3-上传成功, 4-转码失败, 5-上传失败:"+(status == null ? "空" : status)+"，排序权重（仅目录使用）:"+(sortOrder == null ? "空" : sortOrder)+"，创建时间:"+(createTime == null ? "空" : DateUtil.format(createTime, DateTimePatternEnum.YYYY_MM_DD_HH_MM_SS.getPattern()))+"，更新时间:"+(updateTime == null ? "空" : DateUtil.format(updateTime, DateTimePatternEnum.YYYY_MM_DD_HH_MM_SS.getPattern()));
	}
}
