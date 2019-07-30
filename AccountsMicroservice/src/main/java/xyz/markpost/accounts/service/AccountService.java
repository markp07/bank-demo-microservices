package xyz.markpost.accounts.service;

import java.util.List;
import xyz.markpost.accounts.dto.AccountRequestDTO;
import xyz.markpost.accounts.dto.AccountResponseDTO;
import xyz.markpost.util.dto.TransactionType;

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
   * @param amount
   * @return
   */
  boolean checkBalance(Long id, float amount);

  /**
   *
   * @param id
   * @param amount
   * @param type
   * @return
   */
  boolean updateBalance(Long id, float amount, TransactionType type);

  /**
   *
   * @param id
   */
  void delete(Long id);
}
