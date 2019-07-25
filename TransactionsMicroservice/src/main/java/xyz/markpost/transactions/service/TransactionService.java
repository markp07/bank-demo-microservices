package xyz.markpost.transactions.service;

import java.util.List;
import xyz.markpost.transactions.dto.TransactionRequestDTO;
import xyz.markpost.transactions.dto.TransactionResponseDTO;

/**
 *
 */
public interface TransactionService {

  /**
   *
   * @param transactionRequestDTO
   * @return
   */
  TransactionResponseDTO create(TransactionRequestDTO transactionRequestDTO);

  /**
   *
   * @param transactionId
   * @return
   */
  List<TransactionResponseDTO> findById(Long transactionId);

  /**
   *
   * @param accountId
   * @return
   */
  List<TransactionResponseDTO> findByAccountId(Long accountId);

  /**
   *
   * @return
   */
  List<TransactionResponseDTO> findAll();

}
