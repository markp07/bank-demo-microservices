package xyz.markpost.accounts.exception;

import java.sql.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Object for error response back to the consumer
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
class ErrorResponseBody {

  private Timestamp timestamp;

  private int status;

  private String error;

  private String message;

  private static final String PATH = "/accounts";

}
