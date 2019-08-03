package xyz.markpost.util;

public class EntityNotFoundMessages {

  /**
   *
   * @param accountId
   * @return
   */
  public static String accountNotFound(Long accountId) {
    return "Account with id " + accountId.toString() + " not found.";
  }

  /**
   *
   * @param clientId
   * @return
   */
  public static String clientNotFound(Long clientId) {
    return "Client with id " + clientId.toString() + " not found.";
  }

  /**
   *
   * @param transactionId
   * @return
   */
  public static String transactionNotFound(Long transactionId) {
    return "Transaction with id " + transactionId.toString() + " not found.";
  }

}
