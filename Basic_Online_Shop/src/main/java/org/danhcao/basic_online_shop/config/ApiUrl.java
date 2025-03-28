package org.danhcao.basic_online_shop.config;

public class ApiUrl {
    public static final String[] URL_ANONYMOUS = {
            "/api/account/register",
            "/api/account/login",
            "/api/account/change-password",
            "/api/account/forgot_password/**",
            "/api/account/send-reset-token/**",
            "/api/account/reset-password",

            "/api/banners/",
            "/api/banners/{id}",

            "/api/category",
            "/api/category/{id}",

            "/api/products",
            "/api/products/{id}"


    };

    public static final String[] URL_USER = {
            "api-example/user",
            "api-example/user1",
            "/api/account/change-password"
    };

    public static final String[] URL_ADMIN = {
            "/api/category/create",
            "/api/category/update/**",
            "/api/category/delete/**",

            "/api/banners/create",
            "/api/banners/update/**",
            "/api/banners/delete/**",

            "/api/products/create",
            "/api/products/update/**",
            "/api/products/delete/**",
    };

}
