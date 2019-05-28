package com.ljc.autapi.testCase;

import com.ljc.autapi.readExcel.ContentType;
import com.ljc.autapi.readExcel.EasyExcel;
import com.ljc.autapi.readExcel.InfoModel;
import com.ljc.autapi.readExcel.InitModel;
import com.ljc.autapi.utils.ApiTool;
import com.ljc.autapi.utils.JsonUtil;
import com.ljc.autapi.utils.ObjectUtil;
import io.qameta.allure.*;
import io.restassured.RestAssured;
import io.restassured.config.SSLConfig;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.qameta.allure.Allure.step;

import java.util.HashMap;
import java.util.Map;

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

    private static final String FILENAME = "api.xlsx";
    private static final String POST = "post";
    private static final String GET = "get";
    private String baseUri = "";
    private Map<String, String> cookies = new HashMap<>();
    private Headers heards = null;

    @BeforeClass
    public void init() {
        for (Object object : easyExcel.readExcel(FILENAME, 1, 2, InitModel.class)) {
            InitModel initModel = (InitModel) object;
            if (ObjectUtil.isNotNull(initModel.getHost()) && ObjectUtil.isNotNull(initModel.getProtocol())) {
                StringBuffer sb = new StringBuffer();
                sb.append(initModel.getProtocol());
                sb.append("://");
                sb.append(initModel.getHost());
                baseUri = sb.toString();
                log.info("接口地址为：" + baseUri);
                if (ObjectUtil.isNotNull(initModel.getPath()) && initModel.getContentType().equalsIgnoreCase(ContentType.FORM)) {
                    Response http = apiTool.postLogin(baseUri, JsonUtil.jsonToMap(initModel.getParameters()));
                    log.info(String.valueOf(http.getStatusCode()));
                    cookies = http.getCookies();
                    heards = http.getHeaders();
                    log.info(http.asString());
                    log.info(String.valueOf(cookies));
                    log.info(String.valueOf(heards));
                } else if (initModel.getContentType().equalsIgnoreCase(ContentType.JSON)) {
                    Response http = apiTool.postLogin(baseUri, initModel.getParameters());
                    log.info(String.valueOf(http.getStatusCode()));
                    cookies = http.getCookies();
                    heards = http.getHeaders();
                    log.info(http.asString());
                    log.info(String.valueOf(cookies));
                    log.info(String.valueOf(heards));
                }

            } else {
                throw new RuntimeException("请填写Protocol及HOST!!!");
            }
        }
        //设置初始化 接口地址
        RestAssured.baseURI = baseUri;

    }

    @Feature("ss")
    @Story("sss")
    @Test
    public void http() {

        for (Object object : easyExcel.readExcel(FILENAME, 2, 1, InfoModel.class)) {
            InfoModel infoModel = (InfoModel) object;
            if (ObjectUtil.isNotNull(infoModel.getId()) && JsonUtil.isJson(infoModel.getParameters())) {

                if (infoModel.getMethod().equalsIgnoreCase(GET)) {
                    Response http = apiTool.getHttp(infoModel.getPath(), JsonUtil.jsonToMap(infoModel.getParameters()), cookies);
                    log.info(String.valueOf(http.getStatusCode()));
                    step(http.asString());
                    Allure.addAttachment("状态", String.valueOf(http.getStatusCode()));
                    Allure.addAttachment("时间", String.valueOf(http.getTime()));
                    System.out.println(http.getHeaders());
                    log.info(http.asString());
                    http.print();
                } else if (infoModel.getMethod().equalsIgnoreCase(POST) && infoModel.getContentType().equalsIgnoreCase(ContentType.FORM)) {
                    Response http = apiTool.postHttp(infoModel.getPath(), JsonUtil.jsonToMap(infoModel.getParameters()));
                    log.info(String.valueOf(http.getStatusCode()));

                    log.info(http.asString());
                } else if (infoModel.getMethod().equalsIgnoreCase(POST) && infoModel.getContentType().equalsIgnoreCase(ContentType.JSON)) {
                    Response http = apiTool.postHttp(infoModel.getPath(), infoModel.getParameters());
                    log.info(String.valueOf(http.getStatusCode()));
                    log.info(http.asString());
                }
            }else if(JsonUtil.isJson(infoModel.getParameters())){
                log.error("JSON格式错误"+infoModel.getParameters()+infoModel.getPath());
            }

        }
    }
}
