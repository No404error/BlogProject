package com.blog.service;

import com.blog.vo.Result;
import com.blog.vo.fromParams.LoginParam;

public interface LoginService {

    Result login(LoginParam loginParam);
}
