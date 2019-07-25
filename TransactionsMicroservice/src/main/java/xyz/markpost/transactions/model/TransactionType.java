package xyz.markpost.transactions.model;

/**
 * Enum of transaction types
 */
public enum TransactionType {
  DEPOSIT("DEPOSIT"),
  WITHDRAWAL("WITHDRAWEL");

  private final String text;

  TransactionType(final String text) {
    this.text = text;
  }

  /* (non-Javadoc)
   * @see java.lang.Enum#toString()
   */
  @Override
  public String toString() {
    return text;
  }
}
