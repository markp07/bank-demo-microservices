package xyz.markpost.accounts.service;

import static xyz.markpost.util.EntityNotFoundMessages.accountNotFound;
import static xyz.markpost.util.EntityNotFoundMessages.clientNotFound;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;
import javax.persistence.EntityNotFoundException;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.markpost.accounts.controller.ClientsClient;
import xyz.markpost.accounts.dto.AccountRequestDTO;
import xyz.markpost.accounts.dto.AccountResponseDTO;
import xyz.markpost.accounts.model.Account;
import xyz.markpost.accounts.model.AccountType;
import xyz.markpost.accounts.dto.ClientResponseDTO;
import xyz.markpost.accounts.repository.AccountRepository;
import xyz.markpost.util.dto.TransactionType;

/**
 * Business logic for accounts
 */
@Service
@Transactional
@Log4j2
public class AccountServiceImpl implements AccountService {

  private final AccountRepository accountRepository;

  private final ClientsClient clientsClient;

  @Autowired
  public AccountServiceImpl(
      AccountRepository accountRepository,
      ClientsClient clientsClient
  ) {
    this.accountRepository = accountRepository;
    this.clientsClient = clientsClient;
  }

  private static final long BANK_NUMBER_MIN = 10000000;
  private static final long BANK_NUMBER_MAX = 99999999;

  /**
   * Create a new account
   * TODO: check request DTO
   * @param accountRequestDTO The data for creating an account
   * @return The response dto for the created account
   */
  @Override
  public AccountResponseDTO create(AccountRequestDTO accountRequestDTO) {
    ClientResponseDTO client = clientsClient.getClient(accountRequestDTO.getClientId());

    if (null != client) {
      Account account = new Account();

      account.setClientId(accountRequestDTO.getClientId());
      account.setNumber(createAccountNumber());
      account.setType(accountRequestDTO.getType());
      account.setBalance(0);

      account = accountRepository.save(account);

      return createResponseAccount(account);
    } else {
      throw new EntityNotFoundException(clientNotFound(accountRequestDTO.getClientId()));
    }
  }

  /**
   * Find an account based on its' ID
   * @param id The ID of the account to search
   * @return The response DTO of the found account or null
   */
  @Override
  public AccountResponseDTO findById(Long id) {
    Account account = findSingleAccount(id);
    AccountResponseDTO accountResponseDTO = null;

    if (null != account) {
      accountResponseDTO = createResponseAccount(account);
    }

    return accountResponseDTO;
  }

  /**
   * Find all accounts with this client ID
   * @param clientId The client ID to use
   * @return The found accounts or an empty list
   */
  @Override
  public List<AccountResponseDTO> findByClientId(Long clientId) {
    List<Account> accounts = accountRepository.findAccountsByClientId(clientId);

    return handleFoundAccounts(accounts);
  }

  /**
   * Find all accounts in the system
   * @return All accounts or empty list
   */
  @Override
  public List<AccountResponseDTO> findAll() {
    Iterable<Account> accounts = accountRepository.findAll();

    return handleFoundAccounts(accounts);
  }

  /**
   * Update account with given data
   * @param id The ID of the account to update
   * @param accountRequestDTO The data to update
   * @return The response dto of the updated account
   */
  @Override
  public AccountResponseDTO update(Long id, AccountRequestDTO accountRequestDTO) {
    Account account = findSingleAccount(id);

    if (null != account) {
      Long clientId = accountRequestDTO.getClientId();
      if (null != clientId) {
        ClientResponseDTO client = clientsClient.getClient(accountRequestDTO.getClientId());
        if (null != client) {
          account.setClientId(clientId);
        } else {
          throw new EntityNotFoundException(clientNotFound(accountRequestDTO.getClientId()));
        }
      }

      AccountType accountType = accountRequestDTO.getType();
      if (null != accountType) {
        account.setType(accountType);
      }

      account = accountRepository.save(account);
    } else {
      throw new EntityNotFoundException(accountNotFound(id));
    }

    return createResponseAccount(account);
  }

  /**
   * Check if the account has sufficient balance
   * @param id The ID of the account
   * @param amount The amount the account should minimally have
   * @return Whether or not the account has sufficient balance
   */
  @Override
  public boolean checkBalance(Long id, float amount) {
    Account account = findSingleAccount(id);
    boolean success = false;

    if (null != account) {
      success = (account.getBalance() >= amount);
    }

    return success;
  }

  /**
   * Update the balance of the account
   * @param id The ID of the account where the amount needs to be updated
   * @param amount The amount to add or remove to the current amount
   * @param type The TransactionType to apply
   * @return Whether or not the action succeeded
   */
  @Override
  public boolean updateBalance(Long id, float amount, TransactionType type) {
    Account account = findSingleAccount(id);
    boolean success = false;

    if (null != account) {
      if (TransactionType.DEPOSIT == type) {
        account.updateBalance(amount);
      } else if (TransactionType.WITHDRAWAL == type) {
        account.updateBalance(amount * -1);
      }
      success = true;
    }
    return success;
  }

  /**
   * Delete the account
   * @param id The ID of the account to delete
   */
  @Override
  public void delete(Long id) {
    Account account = findSingleAccount(id);

    if (null != account) {
      accountRepository.delete(account);
    } else {
      throw new EntityNotFoundException(accountNotFound(id));
    }
  }

  /**
   * Method finds a simple account with the given ID
   * @param id The ID of the account
   * @return The found account or null when not found
   */
  private Account findSingleAccount(Long id) {
    Optional<Account> accountOptional = accountRepository.findById(id);

    return accountOptional.orElse(null);
  }

  /**
   * Creates a response dto for the given account
   * @param account The account to create a response dot for
   * @return The created response dto
   */
  private AccountResponseDTO createResponseAccount(Account account) {
    AccountResponseDTO accountResponseDTO = new AccountResponseDTO();

    accountResponseDTO.setId(account.getId());
    accountResponseDTO.setClientId(account.getClientId());
    accountResponseDTO.setNumber(account.getNumber());
    accountResponseDTO.setType(account.getType());
    accountResponseDTO.setBalance(account.getBalance());

    return accountResponseDTO;
  }

  /**
   * Create a random account number
   * @return The created account number
   */
  private String createAccountNumber() {
    long number = ThreadLocalRandom.current().nextLong(BANK_NUMBER_MIN, BANK_NUMBER_MAX + 1);

    return "BANK" + number;
  }

  /**
   * Handle the found accounts and create a list of response dto's
   * @param accounts The list of found accounts
   * @return The list of converted accounts into response dto's
   */
  private List<AccountResponseDTO> handleFoundAccounts(Iterable<Account> accounts) {
    ArrayList<AccountResponseDTO> accountResponseDTOS = new ArrayList<>();

    accounts.forEach(account -> {
      AccountResponseDTO accountResponseDTO = createResponseAccount(account);
      accountResponseDTOS.add(accountResponseDTO);
    });

    return accountResponseDTOS;
  }

}
