package cn.com.citydo.config.security;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * <p>
 * 自定义配置
 * </p>
 *  @author blackcat
 *  @since 2020-07-21
 */

@ConfigurationProperties(prefix = "custom.config")
@Data
@Component
public class CustomConfig {
    /**
     * 不需要拦截的地址
     */
    private IgnoreConfig ignores;
}
