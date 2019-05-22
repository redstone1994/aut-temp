package com.ljc.autapi.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;


public class JsonUtil {
    private static Logger logger = LoggerFactory.getLogger(JsonUtil.class);

    public static Map<String, Object> jsonToMap(String json) {

        Map<String, Object> map = new HashMap();
        if (StringUtil.isNotEmpty(json)) {
            Map<String, Object> jsonMap = JSON.parseObject(json);
            return jsonMap;
        }
        return map;
    }

    public static boolean isJson(String json) {
        try {
            JSONObject.parseObject(json);
            return true;
        } catch (Exception e) {
            logger.error(json + ">>不是正确的json格式");
            return false;
        }
    }

}
