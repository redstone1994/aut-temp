package com.ljc.autapi.testCase;

import com.ljc.autapi.listener.Assertion;
import com.ljc.autapi.listener.MyTestngListener;
import com.ljc.autapi.listener.RetryListener;
import com.ljc.autapi.readExcel.ContentType;
import com.ljc.autapi.readExcel.EasyExcel;
import com.ljc.autapi.readExcel.InfoModel;
import com.ljc.autapi.readExcel.InitModel;
import com.ljc.autapi.utils.ApiTool;
import com.ljc.autapi.utils.JsonUtil;
import com.ljc.autapi.utils.ObjectUtil;
import io.qameta.allure.Allure;
import io.restassured.RestAssured;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;


/**
 * @Author Lijc
 * @Description 执行测试，继承AbstractTestNGSpringContextTests
 * @Date 2019/4/12-10:58
 **/
@Listeners(value = {MyTestngListener.class, RetryListener.class})
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
                    cookies = http.getCookies();
                    heards = http.getHeaders();
                } else if (initModel.getContentType().equalsIgnoreCase(ContentType.JSON)) {
                    Response http = apiTool.postLogin(baseUri, initModel.getParameters());
                    cookies = http.getCookies();
                    heards = http.getHeaders();
                }

            } else {
                throw new RuntimeException("请填写Protocol及HOST!!!");
            }
        }
        //设置初始化 接口地址
        RestAssured.baseURI = baseUri;

    }


    @Test
    public void http() {

        for (Object object : easyExcel.readExcel(FILENAME, 2, 1, InfoModel.class)) {
            InfoModel infoModel = (InfoModel) object;
            if (ObjectUtil.isNotNull(infoModel.getId()) && JsonUtil.isJson(infoModel.getParameters())) {

                if (infoModel.getMethod().equalsIgnoreCase(GET)) {
                    try {
                        Response http = apiTool.getHttp(infoModel.getPath(), JsonUtil.jsonToMap(infoModel.getParameters()), cookies);
                        Assertion.verifyEquals(http.getStatusCode(),200,"请求错误");
                        Allure.feature(infoModel.getId()+"=====>>>"+http.asString());
                        System.out.println(http.getHeaders());
                    }catch (Exception e){
                        log.error(String.valueOf(e));
                    }

                } else if (infoModel.getMethod().equalsIgnoreCase(POST) && infoModel.getContentType().equalsIgnoreCase(ContentType.FORM)) {
                    try {
                        Response http = apiTool.postHttp(infoModel.getPath(), JsonUtil.jsonToMap(infoModel.getParameters()));
                        log.info(String.valueOf(http.getStatusCode()));
                        log.info(http.asString());
                    }catch (Exception e){
                        log.error(String.valueOf(e));
                    }

                } else if (infoModel.getMethod().equalsIgnoreCase(POST) && infoModel.getContentType().equalsIgnoreCase(ContentType.JSON)) {
                    try {
                        Response http = apiTool.postHttp(infoModel.getPath(), infoModel.getParameters());
                        log.info(String.valueOf(http.getStatusCode()));
                        log.info(http.asString());
                    }catch (Exception e){
                        log.error(String.valueOf(e));
                    }

                }
            }else if(JsonUtil.isJson(infoModel.getParameters())){
                log.error("JSON格式错误"+infoModel.getParameters()+infoModel.getPath());
            }

        }
    }

}
