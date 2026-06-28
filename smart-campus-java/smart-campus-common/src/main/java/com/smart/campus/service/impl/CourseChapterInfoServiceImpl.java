package com.smart.campus.service.impl;

import java.util.List;

import jakarta.annotation.Resource;

import org.springframework.stereotype.Service;

import com.smart.campus.entity.enums.PageSize;
import com.smart.campus.entity.query.CourseChapterInfoQuery;
import com.smart.campus.entity.po.CourseChapterInfo;
import com.smart.campus.entity.vo.PaginationResultVO;
import com.smart.campus.entity.query.SimplePage;
import com.smart.campus.mappers.CourseChapterInfoMapper;
import com.smart.campus.service.CourseChapterInfoService;
import com.smart.campus.utils.StringTools;


/**
 * 章节信息表 业务接口实现
 */
@Service("courseChapterInfoService")
public class CourseChapterInfoServiceImpl implements CourseChapterInfoService {

	@Resource
	private CourseChapterInfoMapper<CourseChapterInfo, CourseChapterInfoQuery> courseChapterInfoMapper;

	/**
	 * 根据条件查询列表
	 */
	@Override
	public List<CourseChapterInfo> findListByParam(CourseChapterInfoQuery param) {
		return this.courseChapterInfoMapper.selectList(param);
	}

	/**
	 * 根据条件查询列表
	 */
	@Override
	public Integer findCountByParam(CourseChapterInfoQuery param) {
		return this.courseChapterInfoMapper.selectCount(param);
	}

	/**
	 * 分页查询方法
	 */
	@Override
	public PaginationResultVO<CourseChapterInfo> findListByPage(CourseChapterInfoQuery param) {
		int count = this.findCountByParam(param);
		int pageSize = param.getPageSize() == null ? PageSize.SIZE15.getSize() : param.getPageSize();

		SimplePage page = new SimplePage(param.getPageNo(), count, pageSize);
		param.setSimplePage(page);
		List<CourseChapterInfo> list = this.findListByParam(param);
		PaginationResultVO<CourseChapterInfo> result = new PaginationResultVO(count, page.getPageSize(), page.getPageNo(), page.getPageTotal(), list);
		return result;
	}

	/**
	 * 新增
	 */
	@Override
	public Integer add(CourseChapterInfo bean) {
		return this.courseChapterInfoMapper.insert(bean);
	}

	/**
	 * 批量新增
	 */
	@Override
	public Integer addBatch(List<CourseChapterInfo> listBean) {
		if (listBean == null || listBean.isEmpty()) {
			return 0;
		}
		return this.courseChapterInfoMapper.insertBatch(listBean);
	}

	/**
	 * 批量新增或者修改
	 */
	@Override
	public Integer addOrUpdateBatch(List<CourseChapterInfo> listBean) {
		if (listBean == null || listBean.isEmpty()) {
			return 0;
		}
		return this.courseChapterInfoMapper.insertOrUpdateBatch(listBean);
	}

	/**
	 * 多条件更新
	 */
	@Override
	public Integer updateByParam(CourseChapterInfo bean, CourseChapterInfoQuery param) {
		StringTools.checkParam(param);
		return this.courseChapterInfoMapper.updateByParam(bean, param);
	}

	/**
	 * 多条件删除
	 */
	@Override
	public Integer deleteByParam(CourseChapterInfoQuery param) {
		StringTools.checkParam(param);
		return this.courseChapterInfoMapper.deleteByParam(param);
	}

	/**
	 * 根据ChapterId获取对象
	 */
	@Override
	public CourseChapterInfo getCourseChapterInfoByChapterId(String chapterId) {
		return this.courseChapterInfoMapper.selectByChapterId(chapterId);
	}

	/**
	 * 根据ChapterId修改
	 */
	@Override
	public Integer updateCourseChapterInfoByChapterId(CourseChapterInfo bean, String chapterId) {
		return this.courseChapterInfoMapper.updateByChapterId(bean, chapterId);
	}

	/**
	 * 根据ChapterId删除
	 */
	@Override
	public Integer deleteCourseChapterInfoByChapterId(String chapterId) {
		return this.courseChapterInfoMapper.deleteByChapterId(chapterId);
	}
}