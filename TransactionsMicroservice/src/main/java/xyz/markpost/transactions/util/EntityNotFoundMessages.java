package xyz.markpost.transactions.util;

public class EntityNotFoundMessages {

  /**
   *
   * @param transactionId
   * @return
   */
  public static String transactionNotFound(Long transactionId){
    return "Transaction with id " + transactionId.toString() + " not found.";
  }

}
