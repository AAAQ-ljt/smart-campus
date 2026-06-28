package com.smart.campus.service;

import java.util.List;

import com.smart.campus.entity.query.CourseChapterInfoQuery;
import com.smart.campus.entity.po.CourseChapterInfo;
import com.smart.campus.entity.vo.PaginationResultVO;


/**
 * 章节信息表 业务接口
 */
public interface CourseChapterInfoService {

	/**
	 * 根据条件查询列表
	 */
	List<CourseChapterInfo> findListByParam(CourseChapterInfoQuery param);

	/**
	 * 根据条件查询列表
	 */
	Integer findCountByParam(CourseChapterInfoQuery param);

	/**
	 * 分页查询
	 */
	PaginationResultVO<CourseChapterInfo> findListByPage(CourseChapterInfoQuery param);

	/**
	 * 新增
	 */
	Integer add(CourseChapterInfo bean);

	/**
	 * 批量新增
	 */
	Integer addBatch(List<CourseChapterInfo> listBean);

	/**
	 * 批量新增/修改
	 */
	Integer addOrUpdateBatch(List<CourseChapterInfo> listBean);

	/**
	 * 多条件更新
	 */
	Integer updateByParam(CourseChapterInfo bean,CourseChapterInfoQuery param);

	/**
	 * 多条件删除
	 */
	Integer deleteByParam(CourseChapterInfoQuery param);

	/**
	 * 根据ChapterId查询对象
	 */
	CourseChapterInfo getCourseChapterInfoByChapterId(String chapterId);


	/**
	 * 根据ChapterId修改
	 */
	Integer updateCourseChapterInfoByChapterId(CourseChapterInfo bean,String chapterId);


	/**
	 * 根据ChapterId删除
	 */
	Integer deleteCourseChapterInfoByChapterId(String chapterId);

}