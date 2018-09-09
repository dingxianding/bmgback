package com.example.filter;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.WebUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 描述：打印所有request，测试用
 * http://slackspace.de/articles/log-request-body-with-spring-boot/
 *
 * @author huchenqiang
 * @date 2018/8/24 11:10
 */
@Component
public class LogRequestFilter extends OncePerRequestFilter implements Ordered {

    private final Log logger = LogFactory.getLog(getClass());

    // put filter at the end of all other filters to make sure we are processing after all others
    private int order = Ordered.LOWEST_PRECEDENCE - 8;

    @Override
    public int getOrder() {
        return order;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        ContentCachingRequestWrapper wrappedRequest = new ContentCachingRequestWrapper(request);

        // pass through filter chain to do the actual request handling
        filterChain.doFilter(wrappedRequest, response);


        // only log request if there was an error

        Map<String, Object> trace = getTrace(wrappedRequest);

        // body can only be read after the actual request handling was done!
        getBody(wrappedRequest, trace);
        logTrace(wrappedRequest, trace);

    }

    private void getBody(ContentCachingRequestWrapper request, Map<String, Object> trace) {
        // wrap request to make sure we can read the body of the request (otherwise it will be consumed by the actual
        // request handler)
        ContentCachingRequestWrapper wrapper = WebUtils.getNativeRequest(request, ContentCachingRequestWrapper.class);
        if (wrapper != null) {
            byte[] buf = wrapper.getContentAsByteArray();
            if (buf.length > 0) {
                String payload;
                try {
                    payload = new String(buf, 0, buf.length, wrapper.getCharacterEncoding());
                } catch (UnsupportedEncodingException ex) {
                    payload = "[unknown]";
                }

                trace.put("body", payload);
            }
        }
    }

    private void logTrace(HttpServletRequest request, Map<String, Object> trace) {
        Object method = trace.get("method");
        Object path = trace.get("path");

        logger.info(String.format("request coming: METHOD %s ---- PATH %s ----- TRACE '%s'", method, path, trace));
    }

    protected Map<String, Object> getTrace(HttpServletRequest request) {
        Principal principal = request.getUserPrincipal();

        Map<String, Object> trace = new LinkedHashMap<String, Object>();
        trace.put("method", request.getMethod());
        trace.put("path", request.getRequestURI());
        trace.put("query", request.getQueryString());
        return trace;
    }

}
