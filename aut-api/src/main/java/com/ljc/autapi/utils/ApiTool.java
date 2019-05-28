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
 * @Description 接口访问工具
 * @Date 2019/5/24-16:16
 **/
@Component
@Slf4j
public class ApiTool {

    public Response postLogin(String uri, String parm) {
        return given()
                .config((RestAssured.config().sslConfig(new SSLConfig().relaxedHTTPSValidation())))
                .body(parm)
                .get(uri);
    }

    public Response postLogin(String uri, Map parm) {
        Response response = given()
                .config((RestAssured.config().sslConfig(new SSLConfig().relaxedHTTPSValidation())))
                .header("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/74.0.3729.169 Safari/537.36")
                .body(parm)
                .get(uri);
        return response;
    }

    public Response getHttp(String path, Map parm,Map cookies) {

        Response response = given()
                .config((RestAssured.config().sslConfig(new SSLConfig().relaxedHTTPSValidation())))
                .header("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/74.0.3729.169 Safari/537.36")
                .params(parm)
                .cookies(cookies)
                .when()
                .get(path);
        return response;
    }

    public Response postHttp(String path, String parm) {
        Response response = given()
                .config((RestAssured.config().sslConfig(new SSLConfig().relaxedHTTPSValidation())))
                .header("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/74.0.3729.169 Safari/537.36")
                .body(parm)
                .post(path);
        return response;
    }

    public Response postHttp(String path, Map parm) {
        Response response = given()
                .config((RestAssured.config().sslConfig(new SSLConfig().relaxedHTTPSValidation())))
                .header("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/74.0.3729.169 Safari/537.36")
                .body(parm)
                .post(path);
        return response;
    }

}
