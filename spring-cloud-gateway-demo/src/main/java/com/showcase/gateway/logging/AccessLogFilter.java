package com.showcase.gateway.logging;

import java.io.BufferedReader;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import com.showcase.gateway.config.ProjectProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by youpengfei on 2017/7/28. TODO
 */
public class AccessLogFilter extends ZuulFilter {
    private final static Logger LOGGER = LoggerFactory.getLogger(AccessLogFilter.class);

    @Inject
    private ProjectProperties projectProperties;

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        final HttpServletRequest currentRequest = RequestContext.getCurrentContext().getRequest();
        String requestUri = currentRequest.getRequestURI();
        final Map<String, String[]> parameterMap = currentRequest.getParameterMap();
        final String method = currentRequest.getMethod();
        String parmas = StringUtils.EMPTY;
        String bodyData = StringUtils.EMPTY;
        try {

            //过滤掉敏感词汇，不让他打印出来
            for (String parameterName : projectProperties.getNotInlogParams()) {
                if (parameterMap.containsKey(parameterName)) {
                    parameterMap.remove(parameterName);
                }
            }


            parmas = new ObjectMapper().writeValueAsString(parameterMap);
            final BufferedReader reader = currentRequest.getReader();
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            bodyData = sb.toString();
            //上传文件不打印body内容
            if(bodyData.contains("Content-Type:")){
                bodyData = StringUtils.EMPTY;
            }

        } catch (Exception ignore) {
        }
        if((parameterMap == null || parameterMap.size() == 0) && StringUtils.isEmpty(bodyData)){
            LOGGER.info("requestURI: {},method: {}", requestUri, method);
        }else if((parameterMap != null && parameterMap.size() > 0) && StringUtils.isEmpty(bodyData)){
            LOGGER.info("requestURI: {},method: {},requestParameters:{}", requestUri, method, parmas);
        }else if((parameterMap == null || parameterMap.size() == 0) && StringUtils.isNotEmpty(bodyData)){
            LOGGER.info("requestURI: {},method: {},requestBody:{}", requestUri, method, bodyData);
        }else {
            LOGGER.info("requestURI: {},method: {},requestParameters:{},requestBody:{}", requestUri, method, parmas, bodyData);
        }
        return false;
    }

    @Override
    public Object run() {
        return null;
    }
}
