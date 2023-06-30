package cn.com.citydo.config;


import com.github.xiaoymin.swaggerbootstrapui.annotations.EnableSwaggerBootstrapUI;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * <p>
 * Swagger2 的配置
 * </p>
 *  @author blackcat
 *  @since 2020-07-21
 */
@Configuration
@EnableSwagger2
@EnableSwaggerBootstrapUI
public class SwaggerConfig {

    @Value("${swagger2.apiInfo.title:Title}")
    private String title;
    @Value("${swagger2.apiInfo.version:1.0}")
    private String version;
    @Value("${swagger2.apiInfo.description:}")
    private String description;
    @Value("${swagger2.apiInfo.contact.name:Developer}")
    private String name;
    @Value("${swagger2.apiInfo.contact.url:http://127.0.0.1}")
    private String url;
    @Value("${swagger2.apiInfo.contact.email:}")
    private String email;
    @Value("${swagger2.apis.basePackage:*}")
    private String basePackage;

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(new ApiInfoBuilder()
                        .title(title)
                        .version(version)
                        .description(description)
                        .contact(new Contact(name, url, email))
                        .build())
                .select()
                .apis(RequestHandlerSelectors.basePackage(basePackage))
                .paths(PathSelectors.any())
                .build();
    }

}
