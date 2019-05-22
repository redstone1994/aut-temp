package com.ljc.autapi.utils;

import java.io.InputStream;

/**
 * @Author Lijc
 * @Description
 * @Date 2019/4/22-14:29
 **/
public class FileUtil {

    public static InputStream getResourcesFileInputStream(String fileName){
        return Thread.currentThread().getContextClassLoader().getResourceAsStream(""+fileName);
    }
}
