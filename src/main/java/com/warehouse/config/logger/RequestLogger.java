package com.warehouse.config.logger;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

/**
 * Request Logger class for logging requests
 *
 * @author prathush j
 * @version 1.0.0
 * @since 12-Oct-2022
 */
@Order(1)
@Component
@Slf4j
public class RequestLogger implements Filter {

    private final String X_REQUEST_ID = "X-Request-ID";

    @Override
    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        try {
            addXRequestId(req);
            log.debug("path: {}, method: {}, query {}",
                    req.getRequestURI(), req.getMethod(), req.getQueryString());
            res.setHeader(X_REQUEST_ID, MDC.get(X_REQUEST_ID));
            chain.doFilter(request, response);
        } finally {
            log.debug("statusCode {}, path: {}, method: {}, query {}",
                    res.getStatus(), req.getRequestURI(), req.getMethod(), req.getQueryString());
            MDC.clear();
        }
    }

    private void addXRequestId(HttpServletRequest request) {
        String xRequestId = request.getHeader(X_REQUEST_ID);
        if (xRequestId == null) {
            MDC.put(X_REQUEST_ID, UUID.randomUUID().toString());
        } else {
            MDC.put(X_REQUEST_ID, xRequestId);
        }
    }

}





