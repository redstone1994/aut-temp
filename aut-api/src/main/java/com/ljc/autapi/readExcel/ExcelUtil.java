package com.ljc.autapi.readExcel;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.DecimalFormat;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.ljc.autapi.utils.FileUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


/**
 * @Author Lijc
 * @Description
 * @Date 2019/4/11-14:30
 **/

/**
 *
 */

public class ExcelUtil {

    private static final String EXCEL_XLS = "xls";
    private static final String EXCEL_XLSX = "xlsx";

    /**
     * 判断Excel的版本,获取Workbook
     *
     * @param in
     * @param
     * @return
     * @throws IOException
     */
    public static Workbook getWorkbok(InputStream in, File file) throws IOException {
        Workbook wb = null;
        if (file.getName().endsWith(EXCEL_XLS)) {  //Excel 2003
            wb = new HSSFWorkbook(in);
        } else if (file.getName().endsWith(EXCEL_XLSX)) {  // Excel 2007/2010
            wb = new XSSFWorkbook(in);
        }
        return wb;
    }

    /**
     * 判断文件是否是excel
     *
     * @throws Exception
     */
    public static void checkExcelVaild(File file) throws Exception {
        if (!file.exists()) {
            throw new Exception("文件不存在");
        }
        if (!(file.isFile() && (file.getName().endsWith(EXCEL_XLS) || file.getName().endsWith(EXCEL_XLSX)))) {
            throw new Exception("文件不是Excel");
        }
    }

    /**
     * 读取Excel测试，兼容 Excel 2003/2007/2010
     *
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        ExcelUtil ec = new ExcelUtil();

        for (Map<Integer,String> values : ec.readExcelContent().values()) {
//            System.out.println(values);
            for (Map.Entry<Integer, String> value : values.entrySet()) {
                System.out.println(value.getKey()+"="+value.getValue());
            }
        }
    }

    /**
     * 读取Excel数据内容
     *
     * @return Map 包含单元格数据内容的Map对象
     */
    public Map<Integer, Map<Integer, String>> readExcelContent() throws Exception {

        File excelFile = new File(String.valueOf(FileUtil.getResourcesFileInputStream("api.xlsx"))); // 创建文件对象

        FileInputStream in = new FileInputStream(excelFile); // 文件流
        checkExcelVaild(excelFile);
        Workbook workbook = getWorkbok(in, excelFile);
        Map<Integer, Map<Integer, String>> content = new HashMap<>();

        Sheet sheet = workbook.getSheetAt(0);
        // 得到总行数
        int rowNum = sheet.getLastRowNum();
        Row row = sheet.getRow(0);
        int colNum = row.getPhysicalNumberOfCells();

        // 正文内容应该从第二行开始,第一行为表头的标题
        for (int i = 1; i <= rowNum; i++) {
            row = sheet.getRow(i);
            int j = 0;
            Map<Integer, String> cellValue = new HashMap<>();
            while (j < colNum) {
                String obj = getCellValue(row.getCell(j));
                cellValue.put(j, obj);
                j++;
            }
            content.put(i, cellValue);
        }
        return content;
    }

    public static String getCellValue(Cell cell) {
        String cellValue = "";
        if (cell == null) {
            return cellValue;
        }

        //判断数据的类型
        switch (cell.getCellType()) {
            case _NONE:
                break;
            case NUMERIC: //数字
                if (DateUtil.isCellDateFormatted(cell)){
                    DateFormat df=new SimpleDateFormat("yyyy-MM-dd");
                    Date date=cell.getDateCellValue();
                    cellValue=df.format(date);
                }else {
                    DecimalFormat decimalFormat = new DecimalFormat("###################.###########");
                    cellValue = decimalFormat.format(cell.getNumericCellValue());
                }
                break;
            case STRING: //字符串
                cellValue = cell.getStringCellValue();
                break;
            case BOOLEAN: //Boolean
                cellValue = String.valueOf(cell.getBooleanCellValue());
                break;
            case FORMULA: //公式
                cellValue = cell.getCellFormula();
                break;
            case BLANK: //空值
                cellValue = "";
                break;
            case ERROR: //故障
                cellValue = "非法字符";
                break;
            default:
                cellValue = "未知类型";
                break;
        }
        return cellValue;
    }
}