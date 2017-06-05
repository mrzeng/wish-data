package com.raycloud.overseas.erp.data.request;


import com.raycloud.overseas.erp.data.vo.Response;

/**
 * Created by forest on 14-9-10.
 */
public class Request {

    public boolean validate(Request request){
        return true;
    }

    private String api_name;

    private Response response;

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

    public String getApi_name() {
        return api_name;
    }

    public void setApi_name(String api_name) {
        this.api_name = api_name;
    }

    public boolean validate(Response response){return true;}
}
