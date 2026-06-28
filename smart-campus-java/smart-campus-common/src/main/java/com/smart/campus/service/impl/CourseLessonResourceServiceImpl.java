package com.smart.campus.service.impl;

import java.util.List;

import jakarta.annotation.Resource;

import org.springframework.stereotype.Service;

import com.smart.campus.entity.enums.PageSize;
import com.smart.campus.entity.query.CourseLessonResourceQuery;
import com.smart.campus.entity.po.CourseLessonResource;
import com.smart.campus.entity.vo.PaginationResultVO;
import com.smart.campus.entity.query.SimplePage;
import com.smart.campus.mappers.CourseLessonResourceMapper;
import com.smart.campus.service.CourseLessonResourceService;
import com.smart.campus.utils.StringTools;


/**
 * 课时资源关联表 业务接口实现
 */
@Service("courseLessonResourceService")
public class CourseLessonResourceServiceImpl implements CourseLessonResourceService {

	@Resource
	private CourseLessonResourceMapper<CourseLessonResource, CourseLessonResourceQuery> courseLessonResourceMapper;

	/**
	 * 根据条件查询列表
	 */
	@Override
	public List<CourseLessonResource> findListByParam(CourseLessonResourceQuery param) {
		return this.courseLessonResourceMapper.selectList(param);
	}

	/**
	 * 根据条件查询列表
	 */
	@Override
	public Integer findCountByParam(CourseLessonResourceQuery param) {
		return this.courseLessonResourceMapper.selectCount(param);
	}

	/**
	 * 分页查询方法
	 */
	@Override
	public PaginationResultVO<CourseLessonResource> findListByPage(CourseLessonResourceQuery param) {
		int count = this.findCountByParam(param);
		int pageSize = param.getPageSize() == null ? PageSize.SIZE15.getSize() : param.getPageSize();

		SimplePage page = new SimplePage(param.getPageNo(), count, pageSize);
		param.setSimplePage(page);
		List<CourseLessonResource> list = this.findListByParam(param);
		PaginationResultVO<CourseLessonResource> result = new PaginationResultVO(count, page.getPageSize(), page.getPageNo(), page.getPageTotal(), list);
		return result;
	}

	/**
	 * 新增
	 */
	@Override
	public Integer add(CourseLessonResource bean) {
		return this.courseLessonResourceMapper.insert(bean);
	}

	/**
	 * 批量新增
	 */
	@Override
	public Integer addBatch(List<CourseLessonResource> listBean) {
		if (listBean == null || listBean.isEmpty()) {
			return 0;
		}
		return this.courseLessonResourceMapper.insertBatch(listBean);
	}

	/**
	 * 批量新增或者修改
	 */
	@Override
	public Integer addOrUpdateBatch(List<CourseLessonResource> listBean) {
		if (listBean == null || listBean.isEmpty()) {
			return 0;
		}
		return this.courseLessonResourceMapper.insertOrUpdateBatch(listBean);
	}

	/**
	 * 多条件更新
	 */
	@Override
	public Integer updateByParam(CourseLessonResource bean, CourseLessonResourceQuery param) {
		StringTools.checkParam(param);
		return this.courseLessonResourceMapper.updateByParam(bean, param);
	}

	/**
	 * 多条件删除
	 */
	@Override
	public Integer deleteByParam(CourseLessonResourceQuery param) {
		StringTools.checkParam(param);
		return this.courseLessonResourceMapper.deleteByParam(param);
	}

	/**
	 * 根据Id获取对象
	 */
	@Override
	public CourseLessonResource getCourseLessonResourceById(String id) {
		return this.courseLessonResourceMapper.selectById(id);
	}

	/**
	 * 根据Id修改
	 */
	@Override
	public Integer updateCourseLessonResourceById(CourseLessonResource bean, String id) {
		return this.courseLessonResourceMapper.updateById(bean, id);
	}

	/**
	 * 根据Id删除
	 */
	@Override
	public Integer deleteCourseLessonResourceById(String id) {
		return this.courseLessonResourceMapper.deleteById(id);
	}
}