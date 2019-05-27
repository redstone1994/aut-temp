package com.ljc.autapi.utils;

import io.restassured.RestAssured;
import io.restassured.config.SSLConfig;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;

import static io.restassured.RestAssured.given;

/**
 * @Author Lijc
 * @Description
 * @Date 2019/5/24-16:16
 **/
@Component
@Slf4j
public class ApiTool {

    public Response postLogin(String uri, String parm, String contentType) {
        return given()
                .config((RestAssured.config().sslConfig(new SSLConfig().relaxedHTTPSValidation())))
                .contentType(contentType)
                .body(parm)
                .get(uri);
    }

    public Response postLogin(String uri, Map parm, String contentType) {
        Response response = given()
                .config((RestAssured.config().sslConfig(new SSLConfig().relaxedHTTPSValidation())))
                .contentType(contentType)
                .body(parm)
                .get(uri);
        return response;
    }

    public Response getHttp(String uri, String parm, String contentType) {

        Response response = given()
                .config((RestAssured.config().sslConfig(new SSLConfig().relaxedHTTPSValidation())))
                .contentType(contentType)
                .param(parm)
                .get(uri);
        return response;
    }

    public Response getHttp(String uri, Map parm, String contentType) {

        Response response = given()
                .config((RestAssured.config().sslConfig(new SSLConfig().relaxedHTTPSValidation())))
                .contentType(contentType)
                .params(parm)
                .when()
                .post(uri);
        return response;
    }

    public Response postHttp(String uri, String parm, String contentType) {
        Response response = given()
                .config((RestAssured.config().sslConfig(new SSLConfig().relaxedHTTPSValidation())))
                .contentType(contentType)
                .body(parm)
                .post(uri);
        return response;
    }

    public Response postHttp(String uri, Map parm, String contentType) {
        Response response = given()
                .config((RestAssured.config().sslConfig(new SSLConfig().relaxedHTTPSValidation())))
                .contentType(contentType)
                .body(parm)
                .post(uri);
        return response;
    }

}
