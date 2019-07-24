package xyz.markpost.accounts;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class AccountsMicroserviceApplication {

  public static void main(String[] args) {
    SpringApplication.run(AccountsMicroserviceApplication.class, args);
  }

}
