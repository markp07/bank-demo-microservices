package xyz.markpost.util.dto;

import java.sql.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The DTO for transaction data. We don't want to send all data of the entity back to the requester
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TransactionResponseDTO {

  private long id;

  private long accountId;

  private long contraAccountId;

  private TransactionType type;

  private Date date;

  private float amount;

  private String description;

  private TransactionStatus status;

}
