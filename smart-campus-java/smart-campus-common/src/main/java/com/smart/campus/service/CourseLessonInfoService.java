package com.smart.campus.service;

import java.util.List;

import com.smart.campus.entity.query.CourseLessonInfoQuery;
import com.smart.campus.entity.po.CourseLessonInfo;
import com.smart.campus.entity.vo.PaginationResultVO;


/**
 * 课时信息表 业务接口
 */
public interface CourseLessonInfoService {

	/**
	 * 根据条件查询列表
	 */
	List<CourseLessonInfo> findListByParam(CourseLessonInfoQuery param);

	/**
	 * 根据条件查询列表
	 */
	Integer findCountByParam(CourseLessonInfoQuery param);

	/**
	 * 分页查询
	 */
	PaginationResultVO<CourseLessonInfo> findListByPage(CourseLessonInfoQuery param);

	/**
	 * 新增
	 */
	Integer add(CourseLessonInfo bean);

	/**
	 * 批量新增
	 */
	Integer addBatch(List<CourseLessonInfo> listBean);

	/**
	 * 批量新增/修改
	 */
	Integer addOrUpdateBatch(List<CourseLessonInfo> listBean);

	/**
	 * 多条件更新
	 */
	Integer updateByParam(CourseLessonInfo bean,CourseLessonInfoQuery param);

	/**
	 * 多条件删除
	 */
	Integer deleteByParam(CourseLessonInfoQuery param);

	/**
	 * 根据LessonId查询对象
	 */
	CourseLessonInfo getCourseLessonInfoByLessonId(String lessonId);


	/**
	 * 根据LessonId修改
	 */
	Integer updateCourseLessonInfoByLessonId(CourseLessonInfo bean,String lessonId);


	/**
	 * 根据LessonId删除
	 */
	Integer deleteCourseLessonInfoByLessonId(String lessonId);

}