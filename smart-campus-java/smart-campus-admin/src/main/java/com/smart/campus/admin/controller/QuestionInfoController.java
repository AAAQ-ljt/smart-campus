package com.smart.campus.admin.controller;

import com.smart.campus.controller.ABaseController;
import com.smart.campus.controller.LoginContextHolder;
import com.smart.campus.entity.po.QuestionInfo;
import com.smart.campus.entity.po.QuestionOption;
import com.smart.campus.entity.query.QuestionInfoQuery;
import com.smart.campus.entity.query.QuestionOptionQuery;
import com.smart.campus.entity.vo.PaginationResultVO;
import com.smart.campus.entity.vo.ResponseVO;
import com.smart.campus.exception.BusinessException;
import com.smart.campus.service.QuestionInfoService;
import com.smart.campus.service.QuestionOptionService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/questionInfo")
public class QuestionInfoController extends ABaseController {

    @Resource
    private QuestionInfoService questionInfoService;

    @Resource
    private QuestionOptionService questionOptionService;

    private static final String[] OPTION_LETTERS = { "A", "B", "C", "D", "E", "F", "G", "H" };

    /**
     * 分页查询
     */
    @PostMapping("/loadDataList")
    public ResponseVO<PaginationResultVO<QuestionInfo>> loadDataList(@RequestBody QuestionInfoQuery query) {
        query.setOrderBy("create_time desc");
        PaginationResultVO<QuestionInfo> result = questionInfoService.findListByPage(query);
        return getSuccessResponseVO(result);
    }

    /**
     * 获取习题详情（含选项列表）
     */
    @PostMapping("/detail")
    public ResponseVO<Map<String, Object>> detail(@RequestParam String questionId) {
        QuestionInfo question = questionInfoService.getQuestionInfoByQuestionId(questionId);
        if (question == null) throw new BusinessException("习题不存在");

        QuestionOptionQuery optQuery = new QuestionOptionQuery();
        optQuery.setQuestionId(questionId);
        optQuery.setOrderBy("sort_order asc");
        List<QuestionOption> options = questionOptionService.findListByParam(optQuery);

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("question", question);
        result.put("options", options);
        return getSuccessResponseVO(result);
    }

    /**
     * 新增习题（含选项）
     */
    @PostMapping("/add")
    public ResponseVO<String> add(@RequestBody Map<String, Object> params) {
        QuestionInfo question = buildQuestionFromParams(params);
        question.setCreateTime(new Date());
        question.setUpdateTime(new Date());
        questionInfoService.add(question);

        String questionId = question.getQuestionId();
        saveOptions(questionId, params);

        return getSuccessResponseVO(questionId);
    }

    /**
     * 更新习题（含选项）
     */
    @PostMapping("/update")
    public ResponseVO<Integer> update(@RequestBody Map<String, Object> params) {
        String questionId = (String) params.get("questionId");
        if (questionId == null) throw new BusinessException("questionId 不能为空");

        QuestionInfo question = buildQuestionFromParams(params);
        question.setUpdateTime(new Date());
        Integer result = questionInfoService.updateQuestionInfoByQuestionId(question, questionId);

        // 删除旧选项，重新保存
        QuestionOptionQuery delQuery = new QuestionOptionQuery();
        delQuery.setQuestionId(questionId);
        questionOptionService.deleteByParam(delQuery);
        saveOptions(questionId, params);

        return getSuccessResponseVO(result);
    }

    /**
     * 删除习题（含选项）
     */
    @PostMapping("/delete")
    public ResponseVO<Integer> delete(@RequestParam String questionId) {
        QuestionOptionQuery optQuery = new QuestionOptionQuery();
        optQuery.setQuestionId(questionId);
        questionOptionService.deleteByParam(optQuery);

        Integer result = questionInfoService.deleteQuestionInfoByQuestionId(questionId);
        return getSuccessResponseVO(result);
    }

    @SuppressWarnings("unchecked")
    private QuestionInfo buildQuestionFromParams(Map<String, Object> params) {
        QuestionInfo question = new QuestionInfo();
        String questionId = (String) params.get("questionId");
        if (questionId == null || questionId.isEmpty()) {
            questionId = UUID.randomUUID().toString().replace("-", "");
        }
        question.setQuestionId(questionId);
        question.setQuestionTitle((String) params.get("questionTitle"));
        question.setQuestionType((Integer) params.get("questionType"));
        question.setDifficulty((Integer) params.get("difficulty"));
        question.setAnswer((String) params.get("answer"));
        question.setAnalysis((String) params.get("analysis"));
        Object scoreObj = params.get("score");
        question.setScore(scoreObj instanceof Integer ? (Integer) scoreObj : 5);
        Object sortOrderObj = params.get("sortOrder");
        question.setSortOrder(sortOrderObj instanceof Integer ? (Integer) sortOrderObj : 0);
        question.setCreateUserId(LoginContextHolder.getCurrentUserId());
        return question;
    }

    @SuppressWarnings("unchecked")
    private void saveOptions(String questionId, Map<String, Object> params) {
        Object optionsObj = params.get("options");
        if (!(optionsObj instanceof List)) return;

        List<Map<String, Object>> options = (List<Map<String, Object>>) optionsObj;
        List<QuestionOption> batch = new ArrayList<>();

        for (int i = 0; i < options.size(); i++) {
            Map<String, Object> opt = options.get(i);
            QuestionOption option = new QuestionOption();
            option.setOptionId(UUID.randomUUID().toString().replace("-", ""));
            option.setQuestionId(questionId);
            option.setOptionContent((String) opt.get("optionContent"));
            option.setSortOrder(i + 1);
            option.setCreateTime(new Date());
            batch.add(option);
        }

        if (!batch.isEmpty()) {
            questionOptionService.addBatch(batch);
        }
    }
}
