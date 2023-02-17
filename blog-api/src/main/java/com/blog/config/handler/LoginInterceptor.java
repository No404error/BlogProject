package com.blog.config.handler;

import com.alibaba.fastjson.JSON;
import com.blog.dao.pojo.SysUser;
import com.blog.service.TokenService;
import com.blog.utils.UserThreadLocal;
import com.blog.vo.ErrorCode;
import com.blog.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//登录拦截器,在这里处理用户信息的赋值与清理
@Component
@Slf4j
public class LoginInterceptor implements HandlerInterceptor {
    @Autowired
    private TokenService tokenService;

    //在request对应的控制器被执行前将user信息记录到本地变量中
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //不是HandlerMethod方法,例如请求资源等方法就跳过
        if(!(handler instanceof HandlerMethod))
            return true;

        //log日志
        String token = request.getHeader("Authorization");
        log.info("=================request start===========================");
        String requestURI = request.getRequestURI();
        log.info("request uri:{}",requestURI);
        log.info("request method:{}",request.getMethod());
        log.info("token:{}", token);
        log.info("=================request end===========================");

        //只有token合法且有意义时才放行,并保存user至threadLocal
        if(!StringUtils.isBlank(token)){
            SysUser user = tokenService.getInfoByToken(token);
            if(user!=null) {
                UserThreadLocal.setUser(user);
                return true;
            }
        }

        //返回错误信息
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(JSON.toJSONString(Result.fail(ErrorCode.NO_LOGIN)));
        return false;
    }

    //控制器执行后回收资源,防止内存泄露
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        UserThreadLocal.remove();
    }
}
