package com.blog.vo.fromParams;

import lombok.Data;

@Data
public class LoginParam {
    private String account;
    private String password;
    private String nickname;
}
