package com.showcase.gateway.web.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.filter.OrderedCharacterEncodingFilter;

/**
 * @author iw
 * @create 2017-05-03 15:00
 **/
public class OpenApiCharacterEncodingFilter extends OrderedCharacterEncodingFilter {
    public static final Logger LOGGER = LoggerFactory.getLogger(OpenApiCharacterEncodingFilter.class);
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String uri = request.getRequestURI();
        if(uri.endsWith("gbk")){
            request.setCharacterEncoding("GBK");
        }else{
            request.setCharacterEncoding("UTF-8");
        }
        filterChain.doFilter(request, response);
    }
}
