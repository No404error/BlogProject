package com.blog.service;

import com.blog.dao.pojo.SysUser;
import com.blog.vo.Result;

/**
 * @Author: 34848
 * @Date: 2023/01/14/15:52
 * @Description:调用前做好准备工作
 */

public interface TokenService {
    String storageTokenAndUser(SysUser user);

    SysUser getInfoByToken(String token);

    Result clearUserInfoByToken(String token);

}
