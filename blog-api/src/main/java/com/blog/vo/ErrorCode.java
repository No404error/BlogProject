package com.blog.vo;


public enum ErrorCode {
    SYS_ERROR(-1,"系统异常"),
    PARAMS_ERROR(10001,"参数格式异常"),
    ACCOUNT_PWD_NOT_EXIST(10002,"用户名或密码不存在"),
    NO_PERMISSION(10003,"无访问权限"),
    SESSION_TIME_OUT(10004,"会话超时"),
    NO_LOGIN(10005,"未登录"),
    TOKEN_ERROR(10006,"token出错"),
    ACCOUNT_EXIST(10007,"账号已存在"),
    ;

    private int code;
    private String msg;
    ErrorCode(int code,String msg){
        this.code=code;
        this.msg=msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
