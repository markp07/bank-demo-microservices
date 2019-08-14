package xyz.markpost.accounts.service;

import static xyz.markpost.util.KafkaTopics.KAFKA_TOPIC_TRANSACTIONS;
import static xyz.markpost.util.KafkaTopics.KAFKA_TRANSACTION_IDENTIFIER_BALANCE_INSUFFICIENT;
import static xyz.markpost.util.KafkaTopics.KAFKA_TRANSACTION_IDENTIFIER_BALANCE_SUFFICIENT;
import static xyz.markpost.util.KafkaTopics.KAFKA_TRANSACTION_IDENTIFIER_NEW;

import java.util.Arrays;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import xyz.markpost.util.dto.TransactionType;

/**
 * The consumer to process consuming messages
 */
@Service
@Log4j2
public class KafkaConsumer {


  private final AccountService accountService;

  private final KafkaTemplate<String, String> kafkaTemplate;

  @Autowired
  public KafkaConsumer(
      AccountService accountService,
      KafkaTemplate<String, String> kafkaTemplate
  ) {
    this.accountService = accountService;
    this.kafkaTemplate = kafkaTemplate;
  }

  /**
   * Kafka listener
   * @param message Consuming messages
   */
  @KafkaListener(topics = KAFKA_TOPIC_TRANSACTIONS, groupId = "AccountMicroservice")
  public void consume(String message) {
    log.info("KAFKA CONSUMED -> " + message);

    if (message.contains(KAFKA_TRANSACTION_IDENTIFIER_NEW)) {
      handleNewTransaction(message);
    }
  }

  /**
   * Handles the message based on the transaction type. Add or remove money from the accounts
   * @param message The received message
   */
  private void handleNewTransaction(String message) {
    NewTransactionMessage newTransactionMessage = splitMessage(message);
    boolean balanceSufficient = false;

    //Update balances of accounts
    if (TransactionType.DEPOSIT == newTransactionMessage.getType()) {
      balanceSufficient = accountService.checkBalance(newTransactionMessage.getContraAccountId(),
          newTransactionMessage.getAmount());

      if (balanceSufficient) {
        accountService
            .updateBalance(newTransactionMessage.getAccountId(), newTransactionMessage.getAmount(),
                TransactionType.DEPOSIT);
        accountService
            .updateBalance(newTransactionMessage.getAccountId(), newTransactionMessage.getAmount(),
                TransactionType.WITHDRAWAL);
      }
    } else if (TransactionType.WITHDRAWAL == newTransactionMessage.getType()) {
      balanceSufficient = accountService
          .checkBalance(newTransactionMessage.getAccountId(), newTransactionMessage.getAmount());

      if (balanceSufficient) {
        accountService
            .updateBalance(newTransactionMessage.getAccountId(), newTransactionMessage.getAmount(),
                TransactionType.WITHDRAWAL);
        accountService
            .updateBalance(newTransactionMessage.getAccountId(), newTransactionMessage.getAmount(),
                TransactionType.DEPOSIT);
      }
    }

    //Send message back to complete transaction
    if (balanceSufficient) {
      sendKafkaMessage(KAFKA_TOPIC_TRANSACTIONS, KAFKA_TRANSACTION_IDENTIFIER_BALANCE_SUFFICIENT,
          newTransactionMessage);
    } else {
      sendKafkaMessage(KAFKA_TOPIC_TRANSACTIONS, KAFKA_TRANSACTION_IDENTIFIER_BALANCE_INSUFFICIENT,
          newTransactionMessage);
    }

  }

  /**
   * Parse the incoming message and create a NewTransactionMessage
   * @param message The incoming message to use
   * @return The created NewTransactionMessage
   */
  private NewTransactionMessage splitMessage(String message) {
    List<String> items = Arrays.asList(message.split("\\s*,\\s*"));

    return new NewTransactionMessage(
        Long.parseLong(items.get(1)),
        Long.parseLong(items.get(2)),
        Long.parseLong(items.get(3)),
        items.get(4),
        Float.parseFloat(items.get(5))
    );
  }

  /**
   * Sends the message with the given topic to kafka
   * @param topic The topic to use
   * @param messageKey The message key to send
   * @param newTransactionMessage The data object for the message
   */
  private void sendKafkaMessage(String topic, String messageKey,
      NewTransactionMessage newTransactionMessage) {
    String returnMessage = messageKey + ","
        + newTransactionMessage.getTransactionId() + ","
        + newTransactionMessage.getAccountId() + ","
        + newTransactionMessage.getContraAccountId();
    kafkaTemplate.send(topic, returnMessage);
  }

  /**
   * Object for the received the message for a new transaction. This contains all the data
   */
  @Getter
  @Setter
  class NewTransactionMessage {

    NewTransactionMessage(Long transactionId, Long accountId, Long contraAccountId, String type,
        float amount) {
      this.transactionId = transactionId;
      this.accountId = accountId;
      this.contraAccountId = contraAccountId;
      this.amount = amount;

      if ("DEPOSIT".equals(type)) {
        this.type = TransactionType.DEPOSIT;
      } else if ("WITHDRAWAL".equals(type)) {
        this.type = TransactionType.WITHDRAWAL;
      }
    }

    private Long transactionId;

    private Long accountId;

    private Long contraAccountId;

    private TransactionType type;

    private float amount;

  }

}
