package xyz.markpost.accounts.service;

import java.util.List;
import xyz.markpost.accounts.dto.AccountRequestDTO;
import xyz.markpost.accounts.dto.AccountResponseDTO;

/**
 *
 */
public interface AccountService {

  /**
   *
   * @param accountRequestDTO
   * @return
   */
  AccountResponseDTO create(AccountRequestDTO accountRequestDTO);

  /**
   *
   * @param id
   * @return
   */
  AccountResponseDTO findById(Long id);

  /**
   *
   * @param clientId
   * @return
   */
  List<AccountResponseDTO> findByClientId(Long clientId);

  /**
   *
   * @return
   */
  List<AccountResponseDTO> findAll();

  /**
   *
   * @param id
   * @param accountRequestDTO
   * @return
   */
  AccountResponseDTO update(Long id, AccountRequestDTO accountRequestDTO);

  /**
   *
   * @param id
   */
  void delete(Long id);
}
