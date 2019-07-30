package xyz.markpost.accounts.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import xyz.markpost.accounts.dto.AccountRequestDTO;
import xyz.markpost.accounts.dto.AccountResponseDTO;
import xyz.markpost.accounts.service.AccountService;

@ExtendWith(SpringExtension.class)
class AccountsControllerUT {

  @Mock
  private AccountService accountService;

  @InjectMocks
  AccountsController accountsController;

  @Test
  @DisplayName("Test creating an account")
  void createAccountTest() {
    AccountRequestDTO accountRequestDTO = new AccountRequestDTO();
    AccountResponseDTO accountResponseDTO = new AccountResponseDTO();
    when(accountService.create(accountRequestDTO)).thenReturn(accountResponseDTO);

    AccountResponseDTO result = accountsController.createAccount(accountRequestDTO);

    assertThat(result).isEqualTo(accountResponseDTO);
  }

  @Test
  @DisplayName("Test retrieving a single account")
  void retrieveSingleAccountTest() {
    AccountResponseDTO accountResponseDTO = new AccountResponseDTO();
    when(accountService.findById(any())).thenReturn(accountResponseDTO);

    AccountResponseDTO result = accountsController.retrieveSingleAccount(1L);

    assertThat(result)
        .isEqualTo(accountResponseDTO);
  }

  @Test
  @DisplayName("Test retrieving all accounts")
  void retrieveAllAccountWithoutClientIdTest() {
    List<AccountResponseDTO> accountResponseDTOS = new ArrayList<>();

    AccountResponseDTO accountResponseDTOA = new AccountResponseDTO();
    accountResponseDTOS.add(accountResponseDTOA);

    AccountResponseDTO accountResponseDTOB = new AccountResponseDTO();
    accountResponseDTOS.add(accountResponseDTOB);

    AccountResponseDTO accountResponseDTOC = new AccountResponseDTO();
    accountResponseDTOS.add(accountResponseDTOC);

    when(accountService.findAll()).thenReturn(accountResponseDTOS);

    List<AccountResponseDTO> result = accountsController.retrieveAllAccounts(null);
    verify(accountService, never()).findByClientId(anyLong());
    verify(accountService, times(1)).findAll();

    assertThat(result)
        .hasSize(3)
        .isEqualTo(accountResponseDTOS);
  }

  @Test
  @DisplayName("Test retrieving all accounts filtered on clientId")
  void retrieveAllAccountWithClientIdTest() {
    List<AccountResponseDTO> accountResponseDTOS = new ArrayList<>();

    AccountResponseDTO accountResponseDTOA = new AccountResponseDTO();
    accountResponseDTOS.add(accountResponseDTOA);

    AccountResponseDTO accountResponseDTOB = new AccountResponseDTO();
    accountResponseDTOS.add(accountResponseDTOB);

    AccountResponseDTO accountResponseDTOC = new AccountResponseDTO();
    accountResponseDTOS.add(accountResponseDTOC);

    when(accountService.findByClientId(anyLong())).thenReturn(accountResponseDTOS);

    List<AccountResponseDTO> result = accountsController.retrieveAllAccounts(1L);
    verify(accountService, times(1)).findByClientId(anyLong());
    verify(accountService, never()).findAll();

    assertThat(result)
        .hasSize(3)
        .isEqualTo(accountResponseDTOS);
  }

  @Test
  @DisplayName("Test updating an account")
  void updateAccountTest() {
    AccountRequestDTO accountRequestDTO = new AccountRequestDTO();
    AccountResponseDTO accountResponseDTO = new AccountResponseDTO();
    when(accountService.update(any(), any(AccountRequestDTO.class))).thenReturn(accountResponseDTO);

    AccountResponseDTO result = accountsController.updateAccount(1L, accountRequestDTO);

    assertThat(result).isEqualTo(accountResponseDTO);
  }

  @Test
  @DisplayName("Test deleting an account")
  void deleteAccountTest() {
    accountsController.deleteAccount(1L);
    //We cannot assert anything here
  }

}