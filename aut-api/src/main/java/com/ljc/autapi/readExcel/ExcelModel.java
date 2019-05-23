package com.ljc.autapi.readExcel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;

/**
 * @Author Lijc
 * @Description excel  model
 * @Date 2019/4/28-10:31
 **/
public class ExcelModel extends BaseRowModel {
    @ExcelProperty(index = 0)
    private String id;
    @ExcelProperty(index = 1)
    private String protocol;
    @ExcelProperty(index = 2)
    private String method;
    @ExcelProperty(index = 3)
    private String host;
    @ExcelProperty(index = 4)
    private String path;
    @ExcelProperty(index = 5)
    private String parameters;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
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
        return "{" +
                "id='" + id + '\'' +
                ", protocol='" + protocol + '\'' +
                ", method='" + method + '\'' +
                ", host='" + host + '\'' +
                ", path='" + path + '\'' +
                ", parameters='" + parameters + '\'' +
                '}';
    }
}
