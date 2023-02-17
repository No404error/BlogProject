package com.blog.utils;

import com.blog.dao.pojo.SysUser;

//使用threadLocal保存sysUser信息
public class UserThreadLocal {
    private UserThreadLocal(){};

    private static ThreadLocal<SysUser> userThreadLocal=new ThreadLocal<>();

    public static void setUser(SysUser sysUser){
        userThreadLocal.set(sysUser);
    }

    public static SysUser getUser(){
        return userThreadLocal.get();
    }

    public static void remove(){
        userThreadLocal.remove();
    }

}
