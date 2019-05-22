package com.ljc.utils;

import com.codeborne.selenide.WebDriverRunner;
import io.qameta.allure.Allure;
import org.openqa.selenium.TakesScreenshot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.IHookCallBack;
import org.testng.IHookable;
import org.testng.ITestResult;

import java.io.ByteArrayInputStream;

import static org.openqa.selenium.OutputType.BYTES;

/**
 * case执行失败自动截图加入allure报告中
 */

public class AllureReporterListener implements IHookable {
    private Logger logger= LoggerFactory.getLogger(AllureReporterListener.class);
//    @Attachment(value = "Failure Screenshot", type = "image/png")
//    public byte[] takeScreenShot(String methodName) throws Exception{
//        File screenshot = Screenshots.getLastScreenshot();
//        return Files.toByteArray(screenshot);
//    }

    @Override
    public void run(IHookCallBack iHookCallBack, ITestResult iTestResult) {
        iHookCallBack.runTestMethod(iTestResult);
        if (iTestResult.getThrowable() != null){
            try {
                logger.info("失败截图中...");
                Allure.addAttachment(iTestResult.getMethod().getMethodName(),
                        "image/png",
                        new ByteArrayInputStream(((TakesScreenshot)WebDriverRunner.getWebDriver()).getScreenshotAs(BYTES)),
                        "png");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

//    @Override
//    public void run(IHookCallBack callBack, ITestResult testResult) {
//        callBack.runTestMethod(testResult);
//        if (testResult.getThrowable() != null) {
//            try {
//                takeScreenShot(testResult.getMethod().getMethodName());
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//    }

}