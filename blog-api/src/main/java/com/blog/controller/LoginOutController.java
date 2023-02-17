package com.blog.controller;

import com.blog.service.TokenService;
import com.blog.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("logout")
public class LoginOutController {
    @Autowired
    private TokenService tokenService;
    @GetMapping
    public Result logout(@RequestHeader("Authorization") String token){
        return tokenService.clearUserInfoByToken(token);
    }
}
