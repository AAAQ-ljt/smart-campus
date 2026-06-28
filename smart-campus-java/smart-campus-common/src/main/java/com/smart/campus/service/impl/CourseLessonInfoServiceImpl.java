package com.smart.campus.service.impl;

import java.util.List;

import jakarta.annotation.Resource;

import org.springframework.stereotype.Service;

import com.smart.campus.entity.enums.PageSize;
import com.smart.campus.entity.query.CourseLessonInfoQuery;
import com.smart.campus.entity.po.CourseLessonInfo;
import com.smart.campus.entity.vo.PaginationResultVO;
import com.smart.campus.entity.query.SimplePage;
import com.smart.campus.mappers.CourseLessonInfoMapper;
import com.smart.campus.service.CourseLessonInfoService;
import com.smart.campus.utils.StringTools;


/**
 * 课时信息表 业务接口实现
 */
@Service("courseLessonInfoService")
public class CourseLessonInfoServiceImpl implements CourseLessonInfoService {

	@Resource
	private CourseLessonInfoMapper<CourseLessonInfo, CourseLessonInfoQuery> courseLessonInfoMapper;

	/**
	 * 根据条件查询列表
	 */
	@Override
	public List<CourseLessonInfo> findListByParam(CourseLessonInfoQuery param) {
		return this.courseLessonInfoMapper.selectList(param);
	}

	/**
	 * 根据条件查询列表
	 */
	@Override
	public Integer findCountByParam(CourseLessonInfoQuery param) {
		return this.courseLessonInfoMapper.selectCount(param);
	}

	/**
	 * 分页查询方法
	 */
	@Override
	public PaginationResultVO<CourseLessonInfo> findListByPage(CourseLessonInfoQuery param) {
		int count = this.findCountByParam(param);
		int pageSize = param.getPageSize() == null ? PageSize.SIZE15.getSize() : param.getPageSize();

		SimplePage page = new SimplePage(param.getPageNo(), count, pageSize);
		param.setSimplePage(page);
		List<CourseLessonInfo> list = this.findListByParam(param);
		PaginationResultVO<CourseLessonInfo> result = new PaginationResultVO(count, page.getPageSize(), page.getPageNo(), page.getPageTotal(), list);
		return result;
	}

	/**
	 * 新增
	 */
	@Override
	public Integer add(CourseLessonInfo bean) {
		return this.courseLessonInfoMapper.insert(bean);
	}

	/**
	 * 批量新增
	 */
	@Override
	public Integer addBatch(List<CourseLessonInfo> listBean) {
		if (listBean == null || listBean.isEmpty()) {
			return 0;
		}
		return this.courseLessonInfoMapper.insertBatch(listBean);
	}

	/**
	 * 批量新增或者修改
	 */
	@Override
	public Integer addOrUpdateBatch(List<CourseLessonInfo> listBean) {
		if (listBean == null || listBean.isEmpty()) {
			return 0;
		}
		return this.courseLessonInfoMapper.insertOrUpdateBatch(listBean);
	}

	/**
	 * 多条件更新
	 */
	@Override
	public Integer updateByParam(CourseLessonInfo bean, CourseLessonInfoQuery param) {
		StringTools.checkParam(param);
		return this.courseLessonInfoMapper.updateByParam(bean, param);
	}

	/**
	 * 多条件删除
	 */
	@Override
	public Integer deleteByParam(CourseLessonInfoQuery param) {
		StringTools.checkParam(param);
		return this.courseLessonInfoMapper.deleteByParam(param);
	}

	/**
	 * 根据LessonId获取对象
	 */
	@Override
	public CourseLessonInfo getCourseLessonInfoByLessonId(String lessonId) {
		return this.courseLessonInfoMapper.selectByLessonId(lessonId);
	}

	/**
	 * 根据LessonId修改
	 */
	@Override
	public Integer updateCourseLessonInfoByLessonId(CourseLessonInfo bean, String lessonId) {
		return this.courseLessonInfoMapper.updateByLessonId(bean, lessonId);
	}

	/**
	 * 根据LessonId删除
	 */
	@Override
	public Integer deleteCourseLessonInfoByLessonId(String lessonId) {
		return this.courseLessonInfoMapper.deleteByLessonId(lessonId);
	}
}