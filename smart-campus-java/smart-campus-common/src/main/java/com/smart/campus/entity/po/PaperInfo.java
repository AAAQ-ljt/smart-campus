package com.smart.campus.entity.po;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Date;
import com.smart.campus.entity.enums.DateTimePatternEnum;
import com.smart.campus.utils.DateUtil;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;


/**
 * 试卷信息表
 */
public class PaperInfo implements Serializable {


	/**
	 * 试卷ID（UUID）
	 */
	private String paperId;

	/**
	 * 试卷名称
	 */
	private String paperTitle;

	/**
	 * 试卷类型：1-课后习题, 2-考试试卷
	 */
	private Integer paperType;

	/**
	 * 试卷描述/说明
	 */
	private String description;

	/**
	 * 试卷总分
	 */
	private Integer totalScore;

	/**
	 * 考试时长（分钟，考试试卷使用）
	 */
	private Integer duration;

	/**
	 * 状态：1-启用, 0-禁用
	 */
	private Integer status;

	/**
	 * 创建人用户ID
	 */
	private Integer createUserId;

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


	public void setPaperId(String paperId){
		this.paperId = paperId;
	}

	public String getPaperId(){
		return this.paperId;
	}

	public void setPaperTitle(String paperTitle){
		this.paperTitle = paperTitle;
	}

	public String getPaperTitle(){
		return this.paperTitle;
	}

	public void setPaperType(Integer paperType){
		this.paperType = paperType;
	}

	public Integer getPaperType(){
		return this.paperType;
	}

	public void setDescription(String description){
		this.description = description;
	}

	public String getDescription(){
		return this.description;
	}

	public void setTotalScore(Integer totalScore){
		this.totalScore = totalScore;
	}

	public Integer getTotalScore(){
		return this.totalScore;
	}

	public void setDuration(Integer duration){
		this.duration = duration;
	}

	public Integer getDuration(){
		return this.duration;
	}

	public void setStatus(Integer status){
		this.status = status;
	}

	public Integer getStatus(){
		return this.status;
	}

	public void setCreateUserId(Integer createUserId){
		this.createUserId = createUserId;
	}

	public Integer getCreateUserId(){
		return this.createUserId;
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
		return "试卷ID（UUID）:"+(paperId == null ? "空" : paperId)+"，试卷名称:"+(paperTitle == null ? "空" : paperTitle)+"，试卷类型：1-课后习题, 2-考试试卷:"+(paperType == null ? "空" : paperType)+"，试卷描述/说明:"+(description == null ? "空" : description)+"，试卷总分:"+(totalScore == null ? "空" : totalScore)+"，考试时长（分钟，考试试卷使用）:"+(duration == null ? "空" : duration)+"，状态：1-启用, 0-禁用:"+(status == null ? "空" : status)+"，创建人用户ID:"+(createUserId == null ? "空" : createUserId)+"，排序权重:"+(sortOrder == null ? "空" : sortOrder)+"，创建时间:"+(createTime == null ? "空" : DateUtil.format(createTime, DateTimePatternEnum.YYYY_MM_DD_HH_MM_SS.getPattern()))+"，更新时间:"+(updateTime == null ? "空" : DateUtil.format(updateTime, DateTimePatternEnum.YYYY_MM_DD_HH_MM_SS.getPattern()));
	}
}
