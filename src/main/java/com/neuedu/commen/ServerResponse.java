package com.neuedu.commen;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class ServerResponse<T> {

    private int status;
    private T data;
    private String msg;

    public ServerResponse() {
    }

    public ServerResponse(int status) {
        this.status = status;
    }

    public ServerResponse(int status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    public ServerResponse(int status, T data, String msg) {
        this.status = status;
        this.data = data;
        this.msg = msg;
    }

//判断是否调用成功
    @JsonIgnore
    public boolean isSuccess(){

        return this.status==Const.SUCCESS_CODE;
    }


    /**
     *成功
     * */

    public static ServerResponse createServerResponseBySucess(){

        return new ServerResponse(Const.SUCCESS_CODE);

    }

    public static ServerResponse createServerResponseBySucess(String msg){

        return new ServerResponse(Const.SUCCESS_CODE,msg);

    }

    public static<T> ServerResponse createServerResponseBySucess(String msg,T data){

        return new ServerResponse(Const.SUCCESS_CODE,data,msg);

    }


    /**
     * 失败
     * */

    public static ServerResponse createServerResponseByError(){

        return new ServerResponse(Const.SUCCESS_ERROR);

    }

    public static ServerResponse createServerResponseByError(String msg){

        return new ServerResponse(Const.SUCCESS_ERROR,msg);

    }

    public static ServerResponse createServerResponseByError(int status){

        return new ServerResponse(status);

    }

    public static ServerResponse createServerResponseByError(int status,String msg){

        return new ServerResponse(status,msg);

    }






    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
