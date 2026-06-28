package com.smart.campus.entity.query;

import java.util.Date;


/**
 * 试卷信息表参数
 */
public class PaperInfoQuery extends BaseParam {


	/**
	 * 试卷ID（UUID）
	 */
	private String paperId;

	private String paperIdFuzzy;

	/**
	 * 试卷名称
	 */
	private String paperTitle;

	private String paperTitleFuzzy;

	/**
	 * 试卷类型：1-课后习题, 2-考试试卷
	 */
	private Integer paperType;

	/**
	 * 试卷描述/说明
	 */
	private String description;

	private String descriptionFuzzy;

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
	private String createTime;

	private String createTimeStart;

	private String createTimeEnd;

	/**
	 * 更新时间
	 */
	private String updateTime;

	private String updateTimeStart;

	private String updateTimeEnd;


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

	public void setPaperTitle(String paperTitle){
		this.paperTitle = paperTitle;
	}

	public String getPaperTitle(){
		return this.paperTitle;
	}

	public void setPaperTitleFuzzy(String paperTitleFuzzy){
		this.paperTitleFuzzy = paperTitleFuzzy;
	}

	public String getPaperTitleFuzzy(){
		return this.paperTitleFuzzy;
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

	public void setDescriptionFuzzy(String descriptionFuzzy){
		this.descriptionFuzzy = descriptionFuzzy;
	}

	public String getDescriptionFuzzy(){
		return this.descriptionFuzzy;
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
