package xyz.markpost.clients.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.SwaggerDefinition;
import io.swagger.annotations.Tag;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import xyz.markpost.clients.dto.ClientRequestDTO;
import xyz.markpost.clients.dto.ClientResponseDTO;
import xyz.markpost.clients.service.ClientService;


@SwaggerDefinition(
    tags = {
        @Tag(name = "Clients", description = "API request options related to client entities")
    }
)

@RestController
@RequestMapping("/")
@Api(tags = {"Clients"})
public class ClientsController {

  private final ClientService clientService;

  @Autowired
  public ClientsController(
      ClientService clientService
  ) {
    this.clientService = clientService;
  }

  /**
   * REST API call for creating an client
   * TODO: add ClientRequestDTO validation (custom annotation?)
   * TODO: swagger annotation
   *
   * @param clientRequestDTO DTO containing data for new client entity
   * @return The response DTO of the created client entity
   */
  @PostMapping(produces = "application/json")
  @ResponseStatus(HttpStatus.CREATED)
  public ClientResponseDTO createClient(@RequestBody ClientRequestDTO clientRequestDTO) {
    return clientService.create(clientRequestDTO);
  }

  /**
   * REST API call for retrieving certain client or all clients
   * TODO: add option for finding set of clients (input list of id's)
   * TODO: swagger annotation
   *
   * @param clientId Client to retrieve (not required)
   * @return List of found clients
   */
  @GetMapping(path = "{clientId}", produces = "application/json")
  public ClientResponseDTO retrieveSingleClient(
      @PathVariable(value = "clientId", required = false) Long clientId) {
    return clientService.findById(clientId);
  }

  /**
   * REST API call for retrieving certain client or all clients
   * TODO: add option for finding set of clients (input list of id's)
   * TODO: swagger annotation
   *
   * @return List of found clients
   */
  @GetMapping(path = "", produces = "application/json")
  public List<ClientResponseDTO> retrieveAllClients() {
    return clientService.findAll();
  }

  /**
   * Update given client
   * TODO: add clientRequestDTO validation (custom annotation?)
   * TODO: swagger annotation
   *
   * @param clientId The id of the client to update
   * @param clientRequestDTO The data of the to update fields
   * @return The updated client
   */
  @PatchMapping(path = "{clientId}", produces = "application/json")
  @ResponseStatus(HttpStatus.OK)
  public ClientResponseDTO updateClient(@PathVariable("clientId") Long clientId,
      @RequestBody ClientRequestDTO clientRequestDTO) {
    return clientService.update(clientId, clientRequestDTO);
  }

  /**
   * Delete the client with the given id
   * TODO: swagger annotation
   *
   * @param clientId The id of the client to delete
   */
  @DeleteMapping(path = "{clientId}", produces = "application/json")
  @ResponseStatus(HttpStatus.OK)
  public void deleteClient(@PathVariable("clientId") Long clientId) {
    clientService.delete(clientId);
  }

}
