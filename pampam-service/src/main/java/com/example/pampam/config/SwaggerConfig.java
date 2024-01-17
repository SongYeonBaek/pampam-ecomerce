package com.example.pampam.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;


@Configuration
public class SwaggerConfig {
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(
                        RequestHandlerSelectors.basePackage("com.example.pampam.product")
                                .or(RequestHandlerSelectors.basePackage("com.example.pampam.cart"))
                                .or(RequestHandlerSelectors.basePackage("com.example.pampam.category"))
                                .or(RequestHandlerSelectors.basePackage("com.example.pampam.orders"))

                )
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo("Local Food FarmPam", "v1.0"));
    }


    private ApiInfo apiInfo(String title, String version) {

        return new ApiInfoBuilder()
                .title(title)
                .description("지역상품 공동구매를 제공하는 서비스")
                .termsOfServiceUrl("https://github.com/beyond-sw-camp/be02-2nd-pampam-ecomerce")
                .version("1.0")
                .build();
    }
}
