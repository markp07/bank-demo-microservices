package xyz.markpost.util.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * AccountResponseDTO
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AccountResponseDTO {

  private long id;

  private Long clientId;

  private AccountType type;

  private String number;

  private float balance;

}
