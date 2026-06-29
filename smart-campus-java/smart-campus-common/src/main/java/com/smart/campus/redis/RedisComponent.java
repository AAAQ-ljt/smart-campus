package com.smart.campus.redis;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Component
public class RedisComponent {

    private static final String ADMIN_TOKEN_KEY = "adminToken:login:";
    private static final String CHECK_CODE_KEY = "adminCheckCode:";
    private static final long TOKEN_EXPIRE_HOURS = 2;
    private static final long CHECK_CODE_EXPIRE_MINUTES = 5;

    @Resource
    private RedisUtils redisUtils;

    // ========== 登录 Token ==========

    public String saveAdminLoginToken(Integer userId) {
        String token = UUID.randomUUID().toString().replace("-", "");
        redisUtils.setEx(ADMIN_TOKEN_KEY + token, String.valueOf(userId), TOKEN_EXPIRE_HOURS, TimeUnit.HOURS);
        return token;
    }

    public Integer getAdminLoginUserId(String token) {
        String userId = redisUtils.get(ADMIN_TOKEN_KEY + token);
        return userId != null ? Integer.valueOf(userId) : null;
    }

    public void extendTokenExpire(String token) {
        redisUtils.expire(ADMIN_TOKEN_KEY + token, TOKEN_EXPIRE_HOURS, TimeUnit.HOURS);
    }

    public void removeAdminLoginToken(String token) {
        redisUtils.delete(ADMIN_TOKEN_KEY + token);
    }

    // ========== 验证码 ==========

    public String saveCheckCode(String code) {
        String codeKey = UUID.randomUUID().toString().replace("-", "").substring(0, 16);
        redisUtils.setEx(CHECK_CODE_KEY + codeKey, code.toLowerCase(), CHECK_CODE_EXPIRE_MINUTES, TimeUnit.MINUTES);
        return codeKey;
    }

    public String getCheckCode(String codeKey) {
        return redisUtils.get(CHECK_CODE_KEY + codeKey);
    }

    public void removeCheckCode(String codeKey) {
        redisUtils.delete(CHECK_CODE_KEY + codeKey);
    }
}
