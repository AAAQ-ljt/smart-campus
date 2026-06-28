package com.smart.campus.mappers;

import org.apache.ibatis.annotations.Param;

/**
 * 用户表（管理员/教师/学生统一存储） 数据库操作接口
 */
public interface UserInfoMapper<T,P> extends BaseMapper<T,P> {

	/**
	 * 根据UserId更新
	 */
	 Integer updateByUserId(@Param("bean") T t,@Param("userId") Integer userId);


	/**
	 * 根据UserId删除
	 */
	 Integer deleteByUserId(@Param("userId") Integer userId);


	/**
	 * 根据UserId获取对象
	 */
	 T selectByUserId(@Param("userId") Integer userId);


	/**
	 * 根据UserNo更新
	 */
	 Integer updateByUserNo(@Param("bean") T t,@Param("userNo") String userNo);


	/**
	 * 根据UserNo删除
	 */
	 Integer deleteByUserNo(@Param("userNo") String userNo);


	/**
	 * 根据UserNo获取对象
	 */
	 T selectByUserNo(@Param("userNo") String userNo);


	/**
	 * 根据Phone更新
	 */
	 Integer updateByPhone(@Param("bean") T t,@Param("phone") String phone);


	/**
	 * 根据Phone删除
	 */
	 Integer deleteByPhone(@Param("phone") String phone);


	/**
	 * 根据Phone获取对象
	 */
	 T selectByPhone(@Param("phone") String phone);


}
