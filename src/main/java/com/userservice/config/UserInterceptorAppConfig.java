package com.userservice.config;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Component
@Slf4j
@EnableWebMvc
public class UserInterceptorAppConfig extends WebMvcConfigurerAdapter {

    private UserServiceInterceptor userServiceInterceptor;

    public UserInterceptorAppConfig(UserServiceInterceptor userServiceInterceptor) {
        this.userServiceInterceptor = userServiceInterceptor;
    }

    /**
     * This function add the interceptor class in the registry
     * and also specifies which request will we intercept or not
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        log.info("UserInterceptorAppConfig.addInterceptor with paramter registry {}.", registry);
        registry.addInterceptor(userServiceInterceptor)
                .addPathPatterns("/v1/users/{id}", "/v1/users*").excludePathPatterns("/v1/user");
    }
}
