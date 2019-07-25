package xyz.markpost.accounts.repository;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import xyz.markpost.accounts.model.Account;

@Component
public interface AccountRepository extends CrudRepository<Account, Long> {

  List<Account> findAccountsByClientId(long clientId);

}
