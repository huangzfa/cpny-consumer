package com.cpny.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author: jason
 * @date:2019/4/17 16:38
 */
@Component
public class ComSystemConfig {

    /**  环境: prod:生产.*/
    public static String environment;

    /** 是否生产环境 */
    public static Boolean isProd;

    /** 是否开发环境 */
    public static Boolean isDev;

    @Value("${spring.profiles.active}")
    public void setEnvironment(String environment) {
        ComSystemConfig.environment = environment;
        ComSystemConfig.isProd = "prod".equals(environment);
        ComSystemConfig.isDev = "dev".equals(environment);
    }


}
