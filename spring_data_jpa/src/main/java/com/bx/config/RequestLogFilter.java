package com.bx.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author lili
 * @version 1.0
 * @date 2026/1/29 9:59
 * @description 请求日志过滤器
 */
@Component
@Slf4j
public class RequestLogFilter extends OncePerRequestFilter {
//    OncePerRequestFilter 是 Spring 提供的一个抽象类，用于确保过滤器在一次请求中只执行一次。
//    在 Servlet 规范中，一个请求可能会经过多次 Filter 链的处理，比如：
//    请求转发（Forward）：一个请求转发到另一个资源时，Filter 可能被执行多次
//    包含（Include）：JSP 页面包含其他资源时
//    错误页面处理：发生异常跳转到错误页面时
//
//    用户请求 → Filter → Controller → Forward 到其他页面 → Filter 又被执行一次 ❌
//    OncePerRequestFilter 保证每个请求只执行一次：
//    用户请求 → Filter（只执行一次）→ Controller → Forward → Filter 跳过 ✅
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String requestUrl = request.getRequestURI();
        log.info("=======当前请求是：{}", requestUrl);
        filterChain.doFilter(request, response);
    }
}
