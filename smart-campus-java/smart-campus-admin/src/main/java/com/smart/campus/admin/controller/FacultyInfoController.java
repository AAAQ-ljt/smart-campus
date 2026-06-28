package com.smart.campus.admin.controller;

import com.smart.campus.controller.ABaseController;
import com.smart.campus.entity.po.FacultyInfo;
import com.smart.campus.entity.query.FacultyInfoQuery;
import com.smart.campus.entity.vo.ResponseVO;
import com.smart.campus.service.FacultyInfoService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/facultyInfo")
public class FacultyInfoController extends ABaseController {

    @Resource
    private FacultyInfoService facultyInfoService;

    @PostMapping("/loadFacultyInfoList")
    public ResponseVO<List<FacultyInfo>> loadFacultyInfoList(@RequestBody(required = false) FacultyInfoQuery query) {
        if (query == null) {
            query = new FacultyInfoQuery();
        }
        query.setOrderBy("sort_order asc, create_time desc");
        List<FacultyInfo> list = facultyInfoService.findListByParam(query);
        return getSuccessResponseVO(list);
    }

    @PostMapping("/getFacultyInfo")
    public ResponseVO<FacultyInfo> getFacultyInfo(@RequestParam Integer facultyId) {
        FacultyInfo info = facultyInfoService.getFacultyInfoByFacultyId(facultyId);
        return getSuccessResponseVO(info);
    }

    @PostMapping("/addFacultyInfo")
    public ResponseVO<Integer> addFacultyInfo(@RequestBody FacultyInfo bean) {
        FacultyInfoQuery query = new FacultyInfoQuery();
        query.setOrderBy("sort_order desc");
        List<FacultyInfo> list = facultyInfoService.findListByParam(query);
        if (list == null || list.isEmpty()) {
            bean.setSortOrder(1);
        } else {
            bean.setSortOrder(list.get(0).getSortOrder() == null ? 1 : list.get(0).getSortOrder() + 1);
        }
        bean.setCreateTime(new Date());
        Integer result = facultyInfoService.add(bean);
        return getSuccessResponseVO(result);
    }

    @PostMapping("/updateFacultyInfo")
    public ResponseVO<Integer> updateFacultyInfo(@RequestBody FacultyInfo bean) {
        FacultyInfo updateBean = new FacultyInfo();
        updateBean.setFacultyName(bean.getFacultyName());
        updateBean.setFacultyCode(bean.getFacultyCode());
        updateBean.setDescription(bean.getDescription());
        Integer result = facultyInfoService.updateFacultyInfoByFacultyId(updateBean, bean.getFacultyId());
        return getSuccessResponseVO(result);
    }

    @PostMapping("/deleteFacultyInfo")
    public ResponseVO<Integer> deleteFacultyInfo(@RequestParam Integer facultyId) {
        Integer result = facultyInfoService.deleteFacultyInfoByFacultyId(facultyId);
        return getSuccessResponseVO(result);
    }

    @PostMapping("/sortFacultyInfo")
    public ResponseVO<Void> sortFacultyInfo(@RequestBody List<Integer> facultyIds) {
        List<FacultyInfo> updateList = new ArrayList<>();
        for (int i = 0; i < facultyIds.size(); i++) {
            FacultyInfo info = new FacultyInfo();
            info.setFacultyId(facultyIds.get(i));
            info.setSortOrder(i + 1);
            updateList.add(info);
        }
        for (FacultyInfo info : updateList) {
            FacultyInfo bean = new FacultyInfo();
            bean.setSortOrder(info.getSortOrder());
            facultyInfoService.updateFacultyInfoByFacultyId(bean, info.getFacultyId());
        }
        return getSuccessResponseVO(null);
    }
}
