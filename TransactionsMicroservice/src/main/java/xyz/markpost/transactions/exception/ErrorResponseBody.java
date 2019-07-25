package xyz.markpost.transactions.exception;

import java.sql.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponseBody {

  private Timestamp timestamp;

  private int status;

  private String error;

  private String message;

  private String url;

}
