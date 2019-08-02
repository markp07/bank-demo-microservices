package xyz.markpost.accounts.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.SwaggerDefinition;
import io.swagger.annotations.Tag;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import xyz.markpost.accounts.dto.AccountRequestDTO;
import xyz.markpost.accounts.dto.AccountResponseDTO;
import xyz.markpost.accounts.service.AccountService;


@SwaggerDefinition(
    tags = {
        @Tag(name = "Accounts", description = "API request options related to account entities")
    }
)

@RestController
@RequestMapping("/")
@Api(tags = {"Accounts"})
public class AccountsController {

  private final AccountService accountService;

  @Autowired
  public AccountsController(
      AccountService accountService
  ) {
    this.accountService = accountService;
  }

  /**
   * REST API call for creating an account
   * TODO: add AccountRequestDTO validation (custom annotation?)
   * TODO: swagger annotation
   *
   * @param accountRequestDTO DTO containing data for new account entity
   * @return The response DTO of the created account entity
   */
  @PostMapping(produces = "application/json")
  @ResponseStatus(HttpStatus.CREATED)
  public AccountResponseDTO createAccount(
      @ApiParam(value = "The values to use to create the account.")
      @RequestBody AccountRequestDTO accountRequestDTO) {
    return accountService.create(accountRequestDTO);
  }

  /**
   * REST API call for retrieving certain account or all accounts
   * TODO: swagger annotation
   *
   * @param accountId Account to retrieve (not required)
   * @return List of found accounts
   */
  @GetMapping(path = "{accountId}", produces = "application/json")
  public AccountResponseDTO retrieveSingleAccount(
      @ApiParam(value = "Account ID of the account to retrieve")
      @PathVariable(value = "accountId", required = false) Long accountId) {
    return accountService.findById(accountId);

  }

  /**
   * REST API call for retrieving certain account or all accounts
   * TODO: add option for finding set of accounts (input list of id's)
   * TODO: swagger annotation
   *
   * @return List of found accounts
   */
  @GetMapping(produces = "application/json")
  public List<AccountResponseDTO> retrieveAllAccounts(
      @ApiParam(value = "Filter on clientId")
      @RequestParam(value = "clientId", required = false)
          Long clientId) {
    if (null != clientId) {
      return accountService.findByClientId(clientId);
    } else {
      return accountService.findAll();
    }
  }

  /**
   * Update given account
   * TODO: add AccountRequestDTO validation (custom annotation?)
   * TODO: swagger annotation
   *
   * @param accountId The id of the account to update
   * @param accountRequestDTO The data of the to update fields
   * @return The updated account
   */
  @PatchMapping(path = "{accountId}", produces = "application/json")
  @ResponseStatus(HttpStatus.OK)
  public AccountResponseDTO updateAccount(
      @ApiParam(value = "Account ID of the account to update")
      @PathVariable("accountId")
          Long accountId,
      @ApiParam(value = "The values to update in the account. Leave fields out that you don't want to update.")
      @RequestBody AccountRequestDTO accountRequestDTO) {
    return accountService.update(accountId, accountRequestDTO);
  }

  /**
   * Delete the account with the given id
   * TODO: swagger annotation
   *
   * @param accountId The id of the account to delete
   */
  @DeleteMapping(path = "{accountId}", produces = "application/json")
  @ResponseStatus(HttpStatus.OK)
  public void deleteAccount(
      @ApiParam(value = "Account ID of the account to delete")
      @PathVariable("accountId")
          Long accountId) {
    accountService.delete(accountId);
  }

}
