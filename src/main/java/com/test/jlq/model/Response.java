package com.test.jlq.model;

/**
 * @author hao
 * @Date 2020/9/22
 * @Desc: 返回实体
 */
public class Response<T> {

    private int code;
    private String message;
    private T data;

    public Response() {

    }

    public Response(int code, String msg, T data) {
        this.code = code;
        this.message = msg;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
