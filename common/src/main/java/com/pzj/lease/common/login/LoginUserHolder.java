package com.pzj.lease.common.login;

public class LoginUserHolder {
    public static ThreadLocal<LoginUser> loginVoThreadLocal = new ThreadLocal<>();

    public static void setLoginUser(LoginUser loginUser) {
        loginVoThreadLocal.set(loginUser);
    }

    public static LoginUser getLoginUser() {
        return loginVoThreadLocal.get();
    }

    public static void clear() {
        loginVoThreadLocal.remove();
    }
}
