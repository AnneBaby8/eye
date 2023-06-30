package cn.com.citydo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Author blackcat
 * @create 2020/9/11 20:57
 * @version: 1.0
 * @description:
 */
@Configuration
public class MyWebMvcConfigurer implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {


        String osName = System.getProperty("user.dir");

        registry.addResourceHandler(osName + "/warnImages/**")
                .addResourceLocations("file:" + osName + "/warnImages/");

    }
}