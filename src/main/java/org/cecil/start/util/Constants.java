package org.cecil.start.util;

public class Constants {
    public static final String DEFAULT_USER_SCHEMA =
            "classpath:org/springframework/security/core/userdetails/jdbc/users.ddl";
    public static final String BASE_URL = "/api";
    public static final String SECRET = "A$ecretF0r$igningJWTs";
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String SIGNUP_URL = "/users/sign-up";
    public static final String USER_ROLE = "USER";
}
