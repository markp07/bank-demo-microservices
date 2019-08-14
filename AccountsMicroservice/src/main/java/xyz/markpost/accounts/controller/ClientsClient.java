package xyz.markpost.accounts.controller;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import xyz.markpost.accounts.dto.ClientResponseDTO;

/**
 * The Client for Accounts Microservice to connect to the Clients Microservice
 */
@FeignClient("clients")
public interface ClientsClient {

  @GetMapping(value = "/{clientId}", produces = "application/json")
  ClientResponseDTO getClient(@PathVariable("clientId") Long clientId);

}