package com.blog.service;

import com.blog.vo.Result;
import com.blog.vo.fromParams.LoginParam;

/**
 * @Author: 34848
 * @Date: 2023/01/14/16:48
 * @Description:
 */
public interface RegisterService {
    Result register(LoginParam loginParam);
}
