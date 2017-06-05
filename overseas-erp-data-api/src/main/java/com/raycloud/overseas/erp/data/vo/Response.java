package com.raycloud.overseas.erp.data.vo;

import java.io.Serializable;
import java.util.HashMap;

/**
 * ResponseResultVo.java
 * Created by zhongliang
 * Created on 2015/3/27 上午9:26
 * Copyright(c)2014  版权所有
 */
public class Response implements Serializable {


    private static final long serialVersionUID = -2688413907565153033L;
    private String api_name;
    private String message;
    private int result=100;
    private Object data;
    private String redirect;

    public Response() {
        this.data = new HashMap<String, Object>();
    }

    public String getRedirect() {
        return redirect;
    }

    public void setRedirect(String redirect) {
        this.redirect = redirect;
    }

    public String getApi_name() {
        return api_name;
    }

    public void setApi_name(String api_name) {
        this.api_name = api_name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
