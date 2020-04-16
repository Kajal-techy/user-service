package com.userservice.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Component
public class UserServiceInterceptor implements HandlerInterceptor {

    private TokenValidationUtil jwtTokenUtil;

    public UserServiceInterceptor(TokenValidationUtil jwtTokenUtil) {
        this.jwtTokenUtil = jwtTokenUtil;
    }

    /**
     * This function performs various operations on the intercepted request then allow that request to the original
     * server after some validation checks.
     *
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
        final String requestTokenHeader = request.getHeader("Authorization");

        String requestParams = request.getQueryString();

        if ((requestParams != null) && (requestParams.contains("userName")) && (!requestParams.contains("password")))
            return true;

        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
            String jwtToken = requestTokenHeader.substring(7);
            String userName = jwtTokenUtil.getUsernameFromToken(jwtToken);
            if (userName != null) {
                if (jwtTokenUtil.validateToken(jwtToken))
                    return true;
            }
        }

        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getOutputStream().println("{\n" + " error : " + "authentication failure" + "\n }");
        return false;
    }
}
