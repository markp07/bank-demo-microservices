package xyz.markpost.transactions.util;

import java.util.Comparator;
import xyz.markpost.transactions.dto.TransactionResponseDTO;

/**
 * Comparator class to sort transactions by date
 */
public class TransactionSortByDate implements Comparator<TransactionResponseDTO> {

  public int compare(TransactionResponseDTO p, TransactionResponseDTO q) {
    if (p.getDate().before(q.getDate())) {
      return -1;
    } else if (p.getDate().after(q.getDate())) {
      return 1;
    } else {
      return 0;
    }
  }
}
