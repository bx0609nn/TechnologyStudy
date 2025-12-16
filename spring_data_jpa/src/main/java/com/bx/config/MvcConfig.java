package com.bx.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.TimeZone;

/**
 * @author lili
 * @version 1.0
 * @date 2025/12/15 14:39
 * @description springmvc配置类
 */
@Configuration
public class MvcConfig implements WebMvcConfigurer {
    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        for (HttpMessageConverter<?> converter : converters) {
            if (converter instanceof MappingJackson2HttpMessageConverter) {
                MappingJackson2HttpMessageConverter jacksonConverter =
                        (MappingJackson2HttpMessageConverter) converter;
                // 设置字符编码
                jacksonConverter.setDefaultCharset(StandardCharsets.UTF_8);
                ObjectMapper objectMapper = jacksonConverter.getObjectMapper();
                // 配置日期格式
                objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
                objectMapper.setTimeZone(TimeZone.getDefault());
                // 禁用循环引用检测
                objectMapper.configure(SerializationFeature.FAIL_ON_SELF_REFERENCES, false);
                break; // 找到后退出循环
            }
        }
    }
}
