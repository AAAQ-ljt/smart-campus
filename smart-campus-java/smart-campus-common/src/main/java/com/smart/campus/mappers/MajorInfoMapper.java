package com.smart.campus.mappers;

import org.apache.ibatis.annotations.Param;

/**
 * 专业表 数据库操作接口
 */
public interface MajorInfoMapper<T,P> extends BaseMapper<T,P> {

	/**
	 * 根据MajorId更新
	 */
	 Integer updateByMajorId(@Param("bean") T t,@Param("majorId") Integer majorId);


	/**
	 * 根据MajorId删除
	 */
	 Integer deleteByMajorId(@Param("majorId") Integer majorId);


	/**
	 * 根据MajorId获取对象
	 */
	 T selectByMajorId(@Param("majorId") Integer majorId);


	/**
	 * 根据MajorCode更新
	 */
	 Integer updateByMajorCode(@Param("bean") T t,@Param("majorCode") String majorCode);


	/**
	 * 根据MajorCode删除
	 */
	 Integer deleteByMajorCode(@Param("majorCode") String majorCode);


	/**
	 * 根据MajorCode获取对象
	 */
	 T selectByMajorCode(@Param("majorCode") String majorCode);


}
