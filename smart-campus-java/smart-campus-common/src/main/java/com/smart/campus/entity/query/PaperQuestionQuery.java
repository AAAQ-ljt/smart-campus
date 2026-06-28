package com.smart.campus.entity.query;

import java.util.Date;


/**
 * 试卷题目关联表参数
 */
public class PaperQuestionQuery extends BaseParam {


	/**
	 * 关联ID（UUID）
	 */
	private String paperQuestionId;

	private String paperQuestionIdFuzzy;

	/**
	 * 父ID
	 */
	private String parentPaperQuestionId;

	private String parentPaperQuestionIdFuzzy;

	/**
	 * 分组名称
	 */
	private String groupName;

	private String groupNameFuzzy;

	/**
	 * 试卷ID
	 */
	private String paperId;

	private String paperIdFuzzy;

	/**
	 * 习题ID
	 */
	private String questionId;

	private String questionIdFuzzy;

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
	private String createTime;

	private String createTimeStart;

	private String createTimeEnd;


	public void setPaperQuestionId(String paperQuestionId){
		this.paperQuestionId = paperQuestionId;
	}

	public String getPaperQuestionId(){
		return this.paperQuestionId;
	}

	public void setPaperQuestionIdFuzzy(String paperQuestionIdFuzzy){
		this.paperQuestionIdFuzzy = paperQuestionIdFuzzy;
	}

	public String getPaperQuestionIdFuzzy(){
		return this.paperQuestionIdFuzzy;
	}

	public void setParentPaperQuestionId(String parentPaperQuestionId){
		this.parentPaperQuestionId = parentPaperQuestionId;
	}

	public String getParentPaperQuestionId(){
		return this.parentPaperQuestionId;
	}

	public void setParentPaperQuestionIdFuzzy(String parentPaperQuestionIdFuzzy){
		this.parentPaperQuestionIdFuzzy = parentPaperQuestionIdFuzzy;
	}

	public String getParentPaperQuestionIdFuzzy(){
		return this.parentPaperQuestionIdFuzzy;
	}

	public void setGroupName(String groupName){
		this.groupName = groupName;
	}

	public String getGroupName(){
		return this.groupName;
	}

	public void setGroupNameFuzzy(String groupNameFuzzy){
		this.groupNameFuzzy = groupNameFuzzy;
	}

	public String getGroupNameFuzzy(){
		return this.groupNameFuzzy;
	}

	public void setPaperId(String paperId){
		this.paperId = paperId;
	}

	public String getPaperId(){
		return this.paperId;
	}

	public void setPaperIdFuzzy(String paperIdFuzzy){
		this.paperIdFuzzy = paperIdFuzzy;
	}

	public String getPaperIdFuzzy(){
		return this.paperIdFuzzy;
	}

	public void setQuestionId(String questionId){
		this.questionId = questionId;
	}

	public String getQuestionId(){
		return this.questionId;
	}

	public void setQuestionIdFuzzy(String questionIdFuzzy){
		this.questionIdFuzzy = questionIdFuzzy;
	}

	public String getQuestionIdFuzzy(){
		return this.questionIdFuzzy;
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

}
