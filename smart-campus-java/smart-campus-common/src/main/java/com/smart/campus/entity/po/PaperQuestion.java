package com.smart.campus.entity.po;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Date;
import com.smart.campus.entity.enums.DateTimePatternEnum;
import com.smart.campus.utils.DateUtil;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;


/**
 * 试卷题目关联表
 */
public class PaperQuestion implements Serializable {


	/**
	 * 关联ID（UUID）
	 */
	private String paperQuestionId;

	/**
	 * 父ID
	 */
	private String parentPaperQuestionId;

	/**
	 * 分组名称
	 */
	private String groupName;

	/**
	 * 试卷ID
	 */
	private String paperId;

	/**
	 * 习题ID
	 */
	private String questionId;

	/**
	 * 该题分值（可覆盖习题默认分值）
	 */
	private Integer score;

	/**
	 * 题目排序
	 */
	private Integer sortOrder;

	/**
	 * 创建时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createTime;


	public void setPaperQuestionId(String paperQuestionId){
		this.paperQuestionId = paperQuestionId;
	}

	public String getPaperQuestionId(){
		return this.paperQuestionId;
	}

	public void setParentPaperQuestionId(String parentPaperQuestionId){
		this.parentPaperQuestionId = parentPaperQuestionId;
	}

	public String getParentPaperQuestionId(){
		return this.parentPaperQuestionId;
	}

	public void setGroupName(String groupName){
		this.groupName = groupName;
	}

	public String getGroupName(){
		return this.groupName;
	}

	public void setPaperId(String paperId){
		this.paperId = paperId;
	}

	public String getPaperId(){
		return this.paperId;
	}

	public void setQuestionId(String questionId){
		this.questionId = questionId;
	}

	public String getQuestionId(){
		return this.questionId;
	}

	public void setScore(Integer score){
		this.score = score;
	}

	public Integer getScore(){
		return this.score;
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
		return "关联ID（UUID）:"+(paperQuestionId == null ? "空" : paperQuestionId)+"，父ID:"+(parentPaperQuestionId == null ? "空" : parentPaperQuestionId)+"，分组名称:"+(groupName == null ? "空" : groupName)+"，试卷ID:"+(paperId == null ? "空" : paperId)+"，习题ID:"+(questionId == null ? "空" : questionId)+"，该题分值（可覆盖习题默认分值）:"+(score == null ? "空" : score)+"，题目排序:"+(sortOrder == null ? "空" : sortOrder)+"，创建时间:"+(createTime == null ? "空" : DateUtil.format(createTime, DateTimePatternEnum.YYYY_MM_DD_HH_MM_SS.getPattern()));
	}
}
