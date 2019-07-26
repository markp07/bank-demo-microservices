package xyz.markpost.clients.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import xyz.markpost.clients.dto.ClientRequestDTO;
import xyz.markpost.clients.dto.ClientResponseDTO;
import xyz.markpost.clients.service.ClientService;

@ExtendWith(SpringExtension.class)
class ClientsControllerUT {

  @Mock
  private ClientService clientService;

  @InjectMocks
  ClientsController clientController;


  @Test
  @DisplayName("Test creating a client")
  void createClientTest() {
    ClientRequestDTO clientRequestDTO = new ClientRequestDTO();
    ClientResponseDTO clientResponseDTO = new ClientResponseDTO();
    when(clientService.create(clientRequestDTO)).thenReturn(clientResponseDTO);

    ClientResponseDTO result = clientController.createClient(clientRequestDTO);

    assertThat(result).isEqualTo(clientResponseDTO);
  }

  @Test
  @DisplayName("Test retrieving a client")
  void retrieveSingleClientTest() {
    ClientResponseDTO clientResponseDTO = new ClientResponseDTO();
    when(clientService.findById(any())).thenReturn(clientResponseDTO);

    ClientResponseDTO result = clientController.retrieveSingleClient(1L);

    assertThat(result)
        .isEqualTo(clientResponseDTO);
  }

  @Test
  @DisplayName("Test retrieving all clients")
  void retrieveAllClientTest() {
    List<ClientResponseDTO> clientResponseDTOS = new ArrayList<>();

    ClientResponseDTO clientResponseDTOA = new ClientResponseDTO();
    clientResponseDTOS.add(clientResponseDTOA);

    ClientResponseDTO clientResponseDTOB = new ClientResponseDTO();
    clientResponseDTOS.add(clientResponseDTOB);

    ClientResponseDTO clientResponseDTOC = new ClientResponseDTO();
    clientResponseDTOS.add(clientResponseDTOC);

    when(clientService.findAll()).thenReturn(clientResponseDTOS);

    List<ClientResponseDTO> result = clientController.retrieveAllClients();

    assertThat(result)
        .hasSize(3)
        .isEqualTo(clientResponseDTOS);
  }

  @Test
  @DisplayName("Test updating a client")
  void updateClientTest() {
    ClientRequestDTO clientRequestDTO = new ClientRequestDTO();
    ClientResponseDTO clientResponseDTO = new ClientResponseDTO();
    when(clientService.update(any(Long.class), any(ClientRequestDTO.class)))
        .thenReturn(clientResponseDTO);

    ClientResponseDTO result = clientController.updateClient(1L, clientRequestDTO);

    assertThat(result).isEqualTo(clientResponseDTO);
  }

  @Test
  @DisplayName("Test deleting a client")
  void deleteClientTest() {
    clientController.deleteClient(1L);
    //We cannot assert anything here
  }


}