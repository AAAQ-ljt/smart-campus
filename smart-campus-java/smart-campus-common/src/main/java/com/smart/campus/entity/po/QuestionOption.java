package com.smart.campus.entity.po;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Date;
import com.smart.campus.entity.enums.DateTimePatternEnum;
import com.smart.campus.utils.DateUtil;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;


/**
 * 题目选项表
 */
public class QuestionOption implements Serializable {


	/**
	 * 选项ID（UUID）
	 */
	private String optionId;

	/**
	 * 所属习题ID
	 */
	private String questionId;

	/**
	 * 选项内容
	 */
	private String optionContent;

	/**
	 * 排序权重（按标识字母顺序排序）
	 */
	private Integer sortOrder;

	/**
	 * 创建时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createTime;


	public void setOptionId(String optionId){
		this.optionId = optionId;
	}

	public String getOptionId(){
		return this.optionId;
	}

	public void setQuestionId(String questionId){
		this.questionId = questionId;
	}

	public String getQuestionId(){
		return this.questionId;
	}

	public void setOptionContent(String optionContent){
		this.optionContent = optionContent;
	}

	public String getOptionContent(){
		return this.optionContent;
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
		return "选项ID（UUID）:"+(optionId == null ? "空" : optionId)+"，所属习题ID:"+(questionId == null ? "空" : questionId)+"，选项内容:"+(optionContent == null ? "空" : optionContent)+"，排序权重（按标识字母顺序排序）:"+(sortOrder == null ? "空" : sortOrder)+"，创建时间:"+(createTime == null ? "空" : DateUtil.format(createTime, DateTimePatternEnum.YYYY_MM_DD_HH_MM_SS.getPattern()));
	}
}
