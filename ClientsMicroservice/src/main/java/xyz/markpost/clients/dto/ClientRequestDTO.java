package xyz.markpost.clients.dto;

import java.sql.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * ClientRequestDTO
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClientRequestDTO {

  private String firstName;

  private String lastName;

  private Date birthDate;

  private String address;

}
