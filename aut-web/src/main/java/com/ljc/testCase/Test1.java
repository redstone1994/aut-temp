package com.ljc.testCase;

import com.ljc.listener.AllureReporterListener;
import com.ljc.listener.Assertion;
import com.ljc.listener.AssertionListener;
import com.ljc.listener.RetryListener;
import com.ljc.pageObject.IndexDom;
import com.ljc.utils.DriverInit;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import static com.codeborne.selenide.Selenide.*;

/**
 * @Author Lijc
 * @Description
 * @Date 2019/3/28-10:35
 **/
@Listeners(value = {AssertionListener.class, AllureReporterListener.class, RetryListener.class})
public class Test1 {

    Logger logger = LoggerFactory.getLogger(Test1.class);

    @BeforeClass
    public void init() {
        DriverInit.setLocal();
    }

    @Feature("demo")
    @Story("test")
    @Test
    public void test1() {
        open("/");
        logger.info("success open!!!");
        Assertion.verifyEquals(1, 2, "is not equal ");
        $(IndexDom.INPUT).setValue("百度地图");

        $(".aa").click();
        $(IndexDom.BTN).click();
    }

    @Feature("demo")
    @Story("test")
    @Test
    public void test2() {
        open("/");
        logger.info("success open!!!");

        Assertion.verifyEquals(title(), "百度一下，你就知道", "ssss=========");
        $(IndexDom.INPUT).setValue("百度地图");

        $(IndexDom.BTN).click();
    }

    @Test
    public void Case1() {
//        Assert.assertEquals(1, 1," is not equal");
        Assertion.verifyEquals(1, 1, "is not equal ");
        System.out.println("=========Case1=======");
    }

    @Test
    public void Case2() {
//        Assert.assertEquals("1","2","ssssssssssss");
        Assertion.verifyEquals(1, 2, "is not equal ");
        System.out.println("=========Case2=======");
    }

    @Test
    public void Case3() {
//        Assert.assertEquals("1","3");
        Assertion.verifyEquals(1, 3);
        System.out.println("=========Case3=======");
    }

}
