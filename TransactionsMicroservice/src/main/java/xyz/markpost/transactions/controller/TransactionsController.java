package xyz.markpost.transactions.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.SwaggerDefinition;
import io.swagger.annotations.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@SwaggerDefinition(
    tags = {
        @Tag(name = "Transactions", description = "API request options related to account entities")
    }
)

@RestController
@RequestMapping("/")
@Api(tags = {"Transactions"})
public class TransactionsController {

  @GetMapping(path = "{name}", produces = "application/json")
  public String sayHelloAccounts(@PathVariable(value = "name", required = false) String name) {
    return "Transactions - Hello World, " + name + "!";
  }

}