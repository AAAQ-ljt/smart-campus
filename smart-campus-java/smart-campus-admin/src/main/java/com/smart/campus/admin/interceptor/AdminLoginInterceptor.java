package com.smart.campus.admin.interceptor;

import com.smart.campus.controller.LoginContextHolder;
import com.smart.campus.entity.enums.ResponseCodeEnum;
import com.smart.campus.redis.RedisComponent;
import com.alibaba.fastjson2.JSON;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.PrintWriter;
import java.util.LinkedHashMap;
import java.util.Map;

@Component
public class AdminLoginInterceptor implements HandlerInterceptor {

    @Resource
    private RedisComponent redisComponent;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("adminToken");
        if (token == null || token.isEmpty()) {
            writeUnauthorized(response);
            return false;
        }
        Integer userId = redisComponent.getAdminLoginUserId(token);
        if (userId == null) {
            writeUnauthorized(response);
            return false;
        }
        redisComponent.extendTokenExpire(token);
        LoginContextHolder.setCurrentUserId(userId);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        LoginContextHolder.clear();
    }

    private void writeUnauthorized(HttpServletResponse response) throws Exception {
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("status", "error");
        result.put("code", ResponseCodeEnum.CODE_401.getCode());
        result.put("info", ResponseCodeEnum.CODE_401.getMsg());
        result.put("data", null);
        PrintWriter out = response.getWriter();
        out.write(JSON.toJSONString(result));
        out.flush();
    }
}
