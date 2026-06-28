package com.smart.campus.service;

import java.util.List;

import com.smart.campus.entity.query.CourseLessonResourceQuery;
import com.smart.campus.entity.po.CourseLessonResource;
import com.smart.campus.entity.vo.PaginationResultVO;


/**
 * 课时资源关联表 业务接口
 */
public interface CourseLessonResourceService {

	/**
	 * 根据条件查询列表
	 */
	List<CourseLessonResource> findListByParam(CourseLessonResourceQuery param);

	/**
	 * 根据条件查询列表
	 */
	Integer findCountByParam(CourseLessonResourceQuery param);

	/**
	 * 分页查询
	 */
	PaginationResultVO<CourseLessonResource> findListByPage(CourseLessonResourceQuery param);

	/**
	 * 新增
	 */
	Integer add(CourseLessonResource bean);

	/**
	 * 批量新增
	 */
	Integer addBatch(List<CourseLessonResource> listBean);

	/**
	 * 批量新增/修改
	 */
	Integer addOrUpdateBatch(List<CourseLessonResource> listBean);

	/**
	 * 多条件更新
	 */
	Integer updateByParam(CourseLessonResource bean,CourseLessonResourceQuery param);

	/**
	 * 多条件删除
	 */
	Integer deleteByParam(CourseLessonResourceQuery param);

	/**
	 * 根据Id查询对象
	 */
	CourseLessonResource getCourseLessonResourceById(String id);


	/**
	 * 根据Id修改
	 */
	Integer updateCourseLessonResourceById(CourseLessonResource bean,String id);


	/**
	 * 根据Id删除
	 */
	Integer deleteCourseLessonResourceById(String id);

}