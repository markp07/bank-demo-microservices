package xyz.markpost.clients.apigateway;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("v1/helloworld")
@Api(tags = {"HelloWorld"})
public class HelloWorldController {

  @GetMapping(path = "{name}", produces = "application/json")
  public String sayHelloAccounts(@PathVariable(value = "name", required = false) String name) {
    return "Transactions - Hello World, " + name + "!";
  }

}
