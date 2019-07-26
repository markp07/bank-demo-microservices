package xyz.markpost.transactions.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.sql.Timestamp;
import javax.persistence.EntityNotFoundException;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Log4j2
@ControllerAdvice
public class RestResponseEntityExceptionHandler
    extends ResponseEntityExceptionHandler {

  @ExceptionHandler(value = { EntityNotFoundException.class, IllegalArgumentException.class, IllegalStateException.class })
  protected ResponseEntity<Object> handleConflict(RuntimeException exception, WebRequest request) {
    ResponseEntity<Object> responseEntity;
    if(exception instanceof EntityNotFoundException){
      responseEntity = handleEntityNotFoundException(exception, request);
    } else {
      //TODO default error
      String bodyOfResponse = "This should be application specific";
      responseEntity = handleExceptionInternal(exception, bodyOfResponse,
          new HttpHeaders(), HttpStatus.CONFLICT, request);
    }

    return responseEntity;
  }

  /**
   *
   * @param ex
   * @param request
   * @return
   */
  private ResponseEntity<Object> handleEntityNotFoundException(RuntimeException ex, WebRequest request) {
    String bodyOfResponse;
    ErrorResponseBody errorResponseBody = new ErrorResponseBody(
        new Timestamp(System.currentTimeMillis()),
        HttpStatus.BAD_REQUEST.value(),
        HttpStatus.BAD_REQUEST.getReasonPhrase(),
        ex.getMessage()
    );
    ObjectMapper jsonMapper = new ObjectMapper();
    try {
      bodyOfResponse = jsonMapper.writeValueAsString(errorResponseBody);
      log.warn("EntityNotFoundException: " + bodyOfResponse);
    } catch (IOException e) {
      bodyOfResponse = "{}";
      e.printStackTrace();
    }

    return handleExceptionInternal(ex, bodyOfResponse,
        new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
  }
}
