package xyz.markpost.accounts.controller;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import xyz.markpost.accounts.model.ClientResponseDTO;

/**
 *
 */
@FeignClient("clients")
public interface ClientsClient {

  @GetMapping(value = "/{clientId}", produces = "application/json")
  ClientResponseDTO getClient(@PathVariable("clientId") Long clientId);

}