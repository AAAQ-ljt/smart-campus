package com.smart.campus.service;

import java.util.List;

import com.smart.campus.entity.query.MajorInfoQuery;
import com.smart.campus.entity.po.MajorInfo;
import com.smart.campus.entity.vo.PaginationResultVO;


/**
 * 专业表 业务接口
 */
public interface MajorInfoService {

	/**
	 * 根据条件查询列表
	 */
	List<MajorInfo> findListByParam(MajorInfoQuery param);

	/**
	 * 根据条件查询列表
	 */
	Integer findCountByParam(MajorInfoQuery param);

	/**
	 * 分页查询
	 */
	PaginationResultVO<MajorInfo> findListByPage(MajorInfoQuery param);

	/**
	 * 新增
	 */
	Integer add(MajorInfo bean);

	/**
	 * 批量新增
	 */
	Integer addBatch(List<MajorInfo> listBean);

	/**
	 * 批量新增/修改
	 */
	Integer addOrUpdateBatch(List<MajorInfo> listBean);

	/**
	 * 多条件更新
	 */
	Integer updateByParam(MajorInfo bean,MajorInfoQuery param);

	/**
	 * 多条件删除
	 */
	Integer deleteByParam(MajorInfoQuery param);

	/**
	 * 根据MajorId查询对象
	 */
	MajorInfo getMajorInfoByMajorId(Integer majorId);


	/**
	 * 根据MajorId修改
	 */
	Integer updateMajorInfoByMajorId(MajorInfo bean,Integer majorId);


	/**
	 * 根据MajorId删除
	 */
	Integer deleteMajorInfoByMajorId(Integer majorId);


	/**
	 * 根据MajorCode查询对象
	 */
	MajorInfo getMajorInfoByMajorCode(String majorCode);


	/**
	 * 根据MajorCode修改
	 */
	Integer updateMajorInfoByMajorCode(MajorInfo bean,String majorCode);


	/**
	 * 根据MajorCode删除
	 */
	Integer deleteMajorInfoByMajorCode(String majorCode);

}