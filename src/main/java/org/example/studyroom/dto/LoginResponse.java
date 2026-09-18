package org.example.studyroom.dto;

import java.util.List;
import lombok.Data;
import org.example.studyroom.entity.SysUser;

/**
 * 登录成功返回结果：用户信息 + 角色编码列表
 */
@Data
public class LoginResponse {

    /** 用户信息 */
    private SysUser user;

    /** 该用户拥有的角色编码，如 ["ADMIN"] */
    private List<String> roles;

    public LoginResponse() {
    }

    public LoginResponse(SysUser user, List<String> roles) {
        this.user = user;
        this.roles = roles;
    }
}
