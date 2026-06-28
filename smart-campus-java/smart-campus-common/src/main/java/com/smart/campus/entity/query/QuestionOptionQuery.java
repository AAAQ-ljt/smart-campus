package com.smart.campus.entity.query;

import java.util.Date;


/**
 * 题目选项表参数
 */
public class QuestionOptionQuery extends BaseParam {


	/**
	 * 选项ID（UUID）
	 */
	private String optionId;

	private String optionIdFuzzy;

	/**
	 * 所属习题ID
	 */
	private String questionId;

	private String questionIdFuzzy;

	/**
	 * 选项内容
	 */
	private String optionContent;

	private String optionContentFuzzy;

	/**
	 * 排序权重（按标识字母顺序排序）
	 */
	private Integer sortOrder;

	/**
	 * 创建时间
	 */
	private String createTime;

	private String createTimeStart;

	private String createTimeEnd;


	public void setOptionId(String optionId){
		this.optionId = optionId;
	}

	public String getOptionId(){
		return this.optionId;
	}

	public void setOptionIdFuzzy(String optionIdFuzzy){
		this.optionIdFuzzy = optionIdFuzzy;
	}

	public String getOptionIdFuzzy(){
		return this.optionIdFuzzy;
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

	public void setOptionContent(String optionContent){
		this.optionContent = optionContent;
	}

	public String getOptionContent(){
		return this.optionContent;
	}

	public void setOptionContentFuzzy(String optionContentFuzzy){
		this.optionContentFuzzy = optionContentFuzzy;
	}

	public String getOptionContentFuzzy(){
		return this.optionContentFuzzy;
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
