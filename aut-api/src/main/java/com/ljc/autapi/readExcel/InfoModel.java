package com.ljc.autapi.readExcel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;

/**
 * @Author Lijc
 * @Description excel  model
 * @Date 2019/4/28-10:31
 **/
public class InfoModel extends BaseRowModel {
    @ExcelProperty(index = 0)
    private String id;
    @ExcelProperty(index = 1)
    private String method;
    @ExcelProperty(index = 2)
    private String contentType;
    @ExcelProperty(index = 3)
    private String path;
    @ExcelProperty(index = 4)
    private String parameters;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getParameters() {
        return parameters;
    }

    public void setParameters(String parameters) {
        this.parameters = parameters;
    }

    @Override
    public String toString() {
        return "InfoModel{" +
                "id='" + id + '\'' +
                ", method='" + method + '\'' +
                ", contentType='" + contentType + '\'' +
                ", path='" + path + '\'' +
                ", parameters='" + parameters + '\'' +
                '}';
    }
}
