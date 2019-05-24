package com.ljc.autapi.utils;

import io.restassured.RestAssured;
import io.restassured.config.SSLConfig;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @Author Lijc
 * @Description
 * @Date 2019/5/24-16:16
 **/
@Component
@Slf4j
@Async
public class ApiTool {

    public Response postLogin(String uri, String parm, String contentType) {
        return RestAssured.given()
                .config((RestAssured.config().sslConfig(new SSLConfig().relaxedHTTPSValidation())))
                .contentType(contentType)
                .body(parm)
                .get(uri);
    }

    public Response postLogin(String uri, Map parm, String contentType) {
        Response response = RestAssured.given()
                .config((RestAssured.config().sslConfig(new SSLConfig().relaxedHTTPSValidation())))
                .contentType(contentType)
                .body(parm)
                .get(uri);
        return response;
    }

    public Response getHttp(String uri, String parm, String contentType) throws InterruptedException {
        log.info("sssssssssssssssss====================");
        Response response = RestAssured.given()
                .config((RestAssured.config().sslConfig(new SSLConfig().relaxedHTTPSValidation())))
                .contentType(contentType)
                .body(parm)
                .get(uri);
        return response;
    }

    public Response getHttp(String uri, Map parm, String contentType) {
        Response response = RestAssured.given()
                .config((RestAssured.config().sslConfig(new SSLConfig().relaxedHTTPSValidation())))
//                .contentType(contentType)
                .body(parm)
                .post(uri);
        return response;
    }

    public Response postHttp(String uri, String parm, String contentType) {
        Response response = RestAssured.given()
                .config((RestAssured.config().sslConfig(new SSLConfig().relaxedHTTPSValidation())))
                .contentType(contentType)
                .body(parm)
                .post(uri);
        return response;
    }

    public Response postHttp(String uri, Map parm, String contentType) {
        Response response = RestAssured.given()
                .config((RestAssured.config().sslConfig(new SSLConfig().relaxedHTTPSValidation())))
                .contentType(contentType)
                .body(parm)
                .post(uri);
        return response;
    }

    public void sss() {

        for (int i = 0; i <= 10; i++) {
            String a = "aaa";
            log.info("asdadasa-----asdadad------");
        }

    }
}
