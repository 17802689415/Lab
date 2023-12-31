package com.it.utils;

import java.io.Serializable;

public class R<T> implements Serializable {
    private Integer code; //编码：1成功，0和其它数字为失败

    private String msg; //错误信息

    private T data; //数据

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public static <T> R<T> success(T object,int code) {
        R<T> r = new R<T>();
        r.data = object;
        r.code = code;
        return r;
    }


    public static <T> R<T> error(String msg,int code) {
        R<T> r = new R<T>();
        r.msg = msg;
        r.code = code;
        return r;
    }


}
