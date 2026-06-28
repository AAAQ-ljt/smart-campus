package com.smart.campus.service.impl;

import java.util.List;

import jakarta.annotation.Resource;

import org.springframework.stereotype.Service;

import com.smart.campus.entity.enums.PageSize;
import com.smart.campus.entity.query.ClassInfoQuery;
import com.smart.campus.entity.po.ClassInfo;
import com.smart.campus.entity.vo.PaginationResultVO;
import com.smart.campus.entity.query.SimplePage;
import com.smart.campus.mappers.ClassInfoMapper;
import com.smart.campus.service.ClassInfoService;
import com.smart.campus.utils.StringTools;


/**
 * 班级表 业务接口实现
 */
@Service("classInfoService")
public class ClassInfoServiceImpl implements ClassInfoService {

	@Resource
	private ClassInfoMapper<ClassInfo, ClassInfoQuery> classInfoMapper;

	/**
	 * 根据条件查询列表
	 */
	@Override
	public List<ClassInfo> findListByParam(ClassInfoQuery param) {
		return this.classInfoMapper.selectList(param);
	}

	/**
	 * 根据条件查询列表
	 */
	@Override
	public Integer findCountByParam(ClassInfoQuery param) {
		return this.classInfoMapper.selectCount(param);
	}

	/**
	 * 分页查询方法
	 */
	@Override
	public PaginationResultVO<ClassInfo> findListByPage(ClassInfoQuery param) {
		int count = this.findCountByParam(param);
		int pageSize = param.getPageSize() == null ? PageSize.SIZE15.getSize() : param.getPageSize();

		SimplePage page = new SimplePage(param.getPageNo(), count, pageSize);
		param.setSimplePage(page);
		List<ClassInfo> list = this.findListByParam(param);
		PaginationResultVO<ClassInfo> result = new PaginationResultVO(count, page.getPageSize(), page.getPageNo(), page.getPageTotal(), list);
		return result;
	}

	/**
	 * 新增
	 */
	@Override
	public Integer add(ClassInfo bean) {
		return this.classInfoMapper.insert(bean);
	}

	/**
	 * 批量新增
	 */
	@Override
	public Integer addBatch(List<ClassInfo> listBean) {
		if (listBean == null || listBean.isEmpty()) {
			return 0;
		}
		return this.classInfoMapper.insertBatch(listBean);
	}

	/**
	 * 批量新增或者修改
	 */
	@Override
	public Integer addOrUpdateBatch(List<ClassInfo> listBean) {
		if (listBean == null || listBean.isEmpty()) {
			return 0;
		}
		return this.classInfoMapper.insertOrUpdateBatch(listBean);
	}

	/**
	 * 多条件更新
	 */
	@Override
	public Integer updateByParam(ClassInfo bean, ClassInfoQuery param) {
		StringTools.checkParam(param);
		return this.classInfoMapper.updateByParam(bean, param);
	}

	/**
	 * 多条件删除
	 */
	@Override
	public Integer deleteByParam(ClassInfoQuery param) {
		StringTools.checkParam(param);
		return this.classInfoMapper.deleteByParam(param);
	}

	/**
	 * 根据ClassId获取对象
	 */
	@Override
	public ClassInfo getClassInfoByClassId(Integer classId) {
		return this.classInfoMapper.selectByClassId(classId);
	}

	/**
	 * 根据ClassId修改
	 */
	@Override
	public Integer updateClassInfoByClassId(ClassInfo bean, Integer classId) {
		return this.classInfoMapper.updateByClassId(bean, classId);
	}

	/**
	 * 根据ClassId删除
	 */
	@Override
	public Integer deleteClassInfoByClassId(Integer classId) {
		return this.classInfoMapper.deleteByClassId(classId);
	}

	/**
	 * 根据ClassCode获取对象
	 */
	@Override
	public ClassInfo getClassInfoByClassCode(String classCode) {
		return this.classInfoMapper.selectByClassCode(classCode);
	}

	/**
	 * 根据ClassCode修改
	 */
	@Override
	public Integer updateClassInfoByClassCode(ClassInfo bean, String classCode) {
		return this.classInfoMapper.updateByClassCode(bean, classCode);
	}

	/**
	 * 根据ClassCode删除
	 */
	@Override
	public Integer deleteClassInfoByClassCode(String classCode) {
		return this.classInfoMapper.deleteByClassCode(classCode);
	}
}