package com.ljc.autapi.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class JsonUtil {

    public static Map<String, Object> jsonToMap(String json) {

        Map<String, Object> map = new HashMap<>();
        if (StringUtil.isNotEmpty(json)) {
            Map<String, Object> jsonMap;
            jsonMap = JSON.parseObject(json);
            return jsonMap;
        }
        return map;
    }

    public static boolean isJson(String json) {
        try {
            JSONObject.parseObject(json);
            return true;
        } catch (Exception e) {
            log.error(json + "不是正确的json格式");
            return false;
        }
    }

}
