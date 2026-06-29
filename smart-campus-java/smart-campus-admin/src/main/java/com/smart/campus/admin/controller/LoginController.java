package com.smart.campus.admin.controller;

import com.smart.campus.controller.ABaseController;
import com.smart.campus.controller.LoginContextHolder;
import com.smart.campus.entity.enums.ResponseCodeEnum;
import com.smart.campus.entity.po.UserInfo;
import com.smart.campus.entity.vo.ResponseVO;
import com.smart.campus.exception.BusinessException;
import com.smart.campus.redis.RedisComponent;
import com.smart.campus.service.UserInfoService;
import com.wf.captcha.SpecCaptcha;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/login")
public class LoginController extends ABaseController {

    @Resource
    private UserInfoService userInfoService;

    @Resource
    private RedisComponent redisComponent;

    @PostMapping("/getCheckCode")
    public ResponseVO<Map<String, String>> getCheckCode(@RequestBody(required = false) Map<String, String> params) {
        if (params != null && params.containsKey("codeKey")) {
            redisComponent.removeCheckCode(params.get("codeKey"));
        }
        SpecCaptcha captcha = new SpecCaptcha(130, 40, 4);
        String code = captcha.text();
        String codeKey = redisComponent.saveCheckCode(code);
        String base64 = captcha.toBase64();
        if (!base64.startsWith("data:")) {
            base64 = "data:image/png;base64," + base64;
        }
        Map<String, String> result = new HashMap<>();
        result.put("codeKey", codeKey);
        result.put("captchaImage", base64);
        return getSuccessResponseVO(result);
    }

    @PostMapping("/login")
    public ResponseVO<Map<String, Object>> login(@RequestBody Map<String, String> params) {
        String account = params.get("userNo");
        String password = params.get("password");
        String codeKey = params.get("codeKey");
        String checkCode = params.get("checkCode");

        if (account == null || password == null || codeKey == null || checkCode == null) {
            throw new BusinessException(ResponseCodeEnum.CODE_600);
        }

        String savedCode = redisComponent.getCheckCode(codeKey);
        if (savedCode == null || !savedCode.equals(checkCode.toLowerCase())) {
            throw new BusinessException(ResponseCodeEnum.CODE_901);
        }
        redisComponent.removeCheckCode(codeKey);

        UserInfo user = userInfoService.getUserInfoByUserNo(account);
        if (user == null) {
            throw new BusinessException("账号或密码错误");
        }
        if (user.getStatus() == null || user.getStatus() != 1) {
            throw new BusinessException("账号已被禁用");
        }

        // 前端已做 MD5，直接比对
        if (!password.equals(user.getPassword())) {
            throw new BusinessException("账号或密码错误");
        }

        String token = redisComponent.saveAdminLoginToken(user.getUserId());

        UserInfo updateBean = new UserInfo();
        updateBean.setLastLoginTime(new Date());
        userInfoService.updateUserInfoByUserId(updateBean, user.getUserId());

        Map<String, Object> result = new HashMap<>();
        result.put("token", token);
        user.setPassword(null);
        result.put("userInfo", user);
        return getSuccessResponseVO(result);
    }

    @PostMapping("/register")
    public ResponseVO<Integer> register(@RequestBody Map<String, String> params) {
        String userNo = params.get("userNo");
        String realName = params.get("realName");
        String phone = params.get("phone");
        String password = params.get("password");

        if (userNo == null || realName == null || phone == null || password == null) {
            throw new BusinessException(ResponseCodeEnum.CODE_600);
        }

        UserInfo existByNo = userInfoService.getUserInfoByUserNo(userNo);
        if (existByNo != null) {
            throw new BusinessException("该工号已被注册");
        }
        UserInfo existByPhone = userInfoService.getUserInfoByPhone(phone);
        if (existByPhone != null) {
            throw new BusinessException("该手机号已被注册");
        }

        UserInfo bean = new UserInfo();
        bean.setUserNo(userNo);
        bean.setRealName(realName);
        bean.setPhone(phone);
        bean.setPassword(password);
        bean.setRoleType("teacher");
        bean.setStatus(1);
        bean.setCreateTime(new Date());
        Integer result = userInfoService.add(bean);
        return getSuccessResponseVO(result);
    }

    @PostMapping("/logout")
    public ResponseVO<Void> logout(HttpServletRequest request) {
        String token = request.getHeader("adminToken");
        if (token != null) {
            redisComponent.removeAdminLoginToken(token);
        }
        LoginContextHolder.clear();
        return getSuccessResponseVO(null);
    }
}
