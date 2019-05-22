package com.ljc.autapi.utils;

import io.restassured.response.Response;

import java.util.Map;

public interface ApiUtil {

    Response postLogin(String uri, String parm, String contentType);
    Response postLogin(String uri, Map parm, String contentType);
    Response getHttp(String uri, String parm, String contentType);
    Response getHttp(String uri, Map parm, String contentType);
    Response postHttp(String uri, String parm, String contentType);
    Response postHttp(String uri, Map parm, String contentType);
}
