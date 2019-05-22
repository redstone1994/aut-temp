package com.ljc.autapi.utils;

import io.restassured.RestAssured;
import io.restassured.config.SSLConfig;
import io.restassured.response.Response;

import java.util.Map;

import static io.restassured.RestAssured.given;

public class ApiUtilImpl implements ApiUtil{

    @Override
    public Response postLogin(String uri, String parm, String contentType) {
        Response response = given()
                .config((RestAssured.config().sslConfig(new SSLConfig().relaxedHTTPSValidation())))
                .contentType(contentType)
                .body(parm)
                .get(uri);
        return response;
    }

    @Override
    public Response postLogin(String uri, Map parm, String contentType) {
        Response response = given()
                .config((RestAssured.config().sslConfig(new SSLConfig().relaxedHTTPSValidation())))
                .contentType(contentType)
                .body(parm)
                .get(uri);
        return response;
    }

    /**
     *
     * @param uri url
     * @param parm json参数
     * @param contentType contentType
     * @return
     */
    @Override
    public Response getHttp(String uri, String parm, String contentType) {
        Response response = given()
                .config((RestAssured.config().sslConfig(new SSLConfig().relaxedHTTPSValidation())))
                .contentType(contentType)
                .body(parm)
                .get(uri);
        return response;
    }

    /**
     *
     * @param uri url
     * @param parm form表单参数
     * @param contentType contentType
     * @return
     */
    @Override
    public Response getHttp(String uri, Map parm, String contentType) {
        Response response = given()
                .config((RestAssured.config().sslConfig(new SSLConfig().relaxedHTTPSValidation())))
                .contentType(contentType)
                .body(parm)
                .get(uri);
        return response;
    }

    /**
     *
     * @param uri url
     * @param parm json参数
     * @param contentType contentType
     * @return
     */
    @Override
    public Response postHttp(String uri, String parm, String contentType) {
        Response response=given()
                .config((RestAssured.config().sslConfig(new SSLConfig().relaxedHTTPSValidation())))
                .contentType(contentType)
                .body(parm)
                .post(uri);
        return response;
    }

    /**
     *
     * @param uri url
     * @param parm form表单参数
     * @param contentType contentType
     * @return
     */
    @Override
    public Response postHttp(String uri, Map parm, String contentType) {
        Response response=given()
                .config((RestAssured.config().sslConfig(new SSLConfig().relaxedHTTPSValidation())))
                .contentType(contentType)
                .body(parm)
                .post(uri);
        return response;
    }
}
