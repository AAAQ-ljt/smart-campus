package com.smart.campus.service;

import java.util.List;

import com.smart.campus.entity.query.FacultyInfoQuery;
import com.smart.campus.entity.po.FacultyInfo;
import com.smart.campus.entity.vo.PaginationResultVO;


/**
 * 院系表 业务接口
 */
public interface FacultyInfoService {

	/**
	 * 根据条件查询列表
	 */
	List<FacultyInfo> findListByParam(FacultyInfoQuery param);

	/**
	 * 根据条件查询列表
	 */
	Integer findCountByParam(FacultyInfoQuery param);

	/**
	 * 分页查询
	 */
	PaginationResultVO<FacultyInfo> findListByPage(FacultyInfoQuery param);

	/**
	 * 新增
	 */
	Integer add(FacultyInfo bean);

	/**
	 * 批量新增
	 */
	Integer addBatch(List<FacultyInfo> listBean);

	/**
	 * 批量新增/修改
	 */
	Integer addOrUpdateBatch(List<FacultyInfo> listBean);

	/**
	 * 多条件更新
	 */
	Integer updateByParam(FacultyInfo bean,FacultyInfoQuery param);

	/**
	 * 多条件删除
	 */
	Integer deleteByParam(FacultyInfoQuery param);

	/**
	 * 根据FacultyId查询对象
	 */
	FacultyInfo getFacultyInfoByFacultyId(Integer facultyId);


	/**
	 * 根据FacultyId修改
	 */
	Integer updateFacultyInfoByFacultyId(FacultyInfo bean,Integer facultyId);


	/**
	 * 根据FacultyId删除
	 */
	Integer deleteFacultyInfoByFacultyId(Integer facultyId);


	/**
	 * 根据FacultyCode查询对象
	 */
	FacultyInfo getFacultyInfoByFacultyCode(String facultyCode);


	/**
	 * 根据FacultyCode修改
	 */
	Integer updateFacultyInfoByFacultyCode(FacultyInfo bean,String facultyCode);


	/**
	 * 根据FacultyCode删除
	 */
	Integer deleteFacultyInfoByFacultyCode(String facultyCode);

}