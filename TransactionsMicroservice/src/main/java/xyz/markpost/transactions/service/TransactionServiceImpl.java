package xyz.markpost.transactions.service;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.markpost.transactions.dto.TransactionRequestDTO;
import xyz.markpost.transactions.dto.TransactionResponseDTO;
import xyz.markpost.transactions.model.Transaction;
import xyz.markpost.transactions.model.TransactionType;
import xyz.markpost.transactions.repository.TransactionRepository;
import xyz.markpost.transactions.util.TransactionSortByDate;

/**
 *
 */
@Service
@Transactional
public class TransactionServiceImpl implements TransactionService {

  private final TransactionRepository transactionRepository;

  /**
   *
   * @param transactionRepository
   */
  @Autowired
  public TransactionServiceImpl(
      TransactionRepository transactionRepository
  ) {
    this.transactionRepository = transactionRepository;
  }


  /**
   * TODO: check requestDTO
   * @param transactionRequestDTO
   * @return
   */
  @Override
  public TransactionResponseDTO create(TransactionRequestDTO transactionRequestDTO) {
    Transaction transaction = new Transaction();

    transaction.setAccountId(transactionRequestDTO.getAccountId());
    transaction.setAmount(transactionRequestDTO.getAmount());
    transaction.setContraAccountId(transactionRequestDTO.getContraAccountId());
    transaction.setDate(transactionRequestDTO.getDate());
    transaction.setDescription(transactionRequestDTO.getDescription());
    transaction.setType(transactionRequestDTO.getType());

    transaction = transactionRepository.save(transaction);

    //TODO: update accounts - connect to other service
//    if (TransactionType.DEPOSIT == transaction.getType()) {
//      float currentBalance = account.getBalance();
//      float newBalance = currentBalance + transaction.getAmount();
//      account.setBalance(newBalance);
//
//      currentBalance = contraAccount.getBalance();
//      newBalance = currentBalance - transaction.getAmount();
//      contraAccount.setBalance(newBalance);
//    } else if (TransactionType.WITHDRAWAL == transaction.getType()) {
//      float currentBalance = account.getBalance();
//      float newBalance = currentBalance - transaction.getAmount();
//      account.setBalance(newBalance);
//
//      currentBalance = contraAccount.getBalance();
//      newBalance = currentBalance + transaction.getAmount();
//      contraAccount.setBalance(newBalance);
//    }

    return createResponseTransaction(transaction);
  }

  /**
   *
   * @param transactionId
   * @return
   */
  @Override
  public List<TransactionResponseDTO> findById(Long transactionId) {
    Transaction account = findSingleTransaction(transactionId);
    ArrayList<TransactionResponseDTO> transactionResponseDTOS = new ArrayList<>();

    if (null != account) {
      TransactionResponseDTO transactionResponseDTO = createResponseTransaction(account);
      transactionResponseDTOS.add(transactionResponseDTO);
    }

    return transactionResponseDTOS;
  }


  /**
   *
   * @param accountId
   * @return
   */
  @Override
  public List<TransactionResponseDTO> findByAccountId(Long accountId) {
    List<Transaction> transactions = transactionRepository.findTransactionByAccountId(accountId);
    transactions.addAll(transactionRepository.findTransactionByContraAccountId(accountId));

    ArrayList<TransactionResponseDTO> transactionResponseDTOS = new ArrayList<>();

    transactions.forEach(transaction -> {
      TransactionResponseDTO transactionResponseDTO = createResponseTransaction(transaction);
      transactionResponseDTOS.add(transactionResponseDTO);
    });

    transactionResponseDTOS.sort(new TransactionSortByDate());

    return transactionResponseDTOS;
  }

  /**
   *
   * @return
   */
  @Override
  public List<TransactionResponseDTO> findAll() {
    Iterable<Transaction> transactions = transactionRepository.findAll();
    ArrayList<TransactionResponseDTO> transactionResponseDTOS = new ArrayList<>();

    transactions.forEach(transaction -> {
      TransactionResponseDTO transactionResponseDTO = createResponseTransaction(transaction);
      transactionResponseDTOS.add(transactionResponseDTO);
    });

    transactionResponseDTOS.sort(new TransactionSortByDate());

    return transactionResponseDTOS;
  }

  /**
   *
   * @param transactionId
   * @return
   */
  private Transaction findSingleTransaction(Long transactionId) {
    Optional<Transaction> transactionOptional = transactionRepository.findById(transactionId);

    return transactionOptional.orElse(null);
  }

  /**
   *
   * @param transaction
   * @return
   */
  private TransactionResponseDTO createResponseTransaction(Transaction transaction) {
    TransactionResponseDTO transactionResponseDTO = new TransactionResponseDTO();

    transactionResponseDTO.setId(transaction.getId());
    transactionResponseDTO.setAccountId(transaction.getAccountId());
    transactionResponseDTO.setContraAccountId(transaction.getContraAccountId());
    transactionResponseDTO.setType(transaction.getType());
    transactionResponseDTO.setDate(transaction.getDate());
    transactionResponseDTO.setAmount(transaction.getAmount());
    transactionResponseDTO.setDescription(transaction.getDescription());

    return transactionResponseDTO;
  }

}
