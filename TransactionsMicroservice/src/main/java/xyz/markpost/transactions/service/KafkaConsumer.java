package xyz.markpost.transactions.service;

import static xyz.markpost.util.KafkaTopics.KAFKA_TOPIC_TRANSACTIONS;
import static xyz.markpost.util.KafkaTopics.KAFKA_TRANSACTION_IDENTIFIER_BALANCE_INSUFFICIENT;
import static xyz.markpost.util.KafkaTopics.KAFKA_TRANSACTION_IDENTIFIER_BALANCE_SUFFICIENT;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import xyz.markpost.transactions.model.Transaction;
import xyz.markpost.transactions.model.TransactionStatus;
import xyz.markpost.transactions.repository.TransactionRepository;

@Service
@Log4j2
public class KafkaConsumer {

  private final TransactionRepository transactionRepository;

  private final KafkaTemplate<String, String> kafkaTemplate;

  @Autowired
  public KafkaConsumer(
      TransactionRepository transactionRepository,
      KafkaTemplate<String, String> kafkaTemplate
  ) {
    this.transactionRepository = transactionRepository;
    this.kafkaTemplate = kafkaTemplate;
  }

  @KafkaListener(topics = KAFKA_TOPIC_TRANSACTIONS, groupId = "TransactionMicroservice")
  public void consume(String message) throws IOException {
    log.info("KAFKA CONSUMED -> " + message);
    boolean success = false;

    if (message.contains(KAFKA_TRANSACTION_IDENTIFIER_BALANCE_SUFFICIENT)) {
      success = updateTransaction(message, TransactionStatus.COMPLETED);
    } else if (message.contains(KAFKA_TRANSACTION_IDENTIFIER_BALANCE_INSUFFICIENT)) {
      success = updateTransaction(message, TransactionStatus.FAILED);
    }

    if (!success) {
      //TODO: handle failure
    }
  }

  /**
   *
   * @param message
   * @param status
   * @return
   */
  private boolean updateTransaction(String message, TransactionStatus status) {
    List<String> items = Arrays.asList(message.split("\\s*,\\s*"));
    boolean success = false;

    Optional<Transaction> transactionOptional = transactionRepository
        .findById(Long.parseLong(items.get(1)));
    if (transactionOptional.isPresent()) {
      Transaction transaction = transactionOptional.get();
      transaction.setStatus(status);
      transactionRepository.save(transaction);
      success = true;
    }

    return success;
  }

}
