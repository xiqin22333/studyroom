package org.example.studyroom.dto;

import lombok.Data;

/**
 * 登录请求参数
 */
@Data
public class LoginRequest {

    /** 登录名 */
    private String username;

    /** 密码（明文，后端加密后比对） */
    private String password;
}
