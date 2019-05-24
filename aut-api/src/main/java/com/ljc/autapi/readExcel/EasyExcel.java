package com.ljc.autapi.readExcel;

import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.metadata.BaseRowModel;
import com.alibaba.excel.metadata.Sheet;
import com.ljc.autapi.utils.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @Author Lijc
 * @Description
 * @Date 2019/5/16-14:10
 **/
@Slf4j
public class EasyExcel {

    /**
     * @param fileName    文件名
     * @param sheetNo     sheet序号
     * @param headLineMun 起始行
     * @param object      继承BaseRowModel的实体
     * @return
     */
    public static List<Object> readExcel(String fileName, int sheetNo, int headLineMun, Class<? extends BaseRowModel> object) {
        InputStream inputStream = FileUtil.getResourcesFileInputStream(fileName);
        try {
            // 解析每行结果在listener中处理
            ExcelListener listener = new ExcelListener();
            ExcelReader excelReader = new ExcelReader(inputStream, null, listener, true);
            excelReader.read(new Sheet(sheetNo, headLineMun, object));
            return listener.getDatas();

        } catch (Exception e) {
            log.error(e.getMessage());
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                log.error(e.getMessage());
            }
        }
        return null;
    }

//    @Test
//    public void t() {
//        for (Object a : readExcel("api.xlsx", 1, 2, ExcelModel.class)) {
//            ExcelModel aa = (ExcelModel) a;
//            if (ObjectUtil.isNotNull(aa)) {
//                System.out.println(aa.getHost());
//                log.info(aa.getParameters());
//            }
//
//        }
//    }

}
