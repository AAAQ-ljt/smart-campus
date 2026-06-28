package com.smart.campus.entity.query;

import java.util.Date;


/**
 * 习题信息表参数
 */
public class QuestionInfoQuery extends BaseParam {


	/**
	 * 习题ID（UUID）
	 */
	private String questionId;

	private String questionIdFuzzy;

	/**
	 * 题干内容
	 */
	private String questionTitle;

	private String questionTitleFuzzy;

	/**
	 * 题型：1-单选题, 2-多选题, 3-判断题, 4-简答题
	 */
	private Integer questionType;

	/**
	 * 难度：1-简单, 2-中等, 3-困难
	 */
	private Integer difficulty;

	/**
	 * 正确答案（客观题存选项key如A/B/AB，主观题存参考答案文本，判断题存T/F）
	 */
	private String answer;

	private String answerFuzzy;

	/**
	 * 答案解析
	 */
	private String analysis;

	private String analysisFuzzy;

	/**
	 * 默认分值
	 */
	private Integer score;

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

	public void setQuestionTitle(String questionTitle){
		this.questionTitle = questionTitle;
	}

	public String getQuestionTitle(){
		return this.questionTitle;
	}

	public void setQuestionTitleFuzzy(String questionTitleFuzzy){
		this.questionTitleFuzzy = questionTitleFuzzy;
	}

	public String getQuestionTitleFuzzy(){
		return this.questionTitleFuzzy;
	}

	public void setQuestionType(Integer questionType){
		this.questionType = questionType;
	}

	public Integer getQuestionType(){
		return this.questionType;
	}

	public void setDifficulty(Integer difficulty){
		this.difficulty = difficulty;
	}

	public Integer getDifficulty(){
		return this.difficulty;
	}

	public void setAnswer(String answer){
		this.answer = answer;
	}

	public String getAnswer(){
		return this.answer;
	}

	public void setAnswerFuzzy(String answerFuzzy){
		this.answerFuzzy = answerFuzzy;
	}

	public String getAnswerFuzzy(){
		return this.answerFuzzy;
	}

	public void setAnalysis(String analysis){
		this.analysis = analysis;
	}

	public String getAnalysis(){
		return this.analysis;
	}

	public void setAnalysisFuzzy(String analysisFuzzy){
		this.analysisFuzzy = analysisFuzzy;
	}

	public String getAnalysisFuzzy(){
		return this.analysisFuzzy;
	}

	public void setScore(Integer score){
		this.score = score;
	}

	public Integer getScore(){
		return this.score;
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
