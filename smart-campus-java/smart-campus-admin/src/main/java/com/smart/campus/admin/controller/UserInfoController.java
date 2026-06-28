package com.smart.campus.admin.controller;

import com.smart.campus.controller.ABaseController;
import com.smart.campus.entity.po.UserInfo;
import com.smart.campus.entity.query.UserInfoQuery;
import com.smart.campus.entity.vo.PaginationResultVO;
import com.smart.campus.entity.vo.ResponseVO;
import com.smart.campus.service.UserInfoService;
import jakarta.annotation.Resource;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.util.Date;

@RestController
@RequestMapping("/userInfo")
public class UserInfoController extends ABaseController {

    @Resource
    private UserInfoService userInfoService;

    @PostMapping("/loadUserInfoList")
    public ResponseVO<PaginationResultVO<UserInfo>> loadUserInfoList(@RequestBody(required = false) UserInfoQuery query) {
        if (query == null) {
            query = new UserInfoQuery();
        }
        query.setOrderBy("create_time desc");
        PaginationResultVO<UserInfo> result = userInfoService.findListByPage(query);
        return getSuccessResponseVO(result);
    }

    @PostMapping("/getUserInfo")
    public ResponseVO<UserInfo> getUserInfo(@RequestParam Integer userId) {
        UserInfo info = userInfoService.getUserInfoByUserId(userId);
        return getSuccessResponseVO(info);
    }

    @PostMapping("/addUserInfo")
    public ResponseVO<Integer> addUserInfo(@RequestBody UserInfo bean) {
        bean.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes(StandardCharsets.UTF_8)));
        bean.setCreateTime(new Date());
        bean.setStatus(1);
        Integer result = userInfoService.add(bean);
        return getSuccessResponseVO(result);
    }

    @PostMapping("/updateUserInfo")
    public ResponseVO<Integer> updateUserInfo(@RequestBody UserInfo bean) {
        UserInfo updateBean = new UserInfo();
        updateBean.setUserNo(bean.getUserNo());
        updateBean.setRealName(bean.getRealName());
        updateBean.setRoleType(bean.getRoleType());
        updateBean.setFacultyId(bean.getFacultyId());
        updateBean.setMajorId(bean.getMajorId());
        updateBean.setClassId(bean.getClassId());
        updateBean.setPhone(bean.getPhone());
        updateBean.setEmail(bean.getEmail());
        updateBean.setGender(bean.getGender());
        updateBean.setStatus(bean.getStatus());
        Integer result = userInfoService.updateUserInfoByUserId(updateBean, bean.getUserId());
        return getSuccessResponseVO(result);
    }

    @PostMapping("/deleteUserInfo")
    public ResponseVO<Integer> deleteUserInfo(@RequestParam Integer userId) {
        Integer result = userInfoService.deleteUserInfoByUserId(userId);
        return getSuccessResponseVO(result);
    }
}
