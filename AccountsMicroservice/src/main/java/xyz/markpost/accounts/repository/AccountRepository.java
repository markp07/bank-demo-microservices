package xyz.markpost.accounts.repository;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import xyz.markpost.accounts.model.Account;

/**
 * The repository for Accounts
 */
@Component
public interface AccountRepository extends CrudRepository<Account, Long> {

  /**
   * Find a accounts based on the client ID
   * @param clientId The client ID of the accounts
   * @return The list of found accounts or an empty list
   */
  List<Account> findAccountsByClientId(long clientId);

}
