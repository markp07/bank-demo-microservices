package xyz.markpost.transactions.config;

import java.util.ArrayList;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * The configuration to add Swagger to the application
 */
@Configuration
@EnableSwagger2
@PropertySource("classpath:swagger.properties")
@Profile("!pr")
public class SwaggerConfig {

  private static final String BASE_PACKAGE = "xyz.markpost.clients.controller";

  @Bean
  public Docket bankDemoApi() {
    return new Docket(DocumentationType.SWAGGER_2)
        .select()
        .apis(RequestHandlerSelectors.basePackage(BASE_PACKAGE))
        .paths(PathSelectors.any())
        .build()
        .apiInfo(apiInfo());
  }

  private ApiInfo apiInfo() {
    return new ApiInfo(
        "Bank Demo - Transactions Microservice API",
        "Demo API for transactions microservice application",
        "1.0",
        "Terms of service",
        new Contact("Mark Post",
            "https://www.markpost.xyz",
            "mark@markpost.xyz"),
        "MIT License",
        "https://opensource.org/licenses/MIT",
        new ArrayList<>());
  }
}
