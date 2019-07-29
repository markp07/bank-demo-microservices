package xyz.markpost.clients.service;


import static xyz.markpost.util.EntityNotFoundMessages.clientNotFound;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.markpost.clients.dto.ClientRequestDTO;
import xyz.markpost.clients.dto.ClientResponseDTO;
import xyz.markpost.clients.model.Client;
import xyz.markpost.clients.repository.ClientRepository;

/**
 *
 */
@Service
@Transactional
public class ClientServiceImpl implements ClientService {

  private final ClientRepository clientRepository;

  @Autowired
  public ClientServiceImpl(
      ClientRepository clientRepository
  ) {
    this.clientRepository = clientRepository;
  }

  /**
   * TODO: check requestDTO
   * @param clientRequestDTO
   * @return
   */
  @Override
  public ClientResponseDTO create(ClientRequestDTO clientRequestDTO) {
    Client client = new Client();

    client.setFirstName(clientRequestDTO.getFirstName());
    client.setLastName(clientRequestDTO.getLastName());
    client.setBirthDate(clientRequestDTO.getBirthDate());
    client.setAddress(clientRequestDTO.getAddress());

    client = clientRepository.save(client);

    return createResponseClient(client);
  }

  /**
   *
   * @param id
   * @return
   */
  @Override
  public ClientResponseDTO findById(Long id) {
    Client client = findSingleClient(id);
    ClientResponseDTO clientResponseDTO = null;

    if (null != client) {
      clientResponseDTO = createResponseClient(client);
    }

    return clientResponseDTO;
  }

  /**
   *
   * @return
   */
  @Override
  public List<ClientResponseDTO> findAll() {
    Iterable<Client> clients = clientRepository.findAll();
    ArrayList<ClientResponseDTO> clientResponseDTOS = new ArrayList<>();

    clients.forEach(client -> {
      ClientResponseDTO clientResponseDTO = createResponseClient(client);
      if (null != clientResponseDTO) {
        clientResponseDTOS.add(clientResponseDTO);
      }
    });

    return clientResponseDTOS;
  }

  /**
   *
   * @param id
   * @param clientRequestDTO
   * @return
   */
  @Override
  public ClientResponseDTO update(Long id, ClientRequestDTO clientRequestDTO) {
    Client client = findSingleClient(id);

    if (null != client) {
      String firstName = clientRequestDTO.getFirstName();
      if (null != firstName) {
        client.setFirstName(firstName);
      }

      String lastName = clientRequestDTO.getLastName();
      if (null != lastName) {
        client.setLastName(lastName);
      }

      Date birthDate = clientRequestDTO.getBirthDate();
      if (null != birthDate) {
        client.setBirthDate(birthDate);
      }

      String address = clientRequestDTO.getAddress();
      if (null != address) {
        client.setAddress(address);
      }

      client = clientRepository.save(client);
    } else {
      throw new EntityNotFoundException(clientNotFound(id));
    }

    return createResponseClient(client);
  }

  /**
   *
   * @param id
   */
  @Override
  public void delete(Long id) {
    Client client = findSingleClient(id);

    if (null != client) {
      clientRepository.delete(client);
    } else {
      throw new EntityNotFoundException(clientNotFound(id));
    }
  }

  /**
   *
   * @param id
   * @return
   */
  private Client findSingleClient(Long id) {
    Optional<Client> clientOptional = clientRepository.findById(id);

    return clientOptional.orElse(null);
  }

  /**
   *
   * @param client
   * @return
   */
  private ClientResponseDTO createResponseClient(Client client) {
    ClientResponseDTO clientResponseDTO = new ClientResponseDTO();

    clientResponseDTO.setId(client.getId());
    clientResponseDTO.setFirstName(client.getFirstName());
    clientResponseDTO.setLastName(client.getLastName());
    clientResponseDTO.setBirthDate(client.getBirthDate());
    clientResponseDTO.setAddress(client.getAddress());

    return clientResponseDTO;
  }

}
