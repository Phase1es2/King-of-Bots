package com.kob.backend.config;

import org.springframework.context.annotation.Configuration;

// Java17 need to change javax.servlet to jakarta.servlet
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
/*
when mapping data from  127.0.0.1:3000 to 127.0.0.1:8080
There is a CORS policy problem

Access to XMLHttpRequest at 'http://127.0.0.1:3000/pk/getBotInfo/'
from origin 'http://localhost:8081' has been blocked by CORS policy:
No 'Access-Control-Allow-Origin' header is present on the requested resource.

Use CorsConfig to fix the problem.
* */

@Configuration
public class CorsConfig implements Filter {
    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) res;
        HttpServletRequest request = (HttpServletRequest) req;

        String origin = request.getHeader("Origin");
        if(origin!=null) {
            response.setHeader("Access-Control-Allow-Origin", origin);
        }

        String headers = request.getHeader("Access-Control-Request-Headers");
        if(headers!=null) {
            response.setHeader("Access-Control-Allow-Headers", headers);
            response.setHeader("Access-Control-Expose-Headers", headers);
        }

        response.setHeader("Access-Control-Allow-Methods", "*");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Credentials", "true");

        chain.doFilter(request, response);
    }

    @Override
    public void init(FilterConfig filterConfig) {

    }

    @Override
    public void destroy() {
    }
}
