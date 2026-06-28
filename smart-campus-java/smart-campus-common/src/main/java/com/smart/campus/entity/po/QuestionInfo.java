package com.smart.campus.entity.po;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Date;
import com.smart.campus.entity.enums.DateTimePatternEnum;
import com.smart.campus.utils.DateUtil;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;


/**
 * 习题信息表
 */
public class QuestionInfo implements Serializable {


	/**
	 * 习题ID（UUID）
	 */
	private String questionId;

	/**
	 * 题干内容
	 */
	private String questionTitle;

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

	/**
	 * 答案解析
	 */
	private String analysis;

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
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createTime;

	/**
	 * 更新时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date updateTime;


	public void setQuestionId(String questionId){
		this.questionId = questionId;
	}

	public String getQuestionId(){
		return this.questionId;
	}

	public void setQuestionTitle(String questionTitle){
		this.questionTitle = questionTitle;
	}

	public String getQuestionTitle(){
		return this.questionTitle;
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

	public void setAnalysis(String analysis){
		this.analysis = analysis;
	}

	public String getAnalysis(){
		return this.analysis;
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
		return "习题ID（UUID）:"+(questionId == null ? "空" : questionId)+"，题干内容:"+(questionTitle == null ? "空" : questionTitle)+"，题型：1-单选题, 2-多选题, 3-判断题, 4-简答题:"+(questionType == null ? "空" : questionType)+"，难度：1-简单, 2-中等, 3-困难:"+(difficulty == null ? "空" : difficulty)+"，正确答案（客观题存选项key如A/B/AB，主观题存参考答案文本，判断题存T/F）:"+(answer == null ? "空" : answer)+"，答案解析:"+(analysis == null ? "空" : analysis)+"，默认分值:"+(score == null ? "空" : score)+"，创建人用户ID:"+(createUserId == null ? "空" : createUserId)+"，排序权重:"+(sortOrder == null ? "空" : sortOrder)+"，创建时间:"+(createTime == null ? "空" : DateUtil.format(createTime, DateTimePatternEnum.YYYY_MM_DD_HH_MM_SS.getPattern()))+"，更新时间:"+(updateTime == null ? "空" : DateUtil.format(updateTime, DateTimePatternEnum.YYYY_MM_DD_HH_MM_SS.getPattern()));
	}
}
