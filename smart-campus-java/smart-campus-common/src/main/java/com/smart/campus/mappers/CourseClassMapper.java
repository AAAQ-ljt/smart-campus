package com.smart.campus.mappers;

import org.apache.ibatis.annotations.Param;

/**
 * 课程班级关联表 数据库操作接口
 */
public interface CourseClassMapper<T,P> extends BaseMapper<T,P> {

	/**
	 * 根据Id更新
	 */
	 Integer updateById(@Param("bean") T t,@Param("id") Integer id);


	/**
	 * 根据Id删除
	 */
	 Integer deleteById(@Param("id") Integer id);


	/**
	 * 根据Id获取对象
	 */
	 T selectById(@Param("id") Integer id);


	/**
	 * 根据CourseIdAndClassId更新
	 */
	 Integer updateByCourseIdAndClassId(@Param("bean") T t,@Param("courseId") String courseId,@Param("classId") Integer classId);


	/**
	 * 根据CourseIdAndClassId删除
	 */
	 Integer deleteByCourseIdAndClassId(@Param("courseId") String courseId,@Param("classId") Integer classId);


	/**
	 * 根据CourseIdAndClassId获取对象
	 */
	 T selectByCourseIdAndClassId(@Param("courseId") String courseId,@Param("classId") Integer classId);


}
