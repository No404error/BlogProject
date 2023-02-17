package com.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.blog.dao.mapper.SysUserMapper;
import com.blog.dao.pojo.SysUser;
import com.blog.service.SysUserService;
import com.blog.service.TokenService;
import com.blog.vo.ErrorCode;
import com.blog.vo.toParams.LoginUserVo;
import com.blog.vo.Result;
import com.blog.vo.toParams.UserVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: 34848
 * @Date: 2023/01/13/20:03
 * @Description:
 */
@Service
public class SysUserServiceImpl implements SysUserService {
    @Autowired
    SysUserMapper sysUserMapper;
    @Autowired
    TokenService tokenService;

    @Override
    public UserVo findUserVoById(Long id) {
        SysUser user = findUserById(id);
        UserVo userVo=new UserVo();
        BeanUtils.copyProperties(user,userVo);
        return userVo;
    }

    @Override
    public SysUser findUserById(Long id) {
        SysUser sysUser = sysUserMapper.selectById(id);
        if(sysUser==null) {
            sysUser = new SysUser();
            sysUser.setNickname("default user");
        }
        return sysUser;
    }

    @Override
    public SysUser findUserByAccount(String account) {
        LambdaQueryWrapper<SysUser> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUser::getAccount,account);
        queryWrapper.last("limit 1");
        return sysUserMapper.selectOne(queryWrapper);
    }

    @Override
    public SysUser findUser(String account, String password) {
        LambdaQueryWrapper<SysUser> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUser::getAccount,account);
        queryWrapper.eq(SysUser::getPassword,password);
        queryWrapper.select(SysUser::getAccount,SysUser::getId,SysUser::getAvatar,SysUser::getNickname);
        queryWrapper.last("limit 1");

        return sysUserMapper.selectOne(queryWrapper);
    }

    @Override
    public Result findUserByToken(String token) {
        SysUser user = tokenService.getInfoByToken(token);
        if(user==null)
            return Result.fail(ErrorCode.TOKEN_ERROR);
        else{
            LoginUserVo loginUserVo = new LoginUserVo();
            BeanUtils.copyProperties(user,loginUserVo);
            return Result.success(loginUserVo);
        }
    }

    @Override
    public void save(SysUser sysUser) {
        sysUserMapper.insert(sysUser);
    }
}
