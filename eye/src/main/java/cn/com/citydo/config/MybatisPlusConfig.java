package cn.com.citydo.config;


import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author blackcat
 * @since 2020-07-22
 */
@Configuration
public class MybatisPlusConfig {

    /**
     * 分页插件
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }

    //性能分析插件
//    @Bean
//    @Profile({"dev,test"})
//    public PerformanceInterceptor performanceInterceptor() {
//        PerformanceInterceptor performanceInterceptor = new PerformanceInterceptor();
//        // 格式化sql输出
//        performanceInterceptor.setFormat(true);
//        // 设置sql执行最大时间，单位（ms）
//        performanceInterceptor.setMaxTime(5L);
//
//        return performanceInterceptor;
//    }
}
