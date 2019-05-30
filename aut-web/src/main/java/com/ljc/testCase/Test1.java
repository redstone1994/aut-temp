package com.ljc.testCase;

import com.ljc.pageObject.IndexDom;
import com.ljc.utils.Assertion;
import com.ljc.utils.DriverInit;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static com.codeborne.selenide.Selenide.*;

/**
 * @Author Lijc
 * @Description
 * @Date 2019/3/28-10:35
 **/

public class Test1 {

    Logger logger= LoggerFactory.getLogger(Test1.class);
    @BeforeClass
    public void init(){
        DriverInit.setLocal();
    }

    @Feature("demo")
    @Story("test")
    @Test
    public  void test1() {
        open("/");
        logger.info("success open!!!");
        $(IndexDom.INPUT).setValue("百度地图");
        $("aa").click();
        $(IndexDom.BTN).click();
    }
    @Feature("demo")
    @Story("test")
    @Test
    public  void test2() {
        open("/");
        logger.info("success open!!!");

        Assertion.verifyEquals(title(),"百度一下");
        $(IndexDom.INPUT).setValue("百度地图");

        $(IndexDom.BTN).click();
    }

    @Test
    public void Case1(){

        System.out.println("=========Case1=======");
        Assert.assertEquals(1, 1," is not equal");
        Assertion.verifyEquals(1, 2,"is not equal ");
    }

    @Test
    public void Case2(){
        System.out.println("=========Case2=======");
        Assertion.verifyEquals(1, 2,"is not equal ");

    }

    @Test
    public void Case3(){
        System.out.println("=========Case3=======");
        Assertion.verifyEquals(1, 3);

    }

}
