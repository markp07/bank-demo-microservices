package xyz.markpost.transactions.service;


import static xyz.markpost.util.EntityNotFoundMessages.transactionNotFound;
import static xyz.markpost.util.KafkaTopics.KAFKA_TOPIC_TRANSACTIONS;
import static xyz.markpost.util.KafkaTopics.KAFKA_TRANSACTION_IDENTIFIER_NEW;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.markpost.transactions.dto.TransactionRequestDTO;
import xyz.markpost.transactions.dto.TransactionResponseDTO;
import xyz.markpost.transactions.model.Transaction;
import xyz.markpost.transactions.model.TransactionStatus;
import xyz.markpost.transactions.repository.TransactionRepository;
import xyz.markpost.transactions.util.TransactionSortByDate;

/**
 *
 */
@Service
@Transactional
public class TransactionServiceImpl implements TransactionService {

  private final TransactionRepository transactionRepository;

  private final KafkaTemplate<String, String> kafkaTemplate;

  /**
   *
   * @param transactionRepository
   */
  @Autowired
  public TransactionServiceImpl(
      TransactionRepository transactionRepository,
      KafkaTemplate<String, String> kafkaTemplate
  ) {
    this.transactionRepository = transactionRepository;
    this.kafkaTemplate = kafkaTemplate;
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
    transaction.setStatus(TransactionStatus.NEW);

    transaction = transactionRepository.save(transaction);

    String message = KAFKA_TRANSACTION_IDENTIFIER_NEW
        + transaction.getId() + ","
        + transaction.getAccountId() + ","
        + transaction.getContraAccountId() + ","
        + transaction.getType().toString() + ","
        + transaction.getAmount();
    kafkaTemplate.send(KAFKA_TOPIC_TRANSACTIONS, message);
    transaction.setStatus(TransactionStatus.PENDING);

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

    return handleRetrievedTransactions(transactions);
  }

  /**
   *
   * @return
   */
  @Override
  public List<TransactionResponseDTO> findAll() {
    Iterable<Transaction> transactions = transactionRepository.findAll();

    return handleRetrievedTransactions(transactions);
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
   * @param transactions
   * @return
   */
  private List<TransactionResponseDTO> handleRetrievedTransactions(Iterable<Transaction> transactions) {
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
    transactionResponseDTO.setStatus(transaction.getStatus());

    return transactionResponseDTO;
  }

  /**
   *
   * @param id
   */
  @Override
  public void delete(Long id) {
    Transaction transaction = findSingleTransaction(id);

    if (null != transaction) {
      transaction.setStatus(TransactionStatus.CANCELLED);
      //TODO: handle balances
    } else {
      throw new EntityNotFoundException(transactionNotFound(id));
    }
  }

}
