package com.neuedu.commen;

public enum  ResponseCode {

    PARAM_EMPTY(2,"参数为空"),
    EXIXTS_USERNAME(3,"该用户已存在"),
    NOT_EXIXTS_USERNAME(5,"该用户不存在"),
    EXIXTS_EMAIL(4,"该邮箱已存在"),
    USER_NOT_LOGIN(6,"用户未登录")

    ;
    private int status;
    private String msg;

    ResponseCode() {
    }

    ResponseCode(int status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
