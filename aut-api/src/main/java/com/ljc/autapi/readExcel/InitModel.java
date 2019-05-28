package com.ljc.autapi.readExcel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;

/**
 * @Author Lijc
 * @Description
 * @Date 2019/5/28-10:07
 **/
public class InitModel extends BaseRowModel {
    @ExcelProperty(index = 0)
    private String protocol;
    @ExcelProperty(index = 1)
    private String host;
    @ExcelProperty(index = 2)
    private String contentType;
    @ExcelProperty(index = 3)
    private String path;
    @ExcelProperty(index = 4)
    private String parameters;

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
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
        return "InitModel{" +
                "protocol='" + protocol + '\'' +
                ", host='" + host + '\'' +
                ", contentType='" + contentType + '\'' +
                ", path='" + path + '\'' +
                ", parameters='" + parameters + '\'' +
                '}';
    }
}
