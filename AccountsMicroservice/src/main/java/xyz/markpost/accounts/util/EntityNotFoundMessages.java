package xyz.markpost.accounts.util;

public class EntityNotFoundMessages {

  /**
   *
   * @param accountId
   * @return
   */
  public static String accountNotFound(Long accountId){
    return "Account with id " + accountId.toString() + " not found.";
  }

  /**
   *
   * @param clientId
   * @return
   */
  public static String clientNotFound(Long clientId){
    return "Client with id " + clientId.toString() + " not found.";
  }

}
