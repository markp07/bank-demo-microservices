package xyz.markpost.accounts.exception;

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
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Handler for handling exceptions. Retrieves exception and handles accordingly
 */
@Log4j2
@ControllerAdvice
public class RestResponseEntityExceptionHandler
    extends ResponseEntityExceptionHandler {

  /**
   * Main handler. Entry point for exceptions
   * @param exception The incoming exception
   * @param request The request that was being handled when the exception occurred
   * @return The response according to retrieved exception
   */
  @ExceptionHandler(value = {EntityNotFoundException.class, IllegalArgumentException.class,
      IllegalStateException.class})
  protected ResponseEntity<Object> handleConflict(RuntimeException exception, WebRequest request) {
    ResponseEntity<Object> responseEntity;
    if (exception instanceof EntityNotFoundException) {
      responseEntity = handleEntityNotFoundException(exception, request);
    } else {
      responseEntity = handleDefaultException(exception, request);
    }

    return responseEntity;
  }

  /**
   * The method handles any other exception
   * @param exception The incoming exception
   * @param request The request that was being handled when the exception occurred
   * @return The response according to retrieved exception
   */
  private ResponseEntity<Object> handleDefaultException(RuntimeException exception,
      WebRequest request) {
    String bodyOfResponse;

    ErrorResponseBody errorResponseBody = new ErrorResponseBody(
        new Timestamp(System.currentTimeMillis()),
        HttpStatus.BAD_REQUEST.value(),
        HttpStatus.BAD_REQUEST.getReasonPhrase(),
        exception.getMessage()
    );

    ObjectMapper jsonMapper = new ObjectMapper();
    try {
      bodyOfResponse = jsonMapper.writeValueAsString(errorResponseBody);
      log.info("Exception: " + bodyOfResponse);
    } catch (IOException e) {
      bodyOfResponse = "{}";
      log.error("Could not convert string into JSON.", e);
    }

    return handleExceptionInternal(exception, bodyOfResponse,
        new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
  }

  /**
   * The method handles the Entity Not Found exceptions
   * @param exception The incoming exception
   * @param request The request that was being handled when the exception occurred
   * @return The response according to retrieved exception
   */
  private ResponseEntity<Object> handleEntityNotFoundException(RuntimeException exception,
      WebRequest request) {
    String bodyOfResponse;

    ErrorResponseBody errorResponseBody = new ErrorResponseBody(
        new Timestamp(System.currentTimeMillis()),
        HttpStatus.NO_CONTENT.value(),
        HttpStatus.NO_CONTENT.getReasonPhrase(),
        exception.getMessage()
    );

    ObjectMapper jsonMapper = new ObjectMapper();
    try {
      bodyOfResponse = jsonMapper.writeValueAsString(errorResponseBody);
      log.info("EntityNotFoundException: " + bodyOfResponse);
    } catch (IOException e) {
      bodyOfResponse = "{}";
      log.error("Could not convert string into JSON.", e);
    }

    return handleExceptionInternal(exception, bodyOfResponse,
        new HttpHeaders(), HttpStatus.NO_CONTENT, request);
  }
}
