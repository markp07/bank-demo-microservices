package xyz.markpost.clients.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.SwaggerDefinition;
import io.swagger.annotations.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@SwaggerDefinition(
    tags = {
        @Tag(name = "Clients", description = "API request options related to client entities")
    }
)

@RestController
@RequestMapping("/")
@Api(tags = {"Clients"})
public class ClientsController {

  @GetMapping(path = "{name:^((?!swagger-ui.html).)*}", produces = "application/json")
  public String sayHelloAccounts(@PathVariable(value = "name", required = false) String name) {
    return "Clients - Hello World, " + name + "!";
  }

}
