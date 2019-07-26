package xyz.markpost.transactions.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import xyz.markpost.transactions.dto.TransactionRequestDTO;
import xyz.markpost.transactions.dto.TransactionResponseDTO;
import xyz.markpost.transactions.service.TransactionService;
import xyz.markpost.transactions.util.TransactionSortByDate;

@ExtendWith(SpringExtension.class)
class TransactionsControllerUT {

  @Mock
  private TransactionService transactionService;

  @InjectMocks
  private TransactionsController transactionController;

  @Test
  @DisplayName("Test creating a transaction")
  void createTransactionTest() {
    TransactionRequestDTO transactionRequestDTO = new TransactionRequestDTO();
    TransactionResponseDTO transactionResponseDTO = new TransactionResponseDTO();
    when(transactionService.create(transactionRequestDTO)).thenReturn(transactionResponseDTO);

    TransactionResponseDTO result = transactionController.createTransaction(transactionRequestDTO);

    assertThat(result).isEqualTo(transactionResponseDTO);
  }

  @Test
  @DisplayName("Test retrieving a transaction using an ID")
  void retrieveSingleTransactionTest() {
    List<TransactionResponseDTO> transactionResponseDTOSWithId = new ArrayList<>();

    TransactionResponseDTO transactionResponseDTO = new TransactionResponseDTO();
    transactionResponseDTOSWithId.add(transactionResponseDTO);
    when(transactionService.findById(any())).thenReturn(transactionResponseDTOSWithId);

    List<TransactionResponseDTO> result = transactionController.retrieveSingleTransaction(1L);

    assertThat(result)
        .hasSize(1)
        .isEqualTo(transactionResponseDTOSWithId);
  }

  @Test
  @DisplayName("Test retrieving list of all transactions")
  void retrieveAllTransactionWithoutAccountIdTest() {
    List<TransactionResponseDTO> transactionResponseDTOSWithoutId = new ArrayList<>();

    TransactionResponseDTO transactionResponseDTOA = new TransactionResponseDTO();
    transactionResponseDTOA.setDate(new Date(0));
    transactionResponseDTOSWithoutId.add(transactionResponseDTOA);

    TransactionResponseDTO transactionResponseDTOB = new TransactionResponseDTO();
    transactionResponseDTOB.setDate(new Date(172800000));
    transactionResponseDTOSWithoutId.add(transactionResponseDTOB);

    TransactionResponseDTO transactionResponseDTOC = new TransactionResponseDTO();
    transactionResponseDTOC.setDate(new Date(86400000));
    transactionResponseDTOSWithoutId.add(transactionResponseDTOC);

    when(transactionService.findAll()).thenReturn(transactionResponseDTOSWithoutId);

    List<TransactionResponseDTO> result = transactionController.retrieveAllTransaction(null);
    verify(transactionService, never()).findByAccountId(anyLong());
    verify(transactionService, times(1)).findAll();

    assertThat(result)
        .hasSize(3)
        .isSortedAccordingTo(new TransactionSortByDate())
        .isEqualTo(transactionResponseDTOSWithoutId);
  }

  @Test
  @DisplayName("Test retrieving list of all transactions")
  void retrieveAllTransactionWithAccountIdTest() {
    List<TransactionResponseDTO> transactionResponseDTOSWithoutId = new ArrayList<>();

    TransactionResponseDTO transactionResponseDTOA = new TransactionResponseDTO();
    transactionResponseDTOA.setDate(new Date(0));
    transactionResponseDTOSWithoutId.add(transactionResponseDTOA);

    TransactionResponseDTO transactionResponseDTOB = new TransactionResponseDTO();
    transactionResponseDTOB.setDate(new Date(172800000));
    transactionResponseDTOSWithoutId.add(transactionResponseDTOB);

    TransactionResponseDTO transactionResponseDTOC = new TransactionResponseDTO();
    transactionResponseDTOC.setDate(new Date(86400000));
    transactionResponseDTOSWithoutId.add(transactionResponseDTOC);

    when(transactionService.findByAccountId(any())).thenReturn(transactionResponseDTOSWithoutId);

    List<TransactionResponseDTO> result = transactionController.retrieveAllTransaction(1L);
    verify(transactionService, times(1)).findByAccountId(anyLong());
    verify(transactionService, never()).findAll();

    assertThat(result)
        .hasSize(3)
        .isSortedAccordingTo(new TransactionSortByDate())
        .isEqualTo(transactionResponseDTOSWithoutId);
  }

}