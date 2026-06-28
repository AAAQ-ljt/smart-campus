package com.smart.campus.service;

import java.util.List;

import com.smart.campus.entity.query.PaperQuestionQuery;
import com.smart.campus.entity.po.PaperQuestion;
import com.smart.campus.entity.vo.PaginationResultVO;


/**
 * 试卷题目关联表 业务接口
 */
public interface PaperQuestionService {

	/**
	 * 根据条件查询列表
	 */
	List<PaperQuestion> findListByParam(PaperQuestionQuery param);

	/**
	 * 根据条件查询列表
	 */
	Integer findCountByParam(PaperQuestionQuery param);

	/**
	 * 分页查询
	 */
	PaginationResultVO<PaperQuestion> findListByPage(PaperQuestionQuery param);

	/**
	 * 新增
	 */
	Integer add(PaperQuestion bean);

	/**
	 * 批量新增
	 */
	Integer addBatch(List<PaperQuestion> listBean);

	/**
	 * 批量新增/修改
	 */
	Integer addOrUpdateBatch(List<PaperQuestion> listBean);

	/**
	 * 多条件更新
	 */
	Integer updateByParam(PaperQuestion bean,PaperQuestionQuery param);

	/**
	 * 多条件删除
	 */
	Integer deleteByParam(PaperQuestionQuery param);

	/**
	 * 根据PaperQuestionId查询对象
	 */
	PaperQuestion getPaperQuestionByPaperQuestionId(String paperQuestionId);


	/**
	 * 根据PaperQuestionId修改
	 */
	Integer updatePaperQuestionByPaperQuestionId(PaperQuestion bean,String paperQuestionId);


	/**
	 * 根据PaperQuestionId删除
	 */
	Integer deletePaperQuestionByPaperQuestionId(String paperQuestionId);

}