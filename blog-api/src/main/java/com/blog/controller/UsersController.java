package com.blog.controller;

import com.blog.service.SysUserService;
import com.blog.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UsersController {
    @Autowired
    private SysUserService sysUserService;

    @GetMapping("/currentUser")
    public Result getCurrentUser(@RequestHeader("Authorization") String token){
        return sysUserService.findUserByToken(token);
    }
}
