package com.smart.campus.service.impl;

import java.util.List;

import jakarta.annotation.Resource;

import org.springframework.stereotype.Service;

import com.smart.campus.entity.enums.PageSize;
import com.smart.campus.entity.query.FacultyInfoQuery;
import com.smart.campus.entity.po.FacultyInfo;
import com.smart.campus.entity.vo.PaginationResultVO;
import com.smart.campus.entity.query.SimplePage;
import com.smart.campus.mappers.FacultyInfoMapper;
import com.smart.campus.service.FacultyInfoService;
import com.smart.campus.utils.StringTools;


/**
 * 院系表 业务接口实现
 */
@Service("facultyInfoService")
public class FacultyInfoServiceImpl implements FacultyInfoService {

	@Resource
	private FacultyInfoMapper<FacultyInfo, FacultyInfoQuery> facultyInfoMapper;

	/**
	 * 根据条件查询列表
	 */
	@Override
	public List<FacultyInfo> findListByParam(FacultyInfoQuery param) {
		return this.facultyInfoMapper.selectList(param);
	}

	/**
	 * 根据条件查询列表
	 */
	@Override
	public Integer findCountByParam(FacultyInfoQuery param) {
		return this.facultyInfoMapper.selectCount(param);
	}

	/**
	 * 分页查询方法
	 */
	@Override
	public PaginationResultVO<FacultyInfo> findListByPage(FacultyInfoQuery param) {
		int count = this.findCountByParam(param);
		int pageSize = param.getPageSize() == null ? PageSize.SIZE15.getSize() : param.getPageSize();

		SimplePage page = new SimplePage(param.getPageNo(), count, pageSize);
		param.setSimplePage(page);
		List<FacultyInfo> list = this.findListByParam(param);
		PaginationResultVO<FacultyInfo> result = new PaginationResultVO(count, page.getPageSize(), page.getPageNo(), page.getPageTotal(), list);
		return result;
	}

	/**
	 * 新增
	 */
	@Override
	public Integer add(FacultyInfo bean) {
		return this.facultyInfoMapper.insert(bean);
	}

	/**
	 * 批量新增
	 */
	@Override
	public Integer addBatch(List<FacultyInfo> listBean) {
		if (listBean == null || listBean.isEmpty()) {
			return 0;
		}
		return this.facultyInfoMapper.insertBatch(listBean);
	}

	/**
	 * 批量新增或者修改
	 */
	@Override
	public Integer addOrUpdateBatch(List<FacultyInfo> listBean) {
		if (listBean == null || listBean.isEmpty()) {
			return 0;
		}
		return this.facultyInfoMapper.insertOrUpdateBatch(listBean);
	}

	/**
	 * 多条件更新
	 */
	@Override
	public Integer updateByParam(FacultyInfo bean, FacultyInfoQuery param) {
		StringTools.checkParam(param);
		return this.facultyInfoMapper.updateByParam(bean, param);
	}

	/**
	 * 多条件删除
	 */
	@Override
	public Integer deleteByParam(FacultyInfoQuery param) {
		StringTools.checkParam(param);
		return this.facultyInfoMapper.deleteByParam(param);
	}

	/**
	 * 根据FacultyId获取对象
	 */
	@Override
	public FacultyInfo getFacultyInfoByFacultyId(Integer facultyId) {
		return this.facultyInfoMapper.selectByFacultyId(facultyId);
	}

	/**
	 * 根据FacultyId修改
	 */
	@Override
	public Integer updateFacultyInfoByFacultyId(FacultyInfo bean, Integer facultyId) {
		return this.facultyInfoMapper.updateByFacultyId(bean, facultyId);
	}

	/**
	 * 根据FacultyId删除
	 */
	@Override
	public Integer deleteFacultyInfoByFacultyId(Integer facultyId) {
		return this.facultyInfoMapper.deleteByFacultyId(facultyId);
	}

	/**
	 * 根据FacultyCode获取对象
	 */
	@Override
	public FacultyInfo getFacultyInfoByFacultyCode(String facultyCode) {
		return this.facultyInfoMapper.selectByFacultyCode(facultyCode);
	}

	/**
	 * 根据FacultyCode修改
	 */
	@Override
	public Integer updateFacultyInfoByFacultyCode(FacultyInfo bean, String facultyCode) {
		return this.facultyInfoMapper.updateByFacultyCode(bean, facultyCode);
	}

	/**
	 * 根据FacultyCode删除
	 */
	@Override
	public Integer deleteFacultyInfoByFacultyCode(String facultyCode) {
		return this.facultyInfoMapper.deleteByFacultyCode(facultyCode);
	}
}