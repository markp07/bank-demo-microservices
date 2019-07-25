package xyz.markpost.accounts.dto;

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
public class AccountResponseDTO extends AccountRequestDTO {

  private long id;

  private String number;

}
