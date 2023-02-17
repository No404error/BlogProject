package com.blog.service;

import com.blog.vo.Result;
import com.blog.vo.fromParams.LoginParam;

public interface RegisterService {
    Result register(LoginParam loginParam);
}
