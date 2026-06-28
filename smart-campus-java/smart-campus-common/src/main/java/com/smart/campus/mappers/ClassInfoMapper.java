package com.smart.campus.mappers;

import org.apache.ibatis.annotations.Param;

/**
 * 班级表 数据库操作接口
 */
public interface ClassInfoMapper<T,P> extends BaseMapper<T,P> {

	/**
	 * 根据ClassId更新
	 */
	 Integer updateByClassId(@Param("bean") T t,@Param("classId") Integer classId);


	/**
	 * 根据ClassId删除
	 */
	 Integer deleteByClassId(@Param("classId") Integer classId);


	/**
	 * 根据ClassId获取对象
	 */
	 T selectByClassId(@Param("classId") Integer classId);


	/**
	 * 根据ClassCode更新
	 */
	 Integer updateByClassCode(@Param("bean") T t,@Param("classCode") String classCode);


	/**
	 * 根据ClassCode删除
	 */
	 Integer deleteByClassCode(@Param("classCode") String classCode);


	/**
	 * 根据ClassCode获取对象
	 */
	 T selectByClassCode(@Param("classCode") String classCode);


}
