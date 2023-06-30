package cn.com.citydo.config.security;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * <p>
 * JWT 配置
 * </p>
 *  @author blackcat
 *  @since 2020-07-21
 */
@ConfigurationProperties(prefix = "jwt.config")
@Data
@Component
public class JwtConfig {
    /**
     * jwt 加密 key，默认值：blackcat.
     */
    private String key = "blackcat";

    /**
     * jwt 过期时间，默认值：600000 {@code 10 分钟}.
     */
    private Long ttl = 7200000L;

    /**
     * 开启 记住我 之后 jwt 过期时间，默认值 604800000 {@code 7 天}
     */
    private Long remember = 604800000L;
}
