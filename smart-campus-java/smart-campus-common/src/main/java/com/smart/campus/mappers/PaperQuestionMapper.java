package com.smart.campus.mappers;

import org.apache.ibatis.annotations.Param;

/**
 * 试卷题目关联表 数据库操作接口
 */
public interface PaperQuestionMapper<T,P> extends BaseMapper<T,P> {

	/**
	 * 根据PaperQuestionId更新
	 */
	 Integer updateByPaperQuestionId(@Param("bean") T t,@Param("paperQuestionId") String paperQuestionId);


	/**
	 * 根据PaperQuestionId删除
	 */
	 Integer deleteByPaperQuestionId(@Param("paperQuestionId") String paperQuestionId);


	/**
	 * 根据PaperQuestionId获取对象
	 */
	 T selectByPaperQuestionId(@Param("paperQuestionId") String paperQuestionId);


}
