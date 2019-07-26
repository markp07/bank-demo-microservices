package xyz.markpost.accounts.service;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import xyz.markpost.accounts.model.Account;
import xyz.markpost.accounts.repository.AccountRepository;

@Service
@Log4j2
public class KafkaConsumer {

  private final AccountRepository accountRepository;

  private final KafkaTemplate<String, String> kafkaTemplate;

  @Autowired
  public KafkaConsumer(
      AccountRepository accountRepository,
      KafkaTemplate<String, String> kafkaTemplate
  ) {
    this.accountRepository = accountRepository;
    this.kafkaTemplate = kafkaTemplate;
  }

  @KafkaListener(topics = "TRANSACTION_B", groupId = "AccountMicroservice")
  public void consume(String message) throws IOException {
    if(message.contains("TRANSACTION_CREATED")){
      List<String> items = Arrays.asList(message.split("\\s*,\\s*"));

      Account account = accountRepository.findById(Long.parseLong(items.get(2))).get();
      Account contraAccount = accountRepository.findById(Long.parseLong(items.get(3))).get();
      float amount = Float.parseFloat(items.get(5));

      if(("DEPOSIT".equals(items.get(4)) && amount <= contraAccount.getBalance()) ||
          ("WITHDRAWAL".equals(items.get(4)) && amount <= account.getBalance())) {
        String topic = "TRANSACTION_A";
        String returnMessage = "BALANCE_SUFFICIENT,"
            + items.get(1) + ","
            + items.get(2) + ","
            + items.get(3);
        kafkaTemplate.send(topic, returnMessage);

        if ("DEPOSIT".equals(items.get(4))) {
          float currentBalance = account.getBalance();
          float newBalance = currentBalance + amount;
          account.setBalance(newBalance);

          currentBalance = contraAccount.getBalance();
          newBalance = currentBalance - amount;
          contraAccount.setBalance(newBalance);
        } else if ("WITHDRAWAL".equals(items.get(4))) {
          float currentBalance = account.getBalance();
          float newBalance = currentBalance - amount;
          account.setBalance(newBalance);

          currentBalance = contraAccount.getBalance();
          newBalance = currentBalance + amount;
          contraAccount.setBalance(newBalance);
        }
        accountRepository.save(account);
        accountRepository.save(contraAccount);
      } else {
        String topic = "TRANSACTION";
        String returnMessage = "BALANCE_UNSUFFICIENT,"
            + items.get(1) + ","
            + items.get(2) + ","
            + items.get(3);
        kafkaTemplate.send(topic, returnMessage);
      }
    }

    log.info(String.format("#### -> Consumed message -> %s", message));
  }

}
