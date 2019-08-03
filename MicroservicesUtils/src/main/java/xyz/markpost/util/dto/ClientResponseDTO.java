package xyz.markpost.util.dto;

import java.sql.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * ClientResponseDTO
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClientResponseDTO {

  private Long id;

  private String firstName;

  private String lastName;

  private Date birthDate;

  private String address;

}