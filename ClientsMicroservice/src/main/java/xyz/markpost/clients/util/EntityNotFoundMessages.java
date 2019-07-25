package xyz.markpost.clients.util;

public class EntityNotFoundMessages {

  /**
   *
   * @param clientId
   * @return
   */
  public static String clientNotFound(Long clientId){
    return "Client with id " + clientId.toString() + " not found.";
  }

}
