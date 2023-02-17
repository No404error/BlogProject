package com.blog.service;

import com.blog.dao.pojo.SysUser;
import com.blog.vo.Result;
import com.blog.vo.toParams.UserVo;

/**
 * @Author: 34848
 * @Date: 2023/01/13/20:02
 * @Description:
 */
public interface SysUserService {
    UserVo findUserVoById(Long id);

    SysUser findUserById(Long id);

    SysUser findUserByAccount(String account);

    SysUser findUser(String account, String password);

    Result findUserByToken(String token);

    void save(SysUser sysUser);
}
