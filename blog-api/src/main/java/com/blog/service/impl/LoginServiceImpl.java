package com.blog.service.impl;

import com.blog.dao.pojo.SysUser;
import com.blog.service.LoginService;
import com.blog.service.SysUserService;
import com.blog.service.TokenService;
import com.blog.utils.InfoUtils;
import com.blog.vo.ErrorCode;
import com.blog.vo.Result;
import com.blog.vo.fromParams.LoginParam;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private TokenService tokenService;

    @Override
    public Result login(LoginParam loginParam) {
        String account=loginParam.getAccount();
        String password = loginParam.getPassword();

        if(StringUtils.isBlank(account)||StringUtils.isBlank(password))
            return Result.fail(ErrorCode.PARAMS_ERROR);

        password= DigestUtils.md5Hex(password+ InfoUtils.salt);

        SysUser user = sysUserService.findUser(account, password);

        if(user==null)
            return Result.fail(ErrorCode.ACCOUNT_PWD_NOT_EXIST);

        String token = tokenService.storageTokenAndUser(user);

        return Result.success(token);
    }

}