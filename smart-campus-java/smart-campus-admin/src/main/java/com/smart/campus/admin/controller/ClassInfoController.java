package com.smart.campus.admin.controller;

import com.smart.campus.controller.ABaseController;
import com.smart.campus.entity.po.ClassInfo;
import com.smart.campus.entity.query.ClassInfoQuery;
import com.smart.campus.entity.query.UserInfoQuery;
import com.smart.campus.entity.vo.PaginationResultVO;
import com.smart.campus.entity.vo.ResponseVO;
import com.smart.campus.exception.BusinessException;
import com.smart.campus.service.ClassInfoService;
import com.smart.campus.service.UserInfoService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/classInfo")
public class ClassInfoController extends ABaseController {

    @Resource
    private ClassInfoService classInfoService;

    @Resource
    private UserInfoService userInfoService;

    @PostMapping("/loadClassInfoList")
    public ResponseVO<PaginationResultVO<ClassInfo>> loadClassInfoList(@RequestBody(required = false) ClassInfoQuery query) {
        if (query == null) {
            query = new ClassInfoQuery();
        }
        query.setOrderBy("sort_order asc, create_time desc");
        PaginationResultVO<ClassInfo> result = classInfoService.findListByPage(query);
        return getSuccessResponseVO(result);
    }

    @PostMapping("/getClassInfo")
    public ResponseVO<ClassInfo> getClassInfo(@RequestParam Integer classId) {
        ClassInfo info = classInfoService.getClassInfoByClassId(classId);
        return getSuccessResponseVO(info);
    }

    @PostMapping("/addClassInfo")
    public ResponseVO<Integer> addClassInfo(@RequestBody ClassInfo bean) {
        ClassInfoQuery query = new ClassInfoQuery();
        query.setOrderBy("sort_order desc");
        List<ClassInfo> list = classInfoService.findListByParam(query);
        if (list == null || list.isEmpty()) {
            bean.setSortOrder(1);
        } else {
            bean.setSortOrder(list.get(0).getSortOrder() == null ? 1 : list.get(0).getSortOrder() + 1);
        }
        bean.setCreateTime(new Date());
        Integer result = classInfoService.add(bean);
        return getSuccessResponseVO(result);
    }

    @PostMapping("/updateClassInfo")
    public ResponseVO<Integer> updateClassInfo(@RequestBody ClassInfo bean) {
        ClassInfo updateBean = new ClassInfo();
        updateBean.setClassName(bean.getClassName());
        updateBean.setClassCode(bean.getClassCode());
        updateBean.setMajorId(bean.getMajorId());
        updateBean.setGrade(bean.getGrade());
        updateBean.setDescription(bean.getDescription());
        Integer result = classInfoService.updateClassInfoByClassId(updateBean, bean.getClassId());
        return getSuccessResponseVO(result);
    }

    @PostMapping("/deleteClassInfo")
    public ResponseVO<Integer> deleteClassInfo(@RequestParam Integer classId) {
        UserInfoQuery userQuery = new UserInfoQuery();
        userQuery.setClassId(String.valueOf(classId));
        Integer count = userInfoService.findCountByParam(userQuery);
        if (count != null && count > 0) {
            throw new BusinessException("该班级下存在学生，无法删除");
        }
        Integer result = classInfoService.deleteClassInfoByClassId(classId);
        return getSuccessResponseVO(result);
    }

    @PostMapping("/sortClassInfo")
    public ResponseVO<Void> sortClassInfo(@RequestBody List<Integer> classIds) {
        for (int i = 0; i < classIds.size(); i++) {
            ClassInfo bean = new ClassInfo();
            bean.setSortOrder(i + 1);
            classInfoService.updateClassInfoByClassId(bean, classIds.get(i));
        }
        return getSuccessResponseVO(null);
    }
}
