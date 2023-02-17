package com.blog.config.handler;

import com.blog.vo.ErrorCode;
import com.blog.vo.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

//异常处理
@ControllerAdvice
public class AllExceptionHandler {
    @ExceptionHandler
    @ResponseBody
    public Result doException(Exception ex){
        ex.printStackTrace();
        return Result.fail(ErrorCode.SYS_ERROR);
    }
}
