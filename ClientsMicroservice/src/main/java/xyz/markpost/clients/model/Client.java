package xyz.markpost.clients.model;

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
 * Client entity TODO: add not null TODO: add validation
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Client {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "client-sequence-generator")
  @GenericGenerator(
      name = "client-sequence-generator",
      strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
      parameters = {
          @Parameter(name = "sequence_name", value = "client_sequence"),
          @Parameter(name = "initial_value", value = "5"),
          @Parameter(name = "increment_size", value = "1")
      }
  )
  @Column(name = "id")
  private long id;

  @Column(name = "first_name")
  private String firstName;

  @Column(name = "last_name")
  private String lastName;

  @Column(name = "birth_date")
  private Date birthDate;

  @Column(name = "address")
  private String address;

  public String getFullName() {
    return firstName + " " + lastName;
  }

}
