package org.danhcao.basic_online_shop.config;

public class ApiUrl {
    String[] urlAnonymous = {
            "/api/account/change-password",      // Thay đổi mật khẩu
            "/api/account/register",
            "/api/account/login",//Đăng nhập cho tất cả
            "/api/account/forgot_password/**",//Quên mật khẩu
            "/api/account/send-reset-token/**",
            "/api/account/reset-password",

    };
    String[] urlUser = {
            "api-example/user",
            "api-example/user1",
            "/api/account/change-password"
    };
    String[] urlAdmin = {
            "/api/banners/create",
            "/api/banners/update",
            "/api/banners/delete",
            "/api/banners/**"
    };
}