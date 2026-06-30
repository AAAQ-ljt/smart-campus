package com.smart.campus.mappers;

import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * 题目选项表 数据库操作接口
 */
public interface QuestionOptionMapper<T,P> extends BaseMapper<T,P> {

	/**
	 * 根据OptionId更新
	 */
	 Integer updateByOptionId(@Param("bean") T t,@Param("optionId") String optionId);


	/**
	 * 根据OptionId删除
	 */
	 Integer deleteByOptionId(@Param("optionId") String optionId);


	/**
	 * 根据OptionId获取对象
	 */
	 T selectByOptionId(@Param("optionId") String optionId);

	/**
	 * 根据QuestionId列表批量查询
	 */
	 List<T> selectByQuestionIds(@Param("questionIds") List<String> questionIds);

}
