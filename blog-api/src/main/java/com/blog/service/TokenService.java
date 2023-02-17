package com.blog.service;

import com.blog.dao.pojo.SysUser;
import com.blog.vo.Result;

public interface TokenService {
    String storageTokenAndUser(SysUser user);

    SysUser getInfoByToken(String token);

    Result clearUserInfoByToken(String token);

}
