package com.userservice.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Component
public class UserServiceInterceptor implements HandlerInterceptor {

    @Autowired
    private TokenValidationUtil jwtTokenUtil;

    /**
     * This function performs various operations on the intercepted request then allow that request to the original
     * server after some validation checks.
     * @param request
     * @param response
     * @param handler
     * @return boolean
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        log.info("Entering UserServiceInterceptor.preHandle with parameters request {}, response {} and handler {}",
                request, response, handler);
        boolean result = false;
        String requestParams = request.getQueryString();
        if (requestParams != null) {
            if (requestParams.contains("userName") && !requestParams.contains("password"))
                result = true;
        }

        if ((requestParams == null) || (requestParams != null)) {
            final String requestTokenHeader = request.getHeader("Authorization");
            String userName = null;
            String jwtToken = null;
            if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
                jwtToken = requestTokenHeader.substring(7);
                userName = jwtTokenUtil.getUsernameFromToken(jwtToken);
            }

            if (userName != null) {
                if (jwtTokenUtil.validateToken(jwtToken))
                    result = true;
            }
        }

        if (result == false) {
            response.setContentType("application/json");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getOutputStream().println("{\n" + " error : " + "authentication failure" + "\n }");
        }
        return result;
    }
}
