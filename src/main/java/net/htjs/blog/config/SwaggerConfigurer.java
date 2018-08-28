package net.htjs.blog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * blog/net.htjs.blog.config
 *
 * @Description:
 * @Author: dingdongliang
 * @Date: 2018/8/13 16:45
 */
@Configuration
@EnableSwagger2
public class SwaggerConfigurer {
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("net.htjs.blog.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        Contact contact = new Contact("dyenigma", "https://github.com/dingdongliang", "dyenigma@163.com");
        return new ApiInfoBuilder()
                .title("航天金穗研发部技术拾遗")
                .description("made by dyenigma")
                .contact(contact)
                .version("0.5")
                .build();
    }
}
