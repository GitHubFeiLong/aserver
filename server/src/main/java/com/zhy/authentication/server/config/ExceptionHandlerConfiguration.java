package com.zhy.authentication.server.config;

import com.goudong.boot.web.core.ErrorAttributesService;
import com.zhy.authentication.server.handler.JwtExceptionHandler;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import javax.servlet.http.HttpServletRequest;

/**
 * 类描述：
 *
 * @author cfl
 * @version 1.0
 * @date 2023/7/24 9:15
 */
@Order(Ordered.HIGHEST_PRECEDENCE)
@Configuration
public class ExceptionHandlerConfiguration {

    @Bean
    public JwtExceptionHandler jwtExceptionHandler() {
        return new JwtExceptionHandler();
    }


}
