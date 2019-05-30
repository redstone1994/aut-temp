package com.ljc.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;
import org.testng.Reporter;

public class Retry implements IRetryAnalyzer {

    private static Logger logger = LoggerFactory.getLogger(Retry.class);
    private int retryCount = 1;
    private static final int maxRetryCount = 1; //重跑次数

    @Override
    public boolean retry(ITestResult iTestResult) {
        if (retryCount <= maxRetryCount) {
            String message = "方法[" + iTestResult.getName() + "]执行失败，重试第" + retryCount + "次";
            retryCount++;
            logger.info(message);
            Reporter.setCurrentTestResult(iTestResult);
            Reporter.log(message);
            return true;
        }
        return false;
    }
}