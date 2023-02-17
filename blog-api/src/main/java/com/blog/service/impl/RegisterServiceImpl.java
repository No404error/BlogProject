package com.blog.service.impl;

import com.blog.dao.pojo.SysUser;
import com.blog.service.RegisterService;
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
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author: 34848
 * @Date: 2023/01/14/16:52
 * @Description:
 */
@Service
@Transactional
public class RegisterServiceImpl implements RegisterService {
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private TokenService tokenService;

    @Override
    public Result register(LoginParam loginParam) {
        String account = loginParam.getAccount();
        String password = loginParam.getPassword();
        String nickname = loginParam.getNickname();

        if (StringUtils.isBlank(account)
                || StringUtils.isBlank(password)
                || StringUtils.isBlank(nickname))
            return Result.fail(ErrorCode.PARAMS_ERROR);

        SysUser sysUser = sysUserService.findUserByAccount(account);

        if(sysUser !=null)
            return Result.fail(ErrorCode.ACCOUNT_EXIST);

        //init a user info
        sysUser = new SysUser();
        sysUser.setNickname(nickname);
        sysUser.setAccount(account);
        sysUser.setPassword(DigestUtils.md5Hex(password+ InfoUtils.salt));
        sysUser.setCreateDate(System.currentTimeMillis());
        sysUser.setLastLogin(System.currentTimeMillis());
        sysUser.setAvatar("/static/img/logo.b3a48c0.png");
        sysUser.setAdmin(1); //1 为true
        sysUser.setDeleted(0); // 0 为false
        sysUser.setSalt("");
        sysUser.setStatus("");
        sysUser.setEmail("");

        sysUserService.save(sysUser);

        String token = tokenService.storageTokenAndUser(sysUser);

        return Result.success(token);
    }
}
