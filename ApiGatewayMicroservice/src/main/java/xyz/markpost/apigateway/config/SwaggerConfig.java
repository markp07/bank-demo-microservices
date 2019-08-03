package xyz.markpost.apigateway.config;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.RouteLocator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * The configuration to add Swagger to the application
 */
@Configuration
@EnableSwagger2
@PropertySource("classpath:swagger.properties")
@Profile("!pr")
@Primary
public class SwaggerConfig implements SwaggerResourcesProvider {

  @Autowired
  RouteLocator routeLocator;

  private static final String BASE_PACKAGE = "xyz.markpost.clients.controller";

  @Bean
  public Docket bankDemoApi() {
    return new Docket(DocumentationType.SWAGGER_2)
        .select()
        .apis(RequestHandlerSelectors.basePackage("xyz.markpost.apigateway"))
        .paths(PathSelectors.any())
        .build()
        .apiInfo(apiInfo());
  }

  private ApiInfo apiInfo() {
    return new ApiInfo(
        "Bank Demo - ApiGateway Microservice API",
        "Demo API for api gateway microservice application",
        "1.0",
        "Terms of service",
        new Contact("Mark Post",
            "https://www.markpost.xyz",
            "mark@markpost.xyz"),
        "MIT License",
        "https://opensource.org/licenses/MIT",
        new ArrayList<>());
  }

  /**
   *
   * @return
   */
  @Override
  public List<SwaggerResource> get() {
    //Dynamic introduction of micro services using routeLocator
    List<SwaggerResource> resources = new ArrayList<>();
    resources.add(swaggerResource("ApiGatewayMicroservice", "/api-docs", "1.0"));
    //Recycling Lambda expressions to simplify code
    routeLocator.getRoutes().forEach(route -> {
      //Dynamic acquisition
      resources.add(
          swaggerResource(route.getId(), route.getFullPath().replace("**", "api-docs"), "1.0"));
    });

    return resources;
  }

  /**
   *
   */
  private SwaggerResource swaggerResource(String name, String location, String version) {
    SwaggerResource swaggerResource = new SwaggerResource();
    swaggerResource.setName(name);
    swaggerResource.setLocation(location);
    swaggerResource.setSwaggerVersion(version);
    return swaggerResource;
  }
}
