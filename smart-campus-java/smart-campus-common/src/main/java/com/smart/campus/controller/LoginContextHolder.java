package com.smart.campus.controller;

public class LoginContextHolder {

    private static final ThreadLocal<Integer> CURRENT_USER = new ThreadLocal<>();

    public static void setCurrentUserId(Integer userId) {
        CURRENT_USER.set(userId);
    }

    public static Integer getCurrentUserId() {
        return CURRENT_USER.get();
    }

    public static void clear() {
        CURRENT_USER.remove();
    }
}
