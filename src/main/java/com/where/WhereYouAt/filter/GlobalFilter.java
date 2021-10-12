package com.where.WhereYouAt.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;

@Slf4j
@WebFilter(urlPatterns = "/user/*")
public class GlobalFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException{
        //전처리
        ContentCachingRequestWrapper httpServletRequest =new ContentCachingRequestWrapper((HttpServletRequest) request);
        ContentCachingResponseWrapper httpServletResponse =new ContentCachingResponseWrapper((HttpServletResponse) response);
        String url = httpServletRequest.getRequestURI();

        chain.doFilter(httpServletRequest,httpServletResponse);

        //후처리
        //req
        String reqContent = new String(httpServletRequest.getContentAsByteArray());

        log.info("request url: {}, request body: {}",url,reqContent);

        String resContent = new String(httpServletResponse.getContentAsByteArray());
        int httpStatus = httpServletResponse.getStatus();

        //이미 읽은 body 의 내용을 다시 채워주기 위해서
        httpServletResponse.copyBodyToResponse();

        log.info("response status: {}, responseBody: {}", httpStatus, resContent);
    }
}
