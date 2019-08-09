package xyz.markpost.accounts.service;

import java.util.List;
import xyz.markpost.accounts.dto.AccountRequestDTO;
import xyz.markpost.accounts.dto.AccountResponseDTO;
import xyz.markpost.util.dto.TransactionType;

/**
 * Business logic for accounts
 */
public interface AccountService {

  /**
   * Create a new account
   * @param accountRequestDTO The data for creating an account
   * @return The response dto for the created account
   */
  AccountResponseDTO create(AccountRequestDTO accountRequestDTO);

  /**
   * Find an account based on its' ID
   * @param id The ID of the account to search
   * @return The response DTO of the found account or null
   */
  AccountResponseDTO findById(Long id);

  /**
   * Find all accounts with this client ID
   * @param clientId The client ID to use
   * @return The found accounts or an empty list
   */
  List<AccountResponseDTO> findByClientId(Long clientId);

  /**
   * Find all accounts in the system
   * @return All accounts or empty list
   */
  List<AccountResponseDTO> findAll();

  /**
   * Update account with given data
   * @param id The ID of the account to update
   * @param accountRequestDTO The data to update
   * @return The response dto of the updated account
   */
  AccountResponseDTO update(Long id, AccountRequestDTO accountRequestDTO);

  /**
   * Check if the account has sufficient balance
   * @param id The ID of the account
   * @param amount The amount the account should minimally have
   * @return Whether or not the account has sufficient balance
   */
  boolean checkBalance(Long id, float amount);

  /**
   * Update the balance of the account
   * @param id The ID of the account where the amount needs to be updated
   * @param amount The amount to add or remove to the current amount
   * @param type The TransactionType to apply
   * @return Whether or not the action succeeded
   */
  boolean updateBalance(Long id, float amount, TransactionType type);

  /**
   * Delete the account
   * @param id The ID of the account to delete
   */
  void delete(Long id);
}
