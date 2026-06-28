package com.smart.campus.service;

import java.util.List;

import com.smart.campus.entity.query.UserInfoQuery;
import com.smart.campus.entity.po.UserInfo;
import com.smart.campus.entity.vo.PaginationResultVO;


/**
 * 用户表（管理员/教师/学生统一存储） 业务接口
 */
public interface UserInfoService {

	/**
	 * 根据条件查询列表
	 */
	List<UserInfo> findListByParam(UserInfoQuery param);

	/**
	 * 根据条件查询列表
	 */
	Integer findCountByParam(UserInfoQuery param);

	/**
	 * 分页查询
	 */
	PaginationResultVO<UserInfo> findListByPage(UserInfoQuery param);

	/**
	 * 新增
	 */
	Integer add(UserInfo bean);

	/**
	 * 批量新增
	 */
	Integer addBatch(List<UserInfo> listBean);

	/**
	 * 批量新增/修改
	 */
	Integer addOrUpdateBatch(List<UserInfo> listBean);

	/**
	 * 多条件更新
	 */
	Integer updateByParam(UserInfo bean,UserInfoQuery param);

	/**
	 * 多条件删除
	 */
	Integer deleteByParam(UserInfoQuery param);

	/**
	 * 根据UserId查询对象
	 */
	UserInfo getUserInfoByUserId(Integer userId);


	/**
	 * 根据UserId修改
	 */
	Integer updateUserInfoByUserId(UserInfo bean,Integer userId);


	/**
	 * 根据UserId删除
	 */
	Integer deleteUserInfoByUserId(Integer userId);


	/**
	 * 根据UserNo查询对象
	 */
	UserInfo getUserInfoByUserNo(String userNo);


	/**
	 * 根据UserNo修改
	 */
	Integer updateUserInfoByUserNo(UserInfo bean,String userNo);


	/**
	 * 根据UserNo删除
	 */
	Integer deleteUserInfoByUserNo(String userNo);


	/**
	 * 根据Phone查询对象
	 */
	UserInfo getUserInfoByPhone(String phone);


	/**
	 * 根据Phone修改
	 */
	Integer updateUserInfoByPhone(UserInfo bean,String phone);


	/**
	 * 根据Phone删除
	 */
	Integer deleteUserInfoByPhone(String phone);

}