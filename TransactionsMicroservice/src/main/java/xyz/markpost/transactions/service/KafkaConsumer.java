package xyz.markpost.transactions.service;

import static xyz.markpost.util.KafkaTopics.KAFKA_TOPIC_TRANSACTIONS;
import static xyz.markpost.util.KafkaTopics.KAFKA_TRANSACTION_IDENTIFIER_BALANCE_INSUFFICIENT;
import static xyz.markpost.util.KafkaTopics.KAFKA_TRANSACTION_IDENTIFIER_BALANCE_SUFFICIENT;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
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
    //TODO message when failed so trnasactions can rollback

    if(message.contains(KAFKA_TRANSACTION_IDENTIFIER_BALANCE_SUFFICIENT)){
      List<String> items = Arrays.asList(message.split("\\s*,\\s*"));

      Transaction transaction = transactionRepository.findById(Long.parseLong(items.get(1))).get();
      transaction.setStatus(TransactionStatus.COMPLETED);
      transactionRepository.save(transaction);
    } else if(message.contains(KAFKA_TRANSACTION_IDENTIFIER_BALANCE_INSUFFICIENT)) {
      List<String> items = Arrays.asList(message.split("\\s*,\\s*"));

      Transaction transaction = transactionRepository.findById(Long.parseLong(items.get(1))).get();
      transaction.setStatus(TransactionStatus.FAILED);
      transactionRepository.save(transaction);
    }

    log.info(String.format("#### -> Consumed message -> %s", message));
  }

}
