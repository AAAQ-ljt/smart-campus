package com.smart.campus.admin.controller;

import com.smart.campus.controller.ABaseController;
import com.smart.campus.controller.LoginContextHolder;
import com.smart.campus.entity.po.PaperInfo;
import com.smart.campus.entity.po.PaperQuestion;
import com.smart.campus.entity.po.QuestionInfo;
import com.smart.campus.entity.po.QuestionOption;
import com.smart.campus.entity.query.PaperInfoQuery;
import com.smart.campus.entity.query.PaperQuestionQuery;
import com.smart.campus.entity.vo.PaginationResultVO;
import com.smart.campus.entity.vo.ResponseVO;
import com.smart.campus.exception.BusinessException;
import com.smart.campus.service.PaperInfoService;
import com.smart.campus.service.PaperQuestionService;
import com.smart.campus.service.QuestionInfoService;
import com.smart.campus.service.QuestionOptionService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/paperInfo")
public class PaperInfoController extends ABaseController {

    @Resource
    private PaperInfoService paperInfoService;

    @Resource
    private PaperQuestionService paperQuestionService;

    @Resource
    private QuestionInfoService questionInfoService;

    @Resource
    private QuestionOptionService questionOptionService;

    /**
     * 分页查询试卷列表
     */
    @PostMapping("/loadDataList")
    public ResponseVO<PaginationResultVO<PaperInfo>> loadDataList(@RequestBody PaperInfoQuery query) {
        query.setOrderBy("create_time desc");
        PaginationResultVO<PaperInfo> result = paperInfoService.findListByPage(query);
        return getSuccessResponseVO(result);
    }

    /**
     * 获取试卷详情（含分组与题目信息）
     */
    @PostMapping("/detail")
    public ResponseVO<Map<String, Object>> detail(@RequestParam String paperId) {
        PaperInfo paper = paperInfoService.getPaperInfoByPaperId(paperId);
        if (paper == null) throw new BusinessException("试卷不存在");

        PaperQuestionQuery pqQuery = new PaperQuestionQuery();
        pqQuery.setPaperId(paperId);
        pqQuery.setOrderBy("sort_order asc");
        List<PaperQuestion> paperQuestions = paperQuestionService.findListByParam(pqQuery);

        // 收集所有题目ID
        List<String> questionIds = paperQuestions.stream()
                .map(PaperQuestion::getQuestionId)
                .filter(Objects::nonNull)
                .distinct()
                .collect(Collectors.toList());

        Map<String, QuestionInfo> questionMap = new LinkedHashMap<>();
        Map<String, List<QuestionOption>> optionMap = new LinkedHashMap<>();
        if (!questionIds.isEmpty()) {
            List<QuestionInfo> questions = questionInfoService.findByQuestionIds(questionIds);
            questionMap = questions.stream().collect(Collectors.toMap(
                    QuestionInfo::getQuestionId, q -> q, (a, b) -> a, LinkedHashMap::new));
            List<QuestionOption> allOptions = questionOptionService.findByQuestionIds(questionIds);
            optionMap = allOptions.stream().collect(Collectors.groupingBy(
                    QuestionOption::getQuestionId, LinkedHashMap::new, Collectors.toList()));
        }

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("paper", paper);
        result.put("paperQuestions", paperQuestions);
        result.put("questionMap", questionMap);
        result.put("optionMap", optionMap);
        return getSuccessResponseVO(result);
    }

    /**
     * 新增试卷（含分组与题目）
     */
    @PostMapping("/add")
    @SuppressWarnings("unchecked")
    public ResponseVO<String> add(@RequestBody Map<String, Object> params) {
        PaperInfo paper = buildPaperFromParams(params);
        paper.setCreateTime(new Date());
        paper.setUpdateTime(new Date());
        paperInfoService.add(paper);

        String paperId = paper.getPaperId();
        savePaperQuestions(paperId, params);

        return getSuccessResponseVO(paperId);
    }

    /**
     * 更新试卷（含分组与题目）
     */
    @PostMapping("/update")
    @SuppressWarnings("unchecked")
    public ResponseVO<Integer> update(@RequestBody Map<String, Object> params) {
        String paperId = (String) params.get("paperId");
        if (paperId == null || paperId.isEmpty()) throw new BusinessException("paperId 不能为空");

        PaperInfo paper = buildPaperFromParams(params);
        paper.setUpdateTime(new Date());
        Integer result = paperInfoService.updatePaperInfoByPaperId(paper, paperId);

        // 删除旧的试卷题目关联，重新保存
        PaperQuestionQuery delQuery = new PaperQuestionQuery();
        delQuery.setPaperId(paperId);
        paperQuestionService.deleteByParam(delQuery);
        savePaperQuestions(paperId, params);

        return getSuccessResponseVO(result);
    }

