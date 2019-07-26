package xyz.markpost.transactions.model;

import java.sql.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

/**
 * Transaction entity TODO: add not null TODO: add validation
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "transaction-sequence-generator")
  @GenericGenerator(
      name = "transaction-sequence-generator",
      strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
      parameters = {
          @Parameter(name = "sequence_name", value = "transaction_sequence"),
          @Parameter(name = "initial_value", value = "5"),
          @Parameter(name = "increment_size", value = "1")
      }
  )
  @Column(name = "id")
  private long id;

  @Column(name = "account_id")
  private long accountId;

  @Column(name = "contra_account_id")
  private long contraAccountId;

  @Column(name = "type")
  private TransactionType type;

  @Column(name = "date")
  private Date date;

  @Column(name = "amount")
  private float amount;

  @Column(name = "description")
  private String description;

  @Column(name = "status")
  private TransactionStatus status;

}
