package com.bx.utils;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

/**
 * @author lili
 * @version 1.0
 * @date 2026/6/3 9:27
 * @description
 */

@Slf4j
public class HttpUtil {

    /** 默认超时时间（毫秒） */
    private static final int DEFAULT_TIMEOUT = 5000;

    // ==================== GET ====================
    /**
     * @param url    请求地址（可包含查询参数）
     * @param params 查询参数（可为 null）
     * @return {@link String } 响应体，异常时返回 null
     * @description GET请求，支持 URL 拼接参数，返回响应体字符串
     */
    public static String get(String url, Map<String, Object> params) {
        log.info("[HTTP] GET url={} params={}", url, params);
        try {
            HttpRequest request = HttpRequest.get(url)
                    .timeout(DEFAULT_TIMEOUT);
            if (CollUtil.isNotEmpty(params)) {
                params.forEach((k, v) -> request.form(k, v));
            }
            try (HttpResponse response = request.execute()) {
                String result = response.body();
                log.info("[HTTP] GET 响应 statusCode={} body={}", response.getStatus(), result);
                return result;
            }
        } catch (Exception e) {
            log.error("[HTTP] GET 异常 url={}", url, e);
            return null;
        }
    }

    // ==================== POST ====================
    /**
     * @param url 请求地址
     * @param jsonStr JSON 字符串
     * @return {@link String } 响应体，异常时返回 null
     * @description POST 发送 JSON 字符串，返回字符串
     */
    public static String postJson(String url, String jsonStr) {
        return postJson(url, jsonStr, null);
    }

    /**
     * @param url    请求地址
     * @param params 请求参数
     * @return {@link String } 响应体，异常时返回 null
     * @description POST 发送 Map，自动序列化为 JSON，返回字符串
     */
    public static String postJson(String url, Map<String, Object> params) {
        return postJson(url, JSONUtil.toJsonStr(params));
    }

    /**
     * @param url     请求地址
     * @param params  请求参数
     * @param headers 请求头
     * @return {@link String } 响应体，异常时返回 null
     * @description POST 发送 Map，支持自定义请求头
     */
    public static String postJson(String url, Map<String, Object> params, Map<String, String> headers) {
        return postJson(url, JSONUtil.toJsonStr(params), headers);
    }

    /**
     * @param url  请求地址
     * @param body 请求体对象
     * @return {@link String } 响应体，异常时返回 null
     * @description POST 发送对象，自动序列化为 JSON，返回字符串
     */
    public static String postJson(String url, Object body) {
        return postJson(url, JSONUtil.toJsonStr(body));
    }

    /**
     * @param url     请求地址
     * @param jsonStr JSON 字符串
     * @param headers 请求头（可为 null）
     * @return {@link String } 响应体，异常时返回 null
     * @description POST 发送 JSON 字符串，支持自定义请求头，返回字符串
     */
    public static String postJson(String url, String jsonStr, Map<String, String> headers) {
        log.info("[HTTP] POST url={} body={}", url, jsonStr);
        try {
            HttpRequest request = HttpRequest.post(url)
                    .contentType("application/json; charset=utf-8")
                    .timeout(DEFAULT_TIMEOUT)
                    .body(jsonStr);
            if (CollUtil.isNotEmpty(headers)) {
                headers.forEach(request::header);
            }
            try (HttpResponse response = request.execute()) {
                String result = response.body();
                log.info("[HTTP] POST 响应 statusCode={} body={}", response.getStatus(), result);
                return result;
            }
        } catch (Exception e) {
            log.error("[HTTP] POST 异常 url={}", url, e);
            return null;
        }
    }

    // ==================== 响应解析 ====================
    /**
     * 判断响应是否成功（非 null 且能解析为 JSONObject）
     *
     * @param result 响应体字符串
     * @return true 表示响应非空且为合法 JSON
     * @description
     */
    public static boolean isValidJson(String result) {
        if (StrUtil.isBlank(result)) {
            return false;
        }
        return JSONUtil.isTypeJSON(result);
    }
}