    /**
     * 删除试卷（含分组与题目）
     */
    @PostMapping("/delete")
    public ResponseVO<Integer> delete(@RequestParam String paperId) {
        PaperQuestionQuery pqQuery = new PaperQuestionQuery();
        pqQuery.setPaperId(paperId);
        paperQuestionService.deleteByParam(pqQuery);

        Integer result = paperInfoService.deletePaperInfoByPaperId(paperId);
        return getSuccessResponseVO(result);
    }

    /**
     * 批量查询题目选项
     */
    @PostMapping("/batchQuestionOptions")
    @SuppressWarnings("unchecked")
    public ResponseVO<Map<String, List<QuestionOption>>> batchQuestionOptions(@RequestBody Map<String, Object> params) {
        List<String> questionIds = (List<String>) params.get("questionIds");
        if (questionIds == null || questionIds.isEmpty()) {
            return getSuccessResponseVO(new LinkedHashMap<>());
        }
        List<QuestionOption> options = questionOptionService.findByQuestionIds(questionIds);
        Map<String, List<QuestionOption>> result = options.stream().collect(Collectors.groupingBy(
                QuestionOption::getQuestionId, LinkedHashMap::new, Collectors.toList()));
        return getSuccessResponseVO(result);
    }

    @SuppressWarnings("unchecked")
    private PaperInfo buildPaperFromParams(Map<String, Object> params) {
        PaperInfo paper = new PaperInfo();
        String paperId = (String) params.get("paperId");
        if (paperId == null || paperId.isEmpty()) {
            paperId = UUID.randomUUID().toString().replace("-", "");
        }
        paper.setPaperId(paperId);
        paper.setPaperTitle((String) params.get("paperTitle"));
        paper.setPaperType((Integer) params.get("paperType"));
        paper.setDescription((String) params.get("description"));
        paper.setDuration((Integer) params.get("duration"));
        Object statusObj = params.get("status");
        paper.setStatus(statusObj instanceof Integer ? (Integer) statusObj : 1);
        Object sortOrderObj = params.get("sortOrder");
        paper.setSortOrder(sortOrderObj instanceof Integer ? (Integer) sortOrderObj : 0);
        paper.setCreateUserId(LoginContextHolder.getCurrentUserId());
        return paper;
    }

    @SuppressWarnings("unchecked")
    private void savePaperQuestions(String paperId, Map<String, Object> params) {
        List<Map<String, Object>> groups = (List<Map<String, Object>>) params.get("groups");
        if (groups == null || groups.isEmpty()) return;

        List<PaperQuestion> batch = new ArrayList<>();
        int groupOrder = 0;
        int totalScore = 0;

        for (Map<String, Object> group : groups) {
            String groupName = (String) group.get("groupName");
            List<Map<String, Object>> questions = (List<Map<String, Object>>) group.get("questions");
            groupOrder++;

            String groupId = UUID.randomUUID().toString().replace("-", "");
            PaperQuestion groupNode = new PaperQuestion();
            groupNode.setPaperQuestionId(groupId);
            groupNode.setPaperId(paperId);
            groupNode.setGroupName(groupName);
            groupNode.setSortOrder(groupOrder);
            groupNode.setCreateTime(new Date());
            batch.add(groupNode);

            if (questions == null || questions.isEmpty()) continue;

            int questionOrder = 0;
            for (Map<String, Object> q : questions) {
                String questionId = (String) q.get("questionId");
                Object scoreObj = q.get("score");
                Integer score = scoreObj instanceof Integer ? (Integer) scoreObj
                        : (scoreObj instanceof Number ? ((Number) scoreObj).intValue() : 5);
                questionOrder++;
                totalScore += score;

                PaperQuestion pq = new PaperQuestion();
                pq.setPaperQuestionId(UUID.randomUUID().toString().replace("-", ""));
                pq.setParentPaperQuestionId(groupId);
                pq.setPaperId(paperId);
                pq.setQuestionId(questionId);
                pq.setScore(score);
                pq.setSortOrder(questionOrder);
                pq.setCreateTime(new Date());
                batch.add(pq);
            }
        }

        if (!batch.isEmpty()) {
            paperQuestionService.addBatch(batch);
        }

        PaperInfo updateScore = new PaperInfo();
        updateScore.setTotalScore(totalScore);
        paperInfoService.updatePaperInfoByPaperId(updateScore, paperId);
    }
}
