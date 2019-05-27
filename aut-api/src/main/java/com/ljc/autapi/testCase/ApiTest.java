package com.ljc.autapi.testCase;

import com.ljc.autapi.readExcel.ContentType;
import com.ljc.autapi.readExcel.EasyExcel;
import com.ljc.autapi.readExcel.ExcelModel;
import com.ljc.autapi.utils.ApiTool;
import com.ljc.autapi.utils.JsonUtil;
import com.ljc.autapi.utils.ObjectUtil;
import io.restassured.RestAssured;
import io.restassured.config.ConnectionConfig;
import io.restassured.config.SSLConfig;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static io.restassured.RestAssured.given;

/**
 * @Author Lijc
 * @Description 执行测试，继承AbstractTestNGSpringContextTests
 * @Date 2019/4/12-10:58
 **/

@SpringBootTest
@Slf4j
public class ApiTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private ApiTool apiTool;

    @Autowired
    private EasyExcel easyExcel;

    @Test
    public void test() {
        String ss = "{\"pno\":1,\"ps\":30,\"dtype\":\"json\",\"key\":\"4beb9d77d2b95ce9bec6d8363ee5a620\"}";

        log.info(String.valueOf(JsonUtil.isJson(ss)));
    }

    @Test
    public void getHttp() {
        Map<String, Object> map = new HashMap<>();
//        "seen_ids=&count=5&only_unfollowed=true"
        Response response = given()
                .config((RestAssured.config().sslConfig(new SSLConfig().relaxedHTTPSValidation())))
                .config(RestAssured.config().connectionConfig(ConnectionConfig.connectionConfig().closeIdleConnectionsAfterEachResponseAfter(2, TimeUnit.SECONDS)))
                .contentType("application/x-www-form-urlencoded")
                .params(map)
                .baseUri("http://www.jianshu.com")
                .when()
                .get("users/recommended");
        // 打印出 response 的body
        log.info(response.asString());
    }

    private static Map<String, String> cookies = new HashMap<>();
    private static Headers heards = null;

    //    @BeforeClass
    public void init() {
        for (Object a : easyExcel.readExcel("api.xlsx", 2, 1, ExcelModel.class)) {
            ExcelModel ss = (ExcelModel) a;
            if (ObjectUtil.isNotNull(ss)) {
                StringBuffer sb = new StringBuffer();
                sb.append(ss.getProtocol());
                sb.append("://");
                sb.append(ss.getHost());
                sb.append(ss.getPath());
                log.info(sb.toString());

                Response http = apiTool.getHttp(sb.toString(), JsonUtil.jsonToMap(ss.getParameters()), "");
                log.info(String.valueOf(http.getStatusCode()));
                cookies = http.getCookies();
                heards = http.getHeaders();
                log.info(http.asString());
                log.info(String.valueOf(cookies));
                log.info(String.valueOf(heards));
            }

        }
    }

    @Test
    public void http() {

        for (Object a : easyExcel.readExcel("api.xlsx", 2, 1, ExcelModel.class)) {
            ExcelModel aa = (ExcelModel) a;
            if (ObjectUtil.isNotNull(aa)) {
                StringBuffer sb = new StringBuffer();
                sb.append(aa.getProtocol());
                sb.append("://");
                sb.append(aa.getHost());
                sb.append(aa.getPath());
                log.info(sb.toString());
                if (aa.getMethod().equalsIgnoreCase("get")){
                    Response http = apiTool.getHttp(sb.toString(), JsonUtil.jsonToMap(aa.getParameters()), ContentType.FORM);
                    log.info(String.valueOf(http.getStatusCode()));
                    log.info(http.print());
                    log.info(http.getContentType());
                }else if (aa.getMethod().equalsIgnoreCase("post")){
                    Response http = apiTool.postHttp(sb.toString(), JsonUtil.jsonToMap(aa.getParameters()), ContentType.FORM);
                    log.info(String.valueOf(http.getStatusCode()));
                    log.info(http.print());
                }

            }

        }
    }

    @Test
    public void t() {
        for (Object a : easyExcel.readExcel("api.xlsx", 2, 1, ExcelModel.class)) {
            ExcelModel aa = (ExcelModel) a;
            if (ObjectUtil.isNotNull(aa)) {
                System.out.println(aa.getHost());
                log.info(aa.getParameters());
            }

        }
    }

}
