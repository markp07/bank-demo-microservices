package xyz.markpost.accounts;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@EnableDiscoveryClient
@SpringBootApplication
@EnableFeignClients
public class AccountsMicroserviceApplication {

  public static void main(String[] args) {
    SpringApplication.run(AccountsMicroserviceApplication.class, args);
  }

  @Bean
  public SecurityWebFilterChain securityWebFilterChain(
      ServerHttpSecurity serverHttpSecurity){
    return serverHttpSecurity.authorizeExchange()
        .pathMatchers("**").permitAll()
        .anyExchange().authenticated()
        .and().build();
  }

}
