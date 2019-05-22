package com.ljc.autapi.utils;

import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.metadata.Sheet;
import org.testng.annotations.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @Author Lijc
 * @Description 阿里easyexcel读取execl
 * @Date 2019/4/22-13:52
 **/
public class  TestEasyExcel{

    @Test
    public void excel() throws IOException {

        InputStream is = FileUtil.getResourcesFileInputStream("data/api.xlsx");
        ExcelListener excelListener = new ExcelListener();
        EasyExcelFactory.readBySax(is, new Sheet(0, 1, ExcelModel.class), excelListener);

        is.close();

    }

    @Test
    public void excel2() throws IOException {

        InputStream is = FileUtil.getResourcesFileInputStream("data/api.xlsx");

        List<Object> data = EasyExcelFactory.read(is, new Sheet(2, 1, ExcelModel.class));
        for (int i = 1; i < data.size(); i++) {

            System.out.println(((List) data.get(i)).get(3).toString() + ((List) data.get(i)).get(4).toString());
        }
        is.close();
    }
}
