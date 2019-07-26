package xyz.markpost.transactions.model;

/**
 * Enum of transaction types
 */
public enum TransactionStatus {
  NEW("NEW"),
  PENDING("PENDING"),
  COMPLETED("COMPLETED"),
  FAILED("FAILED"),
  CANCELLED("CANCELLED");

  private final String text;

  TransactionStatus(final String text) {
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
