package xyz.markpost.accounts.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import xyz.markpost.accounts.model.AccountType;

/**
 * AccountRequestDTO
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountRequestDTO {

  private Long clientId;

  private AccountType type;

}
