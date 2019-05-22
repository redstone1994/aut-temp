package com.ljc.autapi.utils;

import org.testng.IAnnotationTransformer;
import org.testng.IRetryAnalyzer;
import org.testng.annotations.ITestAnnotation;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;


/**
 * case执行失败重试
 */
public class RetryListener implements IAnnotationTransformer {

    @Override
    public void transform(ITestAnnotation iTestAnnotation, Class aClass, Constructor constructor, Method method) {
        IRetryAnalyzer iRetryAnalyzer = iTestAnnotation.getRetryAnalyzer();
        if (iRetryAnalyzer == null) {
            iTestAnnotation.setRetryAnalyzer(Retry.class);
        }
    }
}