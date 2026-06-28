package com.smart.campus.mappers;

import org.apache.ibatis.annotations.Param;

/**
 * 院系表 数据库操作接口
 */
public interface FacultyInfoMapper<T,P> extends BaseMapper<T,P> {

	/**
	 * 根据FacultyId更新
	 */
	 Integer updateByFacultyId(@Param("bean") T t,@Param("facultyId") Integer facultyId);


	/**
	 * 根据FacultyId删除
	 */
	 Integer deleteByFacultyId(@Param("facultyId") Integer facultyId);


	/**
	 * 根据FacultyId获取对象
	 */
	 T selectByFacultyId(@Param("facultyId") Integer facultyId);


	/**
	 * 根据FacultyCode更新
	 */
	 Integer updateByFacultyCode(@Param("bean") T t,@Param("facultyCode") String facultyCode);


	/**
	 * 根据FacultyCode删除
	 */
	 Integer deleteByFacultyCode(@Param("facultyCode") String facultyCode);


	/**
	 * 根据FacultyCode获取对象
	 */
	 T selectByFacultyCode(@Param("facultyCode") String facultyCode);


}
