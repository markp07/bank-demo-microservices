package xyz.markpost.clients.service;

import java.util.List;
import xyz.markpost.clients.dto.ClientRequestDTO;
import xyz.markpost.clients.dto.ClientResponseDTO;

public interface ClientService {

  /**
   *
   */
  ClientResponseDTO create(ClientRequestDTO clientRequestDTO);

  /**
   *
   */
  ClientResponseDTO findById(Long id);

  /**
   *
   */
  List<ClientResponseDTO> findAll();

  /**
   *
   */
  ClientResponseDTO update(Long id, ClientRequestDTO clientRequestDTO);

  /**
   *
   */
  void delete(Long id);

}
