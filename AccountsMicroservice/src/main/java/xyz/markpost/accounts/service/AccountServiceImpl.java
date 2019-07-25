package xyz.markpost.accounts.service;

import static xyz.markpost.accounts.util.EntityNotFoundMessages.accountNotFound;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;
import javax.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.markpost.accounts.dto.AccountRequestDTO;
import xyz.markpost.accounts.dto.AccountResponseDTO;
import xyz.markpost.accounts.model.Account;
import xyz.markpost.accounts.model.AccountType;
import xyz.markpost.accounts.repository.AccountRepository;

/**
 *
 */
@Service
@Transactional
public class AccountServiceImpl implements AccountService {

  private final AccountRepository accountRepository;


  @Autowired
  public AccountServiceImpl(
      AccountRepository accountRepository
  ) {
    this.accountRepository = accountRepository;
  }

  private static final long BANK_NUMBER_MIN = 10000000;
  private static final long BANK_NUMBER_MAX = 99999999;

  /**
   * TODO: check requestDTO
   * TODO: call to check whether client exists
   * @param accountRequestDTO
   * @return
   */
  @Override
  public AccountResponseDTO create(AccountRequestDTO accountRequestDTO) {
    Account account = new Account();

    account.setClientId(accountRequestDTO.getClientId());
    account.setNumber(createAccountNumber());
    account.setType(accountRequestDTO.getType());
    account.setBalance(0);

    account = accountRepository.save(account);

    return createResponseAccount(account);
  }

  /**
   *
   * @param id
   * @return
   */
  @Override
  public AccountResponseDTO findById(Long id) {
    Account account = findSingleAccount(id);
    AccountResponseDTO accountResponseDTO =  null;

    if (null != account) {
      accountResponseDTO = createResponseAccount(account);
    }

    return accountResponseDTO;
  }

  /**
   *
   * @param clientId
   * @return
   */
  @Override
  public List<AccountResponseDTO> findByClientId(Long clientId) {
    List<Account> accounts = accountRepository.findAccountsByClientId(clientId);

    ArrayList<AccountResponseDTO> accountResponseDTOS = new ArrayList<>();

    accounts.forEach(account -> {
      AccountResponseDTO accountResponseDTO = createResponseAccount(account);
      accountResponseDTOS.add(accountResponseDTO);
    });

    return accountResponseDTOS;
  }

  /**
   *
   * @return
   */
  @Override
  public List<AccountResponseDTO> findAll() {
    Iterable<Account> accounts = accountRepository.findAll();
    ArrayList<AccountResponseDTO> accountResponseDTOS = new ArrayList<>();

    accounts.forEach(account -> {
      AccountResponseDTO accountResponseDTO = createResponseAccount(account);
      accountResponseDTOS.add(accountResponseDTO);
    });

    return accountResponseDTOS;
  }

  /**
   *
   * @param id
   * @param accountRequestDTO
   * @return
   */
  @Override
  public AccountResponseDTO update(Long id, AccountRequestDTO accountRequestDTO) {
    Account account = findSingleAccount(id);

    if (null != account) {
      Long clientId = accountRequestDTO.getClientId();
      if (null != clientId) {
        account.setClientId(clientId);
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
   *
   * @param id
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
   *
   * @param id
   * @return
   */
  private Account findSingleAccount(Long id) {
    Optional<Account> accountOptional = accountRepository.findById(id);

    return accountOptional.orElse(null);
  }

  /**
   *
   * @param account
   * @return
   */
  private AccountResponseDTO createResponseAccount(Account account) {
    AccountResponseDTO accountResponseDTO = new AccountResponseDTO();

    accountResponseDTO.setId(account.getId());
    accountResponseDTO.setClientId(account.getClientId());
    accountResponseDTO.setNumber(account.getNumber());
    accountResponseDTO.setType(account.getType());

    return accountResponseDTO;
  }

  /**
   *
   * @return
   */
  private String createAccountNumber() {
    long number = ThreadLocalRandom.current().nextLong(BANK_NUMBER_MIN, BANK_NUMBER_MAX + 1);

    return "BANK" + number;
  }

}