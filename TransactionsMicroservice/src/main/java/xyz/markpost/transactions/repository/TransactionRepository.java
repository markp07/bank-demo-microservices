package xyz.markpost.transactions.repository;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import xyz.markpost.transactions.model.Transaction;

@Component
public interface TransactionRepository extends CrudRepository<Transaction, Long> {

  List<Transaction> findTransactionByAccountId(long accountId);

  List<Transaction> findTransactionByContraAccountId(long contraAccountId);

}
