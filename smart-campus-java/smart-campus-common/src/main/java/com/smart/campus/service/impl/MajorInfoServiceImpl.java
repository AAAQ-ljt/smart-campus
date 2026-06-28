package com.smart.campus.service.impl;

import java.util.List;

import jakarta.annotation.Resource;

import org.springframework.stereotype.Service;

import com.smart.campus.entity.enums.PageSize;
import com.smart.campus.entity.query.MajorInfoQuery;
import com.smart.campus.entity.po.MajorInfo;
import com.smart.campus.entity.vo.PaginationResultVO;
import com.smart.campus.entity.query.SimplePage;
import com.smart.campus.mappers.MajorInfoMapper;
import com.smart.campus.service.MajorInfoService;
import com.smart.campus.utils.StringTools;


/**
 * 专业表 业务接口实现
 */
@Service("majorInfoService")
public class MajorInfoServiceImpl implements MajorInfoService {

	@Resource
	private MajorInfoMapper<MajorInfo, MajorInfoQuery> majorInfoMapper;

	/**
	 * 根据条件查询列表
	 */
	@Override
	public List<MajorInfo> findListByParam(MajorInfoQuery param) {
		return this.majorInfoMapper.selectList(param);
	}

	/**
	 * 根据条件查询列表
	 */
	@Override
	public Integer findCountByParam(MajorInfoQuery param) {
		return this.majorInfoMapper.selectCount(param);
	}

	/**
	 * 分页查询方法
	 */
	@Override
	public PaginationResultVO<MajorInfo> findListByPage(MajorInfoQuery param) {
		int count = this.findCountByParam(param);
		int pageSize = param.getPageSize() == null ? PageSize.SIZE15.getSize() : param.getPageSize();

		SimplePage page = new SimplePage(param.getPageNo(), count, pageSize);
		param.setSimplePage(page);
		List<MajorInfo> list = this.findListByParam(param);
		PaginationResultVO<MajorInfo> result = new PaginationResultVO(count, page.getPageSize(), page.getPageNo(), page.getPageTotal(), list);
		return result;
	}

	/**
	 * 新增
	 */
	@Override
	public Integer add(MajorInfo bean) {
		return this.majorInfoMapper.insert(bean);
	}

	/**
	 * 批量新增
	 */
	@Override
	public Integer addBatch(List<MajorInfo> listBean) {
		if (listBean == null || listBean.isEmpty()) {
			return 0;
		}
		return this.majorInfoMapper.insertBatch(listBean);
	}

	/**
	 * 批量新增或者修改
	 */
	@Override
	public Integer addOrUpdateBatch(List<MajorInfo> listBean) {
		if (listBean == null || listBean.isEmpty()) {
			return 0;
		}
		return this.majorInfoMapper.insertOrUpdateBatch(listBean);
	}

	/**
	 * 多条件更新
	 */
	@Override
	public Integer updateByParam(MajorInfo bean, MajorInfoQuery param) {
		StringTools.checkParam(param);
		return this.majorInfoMapper.updateByParam(bean, param);
	}

	/**
	 * 多条件删除
	 */
	@Override
	public Integer deleteByParam(MajorInfoQuery param) {
		StringTools.checkParam(param);
		return this.majorInfoMapper.deleteByParam(param);
	}

	/**
	 * 根据MajorId获取对象
	 */
	@Override
	public MajorInfo getMajorInfoByMajorId(Integer majorId) {
		return this.majorInfoMapper.selectByMajorId(majorId);
	}

	/**
	 * 根据MajorId修改
	 */
	@Override
	public Integer updateMajorInfoByMajorId(MajorInfo bean, Integer majorId) {
		return this.majorInfoMapper.updateByMajorId(bean, majorId);
	}

	/**
	 * 根据MajorId删除
	 */
	@Override
	public Integer deleteMajorInfoByMajorId(Integer majorId) {
		return this.majorInfoMapper.deleteByMajorId(majorId);
	}

	/**
	 * 根据MajorCode获取对象
	 */
	@Override
	public MajorInfo getMajorInfoByMajorCode(String majorCode) {
		return this.majorInfoMapper.selectByMajorCode(majorCode);
	}

	/**
	 * 根据MajorCode修改
	 */
	@Override
	public Integer updateMajorInfoByMajorCode(MajorInfo bean, String majorCode) {
		return this.majorInfoMapper.updateByMajorCode(bean, majorCode);
	}

	/**
	 * 根据MajorCode删除
	 */
	@Override
	public Integer deleteMajorInfoByMajorCode(String majorCode) {
		return this.majorInfoMapper.deleteByMajorCode(majorCode);
	}
}