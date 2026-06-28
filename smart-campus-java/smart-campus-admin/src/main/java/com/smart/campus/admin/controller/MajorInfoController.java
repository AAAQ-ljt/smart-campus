package com.smart.campus.admin.controller;

import com.smart.campus.controller.ABaseController;
import com.smart.campus.entity.po.MajorInfo;
import com.smart.campus.entity.query.ClassInfoQuery;
import com.smart.campus.entity.query.MajorInfoQuery;
import com.smart.campus.entity.vo.ResponseVO;
import com.smart.campus.exception.BusinessException;
import com.smart.campus.service.ClassInfoService;
import com.smart.campus.service.MajorInfoService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/majorInfo")
public class MajorInfoController extends ABaseController {

    @Resource
    private MajorInfoService majorInfoService;

    @Resource
    private ClassInfoService classInfoService;

    @PostMapping("/loadMajorInfoList")
    public ResponseVO<List<MajorInfo>> loadMajorInfoList(@RequestBody(required = false) MajorInfoQuery query) {
        if (query == null) {
            query = new MajorInfoQuery();
        }
        query.setOrderBy("sort_order asc, create_time desc");
        List<MajorInfo> list = majorInfoService.findListByParam(query);
        return getSuccessResponseVO(list);
    }

    @PostMapping("/getMajorInfo")
    public ResponseVO<MajorInfo> getMajorInfo(@RequestParam Integer majorId) {
        MajorInfo info = majorInfoService.getMajorInfoByMajorId(majorId);
        return getSuccessResponseVO(info);
    }

    @PostMapping("/addMajorInfo")
    public ResponseVO<Integer> addMajorInfo(@RequestBody MajorInfo bean) {
        MajorInfoQuery query = new MajorInfoQuery();
        query.setOrderBy("sort_order desc");
        List<MajorInfo> list = majorInfoService.findListByParam(query);
        if (list == null || list.isEmpty()) {
            bean.setSortOrder(1);
        } else {
            bean.setSortOrder(list.get(0).getSortOrder() == null ? 1 : list.get(0).getSortOrder() + 1);
        }
        bean.setCreateTime(new Date());
        Integer result = majorInfoService.add(bean);
        return getSuccessResponseVO(result);
    }

    @PostMapping("/updateMajorInfo")
    public ResponseVO<Integer> updateMajorInfo(@RequestBody MajorInfo bean) {
        MajorInfo updateBean = new MajorInfo();
        updateBean.setMajorName(bean.getMajorName());
        updateBean.setMajorCode(bean.getMajorCode());
        updateBean.setFacultyId(bean.getFacultyId());
        updateBean.setDescription(bean.getDescription());
        Integer result = majorInfoService.updateMajorInfoByMajorId(updateBean, bean.getMajorId());
        return getSuccessResponseVO(result);
    }

    @PostMapping("/deleteMajorInfo")
    public ResponseVO<Integer> deleteMajorInfo(@RequestParam Integer majorId) {
        ClassInfoQuery classQuery = new ClassInfoQuery();
        classQuery.setMajorId(majorId);
        Integer count = classInfoService.findCountByParam(classQuery);
        if (count != null && count > 0) {
            throw new BusinessException("该专业下存在班级，无法删除");
        }
        Integer result = majorInfoService.deleteMajorInfoByMajorId(majorId);
        return getSuccessResponseVO(result);
    }

    @PostMapping("/sortMajorInfo")
    public ResponseVO<Void> sortMajorInfo(@RequestBody List<Integer> majorIds) {
        for (int i = 0; i < majorIds.size(); i++) {
            MajorInfo bean = new MajorInfo();
            bean.setSortOrder(i + 1);
            majorInfoService.updateMajorInfoByMajorId(bean, majorIds.get(i));
        }
        return getSuccessResponseVO(null);
    }
}
