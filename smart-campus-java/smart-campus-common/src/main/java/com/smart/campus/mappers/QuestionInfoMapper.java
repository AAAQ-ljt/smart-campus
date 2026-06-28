package com.smart.campus.mappers;

import org.apache.ibatis.annotations.Param;

/**
 * 习题信息表 数据库操作接口
 */
public interface QuestionInfoMapper<T,P> extends BaseMapper<T,P> {

	/**
	 * 根据QuestionId更新
	 */
	 Integer updateByQuestionId(@Param("bean") T t,@Param("questionId") String questionId);


	/**
	 * 根据QuestionId删除
	 */
	 Integer deleteByQuestionId(@Param("questionId") String questionId);


	/**
	 * 根据QuestionId获取对象
	 */
	 T selectByQuestionId(@Param("questionId") String questionId);


}
