package xyz.markpost.transactions.dto;

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
public class TransactionResponseDTO extends TransactionRequestDTO {

  private long id;

}
