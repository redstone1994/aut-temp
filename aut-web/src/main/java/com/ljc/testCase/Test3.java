package com.ljc.testCase;

import com.ljc.pageObject.IndexDom;
import com.ljc.utils.DriverInit;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

/**
 * @Author Lijc
 * @Description
 * @Date 2019/3/28-10:36
 **/
public class Test3 {

    Logger logger= LoggerFactory.getLogger(Test3.class);
    @BeforeClass
    public void init(){
        DriverInit.setLocal();
    }

    @Feature("demo")
    @Story("test")
    @Test
    public  void test2() {
        open("/");
        logger.info("success open!!!");
        $(IndexDom.INPUT).setValue("百度地图");
        $(IndexDom.BTN).click();

    }
}
