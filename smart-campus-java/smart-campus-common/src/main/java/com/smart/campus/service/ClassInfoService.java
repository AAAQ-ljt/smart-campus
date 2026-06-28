package com.smart.campus.service;

import java.util.List;

import com.smart.campus.entity.query.ClassInfoQuery;
import com.smart.campus.entity.po.ClassInfo;
import com.smart.campus.entity.vo.PaginationResultVO;


/**
 * 班级表 业务接口
 */
public interface ClassInfoService {

	/**
	 * 根据条件查询列表
	 */
	List<ClassInfo> findListByParam(ClassInfoQuery param);

	/**
	 * 根据条件查询列表
	 */
	Integer findCountByParam(ClassInfoQuery param);

	/**
	 * 分页查询
	 */
	PaginationResultVO<ClassInfo> findListByPage(ClassInfoQuery param);

	/**
	 * 新增
	 */
	Integer add(ClassInfo bean);

	/**
	 * 批量新增
	 */
	Integer addBatch(List<ClassInfo> listBean);

	/**
	 * 批量新增/修改
	 */
	Integer addOrUpdateBatch(List<ClassInfo> listBean);

	/**
	 * 多条件更新
	 */
	Integer updateByParam(ClassInfo bean,ClassInfoQuery param);

	/**
	 * 多条件删除
	 */
	Integer deleteByParam(ClassInfoQuery param);

	/**
	 * 根据ClassId查询对象
	 */
	ClassInfo getClassInfoByClassId(Integer classId);


	/**
	 * 根据ClassId修改
	 */
	Integer updateClassInfoByClassId(ClassInfo bean,Integer classId);


	/**
	 * 根据ClassId删除
	 */
	Integer deleteClassInfoByClassId(Integer classId);


	/**
	 * 根据ClassCode查询对象
	 */
	ClassInfo getClassInfoByClassCode(String classCode);


	/**
	 * 根据ClassCode修改
	 */
	Integer updateClassInfoByClassCode(ClassInfo bean,String classCode);


	/**
	 * 根据ClassCode删除
	 */
	Integer deleteClassInfoByClassCode(String classCode);

}