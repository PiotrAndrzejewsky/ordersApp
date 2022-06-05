package com.example.ordersApp.security;

public class JwtProperties {
    public static final String SECRET = "This_Is_A_Secret_Code_1234";
    public static final int EXPIRATION_TIME = 86400;
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
}
