package com.blog.service.impl;

import com.alibaba.fastjson.JSON;
import com.blog.dao.pojo.SysUser;
import com.blog.service.TokenService;
import com.blog.utils.JWTUtils;
import com.blog.vo.Result;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @Author: 34848
 * @Date: 2023/01/14/15:56
 * @Description:针对token的底层服务
 * */
@Service
public class TokenServiceImpl implements TokenService {
    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    //在redis中储存token和对应的用户信息
    @Override
    public String storageTokenAndUser(SysUser user) {
        String token = JWTUtils.createToken(user.getId());
        redisTemplate.opsForValue().set("TOKEN_"+token, JSON.toJSONString(user),1, TimeUnit.DAYS);
        return token;
    }

    //根据token得到对应用户信息
    @Override
    public SysUser getInfoByToken(String token) {
        if (StringUtils.isBlank(token))
            return null;

        Map<String, Object> map = JWTUtils.checkToken(token);
        if(map==null)
            return null;

        String userJson = redisTemplate.opsForValue().get("TOKEN_" + token);
        if(StringUtils.isBlank(userJson))
            return null;
        SysUser sysUser = JSON.parseObject(userJson, SysUser.class);
        return sysUser;
    }

    //删除redis中对应的用户信息
    @Override
    public Result clearUserInfoByToken(String token) {
        redisTemplate.delete("TOKEN_"+token);
        return Result.success(null);
    }
}
