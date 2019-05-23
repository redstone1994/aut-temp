package com.ljc.autapi.utils;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * @Author Lijc
 * @Description
 * @Date 2019/4/22-14:29
 **/
@Slf4j
public class FileUtil {

    public static InputStream getResourcesFileInputStream(String fileName){
        File f=new File("data/"+fileName);

        try {
            InputStream is=new FileInputStream(f.getAbsolutePath());
            return is;
        } catch (FileNotFoundException e) {
            log.error(e.getMessage());
        }
//        Thread thread = Thread.currentThread();
//        ClassLoader contextClassLoader = thread.getContextClassLoader();
//        InputStream resourceAsStream = contextClassLoader.getResourceAsStream(fileName);
//        return resourceAsStream;
//        return Thread.currentThread().getContextClassLoader().getResourceAsStream(""+fileName);
        return null;
    }

}
