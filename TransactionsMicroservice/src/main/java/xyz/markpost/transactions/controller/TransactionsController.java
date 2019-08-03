package xyz.markpost.transactions.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.SwaggerDefinition;
import io.swagger.annotations.Tag;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import xyz.markpost.transactions.dto.TransactionRequestDTO;
import xyz.markpost.transactions.dto.TransactionResponseDTO;
import xyz.markpost.transactions.service.TransactionService;

@SwaggerDefinition(
    tags = {
        @Tag(name = "Transactions", description = "API request options related to transaction entities")
    }
)

/**
 * REST controller for transaction entity
 */
@RestController
@RequestMapping("/")
@Api(tags = {"Transactions"})
public class TransactionsController {

  private final TransactionService transactionService;

  @Autowired
  public TransactionsController(
      TransactionService transactionService
  ) {
    this.transactionService = transactionService;
  }

  /**
   * REST API call for creating an transaction
   * TODO: add TransactionRequestDTO validation (custom annotation?)
   * TODO: swagger annotation
   *
   * @param transactionRequestDTO DTO containing data for new transaction entity
   * @return The response DTO of the created transaction entity
   */
  @ApiOperation(value = "Create a new transaction")
  @PostMapping(produces = "application/json")
  @ResponseStatus(HttpStatus.CREATED)
  public TransactionResponseDTO createTransaction(
      @RequestBody TransactionRequestDTO transactionRequestDTO) {
    return transactionService.create(transactionRequestDTO);
  }

  /**
   * REST API call for retrieving certain transaction or all transactions
   *
   * @param transactionId Transaction to retrieve (not required)
   * @return List of found transactions
   */
  @ApiOperation(value = "Get call for retrieving certain transaction.")
  @GetMapping(path = "{transactionId}", produces = "application/json")
  public List<TransactionResponseDTO> retrieveSingleTransaction(
      @ApiParam(value = "The ID of the transaction to retrieve.")
      @PathVariable(value = "transactionId", required = false)
          Long transactionId) {
    return transactionService.findById(transactionId);
  }

  /**
   * REST API call for retrieving certain transaction or all transactions
   *
   * @param transactionId Transactions filtered by this transactionId (not required)
   * @return List of found transactions
   */
  @ApiOperation(value = "Get call for retrieving certain transactions or all transactions.",
      notes = "Get call for retrieving certain transactions or all transactions. Can filter on transaction ID.")
  @GetMapping(produces = "application/json")
  public List<TransactionResponseDTO> retrieveAllTransaction(
      @ApiParam(value = "Filter on transactionId")
      @RequestParam(value = "transactionId", required = false)
          Long transactionId) {
    if(null != transactionId) {
      return transactionService.findByAccountId(transactionId);
    } else {
      return transactionService.findAll();
    }
  }

  /**
   * Delete the transaction with the given id
   *
   * @param transactionId The id of the account to delete
   */
  @ApiOperation(value = "Delete the transaction with the given ID if exists.",
      notes = "Delete the transaction with the given ID if exists. Internally it will not delete, but rather chang it status to 'CANCELLED'.")
  @DeleteMapping(path = "{transactionId}", produces = "application/json")
  @ResponseStatus(HttpStatus.OK)
  public void deleteAccount(
      @ApiParam(value = "The ID of the transaction to delete.")
      @PathVariable("transactionId")
          Long transactionId) {
    transactionService.delete(transactionId);
  }

}
