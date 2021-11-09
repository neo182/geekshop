package org.springframework.demo.geekshop.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.VendorExtension;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.ArrayList;
import java.util.List;

/**
 * The default url for Swagger Doc in localhost : http://localhost:8080/swagger-ui/
 */
@Configuration
public class SwaggerDocConfig {
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.withClassAnnotation(GenerateSwaggerDoc.class))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        List<VendorExtension> vendorExtensions = new ArrayList<>();
        Contact contact = new Contact(
                "Satya Ram Twanabasu",
                "https://www.github.com/neo182",
                "satyaramt@yahoo.com"
        );

        ApiInfo apiInfo = new ApiInfo(
                "Geekshop - REST API",
                "The REST API for Geekshop",
                "0.0.1-SNAPSHOT",
                "Terms of service",
                contact,
                "MIT",
                "https://opensource.org/licenses/MIT",
                vendorExtensions);
        return apiInfo;
    }
}
