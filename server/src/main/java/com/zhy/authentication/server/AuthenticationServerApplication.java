package com.zhy.authentication.server;

import com.goudong.boot.redis.EnableCommonsRedisConfig;
import com.goudong.boot.web.EnableCommonsWebMvcConfig;
import com.zhy.authentication.server.config.LogApplicationStartup;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.util.StopWatch;

/**
 * 类描述：
 *
 * @author cfl
 * @version 1.0
 * @date 2023/7/17 14:07
 */
@SpringBootApplication
@EntityScan("com.zhy.authentication.server.domain")
@EnableJpaRepositories(basePackages = {"com.zhy.authentication.server.repository"})
@EnableCommonsWebMvcConfig
@EnableCommonsRedisConfig
public class AuthenticationServerApplication {

    public static void main(String[] args) {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        ConfigurableApplicationContext context = SpringApplication.run(AuthenticationServerApplication.class, args);

        // 获取环境变量
        Environment environment = context.getBean(Environment.class);
        stopWatch.stop();
        LogApplicationStartup.logApplicationStartup(environment, (int)stopWatch.getTotalTimeSeconds());
    }
}
