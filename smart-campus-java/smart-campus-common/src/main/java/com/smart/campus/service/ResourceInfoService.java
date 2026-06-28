package com.smart.campus.service;

import java.util.List;

import com.smart.campus.entity.query.ResourceInfoQuery;
import com.smart.campus.entity.po.ResourceInfo;
import com.smart.campus.entity.vo.PaginationResultVO;


/**
 * 资源信息表（目录+文件统一存储） 业务接口
 */
public interface ResourceInfoService {

	/**
	 * 根据条件查询列表
	 */
	List<ResourceInfo> findListByParam(ResourceInfoQuery param);

	/**
	 * 根据条件查询列表
	 */
	Integer findCountByParam(ResourceInfoQuery param);

	/**
	 * 分页查询
	 */
	PaginationResultVO<ResourceInfo> findListByPage(ResourceInfoQuery param);

	/**
	 * 新增
	 */
	Integer add(ResourceInfo bean);

	/**
	 * 批量新增
	 */
	Integer addBatch(List<ResourceInfo> listBean);

	/**
	 * 批量新增/修改
	 */
	Integer addOrUpdateBatch(List<ResourceInfo> listBean);

	/**
	 * 多条件更新
	 */
	Integer updateByParam(ResourceInfo bean,ResourceInfoQuery param);

	/**
	 * 多条件删除
	 */
	Integer deleteByParam(ResourceInfoQuery param);

	/**
	 * 根据ResourceId查询对象
	 */
	ResourceInfo getResourceInfoByResourceId(String resourceId);


	/**
	 * 根据ResourceId修改
	 */
	Integer updateResourceInfoByResourceId(ResourceInfo bean,String resourceId);


	/**
	 * 根据ResourceId删除
	 */
	Integer deleteResourceInfoByResourceId(String resourceId);

}