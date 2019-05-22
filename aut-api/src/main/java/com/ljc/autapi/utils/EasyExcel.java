package com.ljc.autapi.utils;

import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.metadata.BaseRowModel;
import com.alibaba.excel.metadata.Sheet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @Author Lijc
 * @Description
 * @Date 2019/5/16-14:10
 **/
public class EasyExcel {
    private static Logger logger = LoggerFactory.getLogger(EasyExcel.class);

    /**
     * @param fileName    文件名
     * @param sheetNo     sheet序号
     * @param headLineMun 起始行
     * @param object      继承BaseRowModel的实体
     * @return
     */
    public List<Object> readExcel(String fileName, int sheetNo, int headLineMun, Class<? extends BaseRowModel> object) {
        InputStream inputStream = FileUtil.getResourcesFileInputStream(fileName);
        try {
            // 解析每行结果在listener中处理
            ExcelListener listener = new ExcelListener();
            ExcelReader excelReader = new ExcelReader(inputStream, null, listener, true);
            excelReader.read(new Sheet(sheetNo, headLineMun, object));
            return listener.getDatas();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Test
    public void t() {
        for (Object a : readExcel("data/api.xlsx", 1, 2, ExcelModel.class)) {
            ExcelModel aa = (ExcelModel) a;
            if (ObjectUtil.isNotNull(aa)) {
                System.out.println(aa.getHost());
            }

        }
    }

}
