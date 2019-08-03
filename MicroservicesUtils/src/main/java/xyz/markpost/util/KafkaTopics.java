package xyz.markpost.util;

public final class KafkaTopics {

  private KafkaTopics() {

  }

  public static final String KAFKA_TOPIC_ACCOUNTS = "ACCOUNTS";
  public static final String KAFKA_TOPIC_CLIENTS = "CLIENTS";
  public static final String KAFKA_TOPIC_TRANSACTIONS = "TRANSACTIONS";

  public static final String KAFKA_TRANSACTION_IDENTIFIER_NEW = "TRANSACTION_CREATED";
  public static final String KAFKA_TRANSACTION_IDENTIFIER_BALANCE_SUFFICIENT = "BALANCE_SUFFICIENT";
  public static final String KAFKA_TRANSACTION_IDENTIFIER_BALANCE_INSUFFICIENT = "BALANCE_INSUFFICIENT";

}
