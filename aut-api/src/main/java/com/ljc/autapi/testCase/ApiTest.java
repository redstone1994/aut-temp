package com.ljc.autapi.testCase;

import com.ljc.autapi.utils.JsonUtil;
import io.restassured.RestAssured;
import io.restassured.config.SSLConfig;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

/**
 * @Author Lijc
 * @Description
 * @Date 2019/4/12-10:58
 **/
public class ApiTest {
    @Test
    public void test() {
        String ss="{\"pno\":1,\"ps\":30,\"dtype\":\"json\",\"key\":\"4beb9d77d2b95ce9bec6d8363ee5a620\"}";

        System.out.println(JsonUtil.isJson("aaaaa"));
    }

    @Test
    public void getHttp() {
        Map map=new HashMap<>();
//        map.put("seen_ids","");
//        map.put("count","");
//        map.put("only_unfollowed","true");
        //"seen_ids=&count=5&only_unfollowed=true"
        Response response = given()
                .config((RestAssured.config().sslConfig(new SSLConfig().relaxedHTTPSValidation())))
                .contentType("application/x-www-form-urlencoded")
                .params(map)
                .get("http://www.jianshu.com/users/recommended");
        // 打印出 response 的body
        response.print();
        System.out.println(response.getTime());
        System.out.println(response.statusCode());
    }

}
